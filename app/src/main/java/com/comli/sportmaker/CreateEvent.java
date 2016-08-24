package com.comli.sportmaker;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.comli.sportmaker.request.NewEventRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateEvent extends Fragment implements AdapterView.OnItemSelectedListener{
    public static final String PREFS_NAME="Login";
    int id_utente;
    String username;
    String date;
    Calendar today=Calendar.getInstance();
    String orario;
    int ore,minuti;
    double prezzo;
    int n_players;

    public CreateEvent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView=inflater.inflate(R.layout.fragment_create_event, container, false);
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        id_utente=sharedPref.getInt("id_utente", id_utente);
        username=sharedPref.getString("username", username);
        final ImageButton mostradata=(ImageButton) rootView.findViewById(R.id.mostraData);
        final ImageButton mostraora=(ImageButton) rootView.findViewById(R.id.mostraOra);
        final FloatingActionButton creaevento=(FloatingActionButton) rootView.findViewById(R.id.view11);

        mostradata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), listener,
                        today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mostraora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getContext(), listener2, ore, minuti, false).show();

            }
        });


        final EditText etluogo=(EditText) rootView.findViewById(R.id.luogo);
        final Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);
        final Spinner spinner2 = (Spinner) rootView.findViewById(R.id.spinner2);
        final EditText etprezzo = (EditText) rootView.findViewById(R.id.prezzo);

        creaevento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String location=etluogo.getText().toString();
                //final int n_players=Integer.parseInt(spinner.getSelectedItem().toString());
                final String sportselezionato=spinner2.getSelectedItem().toString();

                if(!etprezzo.getText().toString().equals("")){
                    prezzo=Double.parseDouble(etprezzo.getText().toString());
                }else{
                    prezzo=0;
                }

                if(date==null){
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                    builder.setTitle("Errore data");
                    builder.setMessage("Verifica di aver inserito la data").setNegativeButton("Riprova",null).show();
                }
                else if(orario==null){
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                    builder.setTitle("Errore orario");
                    builder.setMessage("Verifica di aver inserito l'orario dell'evento").setNegativeButton("Riprova",null).show();
                }
                else if(prezzo==0 || etprezzo.getText().toString()==""){
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                    builder.setTitle("Errore prezzo");
                    builder.setMessage("Verifica di aver inserito il costo dell'evento").setNegativeButton("Riprova",null).show();
                }
                else if(location.equals("") || sportselezionato.equals("")){
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                    builder.setTitle("Creazione evento fallita");
                    builder.setMessage("Inserisci luogo dell'evento").setNegativeButton("Riprova",null).show();
                    //Toast.makeText(getActivity(), date+"\n"+orario+"\n"+location+"\n"+n_players+"\n"+sportselezionato+"\n"+prezzo, Toast.LENGTH_SHORT).show();
                }
                else if(spinner.getSelectedItem().toString().contains("Sel")){
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                    builder.setTitle("Errore n° partecipanti");
                    builder.setMessage("Verifica di aver selezionato il n° di partecipanti").setNegativeButton("Riprova",null).show();
                }else{
                    n_players=Integer.parseInt(spinner.getSelectedItem().toString());
                /*}
                else {*/
                    //Toast.makeText(getActivity(), date+"\n"+orario+"\n"+location+"\n"+n_players+"\n"+sportselezionato, Toast.LENGTH_SHORT).show();
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setMessage("Evento creato correttamente").setPositiveButton("Ok", null).create().show();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setTitle("Errore");
                                    builder.setMessage("Si sono verificati problemi!"+"\n"+"Evento non creato").setNegativeButton("Riprova", null).create().show();
                                }

                                MyGameEntity myGameEntity = new MyGameEntity();
                                try {
                                    int id_event = jsonResponse.getInt("cod_event");
                                    myGameEntity.writeMyGame(id_event, getContext());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                NewEventRequest eventoRequest = new NewEventRequest(username, date+" "+orario, sportselezionato, location, n_players, id_utente, prezzo, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(eventoRequest);
                }
            }
        });

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Seleziona partecipanti");
        categories.add("1");
        categories.add("2");
        categories.add("3");
        categories.add("4");
        categories.add("5");
        categories.add("6");
        categories.add("7");
        categories.add("8");
        categories.add("9");
        categories.add("10");
        categories.add("11");

        List<String> sport=new ArrayList<String>();
        sport.add("Calcio");
        sport.add("Pallavolo");
        sport.add("Basket");
        sport.add("Nuoto");

        // Creating adapter for spinner
        /*dalle slide del prof
        ArrayAdapter<CharSequence> adapter =
        ArrayAdapter.createFromResource(this,
        R.array.colors, R.layout.dropdown_item);*/
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sport);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner2.setAdapter(dataAdapter2);
        return rootView;
    }

    DatePickerDialog.OnDateSetListener listener;

    {
        listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                /*faccio +String.valueOf(0) perchè il formato del mese nel DB è a due cifre, quindi se seleziono un mese ad una cifra(esempio Giugno)
                non inserisce data e ora nel db perchè invece di inserire yyyy-mm-dd cerca di inserire yyyy-m-dd*/
                /*vecchio modo*/ //date = String.valueOf(year)+"-"+String.valueOf(0)+String.valueOf(monthOfYear+1)+"-"+String.valueOf(dayOfMonth);
                today.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                date = format.format(today.getTime());
                Toast.makeText(getContext(), "La data selezionata e' " + date, Toast.LENGTH_SHORT).show();
            }
        };
    }

    TimePickerDialog.OnTimeSetListener listener2=new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                /*vecchio modo*///orario=String.valueOf(hourOfDay)+":"+String.valueOf(minute)+":00";
                orario=view.getCurrentHour()+":"+view.getCurrentMinute();//uso metodi deprecati perchè per usare quelli attuali è richiesta la versione min sdk 23
                Toast.makeText(getContext(), "L'evento si svolgerà alle " + orario, Toast.LENGTH_SHORT).show();
            }
        };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Numero di giocatori in squadra: " + item, Toast.LENGTH_SHORT).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
        Toast.makeText(getContext(), "select something", Toast.LENGTH_SHORT).show();
    }
}
