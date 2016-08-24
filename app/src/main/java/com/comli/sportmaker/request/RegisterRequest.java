package com.comli.sportmaker.request;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mario on 22/06/2016.
 */
public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL="http://sportmaker.altervista.org/php/Register3.php"; //dominio
    private Map<String, String> params;


    public RegisterRequest(String email, String username, int age, String password, String name, String surname, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params=new HashMap<>();
        params.put("email", email);
        params.put("username",username);
        params.put("password",password);
        params.put("age",age+"");
        params.put("name", name);
        params.put("surname", surname);

    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }

}
