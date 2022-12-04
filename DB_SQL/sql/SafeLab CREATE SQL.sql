###################################################
#
# SafeLab DB CREATE SQL Query
# 이거 실행하기 전에 꼭 데이터베이스에 있던 데이터들은 백업 필수!!!
# 꼭 백업해야 함!!! 책임 못짐!
#
# Ver. 1.4   |   MySQL, MariaDB에 최적화됨.
#
# - 사용 방법1 : 필요한 부분을 선택하여 복사 후,
#               mysql 터미널(명령 프롬프트) 창에 붙여넣기 후 Enter 키
#              (로그인 후 'mysql>' 또는 'mariaDB[none]>' 라고 떠있어야 함.)
#
# - 사용 방법2 : mysql 클라이언트 프로그램이 있는 디렉토리에서
#               아래와 같은 명령어 입력
#              명령어 : mysql -hlocalhost -u<유저명> -p<비밀번호> < .\"SafeLab CREATE SQL.sql"
#
# - Made by : 한규빈 (Gyubin Han)
#
###################################################

##### 데이터베이스 새로 정의 #####
# 기존 DB 제거
# DB에 데이터가 있었다면 데이터 날라가니 주의!
# DB를 새로 만들 필요가 없으면 이 구문은 실행하지 말 것!
DROP DATABASE IF EXISTS `SafeLabDB`;

# 새로운 DB 생성
CREATE DATABASE SafeLabDB DEFAULT CHARACTER SET utf8;

# 새로운 사용자 생성
# 기본 비밀번호는 1234 임. (꼭 변경할 것)
CREATE USER IF NOT EXISTS safelab_user@localhost IDENTIFIED BY "1234";

# 사용자 권한 부여
USE mysql;
DELETE FROM db WHERE host='localhost' AND user='safelab_user';
INSERT INTO db VALUES('localhost','SafeLabDB','safelab_user','y','y','y','y','y','y','y','y','y','y','y','y','y','y','y','y','y','y','y');
FLUSH PRIVILEGES;





##### 데이터베이스 테이블 정의 #####
# 테이블을 정의하기 위해 DB 전환
USE SafeLabDB;

# 회원 테이블
CREATE TABLE `user`(
	`id` VARCHAR(20) NOT NULL,
	`pw` VARCHAR(50) NOT NULL,
	`name` VARCHAR(20) NOT NULL,
	`email` VARCHAR(100) NOT NULL,
	`depart` INT,
	PRIMARY KEY(id)
);



# 부서 테이블
CREATE TABLE `depart`(
	`dept_no` INT NOT NULL AUTO_INCREMENT,
	`dept_name` VARCHAR(25) NOT NULL,
	`dept_master` VARCHAR(20),
	PRIMARY KEY(dept_no),
	FOREIGN KEY(dept_master) REFERENCES user(id) ON DELETE SET NULL
);



# 화학물질 등급 테이블
#CREATE TABLE `chem_level`(
#	`chem_lv` INT(1) NOT NULL,
#	`chem_lv_`
#);



# 화학물질 테이블
CREATE TABLE `chem`(
	`chem_casNo` VARCHAR(20) NOT NULL,
	`chem_id` INT NOT NULL,
	`chem_nameKor` VARCHAR(500) NOT NULL,
	`chem_enNo` VARCHAR(20) NOT NULL,
	`chem_keNo` VARCHAR(20) NOT NULL,
	`chem_unNo` VARCHAR(20) NOT NULL,
	`chem_level` INT(1) NOT NULL DEFAULT 0,
	PRIMARY KEY(chem_id)
#	FOREIGN KEY(chem_level) REFERENCES chem_level(chem_lv) ON DELETE NO ACTION ON UPDATE CASCADE
);



# 화학물질 관리 테이블
CREATE TABLE `chem_manage`(
	`chem_man_no` INT NOT NULL AUTO_INCREMENT,
	`chem_man_chemno` INT NOT NULL,
	`chem_man_deptno` INT NOT NULL,
	`chem_man_charge` VARCHAR(20) NOT NULL, # 담당자
	`chem_man_purchase` DATETIME NOT NULL, # 구입일자
	`chem_man_open` DATETIME NOT NULL, # 물질 개봉일
	`chem_man_lastday` DATETIME NOT NULL, # 마지막 사용 일자
	PRIMARY KEY(chem_man_no),
	FOREIGN KEY(chem_man_chemno) REFERENCES chem(chem_id) ON DELETE NO ACTION ON UPDATE CASCADE,
	FOREIGN KEY(chem_man_deptno) REFERENCES depart(dept_no) ON DELETE NO ACTION ON UPDATE CASCADE,
	FOREIGN KEY(chem_man_charge) REFERENCES user(id) ON DELETE NO ACTION ON UPDATE CASCADE
);



# 화학물질 사용 기록 테이블 (현황 기능에서도 사용되는 테이블)
CREATE TABLE `chem_uselog`(
	`chem_uselog_no` INT NOT NULL AUTO_INCREMENT,
	`chem_uselog_usedt` DATETIME NOT NULL,
	`chem_uselog_retdt` DATETIME, # 반납일시 (Return DateTime)
	`chem_uselog_chemno` INT NOT NULL,
	`chem_uselog_req` VARCHAR(20) NOT NULL, # 요청자
	`chem_uselog_reqdept` INT NOT NULL, # 요청 부서
	`chem_uselog_approver` VARCHAR(20) NOT NULL, # 승인자
	`chem_uselog_approverdept` INT NOT NULL, # 승인부서
	PRIMARY KEY(chem_uselog_no),
	FOREIGN KEY(chem_uselog_chemno) REFERENCES chem_manage(chem_man_no),
	FOREIGN KEY(chem_uselog_req) REFERENCES user(id),
	FOREIGN KEY(chem_uselog_reqdept) REFERENCES depart(dept_no),
	FOREIGN KEY(chem_uselog_approver) REFERENCES user(id),
	FOREIGN KEY(chem_uselog_approverdept) REFERENCES depart(dept_no)
);



# 사용자 민감정보 테이블
CREATE TABLE `sensitive_info`(
	`sens_user` VARCHAR(20) NOT NULL,
	`sens_on` BOOLEAN NOT NULL,
	`sens_gender` BOOLEAN,
	`sens_blood` INT(2),
	`sens_height` FLOAT,
	`sens_weight` FLOAT,
	`sens_illness` TEXT, # 개인 질환
	PRIMARY KEY(sens_user),
	FOREIGN KEY(sens_user) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE
);



# 응급상황 테이블
CREATE TABLE `emergency`(
	`emer_no` INT NOT NULL AUTO_INCREMENT,
	`emer_dt` DATETIME NOT NULL,
	`emer_user` VARCHAR(20) NOT NULL,
	`emer_dept` INT NOT NULL,
	`emer_ing` BOOLEAN NOT NULL,
	PRIMARY KEY(emer_no),
	FOREIGN KEY(emer_user) REFERENCES user(id),
	FOREIGN KEY(emer_dept) REFERENCES depart(dept_no)
);

# Query OK ~~ 가 떠야 정상 처리 된 것임!
# 만약, 중간에 무슨 오류가 뜬다면 캡쳐 후, 알려줘야 함!

# 화학물질 데이터베이스(테이블)은 추후 구현 예정

################### Query END ###################
