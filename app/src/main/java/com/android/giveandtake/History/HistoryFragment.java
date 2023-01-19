package com.android.giveandtake.History;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.giveandtake.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private DatabaseReference HistoryRef;
    private FirebaseDatabase firebaseDatabase;
    static private ArrayList<History> historyList;
    private RecyclerView _RecyclerView;
    private historyAdapter _Adapter;
    private FirebaseAuth mAuth;
    private RecyclerView.LayoutManager _LayoutManager;
    private String currentUserID;


    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_history, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        HistoryRef = firebaseDatabase.getReference("History");
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();


        createToShowhistory();


        return root;
    }

    public void createToShowhistory() {
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                historyList = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String current_historyId = ds.child("current_historyId").getValue(String.class);
                    String history_giveOption = ds.child("history_giveOption").getValue(String.class);
                    String history_takeOption = ds.child("history_takeOption").getValue(String.class);
                    int imageResocure = ds.child("imageResocure").getValue(Integer.class);
                    String name_userPost = ds.child("name_userPost").getValue(String.class);
                    String name_userTrade = ds.child("name_userTrade").getValue(String.class);
                    String user_postedID = ds.child("user_postedID").getValue(String.class);
                    String text_reason = ds.child("textReason").getValue(String.class);
                    long time = ds.child("history_time").getValue(long.class);


                    if(user_postedID.equals(currentUserID)){
                        History h = new History(imageResocure,name_userPost,name_userTrade,history_giveOption,history_takeOption,current_historyId,user_postedID,time,text_reason);
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
        _RecyclerView = root.findViewById(R.id.recyclerviewHistory);
        _RecyclerView.setHasFixedSize(true);
        _LayoutManager = new LinearLayoutManager(getContext());
        _Adapter = new historyAdapter(historyList);
        _RecyclerView.setLayoutManager(_LayoutManager);
        _RecyclerView.setAdapter(_Adapter);
    }

}