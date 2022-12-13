package com.example.myapplication909;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity5 extends AppCompatActivity {

    private EditText nowid, nowpw, newpw;
    private Button btn_update, btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Safe Lab");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        nowid = findViewById(R.id.nowid);
        nowpw = findViewById(R.id.nowpw);
        newpw = findViewById(R.id.newpw);


        btn_update = findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_id = nowid.getText().toString();
                String current_pw = nowpw.getText().toString();
                String new_pw = newpw.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(), "비밀번호 변경이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity5.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "비밀번호 변경이 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                com.example.myapplication909.UpdateRequest updateRequest = new com.example.myapplication909.UpdateRequest(user_id, current_pw, new_pw, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity5.this);
                queue.add(updateRequest);
            }
        });
        btn_delete = findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_id = nowid.getText().toString();
                String current_pw = nowpw.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(), "삭제 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity5.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "삭제 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                DeleteRequest deleteRequest = new DeleteRequest(user_id, current_pw, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity5.this);
                queue.add(deleteRequest);
            }
        });
    }
}
