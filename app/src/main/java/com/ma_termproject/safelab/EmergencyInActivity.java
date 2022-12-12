package com.ma_termproject.safelab;

import android.media.MediaPlayer;
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

    MediaPlayer beep;
    MediaPlayer.OnCompletionListener completeListener=new MediaPlayer.OnCompletionListener(){
        public void onCompletion(MediaPlayer beep){
            Log.i("TEST","음악 반복 재생");
            beep=MediaPlayer.create(EmergencyInActivity.this,R.raw.beep1b);
            beep.start();
        }
    };

    // 사용자가 화면을 이탈하였을 때 동작되는 메소드
    protected void onUserLeaveHint(){
        beep.pause();
        super.onUserLeaveHint();
    }
    
    // 사용자가 화면에 복귀한 경우 동작되는 메소드
    protected void onResume(){
        beep.start();
        super.onResume();
    }
    
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        Log.i("SafeLab","긴급상황 발동됨!");

        Date date=new Date(System.currentTimeMillis());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Log.i("SafeLab","긴급상황 발동 시간 : "+sdf.format(date));

        // 알림음 반복 재생 부분
        beep=MediaPlayer.create(this,R.raw.beep1b);
        beep.setLooping(true);   // 무한으로 재생되도록 지정
//        beep.setOnCompletionListener(completeListener);   // 완료되었을 때, 다시 재생시키는 리스너 지정
        beep.start();   // 재생 시작

        Button btn_emer_off=(Button)findViewById(R.id.BtnEmergencyOff);
        btn_emer_off.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                beep.stop();
                beep.release();
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