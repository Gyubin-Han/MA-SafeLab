package com.example.myapplication909;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity6 extends AppCompatActivity{
    public void onCreate(Bundle savedInstanceState){
        setTitle("Safe Lab");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_before);
        Log.i("SafeLab","EmergencyActivity(긴급상황 선택 화면) 실행");
        ((Button)findViewById(R.id.BtnEmergencyOn)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                // 긴급상황 발동 버튼 눌림.
                Intent intent=new Intent(MainActivity6.this,EmergencyInActivity.class);
                startActivity(intent);
            }
        });
    }
}