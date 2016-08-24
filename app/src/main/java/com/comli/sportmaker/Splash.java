package com.comli.sportmaker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Simone on 23/06/2016.
 */
public class Splash extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        final ImageView iv =  (ImageView) findViewById(R.id.imageView);
        final Animation an = AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate);
        final Animation an2 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_fade_out);

        final TextView caricamento=(TextView) findViewById(R.id.loading);

        iv.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv.startAnimation(an2);
                finish();
                startActivity(changeActivity());
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        caricamento.startAnimation(AnimationUtils.loadAnimation(Splash.this, android.R.anim.slide_in_left));
    }

    private Intent changeActivity(){
        Intent i;
        boolean loggedIn=false;
        final String PREFS_NAME = "Login";

        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        loggedIn=sharedPref.getBoolean("stayConnected",false);
        if(loggedIn) {
            i = new Intent(getBaseContext(), MenuActivity.class);
            i.putExtra("email", sharedPref.getString("email",""));
            i.putExtra("username", sharedPref.getString("username",""));
            i.putExtra("age", sharedPref.getInt("age",0));
            i.putExtra("name", sharedPref.getString("name",""));
            i.putExtra("surname", sharedPref.getString("surname",""));
            i.putExtra("feedback",sharedPref.getInt("feedback",0));
            i.putExtra("counter",sharedPref.getInt("counter",0));
            Splash.this.startActivity(i);
        }else{
            i = new Intent(getBaseContext(), LoginActivity.class);
        }
        return i;
    }

}
