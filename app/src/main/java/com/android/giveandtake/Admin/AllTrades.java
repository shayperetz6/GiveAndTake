package com.android.giveandtake.Admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.giveandtake.Center.Trade;
import com.android.giveandtake.Center.tradeAdaper;
import com.android.giveandtake.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllTrades extends AppCompatActivity {




    private RecyclerView _RecyclerView;
    private tradeAdaper _Adapter;
    private RecyclerView.LayoutManager _LayoutManager;
    private FirebaseDatabase firebaseDatabase;
    static private ArrayList<Trade> TradeList;
    private DatabaseReference RootRef;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference myRef;
    private DatabaseReference PostsRef;
    private DatabaseReference HistoryRef;
    private FirebaseAuth mAuth;
    private String currentUserID;
    private int countTradePerUser = 0;





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_trades);

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("Trades");
        PostsRef = firebaseDatabase.getReference("Posts");

        RootRef = firebaseDatabase.getInstance().getReference();
        firebaseAuth = firebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();


        createToShowTrades();


    }

    public void createToShowTrades(){

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TradeList = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String current_post_id = ds.child("current_post_id").getValue(String.class);
                    String current_trade_id = ds.child("current_Trade_id").getValue(String.class);
                    String user_post_id = ds.child("user_post_id").getValue(String.class);
                    String user_post_name = ds.child("user_post_name").getValue(String.class);
                    String current_user_name = ds.child("current_user_name").getValue(String.class);
                    String current_user_id = ds.child("current_user_id").getValue(String.class);
                    String post_give = ds.child("post_give").getValue(String.class);
                    String post_take = ds.child("post_take").getValue(String.class);
                    String current_user_phone = ds.child("current_user_phone").getValue(String.class);
                    String current_user_city = ds.child("current_user_city").getValue(String.class);
                    String Textfree = ds.child("textFree").getValue(String.class);
                    String Hours = ds.child("hours").getValue(String.class);


                    Trade t = new Trade(R.drawable.black2people, current_user_id, current_post_id, current_trade_id, user_post_id, user_post_name, current_user_name, post_give, post_take, current_user_phone, current_user_city, Textfree, Hours);
                    TradeList.add(t);


                }
                updateView();


                _Adapter.setOnTradeClickListener(new tradeAdaper.OnTradeClickListener(){
                    @Override
                    public void onTradeClick(final int position) {
                        TradeList.get(position);

                        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(AllTrades.this);
                        mBuilder.setTitle("Post ID: "+TradeList.get(position).getCurrent_post_id());
                        mBuilder.setMessage("Name: "+TradeList.get(position).getCurrent_user_name()+"\n"+
                                "Phone: "+TradeList.get(position).getCurrent_user_phone()+"\n"+
                                "City: "+TradeList.get(position).getCurrent_user_city()+"\n"+
                                "Hours:" + TradeList.get(position).getHours()+"\n"



                        );


                        mBuilder.setNeutralButton("Exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog mDialog = mBuilder.create();
                        mDialog.show();
                    }


                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        myRef.addListenerForSingleValueEvent(eventListener);
    }






    public void updateView(){
        _RecyclerView = findViewById(R.id.recyclerviewCenterAdmin);
        _RecyclerView.setHasFixedSize(true);
        _LayoutManager = new LinearLayoutManager(AllTrades.this);
        _Adapter = new tradeAdaper(TradeList);
        _RecyclerView.setLayoutManager(_LayoutManager);
        _RecyclerView.setAdapter(_Adapter);
    }

}