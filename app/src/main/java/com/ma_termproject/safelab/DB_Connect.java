package com.ma_termproject.safelab;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class DB_Connect extends Thread {
    // NEW CODE

    Handler handler;

    // 데이터베이스를 구분하는데 사용되는 상수 정의
    public static final String TAG_DB_LOGIN="login"; // 로그인 DB (사용자 DB에서 아이디와 계정 진위여부 값만 받아옴.)
    public static final String TAG_DB_CHEM="chem"; // 화학물질 DB
    public static final String TAG_DB_USER="user"; // 사용자 DB
    public static final String TAG_DB_DEPT="depart"; // 부서 DB
    public static final String TAG_DB_EMERGENCY="emergency"; // 긴급모드 DB
    public static final String TAG_DB_CHEM_MANAGE="chem_manage"; // 화학물질 관리 DB
    public static final String TAG_DB_CHEM_USELOG="chem_uselog"; // 화학물질 사용 기록 DB
    public static final String TAG_DB_SENS="sensitive_info";
    // 데이터 속성명을 태그 형태로 미리 정의
    private static final String TAG_RESULTS="result";
    // 로그인(사용자) DB
    // TAG_LOGIN_PW를 제외한 모든 속성은 사용자 테이블에서 사용됨.
    public static final String TAG_LOGIN_ID="id";
    public static final String TAG_LOGIN_PW="pw"; // 로그인하는 경우에만 사용되는 데이터
    public static final String TAG_LOGIN_NAME="name";
    public static final String TAG_LOGIN_DEPT="depart";
    public static final String TAG_LOGIN_EMAIL="email";
    // 부서 DB
    public static final String TAG_DEPT_NO="dept_no";
    public static final String TAG_DEPT_NAME="dept_name";
    public static final String TAG_DEPT_MASTER="dept_master";
    // 화학물질 DB
    public static final String TAG_CHEM_CASNO="chem_casNo";
    public static final String TAG_CHEM_ID="chem_id";
    public static final String TAG_CHEM_NAMEKOR="chem_nameKor";
    public static final String TAG_CHEM_ENNO="chem_enNo";
    public static final String TAG_CHEM_KENO="chem_keNo";
    public static final String TAG_CHEM_UNNO="chem_unNo";
    public static final String TAG_CHEM_LEVEL="chem_level";
    // 화학물질 관리 DB
    public static final String TAG_CHEM_MAN_NO="chem_man_no";
    public static final String TAG_CHEM_MAN_CHEMNO="chem_man_chemno";
    public static final String TAG_CHEM_MAN_DEPT="chem_man_deptno";
    public static final String TAG_CHEM_MAN_CHARGE="chem_man_charge";
    public static final String TAG_CHEM_MAN_PURCHASE="chem_man_purchase";
    public static final String TAG_CHEM_MAN_OPEN="chem_man_open";
    public static final String TAG_CHEM_MAN_LASTDAY="chem_man_lastday";
    // 화학물질 사용 기록 테이블
    public static final String TAG_CHEM_USELOG_NO="chem_uselog_no";
    public static final String TAG_CHEM_USELOG_USEDT="chem_uselog_usedt";
    public static final String TAG_CHEM_USELOG_RETDT="chem_uselog_retdt";
    public static final String TAG_CHEM_USELOG_CHEMNO="chem_uselog_chemno";
    public static final String TAG_CHEM_USELOG_REQ="chem_uselog_req";
    public static final String TAG_CHEM_USELOG_REQDEPT="chem_uselog_reqdept";
    public static final String TAG_CHEM_USELOG_APPROVER="chem_uselog_approver";
    public static final String TAG_CHEM_USELOG_APPROVDEPT="chem_uselog_approverdept";
    // 사용자 민감정보 테이블
    public static final String TAG_SENS_USER="sens_user";
    public static final String TAG_SENS_ON="sens_on";
    public static final String TAG_SENS_GENDER="sens_gender";
    public static final String TAG_SENS_BOOLD="sens_blood";
    public static final String TAG_SENS_HEIGHT="sens_height";
    public static final String TAG_SENS_WEIGHT="sens_weight";
    public static final String TAG_SENS_ILLINESS="sens_illness";
    // 응급상황 테이블
    public static final String TAG_EMER_NO="emer_no";
    public static final String TAG_EMER_DT="emer_dt";
    public static final String TAG_EMER_USER="emer_user";
    public static final String TAG_EMER_DEPT="emer_dept";
    public static final String TAG_EMER_NOW="emer_ing";

    // DB에 접근하는 모드를 정의
    private boolean db_mode;
    public static final boolean READ=true;
    public static final boolean WRITE=false;
    private JSONObject send_json=null;

    private String db=null;
    // 데이터베이스를 구분하는데 사용되는 상수 정의
    public static final short DB_LOGIN=1; // 로그인 DB (사용자 DB에서 아이디와 계정 진위여부 값만 받아옴.)
    public static final short DB_CHEM=2; // 화학물질 DB
    public static final short DB_USER=3; // 사용자 DB
    public static final short DB_DEPT=4; // 부서 DB
    public static final short DB_EMERGENCY=5; // 긴급모드 DB
    public static final short DB_CHEM_MANAGE=6; // 화학물질 관리 DB
    public static final short DB_CHEM_USELOG=7; // 화학물질 사용 기록 DB
    public static final short DB_SENS=8; // 사용자 민감정보 DB

    private String jsonString;
    private JSONArray datas=null;
    private ArrayList<HashMap<String,String>> dataArray;

    private String host;
//    private int port;

    private int conn_time=5000;
    private int read_time=3000;

    DB_Connect(String host, short db, boolean mode, Handler handler){
        this.host=host;
        switch(db){
            case DB_LOGIN:
                this.db="login";
                break;
            case DB_CHEM:
                this.db="chem";
                break;
            case DB_USER:
                this.db="user";
                break;
            case DB_DEPT:
                this.db="depart";
                break;
            case DB_EMERGENCY:
                this.db="emergency";
                break;
            case DB_CHEM_MANAGE:
                this.db="chem_manage";
                break;
            case DB_CHEM_USELOG:
                this.db="chem_uselog";
                break;
            case DB_SENS:
                this.db="sensitive_info";
                break;
            default:
        }
        this.db_mode=mode;
        this.handler=handler;
    }
    DB_Connect(short db,boolean mode,Handler handler){
        this("http://localhost/",db,mode,handler);
    }

    // DB에 데이터 전송 모드로 사용 시, 보낼 데이터를 설정하는 메소드
    // 스레드를 실행하기 전에 이 메소드를 사용하여 데이터 설정부터 해야 함.
    void setSendData(JSONObject write_data){
        send_json=write_data;
    }

    // 현 객체에 있는 데이터 배열을 반환하는 메소드
    ArrayList<HashMap<String,String>> getData(){
        return dataArray;
    }

    // 현 객체 내 데이터 배열에 있는 별 건의 데이터를 찾아서 반환함.
    // 매개변수 - attr : 찾을 데이터의 속성 , data : 찾을 데이터 값
    HashMap<String,String> getData(String attr,String data){
        int size=0;
        size=dataArray.size();
        for(int i=0;i<size;i++){
            if(dataArray.get(i).get(attr).equals(data)){
                return dataArray.get(i);
            }

//            switch(db){
//                case DB_LOGIN:
//                case DB_CHEM:
//                case DB_USER:
//                case DB_DEPT:
//                case DB_EMERGENCY:
//                case DB_CHEM_MANAGE:
//                default:
//                    return null;
//            }
        }
        return null;
    }

    // HashMap 클래스의 데이터를 JSON 객체로 변환하여 반환하는 메소드
    JSONObject makeJson(HashMap<String,String> data){
        JSONObject jobj=new JSONObject();
        try {
            // 각각의 DB 모드에 알맞게 변환
            switch (db) {
                case TAG_DB_LOGIN:
                    jobj.put(TAG_LOGIN_ID, data.get(TAG_LOGIN_ID));
                    jobj.put(TAG_LOGIN_PW,data.get(TAG_LOGIN_PW));
                    break;
                case TAG_DB_CHEM:
                    jobj.put(TAG_CHEM_CASNO,data.get(TAG_CHEM_CASNO));
                    jobj.put(TAG_CHEM_ID,data.get(TAG_CHEM_ID));
                    jobj.put(TAG_CHEM_NAMEKOR,data.get(TAG_CHEM_NAMEKOR));
                    jobj.put(TAG_CHEM_ENNO,data.get(TAG_CHEM_ENNO));
                    jobj.put(TAG_CHEM_KENO,data.get(TAG_CHEM_KENO));
                    jobj.put(TAG_CHEM_UNNO,data.get(TAG_CHEM_UNNO));
                    jobj.put(TAG_CHEM_LEVEL,data.get(TAG_CHEM_LEVEL));
                    break;
                case TAG_DB_USER:
                    jobj.put(TAG_LOGIN_ID, data.get(TAG_LOGIN_ID));
                    jobj.put(TAG_LOGIN_NAME,data.get(TAG_LOGIN_NAME));
                    jobj.put(TAG_LOGIN_DEPT,data.get(TAG_LOGIN_DEPT));
                    jobj.put(TAG_LOGIN_EMAIL,data.get(TAG_LOGIN_EMAIL));
                    break;
                case TAG_DB_DEPT:
                    jobj.put(TAG_DEPT_NO,data.get(TAG_DEPT_NO));
                    jobj.put(TAG_DEPT_NAME,data.get(TAG_DEPT_NAME));
                    jobj.put(TAG_DEPT_MASTER,data.get(TAG_DEPT_MASTER));
                    break;
                case TAG_DB_CHEM_MANAGE:
                    jobj.put(TAG_CHEM_MAN_NO,data.get(TAG_CHEM_MAN_NO));
                    jobj.put(TAG_CHEM_MAN_CHEMNO,data.get(TAG_CHEM_MAN_CHEMNO));
                    jobj.put(TAG_CHEM_MAN_DEPT,data.get(TAG_CHEM_MAN_DEPT));
                    jobj.put(TAG_CHEM_MAN_CHARGE,data.get(TAG_CHEM_MAN_CHARGE));
                    jobj.put(TAG_CHEM_MAN_PURCHASE,data.get(TAG_CHEM_MAN_PURCHASE));
                    jobj.put(TAG_CHEM_MAN_OPEN,data.get(TAG_CHEM_MAN_OPEN));
                    jobj.put(TAG_CHEM_MAN_LASTDAY,data.get(TAG_CHEM_MAN_LASTDAY));
                    break;
                case TAG_DB_CHEM_USELOG:
                    jobj.put(TAG_CHEM_USELOG_NO,data.get(TAG_CHEM_USELOG_NO));
                    jobj.put(TAG_CHEM_USELOG_USEDT,data.get(TAG_CHEM_USELOG_USEDT));
                    jobj.put(TAG_CHEM_USELOG_RETDT,data.get(TAG_CHEM_USELOG_RETDT));
                    jobj.put(TAG_CHEM_USELOG_CHEMNO,data.get(TAG_CHEM_USELOG_CHEMNO));
                    jobj.put(TAG_CHEM_USELOG_REQ,data.get(TAG_CHEM_USELOG_REQ));
                    jobj.put(TAG_CHEM_USELOG_REQDEPT,data.get(TAG_CHEM_USELOG_REQDEPT));
                    jobj.put(TAG_CHEM_USELOG_APPROVER,data.get(TAG_CHEM_USELOG_APPROVER));
                    jobj.put(TAG_CHEM_USELOG_APPROVDEPT,data.get(TAG_CHEM_USELOG_APPROVDEPT));
                    break;
                case TAG_DB_SENS:
                    jobj.put(TAG_SENS_USER,data.get(TAG_SENS_USER));
                    jobj.put(TAG_SENS_ON,data.get(TAG_SENS_ON));
                    jobj.put(TAG_SENS_GENDER,data.get(TAG_SENS_GENDER));
                    jobj.put(TAG_SENS_BOOLD,data.get(TAG_SENS_BOOLD));
                    jobj.put(TAG_SENS_HEIGHT,data.get(TAG_SENS_HEIGHT));
                    jobj.put(TAG_SENS_WEIGHT,data.get(TAG_SENS_WEIGHT));
                    jobj.put(TAG_SENS_ILLINESS,data.get(TAG_SENS_ILLINESS));
                    break;
                case TAG_DB_EMERGENCY:
                    jobj.put(TAG_EMER_NO,data.get(TAG_EMER_NO));
                    jobj.put(TAG_EMER_DT,data.get(TAG_EMER_DT));
                    jobj.put(TAG_EMER_USER,data.get(TAG_EMER_USER));
                    jobj.put(TAG_EMER_DEPT,data.get(TAG_EMER_DEPT));
                    jobj.put(TAG_EMER_NOW,data.get(TAG_EMER_NOW));
                    break;
                default:
                    Log.e("SafeLab","JSON 오류 : 사전 정의된 형태의 데이터가 아닙니다.");
            }
        }catch(JSONException e){
            Log.e("SafeLab","JSON 오류 : "+e);
        }
        return jobj;
    }

    // 수신받은 JSON 데이터를 설정한 DB 구조에 맞게 객체 배열로 변환하는 메소드
    private void jsonProcess(){
        try{
            // JSON 배열 내에 있는 객체 단위로 데이터를 받기 위한 변수
            JSONObject jsonObj=new JSONObject(jsonString);
            datas=jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0;i<datas.length();i++){
                JSONObject jsobj=datas.getJSONObject(i);
                // 해쉬맵을 통해 데이터를 키-값 형태로 저장
                HashMap<String,String> data_map=new HashMap<String,String>();

                switch (db) {
                    case TAG_DB_LOGIN:
                        data_map.put(TAG_LOGIN_ID, jsobj.getString(TAG_LOGIN_ID));
                        data_map.put(TAG_LOGIN_PW, jsobj.getString(TAG_LOGIN_PW));
                        break;
                    case TAG_DB_CHEM:
                        data_map.put(TAG_CHEM_CASNO, jsobj.getString(TAG_CHEM_CASNO));
                        data_map.put(TAG_CHEM_ID, jsobj.getString(TAG_CHEM_ID));
                        data_map.put(TAG_CHEM_NAMEKOR, jsobj.getString(TAG_CHEM_NAMEKOR));
                        data_map.put(TAG_CHEM_ENNO, jsobj.getString(TAG_CHEM_ENNO));
                        data_map.put(TAG_CHEM_KENO, jsobj.getString(TAG_CHEM_KENO));
                        data_map.put(TAG_CHEM_UNNO, jsobj.getString(TAG_CHEM_UNNO));
                        data_map.put(TAG_CHEM_LEVEL, jsobj.getString(TAG_CHEM_LEVEL));
                        break;
                    case TAG_DB_USER:
                        data_map.put(TAG_LOGIN_ID, jsobj.getString(TAG_LOGIN_ID));
                        data_map.put(TAG_LOGIN_NAME, jsobj.getString(TAG_LOGIN_NAME));
                        data_map.put(TAG_LOGIN_DEPT, jsobj.getString(TAG_LOGIN_DEPT));
                        data_map.put(TAG_LOGIN_EMAIL, jsobj.getString(TAG_LOGIN_EMAIL));
                        break;
                    case TAG_DB_DEPT:
                        data_map.put(TAG_DEPT_NO, jsobj.getString(TAG_DEPT_NO));
                        data_map.put(TAG_DEPT_NAME, jsobj.getString(TAG_DEPT_NAME));
                        data_map.put(TAG_DEPT_MASTER, jsobj.getString(TAG_DEPT_MASTER));
                        break;
                    case TAG_DB_CHEM_MANAGE:
                        data_map.put(TAG_CHEM_MAN_NO, jsobj.getString(TAG_CHEM_MAN_NO));
                        data_map.put(TAG_CHEM_MAN_CHEMNO, jsobj.getString(TAG_CHEM_MAN_CHEMNO));
                        data_map.put(TAG_CHEM_MAN_DEPT, jsobj.getString(TAG_CHEM_MAN_DEPT));
                        data_map.put(TAG_CHEM_MAN_CHARGE, jsobj.getString(TAG_CHEM_MAN_CHARGE));
                        data_map.put(TAG_CHEM_MAN_PURCHASE, jsobj.getString(TAG_CHEM_MAN_PURCHASE));
                        data_map.put(TAG_CHEM_MAN_OPEN, jsobj.getString(TAG_CHEM_MAN_OPEN));
                        data_map.put(TAG_CHEM_MAN_LASTDAY, jsobj.getString(TAG_CHEM_MAN_LASTDAY));
                        break;
                    case TAG_DB_CHEM_USELOG:
                        data_map.put(TAG_CHEM_USELOG_NO, jsobj.getString(TAG_CHEM_USELOG_NO));
                        data_map.put(TAG_CHEM_USELOG_USEDT, jsobj.getString(TAG_CHEM_USELOG_USEDT));
                        data_map.put(TAG_CHEM_USELOG_RETDT, jsobj.getString(TAG_CHEM_USELOG_RETDT));
                        data_map.put(TAG_CHEM_USELOG_CHEMNO, jsobj.getString(TAG_CHEM_USELOG_CHEMNO));
                        data_map.put(TAG_CHEM_USELOG_REQ, jsobj.getString(TAG_CHEM_USELOG_REQ));
                        data_map.put(TAG_CHEM_USELOG_REQDEPT, jsobj.getString(TAG_CHEM_USELOG_REQDEPT));
                        data_map.put(TAG_CHEM_USELOG_APPROVER, jsobj.getString(TAG_CHEM_USELOG_APPROVER));
                        data_map.put(TAG_CHEM_USELOG_APPROVDEPT, jsobj.getString(TAG_CHEM_USELOG_APPROVDEPT));
                        break;
                    case TAG_DB_SENS:
                        data_map.put(TAG_SENS_USER, jsobj.getString(TAG_SENS_USER));
                        data_map.put(TAG_SENS_ON, jsobj.getString(TAG_SENS_ON));
                        data_map.put(TAG_SENS_GENDER, jsobj.getString(TAG_SENS_GENDER));
                        data_map.put(TAG_SENS_BOOLD, jsobj.getString(TAG_SENS_BOOLD));
                        data_map.put(TAG_SENS_HEIGHT, jsobj.getString(TAG_SENS_HEIGHT));
                        data_map.put(TAG_SENS_WEIGHT, jsobj.getString(TAG_SENS_WEIGHT));
                        data_map.put(TAG_SENS_ILLINESS, jsobj.getString(TAG_SENS_ILLINESS));
                        break;
                    case TAG_DB_EMERGENCY:
                        data_map.put(TAG_EMER_NO, jsobj.getString(TAG_EMER_NO));
                        data_map.put(TAG_EMER_DT, jsobj.getString(TAG_EMER_DT));
                        data_map.put(TAG_EMER_USER, jsobj.getString(TAG_EMER_USER));
                        data_map.put(TAG_EMER_DEPT, jsobj.getString(TAG_EMER_DEPT));
                        data_map.put(TAG_EMER_NOW, jsobj.getString(TAG_EMER_NOW));
                        break;
                }

                dataArray.add(data_map);
            }

            return;
        }catch(JSONException e){
            e.printStackTrace();
            Log.e("SafeLab","데이터 처리 오류 | "+e);
        }
    }

    // 데이터를 불러오는 역할을 수행함.
    void loadData(){
        BufferedReader bufferedReader = null;
        jsonString=null;
        dataArray=new ArrayList<HashMap<String,String>>();

        try {
            URL url = new URL(host+"/"+db+".php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            StringBuilder sb = new StringBuilder();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String json;
            while ((json = bufferedReader.readLine()) != null) {
                sb.append(json + "\n");
            }

            jsonString=sb.toString().trim(); // 앞 뒤에 붙어있는 불필요한 문자는 제거한 뒤 문자열 저장

            if(bufferedReader!=null) bufferedReader.close();
        } catch (Exception e) {
            Log.e("SafeLab","DB 오류 | "+e);
            return;
        }
        if(jsonString==null){
            Log.e("SafeLab","DB에서 불러온 데이터가 없습니다. (데이터 미존재 또는 데이터베이스 불러오기 실패)");
            return;
        }
        jsonProcess();
    }

    // DB 서버로 데이터를 발신한 뒤, 응답을 수신하는 객체
    boolean sendData() {
        if(send_json==null){
            Log.e("SafeLab","DB 데이터 전송 오류 : 보낼 데이터가 없습니다. 먼저 보낼 데이터를 설정한 뒤, 전송을 시도해야 합니다.");
            return false;
        }

        try {
            JSONArray dataArr=new JSONArray();
            URL url = new URL(host+"/"+db+".php");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(conn_time);
            urlConnection.setReadTimeout(read_time);
            urlConnection.setRequestProperty("Content-Type", "application/json;");
            urlConnection.setDoOutput(true);
            urlConnection.setInstanceFollowRedirects(true);

            OutputStream os = urlConnection.getOutputStream();

            BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            bfw.write(send_json.toString());
            bfw.flush();

            StringBuilder sb=new StringBuilder();
            String readline=null;
            BufferedReader bfr=null;

            // 정상적으로 응답을 수신한 경우
            if(urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                bfr=new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
                while((readline=bfr.readLine())!=null)
                    sb.append(readline).append("\n");
                jsonString=sb.toString().trim(); // 앞 뒤에 붙어있는 불필요한 문자는 제거한 뒤 문자열 저장
                
                // 수신한 응답이 없는 경우 - 서버에 문제가 있음.
                if(jsonString==null){
                    Log.e("SafeLab","DB 통신 | 서버로부터 응답이 없습니다.");
                    return false;
                }

                // 수신받은 JSON 데이터를 처리함.
                jsonProcess();
            }else{
//                sb.append("\"code\" : \""+urlConnection.getResponseCode()+"\"");
                Log.w("SafeLab","통신 결과 코드 : "+urlConnection.getResponseCode());
//                sb.append("\"message\" : \""+urlConnection.getResponseMessage()+"\"");
                Log.w("SafeLab","통신 응답 : "+urlConnection.getResponseMessage());
                return false;
            }

            if(bfw!=null) bfw.close();
            if(os!=null) os.close();
            if(bfr!=null) bfr.close();
//        }catch (JSONException e){
//            Log.e("SafeLab","데이터 생성 오류 | "+e);
//            return false;
        }catch (Exception e){
            Log.e("SafeLab","데이터 전송 오류 | "+e);
            return false;
        }
        return true;
    }

    public void run(){
        // 데이터 발신 모드로 객체 생성 시
        if(db_mode){
            loadData();
            Message msg=new Message();
            msg.obj="";
            handler.sendMessage(msg);
        }
        // 데이터 수신 모드로 객체 생성 시
        else{
            sendData();
            Message msg=new Message();
            msg.obj="";
            handler.sendMessage(msg);
        }
//        else sendData();
//        if(db_mode) {
//            loadData();
//            // 불러온 데이터가 없는 경우, 오류 발생
//            if (jsonString == null) {
//                Log.e("SafeLab", "DB 오류 | DB에서 불러온 데이터가 없습니다.");
//                return;
//            }
//        }else{
//            sendData();
//        }
    }
}
