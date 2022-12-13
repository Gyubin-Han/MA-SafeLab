<?php

if(empty($_POST)){
	echo json_encode(array("result"=>array("s")
	return;
}

$con = mysqli_connect('localhost:3310','web_test1','1234','test_user1_db');

$result=mysqli_query($con,"SELECT * FROM user WHERE id='{$_POST['id']}'");

while($row=mysqli_fetch_assoc($result)){
	if(strcmp($_POST['id'],$row['id'])===0 && strcmp($_POST['pw'],$row['pw'])===0){
		echo "true";
		return;
	}
}

echo json_encode(array("result"=>$result));

mysqli_close($con);
?>