package com.ma_termproject.safelab;

import android.telephony.SmsManager;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class EmergencyInActivity extends Activity {
    public static void sendSMS(String dest, String msg){
        SmsManager smsManager=SmsManager.getDefault();
        smsManager.sendTextMessage(dest,null,msg,null,null);
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        Log.i("SafeLab","긴급상황 발동됨!");
    }
}