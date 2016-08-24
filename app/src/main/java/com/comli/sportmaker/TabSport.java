package com.comli.sportmaker;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Mario on 20/07/2016.
 */
public class TabSport extends Fragment {
    private ProgressDialog dialog;
    private RecyclerView listView;
    private EntityEventAdapter adapter;
    private ArrayList<EntityEvent> array=new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;

   public static TabSport newInstance(String UrlPhp) {
        TabSport tabSport = new TabSport();
        Bundle args = new Bundle();
        args.putString("urlphp", UrlPhp);
        tabSport.setArguments(args);
        return tabSport;
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_eventlist, container, false);
        super.onCreate(savedInstanceState);

        listView = (RecyclerView) rootView.findViewById(R.id.listEvent);
        swipeRefreshLayout=(SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /*dialog=new ProgressDialog(getActivity());
                dialog.setMessage("Aggiornamento eventi...");
                dialog.show();*/
                swipeRefreshLayout.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                swipeRefreshLayout.setRefreshing(true);
                                                getEventfromDb();}
                                                });
            }
        });

        /*dialog=new ProgressDialog(getActivity());
        dialog.setMessage("Caricamento eventi...");
        dialog.show();*/
        getEventfromDb();
        return rootView;
    }

    private void hideDialog(){
        if(dialog !=null){
            dialog.dismiss();
            dialog=null;
        }
    }

    private void getEventfromDb(){
        //Create volley request obj
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(getArguments().getString("urlphp"), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //hideDialog();
                //parsing json
                for(int i=0;i<response.length();i++){
                    try{
                        JSONObject obj=response.getJSONObject(i);
                        EntityEvent entityEvent=new EntityEvent();
                        entityEvent.setCod_event(obj.getInt("Cod_event"));
                        entityEvent.setData(obj.getString("Date"));
                        entityEvent.setSport(obj.getString("Sport"));
                        entityEvent.setLuogo(obj.getString("Location"));
                        entityEvent.setN_giocatori(obj.getInt("N_players"));
                        entityEvent.setId_user(obj.getInt("Id_user"));
                        entityEvent.setCosto(obj.getDouble("Costo"));
                        //entityEvent.setCoordinate(obj.getInt("Coordinate"));
                        //add to array
                        array.add(entityEvent);

                    }catch(JSONException ex){
                        ex.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Server Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //adapter=new EntityEventAdapter(getContext(), array);
        adapter=new EntityEventAdapter(getContext(), array, this);
        listView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getContext());
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
        array.clear(); //serve per non  duplicare gli eventi al refresh
        RequestQueue queue= Volley.newRequestQueue(getContext());
        queue.add(jsonArrayRequest);
    }

    public void changeToEvent(int position) {
        EntityEvent entityEvent = array.get(position);

        EventRequest eventRequest = new EventRequest();
        eventRequest.setCodEvent(entityEvent.getCod_event());
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, eventRequest);
        ft.commit();
    }
}
