package com.comli.sportmaker.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mario on 28/06/2016.
 */
public class NewEventRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://sportmaker.altervista.org/php/NewEvent4.php"; //dominio
    private Map<String, String> params;

    public NewEventRequest(String username, String date, String sport, String location, int n_players, int id_user, double prezzo, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("date", date);
        params.put("sport", sport);
        params.put("location", location);
        params.put("n_players", n_players+"");
        params.put("id_utente", id_user+"");
        params.put("costo", prezzo+"");
    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
