package com.android.giveandtake.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.giveandtake.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**This class is used when a user has forgotten his password and by clicking
 * a button sends an email to it's email address to reinitialize it.
 *
 */
public class Forgot_code extends AppCompatActivity {
private Button returnn;
private Button Sub;
private EditText emailboxLogin;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_code);
        firebaseAuth = firebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);

        emailboxLogin = (EditText)findViewById(R.id.mail_box);

        returnn=(Button)findViewById(R.id.btn_back);
        Sub=(Button)findViewById(R.id.btn_reset_password);

        returnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Forgot_code.this,LoginActivity.class));
                finish();
            }
        });

        Sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailboxLogin.getText().toString().trim();

                if (email.isEmpty()) {
                    emailboxLogin.setError(getString(R.string.input_error_email));
                    emailboxLogin.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailboxLogin.setError(getString(R.string.input_error_email_invalid));
                    emailboxLogin.requestFocus();
                    return;
                }







                progressBar.setVisibility(View.VISIBLE);

                FirebaseAuth.getInstance().sendPasswordResetEmail(email)

                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Forgot_code.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Forgot_code.this,LoginActivity.class));
                                finish();
                                } else {
                                    Toast.makeText(Forgot_code.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                }

                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });

    }
}
