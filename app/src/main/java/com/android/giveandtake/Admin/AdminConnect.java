package com.android.giveandtake.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.giveandtake.R;
import com.android.giveandtake.Start_Application;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminConnect extends AppCompatActivity {
    Button unlogin;
    Button showuser;
    Button showpost;
    Button alltrades;
    Button allHistory;
    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_connect);

        showuser=(Button)findViewById(R.id.View_User);
        showpost=(Button)findViewById(R.id.allpostadmin);
        unlogin=(Button)findViewById(R.id.disconnect_admin);
        alltrades=(Button)findViewById(R.id.ViewAllTrades);
        allHistory=(Button)findViewById(R.id.ViewAllHistory);

        showuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Show_User=new Intent(AdminConnect.this,ShowUsers.class);
                startActivity(Show_User);
            }
        });

        showpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Show_User=new Intent(AdminConnect.this,PostAdmin.class);
                startActivity(Show_User);
            }
        });
        alltrades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TradesView=new Intent(AdminConnect.this,AllTrades.class);
                startActivity(TradesView);
            }
        });
        allHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TradesView=new Intent(AdminConnect.this,History_Show_All.class);
                startActivity(TradesView);
            }
        });



        firebaseAuth=firebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();

        unlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.getInstance().signOut();

                Intent activi = new Intent(AdminConnect.this, Start_Application.class);
                startActivity(activi);
                finish();
                Toast.makeText(AdminConnect.this, "Disconnect Full", Toast.LENGTH_LONG).show();

            }
        });

    }
}

