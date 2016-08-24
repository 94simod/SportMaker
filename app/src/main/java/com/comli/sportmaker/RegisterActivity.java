package com.comli.sportmaker;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.comli.sportmaker.request.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mario on 22/06/2016.
 */
public class RegisterActivity extends AppCompatActivity{
    int age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registrazione);

        final EditText etusername = (EditText) findViewById(R.id.username);
        final EditText etpassword = (EditText) findViewById(R.id.password);
        final EditText etetà = (EditText) findViewById(R.id.età);
        final EditText etemail = (EditText) findViewById(R.id.email);
        final Button bregistrati = (Button) findViewById(R.id.registratiButton);
        final EditText conpassword=(EditText) findViewById(R.id.confirmPassword);
        final EditText etname=(EditText) findViewById(R.id.name);
        final EditText etsurname=(EditText) findViewById(R.id.surname);

        bregistrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etemail.getText().toString();
                final String username = etusername.getText().toString();
                final String password = etpassword.getText().toString();
                final String verificapassword=conpassword.getText().toString();
                final String name = etname.getText().toString();
                final String surname = etsurname.getText().toString();

                if(!etetà.getText().toString().equals("")){
                    age=Integer.parseInt(etetà.getText().toString());
                }else{
                    age=0;
                }

                if(username.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Errore registrazione");
                    builder.setMessage("Inserisci l'username").setNegativeButton("Riprova",null).show();
                }
                else if (age==0 || etetà.getText().toString().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Errore registrazione");
                    builder.setMessage("Inserisci l'età").setNegativeButton("Riprova",null).show();
                }
                else if(email.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Errore registrazione");
                    builder.setMessage("Inserisci email").setNegativeButton("Riprova",null).show();
                }
                else if(name.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Errore registrazione");
                    builder.setMessage("Inserisci nome").setNegativeButton("Riprova",null).show();
                }
                else if(surname.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Errore registrazione");
                    builder.setMessage("Inserisci cognome").setNegativeButton("Riprova",null).show();
                }
                else if (password.equals("") || conpassword.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Errore registrazione");
                    builder.setMessage("Inserisci password").setNegativeButton("Riprova", null).show();
                }
                else if (!password.equals(verificapassword)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Errore registrazione");
                    builder.setMessage("Le password non corrispopndono").setNegativeButton("Riprova", null).show();
                    conpassword.setText("");
                }
                else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setTitle("Registrazione effettuata");
                                    builder.setMessage("Ora puoi utilizzare SportMaker").setPositiveButton("Vai al login", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                            RegisterActivity.this.startActivity(intent);
                                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                            finish();
                                        }
                                    }).show();

                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("Registrazione fallita").setNegativeButton("Riprova", null).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    RegisterRequest registerRequest = new RegisterRequest(email, username, age, password, name, surname, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);

                }
            }});
    }
}
