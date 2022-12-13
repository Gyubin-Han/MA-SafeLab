package com.ma_termproject.safelab;


import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {


    final static private String URL = "http://server.safelab.p-e.kr:7777/login.php";
    private Map<String, String> map;

    public LoginRequest(String id, String pw, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("id", id);
        map.put("pw", pw);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
