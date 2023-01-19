package com.android.giveandtake.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.giveandtake.R;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private ArrayList<Post> Listitem;

    private OnPostClickListener mListener;

    public interface OnPostClickListener{
        void onPostClick(int position);

    }

    public void setOnPostClickListener(OnPostClickListener listener){
        mListener = listener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView _ImageView;
        public TextView _NameAsk;
        public TextView _DateAsk;
        public TextView _GiveAsk;
        public TextView _TakeAsk;
        public TextView _City;
        public TextView _Hours;

//        public EditText _freeText;


        public ViewHolder(View itemView, final OnPostClickListener listener){
            super(itemView);
            _ImageView = itemView.findViewById(R.id.ImageView);
            _NameAsk = itemView.findViewById(R.id.NameAsk);
            _DateAsk = itemView.findViewById(R.id.DateAsk);
            _GiveAsk = itemView.findViewById(R.id.GiveOption);
            _TakeAsk = itemView.findViewById(R.id.TakeOption);
            _City = itemView.findViewById(R.id.CityOption);
            _Hours = itemView.findViewById(R.id.HoursAsk);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onPostClick(position);
                        }
                    }
                }
            });
        }
    }

    public ItemAdapter(ArrayList<Post> myListitem){
        Listitem = myListitem;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        ViewHolder evh = new ViewHolder(v,mListener);
        return evh;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post correntItem = Listitem.get(position);

        holder._ImageView.setImageResource(correntItem.getImageResocure());
        holder._NameAsk.setText(correntItem.getNameAsk());
        holder._DateAsk.setText(correntItem.GetDate());
        holder._GiveAsk.setText(correntItem.getGive());
        holder._TakeAsk.setText(correntItem.getTake());
        holder._City.setText(correntItem.getCity());
        holder._Hours.setText((correntItem.getHours()));






    }

    @Override
    public int getItemCount() {
        return Listitem.size();
    }



}
