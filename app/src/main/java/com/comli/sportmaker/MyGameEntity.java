package com.comli.sportmaker;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Simone on 14/08/2016.
 */
public class MyGameEntity {

    public static final String PREFS_NAME="MyGame.txt";
    Context mContext;

    public MyGameEntity(){
    }

    public void writeMyGame(int id_event, Context context) throws IOException {
        mContext=context;

        FileOutputStream stream = mContext.openFileOutput(PREFS_NAME, Context.MODE_APPEND);

        String tmp = id_event+"\n";
        try {
            stream.write(tmp.getBytes());
        } catch (IOException e) {

            e.printStackTrace();
        } finally {
            stream.close();
        }

    }

    //public ArrayList<String> readAllEvents(Context context) throws IOException{
    public int[] readAllEvents(Context context) throws IOException {
        mContext=context;
        ArrayList<String> games = new ArrayList<>();

        FileInputStream in = mContext.openFileInput(PREFS_NAME);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;

        while ((strLine = br.readLine()) != null)   {
            games.add(strLine);
        }

        int[] tmp = new int[games.size()];
        for(int i=0; i<games.size(); i++){
            tmp[i]= Integer.parseInt(games.get(i));
        }

        return tmp;
    }

    /*
    public int[] readAllEvents(Context context) throws IOException {
        mContext=context;
        int[] games;

        //visualizza solo 10 caratteri che è la lunghezza del nome del file
        FileInputStream in = mContext.openFileInput(PREFS_NAME);

        //byte[] bytes = new byte[cnt];

        int length = PREFS_NAME.length();
        byte[] bytes = new byte[length];

        try {
            in.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            in.close();
        }


        String contents = new String(bytes);
        String[] events=contents.split("/n");
        games = new int[events.length];
        for(int i=0; i<events.length; i++){
            games[i]= Integer.parseInt(events[i]);
        }

        return games;
    }*/

    public void deleteEvent(int id_event, Context context) throws IOException {
        mContext=context;
        int[] temp = readAllEvents(mContext);
        int lengths = temp.length-1;
        int[] elements= new int[lengths];

        int i=0,k=0;
        for(i=0; i<temp.length; i++){
            if(temp[i]!=id_event) {
                elements[k] = temp[i];
                k = k++;
            }
        }
        writeAllEvent(elements, mContext);
    }


    public void writeAllEvent(int[] events, Context context) throws IOException {
        mContext=context;
        int i=0;
        String tmp="";

        while(i<events.length){
            tmp.concat(String.valueOf(events[i])+"\n");
            i=i++;
        }

        FileOutputStream stream = mContext.openFileOutput(PREFS_NAME, Context.MODE_PRIVATE);

        try {
            stream.write(tmp.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stream.close();
        }

    }

}
