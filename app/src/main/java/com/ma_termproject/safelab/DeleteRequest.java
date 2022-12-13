package com.example.myapplication909;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DeleteRequest extends StringRequest {

    //서버 URL 설정
    final static private String URL = "http://172.30.1.64/delete.php"; //"http://server.safelab.p-e.kr:7777/delete.php";
    private Map<String, String> map;

    public DeleteRequest(String user_id, String current_pw, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("user_id", user_id);
        map.put("current_pw", current_pw);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
