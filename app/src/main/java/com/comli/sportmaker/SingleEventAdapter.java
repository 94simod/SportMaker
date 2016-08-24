package com.comli.sportmaker;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Simone on 10/08/2016.
 */
public class SingleEventAdapter extends RecyclerView.Adapter<SingleEventAdapter.RecyclerViewHolder>{
    private ArrayList<String> arrayList=new ArrayList<>();
    Context mContext;

    public SingleEventAdapter(Context context, ArrayList<String> array) {
        mContext=context;
        arrayList=array;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardplayer, parent, false);
        RecyclerViewHolder recyclerViewHolder=new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        holder.giocatore.setText(arrayList.get(position));

        /*
        holder.cardplayer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(v.getContext(),"selezionato evento "+(position+1), Toast.LENGTH_SHORT).show();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                //poi si vede
                                Toast.makeText(mContext, "OK", Toast.LENGTH_SHORT).show();
                            } else {
                                //AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                //builder.setMessage("Registrazione fallita").setNegativeButton("Riprova", null).show();
                                Toast.makeText(mContext, "Fallita", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                AddToEvent addToEvent = new AddToEvent("username", "1", responseListener);
                RequestQueue queue = Volley.newRequestQueue(mContext);
                queue.add(addToEvent);

                return false;
            }
        });  */
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView giocatore;
        CardView cardplayer;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            giocatore = (TextView) itemView.findViewById(R.id.player);
            cardplayer = (CardView) itemView.findViewById(R.id.cardplayer);

        }
    }

}
