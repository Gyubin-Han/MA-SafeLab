
package com.ma_termproject.safelab;

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

public class MainActivity8 extends AppCompatActivity {

    private EditText et_id, et_pw, et_name, et_email, et_depart;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);

        et_id = findViewById(R.id.et_id);
        et_pw = findViewById(R.id.et_pw);
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_depart = findViewById(R.id.et_depart);

        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = et_id.getText().toString();
                String pw = et_pw.getText().toString();
                String name = et_name.getText().toString();
                String email = et_email.getText().toString();
                String depart = et_depart.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity8.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(id, pw, name, email, depart, responseListener);

                RequestQueue queue = Volley.newRequestQueue(MainActivity8.this);
                queue.add(registerRequest);
            }
        });
    }
}
