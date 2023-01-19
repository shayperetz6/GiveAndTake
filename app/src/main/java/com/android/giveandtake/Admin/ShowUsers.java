package com.android.giveandtake.Admin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.giveandtake.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShowUsers extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView FindFriendsRecyclerList;
    private DatabaseReference UsersRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_users);

        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");


        FindFriendsRecyclerList = (RecyclerView) findViewById(R.id.find_friends_recycler_list);
        FindFriendsRecyclerList.setLayoutManager(new LinearLayoutManager(this));
        firebaseAuth=FirebaseAuth.getInstance();

    }


    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<User_Admin_Class> options =
                new FirebaseRecyclerOptions.Builder<User_Admin_Class>()
                        .setQuery(UsersRef, User_Admin_Class.class)
                        .build();

        FirebaseRecyclerAdapter<User_Admin_Class, FindFriendViewHolder> adapter =
                new FirebaseRecyclerAdapter<User_Admin_Class, FindFriendViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final FindFriendViewHolder holder, final int position, @NonNull User_Admin_Class model) {
                        holder.userName.setText(model.getUserName());
                        holder.userStatus.setText(model.getUserPhone());
                        holder.ViewEmail.setText(model.getUserEmail());


                        holder.ViewEmail.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                        "mailto", "", null));
                                startActivity(intent);
                            }
                        });
                        holder.userStatus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String visit_user_id = getRef(position).getKey();
                                Intent sIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:12345"));
                                sIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(sIntent);

//
//                                Intent profileIntent = new Intent(ShowUsers.this, ProfileActivity.class);
//                               profileIntent.putExtra("visit_user_id", visit_user_id);
//                                startActivity(profileIntent);
                            }
                        });


                    }

                    @NonNull
                    @Override
                    public FindFriendViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
                    {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.users_display_layout, viewGroup, false);
                        FindFriendViewHolder viewHolder = new FindFriendViewHolder(view);
                        return viewHolder;
                    }
                };

        FindFriendsRecyclerList.setAdapter(adapter);

        adapter.startListening();
    }



    public static class FindFriendViewHolder extends RecyclerView.ViewHolder
    {
        TextView userName, userStatus,ViewEmail;

        public FindFriendViewHolder(@NonNull View itemView)
        {
            super(itemView);

            userName = itemView.findViewById(R.id.user_profile_name);
            userStatus = itemView.findViewById(R.id.Phone_number);
            ViewEmail = itemView.findViewById(R.id.email_Admin);
        }
    }
}
