package com.android.giveandtake.Admin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.giveandtake.History.History;
import com.android.giveandtake.History.historyAdapter;
import com.android.giveandtake.R;
import com.android.giveandtake.home.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class History_Show_All extends AppCompatActivity{

    private DatabaseReference HistoryRef;
    private FirebaseDatabase firebaseDatabase;
    static private ArrayList<History> historyList;
    private RecyclerView _RecyclerView;
    private historyAdapter _Adapter;
    private FirebaseAuth mAuth;
    private RecyclerView.LayoutManager _LayoutManager;
    private String currentUserID;
    private Button filterAccpet;
    private Button filerDeny;
    static private int imageResocure;
    private String current_historyId;
    private String history_giveOption;
    private String history_takeOption;
    private String name_userPost;
    private String name_userTrade;
    private String user_postedID;
    private String Text_Reason;
    private long time;
    private boolean bool_filerAccept = false;
    private boolean bool_filerDeny = false;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history__show__all);

        firebaseDatabase = FirebaseDatabase.getInstance();
        HistoryRef = firebaseDatabase.getReference("History");
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        filterAccpet = findViewById(R.id.filterAccpet);
        filerDeny = findViewById(R.id.filterDeny);



        filterAccpet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(bool_filerAccept == true){
                    bool_filerAccept=false;
                }
                else{
                    bool_filerAccept=true;
                }
                createToShowhistory();
                updateView();

            }

        });

        filerDeny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(bool_filerDeny == true){
                    bool_filerDeny=false;
                }
                else{
                    bool_filerDeny=true;
                }
                createToShowhistory();
                updateView();

            }

        });


        createToShowhistory();

    }

    public void createToShowhistory() {
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                historyList = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    current_historyId = ds.child("current_historyId").getValue(String.class);
                    history_giveOption = ds.child("history_giveOption").getValue(String.class);
                    history_takeOption = ds.child("history_takeOption").getValue(String.class);
                    imageResocure = ds.child("imageResocure").getValue(Integer.class);
                    name_userPost = ds.child("name_userPost").getValue(String.class);
                    name_userTrade = ds.child("name_userTrade").getValue(String.class);
                    user_postedID = ds.child("user_postedID").getValue(String.class);
                    time = ds.child("history_time").getValue(long.class);
                    Text_Reason = ds.child("textReason").getValue(String.class);

                    if(bool_filerAccept == true && bool_filerDeny == false) {

                        if (imageResocure == R.drawable.accpet) {
                            History h = new History(R.drawable.accpet,name_userPost,name_userTrade,history_giveOption,history_takeOption,current_historyId,user_postedID,time,Text_Reason);
                            historyList.add(h);
                            updateView();
                        }
                    }
                    else if(bool_filerAccept == false && bool_filerDeny == true) {

                        if (imageResocure == R.drawable.deny) {
                            History h = new History(R.drawable.deny,name_userPost,name_userTrade,history_giveOption,history_takeOption,current_historyId,user_postedID,time,Text_Reason);
                            historyList.add(h);
                            updateView();
                        }
                    }
                    else{

                        History h = new History(imageResocure,name_userPost,name_userTrade,history_giveOption,history_takeOption,current_historyId,user_postedID,time,Text_Reason);
                        historyList.add(h);
                    }
                }



                updateView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }


        };
        HistoryRef.addListenerForSingleValueEvent(eventListener);


    }

    public void updateView(){
        _RecyclerView = findViewById(R.id.recyclerviewHistory_Admin);
        _RecyclerView.setHasFixedSize(true);
        _LayoutManager = new LinearLayoutManager(History_Show_All.this);
        _Adapter = new historyAdapter(historyList);
        _RecyclerView.setLayoutManager(_LayoutManager);
        _RecyclerView.setAdapter(_Adapter);
    }

}