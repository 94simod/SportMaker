package com.comli.sportmaker.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Simone on 09/08/2016.
 */
public class SingleEventRequest extends StringRequest {
    private static final String url="http://sportmaker.altervista.org/php/ReadEvent.php";
    private Map<String, String> params;

    public SingleEventRequest(int cod_event, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        params=new HashMap<>();
        params.put("nameFile", String.valueOf(cod_event));
    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
