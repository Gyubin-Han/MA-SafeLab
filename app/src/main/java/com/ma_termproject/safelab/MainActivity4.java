package com.ma_termproject.safelab;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity4 extends AppCompatActivity{



    private static String TAG = "MainActivity4";

    Button load, save, delete;

    EditText inputText;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        setTitle("화학물질 대여/반납");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main4);



        load= (Button) findViewById(R.id.load);

        save = (Button) findViewById(R.id.save);

        delete = (Button) findViewById(R.id.delete);

        inputText = (EditText) findViewById(R.id.inputText);



        load.setOnClickListener(listener);

        save.setOnClickListener(listener);

        delete.setOnClickListener(listener);

    }



    View.OnClickListener listener = new View.OnClickListener() {

        @Override

        public void onClick(View view) {

            switch (view.getId()){

                case R.id.load:

                    Log.i("TAG", "load 진행");

                    FileInputStream fis = null;

                    try{

                        fis = openFileInput("memo.txt");

                        byte[] data = new byte[fis.available()];

                        while( fis.read(data) != -1){

                        }



                        inputText.setText(new String(data));

                        Toast.makeText(MainActivity4.this, "load 완료", Toast.LENGTH_SHORT).show();

                    }catch(Exception e){

                        e.printStackTrace();

                    }finally{

                        try{ if(fis != null) fis.close(); }catch(Exception e){e.printStackTrace();}

                    }

                    break;

                case R.id.save:

                    Log.i("TAG", "save 진행");

                    FileOutputStream fos = null;

                    try{

                        fos = openFileOutput("memo.txt", Context.MODE_PRIVATE);

                        String out = inputText.getText().toString();

                        fos.write(out.getBytes());

                        Toast.makeText(MainActivity4.this, "save 완료", Toast.LENGTH_SHORT).show();

                    }catch(Exception e){

                        e.printStackTrace();

                    }finally{

                        try{ if(fos != null) fos.close(); }catch(Exception e){e.printStackTrace();}

                    }

                    break;

                case R.id.delete:

                    Log.i("TAG", "delete 진행");

                    boolean b = deleteFile("memo.txt");

                    if(b){

                        Toast.makeText(MainActivity4.this, "delete 완료", Toast.LENGTH_SHORT).show();

                    }else{

                        Toast.makeText(MainActivity4.this, "delete 실패", Toast.LENGTH_SHORT).show();

                    }

                    break;

            }

        }

    };

}