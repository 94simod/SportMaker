package com.comli.sportmaker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by mario on 22/06/2016.
 */

public class UserArea extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_user_area, container, false);

        final TextView welcomeMsg=(TextView) rootView.findViewById(R.id.welcomeMsg);
        final TextView etName=(TextView) rootView.findViewById(R.id.etName);
        final TextView etSurname=(TextView) rootView.findViewById(R.id.etSurname);
        final TextView etEmail=(TextView) rootView.findViewById(R.id.etEmail);
        final TextView etAge=(TextView) rootView.findViewById(R.id.etAge);
        final TextView etCounter=(TextView) rootView.findViewById(R.id.etCounter);
        final TextView etFeedback=(TextView) rootView.findViewById(R.id.etFeedback);

        Intent intent = getActivity().getIntent();
        String name=intent.getStringExtra("name");
        String surname=intent.getStringExtra("surname");
        int feedback = intent.getIntExtra("feedback", -1);
        int counter = intent.getIntExtra("counter", -1);
        String email=intent.getStringExtra("email");
        String username=intent.getStringExtra("username");
        int age=intent.getIntExtra("age",-1);

        String message=username+" benvenuto nella tua area utente";
        welcomeMsg.setText(message);
        etName.setText("Nome: " + name);
        etSurname.setText("Cognome: " + surname);
        etEmail.setText("Email: " + email);
        etAge.setText("Età: "+age);
        etCounter.setText("Partite Giocate: " + counter);
        etFeedback.setText("Bidoni: " + feedback);

        return rootView;
    }

}
