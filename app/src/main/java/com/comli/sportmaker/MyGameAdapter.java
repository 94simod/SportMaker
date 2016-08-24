package com.comli.sportmaker;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyGameAdapter extends RecyclerView.Adapter<MyGameAdapter.RecyclerViewHolder> {
        Context mContext;
        ArrayList<String> allEvents;

    public MyGameAdapter(Context context, ArrayList<String> allEvents) {
        mContext=context;
        this.allEvents=allEvents;
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_my_events, parent, false);
        RecyclerViewHolder recyclerViewHolder=new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.my_event.setText(allEvents.get(position));
    }

    @Override
    public int getItemCount() {
        return allEvents.size();
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView my_event;
        CardView card_my_events;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            my_event = (TextView) itemView.findViewById(R.id.my_events);
            card_my_events = (CardView) itemView.findViewById(R.id.card_my_events);
        }
    }


}