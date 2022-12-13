package com.example.myapplication909;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    //서버 URL 설정
    final static private String URL = "http://172.30.1.64/register.php"; //"http://server.safelab.p-e.kr:7777/register.php"
    private Map<String, String> map;

    public RegisterRequest(String id, String pw, String name, String email, String depart, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("id", id);
        map.put("pw", pw);
        map.put("name", name);
        map.put("email", email);
        map.put("depart", depart);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
