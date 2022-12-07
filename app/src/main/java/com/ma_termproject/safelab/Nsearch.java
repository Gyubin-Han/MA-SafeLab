package com.example.myapplication909;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Nsearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Safe Lab");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nsearch);

        Button button1=findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Nsearch.this,Namesearch.class);
                startActivity(intent);
            }
        });
    }
}