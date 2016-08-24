package com.comli.sportmaker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.comli.sportmaker.request.AddToEventRequest;
import com.comli.sportmaker.request.RemoveToEventRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Simone on 08/08/2016.
 * Vado a prelevare dal server il file associato all'evento e carico le informazioni nell'interfaccia,
 * se mi unisco alla partita aggiorno il file nel server
 */

public class EventRequest extends Fragment {
    int giocatori;
    private int cod_event;
    String username = "";
    private String url;
    private ArrayList<String> array = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView Players;
    private SingleEventAdapter adapter;

    private ProgressDialog dialog;
    private ImageView ImageCamp;
    private TextView SportType;
    private TextView Location;
    private TextView Date;
    private TextView Hour;
    private TextView NPlayers;
    private TextView RemainingPost;
    private TextView Cost;
    private Button inEvent;
    private Button outEvent;
    private SwipeRefreshLayout swipeRefreshLayout;


    public EventRequest(){
    }


    public void setCodEvent(int cod_event){
        this.cod_event=cod_event;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_event, container, false);
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        //id_utente=sharedPref.getInt("id_utente", id_utente);
        username=sharedPref.getString("username", username);

        ImageCamp = (ImageView) rootView.findViewById(R.id.ImageCamp);
        SportType = (TextView) rootView.findViewById(R.id.SportType);
        Location = (TextView) rootView.findViewById(R.id.Location);
        Date = (TextView) rootView.findViewById(R.id.Date);
        Hour = (TextView) rootView.findViewById(R.id.Hour);
        NPlayers = (TextView) rootView.findViewById(R.id.NPlayers);
        RemainingPost = (TextView) rootView.findViewById(R.id.RemainingPost);
        Cost = (TextView) rootView.findViewById(R.id.Cost);
        Players = (RecyclerView) rootView.findViewById(R.id.Players);
        inEvent = (Button) rootView.findViewById(R.id.inEvent);
        outEvent = (Button) rootView.findViewById(R.id.outEvent);

        getJSonRequest();

        inEvent.setOnClickListener(new View.OnClickListener() { //devo aggiungerlo anche alle miepartite
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage("Partita aggiunta correttamente").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        getJSonRequest();
                                        array.clear();
                                    }
                                }).create().show();
                                inEvent.setVisibility(View.INVISIBLE);
                                outEvent.setVisibility(View.VISIBLE);
                                MyGameEntity myGameEntity = new MyGameEntity();
                                try {
                                    myGameEntity.writeMyGame(cod_event, getContext());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("Errore");
                                builder.setMessage("Si sono verificati problemi!" + "\n" + "Non sei presente nell'elenco della partita").setNegativeButton("Riprova", null).create().show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                };

                AddToEventRequest addToEventRequest = new AddToEventRequest(username,cod_event+"", responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(addToEventRequest);
            }
        });

        outEvent.setOnClickListener(new View.OnClickListener() { //devo aggiungerlo anche alle miepartite
            @Override
            public void onClick(View v) { //se il giocatore che rimane è unico bisogna promuoverlo amministratore e se non c'è più nessuno bisogna eliminare la partita
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage("OK! Non fai più parte della partita!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        getJSonRequest();
                                        array.clear();
                                    }
                                }).create().show();
                                outEvent.setVisibility(View.INVISIBLE);
                                inEvent.setVisibility(View.VISIBLE);
                                MyGameEntity myGameEntity = new MyGameEntity();
                                try {
                                    myGameEntity.deleteEvent(cod_event, getContext());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("Errore");
                                builder.setMessage("Si sono verificati problemi!" + "\n" + "Sei ancora presente nell'elenco della partita").setNegativeButton("Riprova", null).create().show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                };

                RemoveToEventRequest removeToEventRequest = new RemoveToEventRequest(username,cod_event+"", responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(removeToEventRequest);
            }
        });
        return rootView;
    }


    public void hideDialog(){
        if(dialog !=null){
            dialog.dismiss();
            dialog=null;
        }
    }

    public int getValueTextView(String value){
        String temp[] = value.split(" ");

        //ImageCamp.setText(temp[0]);
        Date.setText("Data: "+temp[0]);
        Hour.setText("Ora: "+temp[1]);
        SportType.setText("Sport: "+temp[2]);
        Location.setText("Luogo: "+temp[3]);
        giocatori = Integer.parseInt(temp[4]);
        NPlayers.setText("Numero giocatori per quadra: "+temp[4]);
        Cost.setText("Prezzo: "+temp[5]);

        return giocatori;
    }


    public void getJSonRequest(){
        url="http://sportmaker.altervista.org/php/ReadEvent.php?nameFile="+cod_event;
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                hideDialog();

                for (int i = 0; i < response.length(); i++) {
                    if (i == 0) {
                        try {
                            giocatori=getValueTextView(response.getString(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            array.add(response.getString(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                int rimanenti = (2*giocatori) - (array.size());
                if(rimanenti<=0)
                    RemainingPost.setText("Posti liberi solo in panchina");
                else
                    RemainingPost.setText("Posti rimanenti: "+rimanenti);

                if(isInGame()){
                    inEvent.setVisibility(View.INVISIBLE);
                    outEvent.setVisibility(View.VISIBLE);
                }else{
                    outEvent.setVisibility(View.INVISIBLE);
                    inEvent.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Server Error: "+error.getMessage()+" "+array.size(), Toast.LENGTH_SHORT).show();
            }
        });

        adapter=new SingleEventAdapter(getContext(), array);
        Players.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getContext());
        Players.setLayoutManager(layoutManager);
        Players.setAdapter(adapter);

        RequestQueue queue= Volley.newRequestQueue(getContext());
        queue.add(jsonArrayRequest);
    }

    private boolean isInGame(){
        boolean response = false;

        for(int i=0; i<array.size(); i++) {
            //se nell'array c'è la mia user allora
            if(array.get(i).contains(username)){
                response=true;
            }
        }
        return response;
    }

}
