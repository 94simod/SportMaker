package com.comli.sportmaker.request;


import com.android.volley.Response;
import com.android.volley.ResponseDelivery;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mario on 23/06/2016.
 */
public class LoginRequest extends StringRequest{
    private static final String LOGIN_REQUEST_URL="http://sportmaker.altervista.org/php/Login3.php"; //dominio
    private Map<String, String> params;


    public LoginRequest(String username, String password, Response.Listener<String> listener){
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params=new HashMap<>();
        params.put("username",username);
        params.put("password",password);

    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
