package com.comli.sportmaker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Simone on 14/08/2016.
 */
public class MyGame extends Fragment{
    private MyGameEntity myGameEntity=new MyGameEntity();
    private RecyclerView Games;
    private RecyclerView.LayoutManager layoutManager;
    private MyGameAdapter adapter;
    private int[] allEvents;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.my_game, container, false);

        Games = (RecyclerView) rootView.findViewById(R.id.listMyGame);

        try {
            allEvents=myGameEntity.readAllEvents(getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(allEvents!=null){
            ArrayList<String> tmp = new ArrayList<>();
            for(int i=0; i<allEvents.length; i++){
                tmp.add(String.valueOf(allEvents[i]));
            }
            //richiesta per ogni cod_evento
            adapter=new MyGameAdapter(getContext(), tmp);
            Games.setHasFixedSize(true);
            layoutManager=new LinearLayoutManager(getContext());
            Games.setLayoutManager(layoutManager);
            Games.setAdapter(adapter);
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Non fai parte di nessuna partita").setPositiveButton("Ok", null).create().show();
            UserArea userArea = new UserArea();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, userArea);
            ft.commit();
        }
        //ciao
        return rootView;
    }

}


