package com.android.giveandtake.Admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.giveandtake.Center.Trade;
import com.android.giveandtake.R;
import com.android.giveandtake.home.ItemAdapter;
import com.android.giveandtake.home.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PostAdmin extends AppCompatActivity {

    private RecyclerView _RecyclerView;
    private ItemAdapter _Adapter;
    private RecyclerView.LayoutManager _LayoutManager;
    private FirebaseDatabase firebaseDatabase;
    static private ArrayList<Post> PostsList;
    static private ArrayList<Trade> TradeList = new ArrayList<>();
    private DatabaseReference RootRef;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference myRef;
    private DatabaseReference myUsers;
    private DatabaseReference tradeRef;


    private Button giveBtn;
    private Button takeBtn;
    private Button filterCitybtn;
    private Button filterGivebtn;
    private Button CancelFilter;
    private Button filterTakebtn;
    private Button filterDatebtn;
    private String currentUserID;
    private FirebaseAuth mAuth;
    private String name;
    private String phone;
    private String city;
    private String give;
    private String take;
    private long time;
    static private String courrentTake;
    private String []giveOptions;
    private String []takeOptions;
    private String PostID;
    private String Hours;
    static private String current_city;
    private String courrentUser;
    static private String couurentGive;
    private int countTradePerUser = 0;


    private String freeText;
    private boolean firstime = true;

    private boolean filerCity = false;
    private boolean filterDate = false;
    private boolean filerGive = false;
    private boolean filerTake = false;


    /// get the Buttoms ////


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_admin);

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("Posts");
        myUsers = firebaseDatabase.getReference("Users");
        tradeRef = firebaseDatabase.getReference("Trades");

        RootRef = firebaseDatabase.getInstance().getReference();
        firebaseAuth = firebaseAuth.getInstance();
        //////////////////// Create Dialog ///////////////////

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(PostAdmin.this);

        filterCitybtn = findViewById(R.id.filterCityAdmin);
        filterGivebtn =findViewById(R.id.filterGiveAdmin);
        filterTakebtn = findViewById(R.id.filterTakeAdmin);
        filterDatebtn = findViewById(R.id.filterDateAdmin);
        CancelFilter = findViewById(R.id.CancelFilterAdmin);
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        CancelFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filerCity = false;
                filterDate = false;
                filerGive = false;
                filerTake = false;
                createToShowPosts();
                updateView();

            }
        });


        filterCitybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(filerCity == true){
                    filerCity=false;
                }
                else{
                    filerCity=true;
                }
                createToShowPosts();
                updateView();

            }

        });




        filterDatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(filterDate == true){
                    filterDate = false;
                }
                else{
                    filterDate = true;
                    if(!PostsList.isEmpty()){
                        Collections.sort(PostsList,comparator);
                    }
                }

                createToShowPosts();
                updateView();

            }

        });


        filterGivebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                giveOptions = getResources().getStringArray(R.array.Option1);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(PostAdmin.this);
                mBuilder.setTitle("Choose From Options:");
                mBuilder.setSingleChoiceItems(giveOptions, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        couurentGive = giveOptions[which];
                        dialog.dismiss();
                        filerGive = true;
                        createToShowPosts();
                        updateView();

                    }

                });
                mBuilder.setNeutralButton("Cancel Filter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        filerGive = false;
                        createToShowPosts();
                        updateView();

                    }
                });
                AlertDialog mDialog = mBuilder.create();

                mDialog.show();

            }

        });

        filterTakebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeOptions = getResources().getStringArray(R.array.Option1);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(PostAdmin.this);
                mBuilder.setTitle("Choose From Options:");
                mBuilder.setSingleChoiceItems(takeOptions, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        courrentTake = takeOptions[which];
                        dialog.dismiss();
                        filerTake = true;
                        createToShowPosts();
                        updateView();


                    }

                });
                mBuilder.setNeutralButton("Cancel Filter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        filerTake = false;
                        createToShowPosts();
                        updateView();

                    }
                });
                AlertDialog mDialog = mBuilder.create();

                mDialog.show();

            }

        });

        createToShowPosts();



    }


    public void DeletePost(String uid) {
        myRef.child(uid).orderByKey().equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String key = dataSnapshot.getKey();
                dataSnapshot.getRef().removeValue();
                updateView();
                createToShowPosts();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
    public void DeleteTrade(String uid) {
        tradeRef.child(uid).orderByKey().equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String key = dataSnapshot.getKey();
                dataSnapshot.getRef().removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }


    Comparator<Post> comparator = new Comparator<Post>() {

        @Override
        public int compare(Post o1, Post o2) {

            return o1.getTime() < o2.getTime() ? 1 : -1;
        }
    };





    public void createToShowPosts(){
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PostsList = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    name = ds.child("nameAsk").getValue(String.class);
                    phone = ds.child("phoneAsk").getValue(String.class);
                    city = ds.child("city").getValue(String.class);
                    give = ds.child("give").getValue(String.class);
                    Log.e("TAGPOST",ds.child("time").getValue(long.class).toString());
                    take = ds.child("take").getValue(String.class);
                    time = ds.child("time").getValue(long.class);
                    freeText = ds.child("freeText").getValue(String.class);
                    courrentUser = ds.child("currentUserID").getValue(String.class);
                    PostID = ds.child("postid").getValue(String.class);
                    Hours = ds.child("hours").getValue(String.class);

                    myUsers.child(currentUserID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            current_city = dataSnapshot.child("city").getValue(String.class);

                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });


                    if(filerCity == true && filerGive == false && filerTake == false) {

                        if (city.equals(current_city)) {
                            Post p = new Post(R.drawable.item_24dp, name, phone, city, give, take, freeText, courrentUser, PostID,time,Hours);
                            PostsList.add(p);
                            if(filterDate == true){
                                Collections.sort(PostsList,comparator);
                            }
                            updateView();
                        }
                    }
                    else if(filerCity == true && filerGive == true && filerTake == false) {

                        if (city.equals(current_city) && give.equals(couurentGive)) {
                            Post p = new Post(R.drawable.item_24dp, name, phone, city, give, take, freeText, courrentUser, PostID,time,Hours);
                            PostsList.add(p);
                            if(filterDate == true){
                                Collections.sort(PostsList,comparator);
                            }
                            updateView();
                        }
                    }
                    else if(filerCity == true && filerGive == false && filerTake == true) {

                        if (city.equals(current_city) && take.equals(courrentTake)) {
                            Post p = new Post(R.drawable.item_24dp, name, phone, city, give, take, freeText, courrentUser, PostID,time,Hours);
                            PostsList.add(p);
                            if(filterDate == true){
                                Collections.sort(PostsList,comparator);
                            }
                            updateView();
                        }
                    }
                    else if(filerCity == true && filerGive == true && filerTake == true) {

                        if (city.equals(current_city) && give.equals(couurentGive) && take.equals(courrentTake)) {
                            Post p = new Post(R.drawable.item_24dp, name, phone, city, give, take, freeText, courrentUser, PostID,time,Hours);
                            PostsList.add(p);
                            if(filterDate == true){
                                Collections.sort(PostsList,comparator);
                            }
                            updateView();
                        }
                    }
                    else if(filerCity == false && filerGive == true && filerTake == true){

                        if(give.equals(couurentGive) && take.equals(courrentTake)){
                            Post p = new Post(R.drawable.item_24dp, name, phone, city, give, take, freeText, courrentUser, PostID,time,Hours);
                            PostsList.add(p);
                            if(filterDate == true){
                                Collections.sort(PostsList,comparator);
                            }
                            updateView();
                        }

                    }
                    else if(filerCity == false && filerGive == true && filerTake == false){

                        if(give.equals(couurentGive)){
                            Post p = new Post(R.drawable.item_24dp, name, phone, city, give, take, freeText, courrentUser, PostID,time,Hours);
                            PostsList.add(p);
                            if(filterDate == true){
                                Collections.sort(PostsList,comparator);
                            }
                            updateView();
                        }

                    }
                    else if(filerCity == false && filerGive == false && filerTake == true){

                        if(take.equals(courrentTake)){
                            Post p = new Post(R.drawable.item_24dp, name, phone, city, give, take, freeText, courrentUser, PostID,time,Hours);
                            PostsList.add(p);
                            if(filterDate == true){
                                Collections.sort(PostsList,comparator);
                            }
                            updateView();
                        }

                    }
                    else{
                        Post p = new Post(R.drawable.item_24dp, name, phone, city, give, take, freeText, courrentUser, PostID,time,Hours);
                        PostsList.add(p);
                        if(filterDate == true){
                            Collections.sort(PostsList,comparator);
                        }


                    }



                }

                updateView();

                _Adapter.setOnPostClickListener(new ItemAdapter.OnPostClickListener() {
                    @Override
                    public void onPostClick(final int position) {
                        PostsList.get(position);

                        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(PostAdmin.this);
                        mBuilder.setTitle("Information Post:");
                        mBuilder.setMessage(PostsList.get(position).getfreeText() + "\n");


                        mBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                DeletePost(PostsList.get(position).getPostid());
                                updateView();
                            }
                        });
                        mBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
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
    public void checkDup(Trade t, String current_post_id, String current_user_id){
        countTradePerUser = 0;
        for(Trade i : TradeList) {
            if((i.getCurrent_post_id().equals(current_post_id)) && (i.getCurrent_user_id().equals(current_user_id))) {
                countTradePerUser++;
            }
        }

        Log.d("TAG11", countTradePerUser+" "+t.getCurrent_post_id() +" "+current_post_id+" and "+t.getCurrent_user_id()+" "+current_user_id );

        if((t.getCurrent_post_id().equals(current_post_id)) && (t.getCurrent_user_id().equals(current_user_id)) && countTradePerUser > 1)
        {
            TradeList.remove(t);
            DeleteTrade(t.getCurrent_Trade_id());
        }
    }


    public void updateView(){
        _RecyclerView = findViewById(R.id.recyclerview2);
        _RecyclerView.setHasFixedSize(true);
        _LayoutManager = new LinearLayoutManager(PostAdmin.this);
        _Adapter = new ItemAdapter(PostsList);
        _RecyclerView.setLayoutManager(_LayoutManager);
        _RecyclerView.setAdapter(_Adapter);
    }

}

