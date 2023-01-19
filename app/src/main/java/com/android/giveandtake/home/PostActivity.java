package com.android.giveandtake.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.giveandtake.ConnectActivity;
import com.android.giveandtake.R;
import com.android.giveandtake.api.GiveAndTakeApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends AppCompatActivity implements View.OnClickListener {

    private Button createPostBtn;
    private ProgressBar progressBar;
    private EditText freeText;
    private Button cancelBtn;
    private String courrentName;
    private String courrentPhone;
    private String couurentGive;
    private String courrentCity;
    private String courrentHours;
    private int pic;

    private String courrentTake;
    private String []giveOptions;
    private String []takeOptions;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private String currentUserID;
    private DatabaseReference RootRef;
    private String MoreInfoText;
    private Spinner mySpinner_take;
    private Spinner mySpinner_give;
    private Spinner mySpinner_hours;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_activity);

        createPostBtn = findViewById(R.id.createPostbtn);
        progressBar = findViewById(R.id.progress_bar);

        freeText = findViewById(R.id.freeText);
        cancelBtn = findViewById(R.id.cancelBtn);
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        RootRef = firebaseDatabase.getInstance().getReference();
        mySpinner_take = (Spinner) findViewById(R.id.spinner_take);
        mySpinner_give = (Spinner) findViewById(R.id.spinner_give);
        mySpinner_hours = (Spinner) findViewById(R.id.spinner_hours);



        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(PostActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Option1));
        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(PostActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Hours));


        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner_take.setAdapter(myAdapter);
        mySpinner_give.setAdapter(myAdapter);
        mySpinner_hours.setAdapter(myAdapter2);


        createPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPostToDataBase();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PostActivity.this, ConnectActivity.class);
                startActivity(i);
                finish();
            }
        });


    }

    public void registerPostToDataBase(){
        MoreInfoText = freeText.getText().toString().trim();
       courrentTake = (String) mySpinner_take.getSelectedItem().toString();
       couurentGive = (String) mySpinner_give.getSelectedItem().toString();
       courrentHours = (String) mySpinner_hours.getSelectedItem().toString();

        if (mySpinner_give.getSelectedItemPosition() < 0) {
            Toast.makeText(PostActivity.this, "Please select Give Option", Toast.LENGTH_LONG).show();
        }
         if (couurentGive.isEmpty()) {
            Toast.makeText(PostActivity.this, "Please select Take Option", Toast.LENGTH_LONG).show();
            return;
        }
        if (couurentGive.isEmpty()) {
            Toast.makeText(PostActivity.this, "Please select Take Option", Toast.LENGTH_LONG).show();
            return;
        }
        if (courrentHours.isEmpty()) {
            Toast.makeText(PostActivity.this, "Please select Hours Option", Toast.LENGTH_LONG).show();
            return;
        }
        RootRef.child("Users").child(currentUserID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                        courrentName = retrieveUserName;
                        String retrieveUserPhone = dataSnapshot.child("phone").getValue().toString();
                        courrentPhone = retrieveUserPhone;
                        String retrieveCity = dataSnapshot.child("city").getValue().toString();
                        courrentCity = retrieveCity;
                        long now= new Date().getTime();
                        String postId = RootRef.push().getKey();
                        Post p = new Post(R.drawable.item_24dp,courrentName,courrentPhone,courrentCity,couurentGive,courrentTake,MoreInfoText,currentUserID,postId,now,courrentHours);
                        createNewPostToServer(p);

//                        FirebaseDatabase.getInstance().getReference("Posts").child(postId).setValue(p);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
    }

    private void createNewPostToServer(Post post) {
        setProgress(true);
        GiveAndTakeApi.getInstance().getApi().createPost(post).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                setProgress(false);
                Intent i = new Intent(PostActivity.this, ConnectActivity.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                setProgress(false);
                Toast.makeText(getApplicationContext(), "Failed save post try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setProgress(boolean display){
        progressBar.setVisibility(display ? View.VISIBLE : View.GONE);
        createPostBtn.setVisibility(display ? View.GONE : View.VISIBLE);
    }


    @Override
    public void onClick(View view) {

    }
}


