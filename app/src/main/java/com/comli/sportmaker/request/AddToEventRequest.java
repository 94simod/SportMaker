package com.comli.sportmaker.request;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Simone on 11/08/2016.
 */
public class AddToEventRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL="http://sportmaker.altervista.org/php/AddToEvent.php"; //dominio
    private Map<String, String> params;


    public AddToEventRequest(String userName, String fileName, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params=new HashMap<>();
        params.put("username", userName);
        params.put("fileName", fileName);

    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }




}
