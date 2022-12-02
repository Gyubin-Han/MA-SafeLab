package com.example.myapplication909;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity8 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("회원가입");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);

        Button btn1=findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity8.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

}