package com.android.giveandtake.History;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.giveandtake.Center.Trade;
import com.android.giveandtake.R;


import java.util.ArrayList;

public class historyAdapter extends RecyclerView.Adapter<historyAdapter.ViewHolder> {
    private ArrayList<History> listHistory;

    private OnHistoryClickListener mListener;

    public interface OnHistoryClickListener{
        void onHistoryClick(int position);

    }

    public void setOnHistoryClickListener(OnHistoryClickListener listener){
        mListener = listener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView ImageViewHistory;
        public TextView history_name_posted;
        public TextView history_name_trade;
        public TextView history_giveOption;
        public TextView history_takeOption;
        public TextView history_date;





        public ViewHolder(View itemView, final OnHistoryClickListener listener){
            super(itemView);
            ImageViewHistory = itemView.findViewById(R.id.StatusImagView);
            history_name_posted = itemView.findViewById(R.id.history_name_posted);
            history_name_trade = itemView.findViewById(R.id.history_name_trade);
            history_giveOption = itemView.findViewById(R.id.history_GiveOption);
            history_takeOption = itemView.findViewById(R.id.history_TakeOption);
            history_date = itemView.findViewById(R.id.history_Date);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onHistoryClick(position);
                        }
                    }
                }
            });
        }
    }

    public historyAdapter(ArrayList<History> myListitem){
        listHistory = myListitem;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history,parent,false);
        ViewHolder evh = new ViewHolder(v,mListener);
        return evh;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        History myListTrades = listHistory.get(position);

        holder.ImageViewHistory.setImageResource(myListTrades.getImageResocure());
        holder.history_name_posted.setText(myListTrades.getName_userPost());
        holder.history_name_trade.setText(myListTrades.getName_userTrade());
        holder.history_giveOption.setText(myListTrades.getHistory_giveOption());
        holder.history_takeOption.setText(myListTrades.getHistory_takeOption());
        holder.history_date.setText(myListTrades.History_timeAndDate());

    }

    @Override
    public int getItemCount() {
        return listHistory.size();
    }



}
