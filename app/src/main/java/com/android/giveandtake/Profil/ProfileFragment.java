package com.android.giveandtake.Profil;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.giveandtake.R;
import com.android.giveandtake.Start_Application;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * In this class the user can view it's profile and have the options to edit it or to disconnect
 */
public class ProfileFragment extends Fragment{

    private ProfileViewModel notificationsViewModel;
    private FirebaseAuth firebaseAuth;
    private Button unsigout, editprofile;
    //private Button delete;
    private TextView name, phone, email, city;
    private DatabaseReference UsersRef,myRef,tradeRef;
    private FirebaseDatabase firebaseDatabase;


    private String currentID, otherId;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =ViewModelProviders.of(this).get(ProfileViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_profile, container, false);
        unsigout = (Button) root.findViewById(R.id.disconnect);
        editprofile = (Button) root.findViewById(R.id.editprofile);
     //   delete = (Button)root.findViewById(R.id.delete);

        firebaseAuth = firebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();

        UsersRef = firebaseDatabase.getReference().child("Users");
        tradeRef = firebaseDatabase.getReference("Trades");
        myRef = firebaseDatabase.getReference("Posts");
        final FirebaseUser user=firebaseAuth.getCurrentUser();

        name=(TextView)root.findViewById(R.id.nameprofile);
        phone=(TextView)root.findViewById(R.id.phoneprofile);
        email=(TextView)root.findViewById(R.id.emailprofile);
        city=(TextView)root.findViewById(R.id.cityprofile);

        String idUser=user.getUid();


        UsersRef.child(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userId=dataSnapshot.child("name").getValue(String.class);
                String phoneid=dataSnapshot.child("phone").getValue(String.class);
                String emailid=dataSnapshot.child("email").getValue(String.class);
                String cityid=dataSnapshot.child("city").getValue(String.class);

                name.setText(userId);
                phone.setText(phoneid);
                email.setText(emailid);
                city.setText(cityid);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        unsigout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.getInstance().signOut();

                Intent activi = new Intent(getActivity(), Start_Application.class);
                startActivity(activi);
                getActivity().finish();
                Toast toast = Toast.makeText(getActivity(), "Disconnect Full", Toast.LENGTH_LONG);
//                View toastView = toast.getView();
//                toastView.setBackgroundResource(R.color.dark_orange);
//                toast.show();

            }
        });

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent acti = new Intent(getActivity(), EditProfile.class);
                startActivity(acti);

            }
        });

//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final FirebaseUser
//                        myuser = firebaseAuth.getCurrentUser();
//                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
//                dialog.create();
//                dialog.setTitle("Are you sure?");
//                dialog.setMessage("Deleting this account will result in completely removing your account from the system " +
//                        "and you won't be able to access the app.");
//                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int which) {
//                        myuser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if(task.isSuccessful()) {
//                                    Toast.makeText(getActivity(), "Account deleted", Toast.LENGTH_LONG).show();
//                   /*                 Intent activi = new Intent(getActivity(), Start_Application.class);
//                                    startActivity(activi);
//                                    getActivity().finish();*/
//                   String currentuid=myuser.getUid().toString();
//                                    DeleteUsers(currentuid);
//                                    DeletePost(currentuid);
////                                    DeleteTrade(myuser.getUid());
////                                    DeleteTrade2(myuser.getUid());
//
//
//
//                                }
//                                else {
//                                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        });
//                    }
//                });
//                dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int which) {
//                        dialogInterface.dismiss();
//                    }
//                });
//                AlertDialog alertDialog = dialog.create();
//                alertDialog.show();
//            }
//        });

        return root;
    }


    public void DeletePost(String uid) {
        myRef.orderByChild("currentUserID").equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("POSTDELETE","SUCCESS");
                String key = dataSnapshot.getKey();
                dataSnapshot.getRef().removeValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
    public void DeleteUsers(String uid) {
        UsersRef.child(uid).orderByKey().equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("USERDELETE","SUCCES");

                String key = dataSnapshot.getKey();
                dataSnapshot.getRef().removeValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
    public void DeleteTrade(String uid) {
        tradeRef.orderByChild("current_user_id").equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
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
    public void DeleteTrade2(String uid) {
        tradeRef.orderByChild("user_post_id").equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
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

}
