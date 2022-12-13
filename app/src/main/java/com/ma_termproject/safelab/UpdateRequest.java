
package com.example.test;


import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class UpdateRequest extends StringRequest {

    //서버 URL 설정
    final static private String URL = "http://server.safelab.p-e.kr:7777/update.php";
    private Map<String, String> map;

    public UpdateRequest(String user_id, String current_pw, String new_pw, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("user id", user_id);
        map.put("current_pw", current_pw);
        map.put("new_pw", new_pw);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
