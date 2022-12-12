package com.ma_termproject.safelab;

import android.telephony.SmsManager;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Date;
import java.text.SimpleDateFormat;

public class EmergencyInActivity extends Activity {
    public static void sendSMS(String dest, String[] msg){
        SmsManager smsManager=SmsManager.getDefault();
        for(int i=0;i<msg.length;i++)
            smsManager.sendTextMessage(dest,null,msg[i],null,null);
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        Log.i("SafeLab","긴급상황 발동됨!");

        Date date=new Date(System.currentTimeMillis());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Log.i("SafeLab","긴급상황 발동 시간 : "+sdf.format(date));

        Button btn_emer_off=(Button)findViewById(R.id.BtnEmergencyOff);
        btn_emer_off.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });

        String message[]=new String[4];
        message[0]="(자동 발송)\n1] 화학물질과 관련하여 긴급한 상황이 발생하였습니다.";
        message[1]="2] 주소: 부산 강서구 녹산산단382로14번가길 1201-35, 공학1관 404호";
        message[2]="3] 일시: "+sdf.format(date);
        message[3]="4] 본 메시지는 자동발송 되었고, 세부 상황에 대해 이 번호로 전화 부탁드립니다.";
        sendSMS("999",message);

    }
}