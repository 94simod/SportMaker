package com.comli.sportmaker;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EntityEventAdapter extends RecyclerView.Adapter<EntityEventAdapter.RecyclerViewHolder>{
    private ArrayList<EntityEvent> arrayList=new ArrayList<>();
    Context mContext;
    //EventList eventList;
    TabSport eventList;

    public EntityEventAdapter(Context context, ArrayList<EntityEvent> arrayList, /*EventList*/ TabSport eventList){
        mContext=context;
        this.arrayList=arrayList;
        this.eventList=eventList;
    }

    public EntityEventAdapter(Context context, ArrayList<EntityEvent> arrayList){
        mContext=context;
        this.arrayList=arrayList;
    }



    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        RecyclerViewHolder recyclerViewHolder=new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {

        EntityEvent entityEvent=arrayList.get(position);
        holder.location.setText(entityEvent.getluogo());
        holder.sport.setText(entityEvent.getSport());
        holder.data.setText(entityEvent.getData());
        holder.n_players.setText("n° giocatori: "+Integer.toString(entityEvent.getNGiocatori()));
        holder.n_remainingPlayers.setText("Posti liberi: "+Integer.toString(entityEvent.getNGiocatoriRimasti()));
        holder.costo.setText("Costo € "+Double.toString(entityEvent.getCosto()));
        holder.cardView.setAnimation(android.view.animation.AnimationUtils.loadAnimation(mContext, R.anim.scale));


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                eventList.changeToEvent(position);
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Toast.makeText(v.getContext(),"selezionato evento "+(position+1), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView sport, location, data, costo, n_players, n_remainingPlayers;
        CardView cardView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            sport= (TextView) itemView.findViewById(R.id.tv_sport);
            location= (TextView) itemView.findViewById(R.id.tv_location);
            data= (TextView) itemView.findViewById(R.id.tv_date);
            costo= (TextView) itemView.findViewById(R.id.tv_costo);
            n_players= (TextView) itemView.findViewById(R.id.tv_n_players);
            n_remainingPlayers= (TextView) itemView.findViewById(R.id.tv_n_remainingPlayers);
            cardView=(CardView) itemView.findViewById(R.id.cardview);
        }
    }
}

