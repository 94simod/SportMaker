package com.comli.sportmaker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.comli.sportmaker.request.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;



/**
 * Created by mario on 22/06/2016.
 */
public class LoginActivity extends AppCompatActivity{

    public static final String PREFS_NAME = "Login";
    String userName;
    String name;
    String surname;
    int feedback;
    int counter;
    String email;
    int age;
    boolean stayconn=false;
    int id_utente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        final TextInputLayout til_username=(TextInputLayout) findViewById(R.id.til_username);
        final TextInputLayout til_password=(TextInputLayout) findViewById(R.id.til_password);
        final Button blogin=(Button) findViewById(R.id.login);
        final TextView openRegisterActivity=(TextView) findViewById(R.id.textView2);


        openRegisterActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent=new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username=til_username.getEditText().getText().toString();
                final String password=til_password.getEditText().getText().toString();
                userName=username;

                if(username.isEmpty() && password.isEmpty()){
                    til_password.setError("Inserire password");
                    til_username.setError("Inserire username");
                    return;
                }

                Response.Listener<String> responseListener=new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("response vale -------->", response.toString());
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success=jsonResponse.getBoolean("success");

                            if(success){
                                putJsonValue(jsonResponse);

                                Intent loginIntent=new Intent(LoginActivity.this, MenuActivity.class);
                                loginIntent.putExtra("email", email);
                                loginIntent.putExtra("username", username);
                                loginIntent.putExtra("age", age);
                                loginIntent.putExtra("name", name);
                                loginIntent.putExtra("surname", surname);
                                loginIntent.putExtra("feedback",feedback);
                                loginIntent.putExtra("counter",counter);
                                LoginActivity.this.startActivity(loginIntent);
                                addFileValue();
                                SendMail sendMail=new SendMail(getBaseContext(), email, "Accesso SportMaker", "Accesso effettuato come "+username);
                                sendMail.execute();
                                finish();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setTitle("Login fallito");
                                builder.setMessage("Combinazione username/password errata").setNegativeButton("Riprova", null).create().show();
                            }
                        }
                        catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest=new LoginRequest(username, password, responseListener);
                RequestQueue queue= Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }

    private void putJsonValue(JSONObject jsonResponse) throws JSONException {
        id_utente=jsonResponse.getInt("id_utente");
        name=jsonResponse.getString("name");
        surname=jsonResponse.getString("surname");
        feedback=jsonResponse.getInt("feedback");
        counter=jsonResponse.getInt("counter");
        email=jsonResponse.getString("email");
        age=jsonResponse.getInt("age");
    }

    private void addFileValue(){
        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("id_utente", id_utente);
        editor.putString("username", userName);
        editor.putString("name", name);
        editor.putString("surname",surname);
        editor.putInt("age", age);
        editor.putString("email", email);
        editor.putInt("feedback", feedback);
        editor.putInt("counter", counter);
        final CheckBox stayConnected=(CheckBox) findViewById(R.id.stayconnected);
        stayconn=stayConnected.isChecked();
        editor.putBoolean("stayConnected",stayconn);
        editor.commit();
    }
}

