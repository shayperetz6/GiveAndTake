package com.android.giveandtake;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.giveandtake.Admin.AdminConnect;
import com.android.giveandtake.Login.Account_Google;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Begin_Application extends AppCompatActivity {
    FirebaseAuth firebaseAuth;


    /**
     * In this class we search in our database if by entering into the app we recognize
     * that the user is still logged in the app so he doesn't have to log again and whether he is the admin.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin__application);

        firebaseAuth = firebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null)//noinspection MagicConstant
                {

                    final String currentUserID = "nDk5cYyLV6Vjpt858AQDF1VNClr2";
                    final String uid = user.getUid().toString();

                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("Users");
                    rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            if (dataSnapshot.hasChild(uid) == true) {

                                if (firebaseAuth.getCurrentUser().getUid().equals(currentUserID)) {
                                    Intent activi = new Intent(Begin_Application.this, AdminConnect.class);
                                    startActivity(activi);
                                    finish();
                                } else {

                                    startActivity(new Intent(Begin_Application.this, ConnectActivity.class));
                                    finish();
                                }
                            } else {
                                startActivity(new Intent(Begin_Application.this, Account_Google.class));
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                } else {
                    Intent activi2 = new Intent(Begin_Application.this, Start_Application.class);
                    startActivity(activi2);
                    finish();

                }
            }
        }, 500);


    }
}
