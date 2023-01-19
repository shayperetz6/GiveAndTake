package com.android.giveandtake.Center;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.giveandtake.R;

import java.util.ArrayList;

public class tradeAdaper extends RecyclerView.Adapter<tradeAdaper.ViewHolder> {
    private ArrayList<Trade> listTrades;

    private OnTradeClickListener mListener;

    public interface OnTradeClickListener{
        void onTradeClick(int position);

    }

    public void setOnTradeClickListener(OnTradeClickListener listener){
        mListener = listener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView _ImageViewTrade;
        public TextView _user_trade_name;
        public TextView _user_giveOption;
        public TextView _user_takeOption;





        public ViewHolder(View itemView, final OnTradeClickListener listener){
            super(itemView);
            _ImageViewTrade = itemView.findViewById(R.id.ImageViewTrade);
            _user_trade_name = itemView.findViewById(R.id.user_trade_name);
            _user_giveOption = itemView.findViewById(R.id.t_give);
            _user_takeOption = itemView.findViewById(R.id.t_take);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onTradeClick(position);
                        }
                    }
                }
            });
        }
    }

    public tradeAdaper(ArrayList<Trade> myListitem){
        listTrades = myListitem;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trade,parent,false);
        ViewHolder evh = new ViewHolder(v,mListener);
        return evh;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trade myListTrades = listTrades.get(position);

        holder._ImageViewTrade.setImageResource(myListTrades.getImageResocure());
        holder._user_trade_name.setText(myListTrades.getCurrent_user_name());
        holder._user_giveOption.setText(myListTrades.getPost_give());
        holder._user_takeOption.setText(myListTrades.getPost_take());
    }

    @Override
    public int getItemCount() {
        return listTrades.size();
    }



}
