package com.android.giveandtake.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.giveandtake.Admin.AdminConnect;
import com.android.giveandtake.ConnectActivity;
import com.android.giveandtake.R;
import com.android.giveandtake.Start_Application;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * This class allows a registered user (only) to log in the app and to the admin to access to the admin section
 */
public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    private EditText emailboxLogin;
    private  EditText passwordboxLogin;
    private TextView forgotPassword;
    private  Button buttonLogin;
    private  Button ReturnBtn;
    private int verif_is_exist;
    private FirebaseAuth firebaseAuth;
    private String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        emailboxLogin = (EditText)findViewById(R.id.emailboxLogin);
        passwordboxLogin = (EditText)findViewById(R.id.passwordboxLogin);
        buttonLogin = (Button)findViewById(R.id.buttonLogin);
        firebaseAuth = firebaseAuth.getInstance();
        ReturnBtn = (Button)findViewById(R.id.returnLoginbtn);

        forgotPassword = findViewById(R.id.forgot);
        String text = "Forgot your password? Click Here";
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent forgot=new Intent(LoginActivity.this,Forgot_code.class);
                startActivity(forgot);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLACK);
                ds.setUnderlineText(true);
            }
        };

        ss.setSpan(clickableSpan1, 22, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        forgotPassword.setText(ss);
        forgotPassword.setMovementMethod(LinkMovementMethod.getInstance());

        ReturnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, Start_Application.class);
                startActivity(i);
            }
        });



        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "Please wait...", "Processing...", true);
                (firebaseAuth.signInWithEmailAndPassword(emailboxLogin.getText().toString(), passwordboxLogin.getText().toString()))
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();

                                if (task.isSuccessful()) {

                                    String currentUserID = "nDk5cYyLV6Vjpt858AQDF1VNClr2";

                                    if (firebaseAuth.getCurrentUser().getUid().equals(currentUserID)) {
                                        startActivity(new Intent(LoginActivity.this, AdminConnect.class)
                                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                        Toast.makeText(LoginActivity.this, "Administrator Connector", Toast.LENGTH_LONG).show();
                                        finish();


                                    } else {


                                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(LoginActivity.this, ConnectActivity.class);
                                        i.putExtra("Email", firebaseAuth.getCurrentUser().getEmail());
                                        startActivity(i);
                                        finish();
                                    }
                                } else {
                                    Log.e("onComplete: Failed=", task.getException().getMessage());
                                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            }

            });


    }}







