package com.ma_termproject.safelab;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class EmergencyInActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        Log.i("SafeLab","긴급상황 발동됨!");
    }
}
