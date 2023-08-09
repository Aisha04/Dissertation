package com.example.train;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ForgetPassword extends AppCompatActivity {
    EditText txtEmail;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private static final String USERS = "Client";
    private FirebaseAuth mAuth;
    FirebaseUser user;
    Button btnEmail;
    private FirebaseAuth.AuthStateListener authListener;
    String DISPLAY_NAME = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);


        txtEmail = (EditText) findViewById(R.id.txt_femail);

        btnEmail = (Button) findViewById(R.id.btn_email);

        mAuth = FirebaseAuth.getInstance();
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = txtEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                   // Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    txtEmail.setError("Please enter Email");
                    return;
                }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    txtEmail.setError("Please enter a valid email address");
                    return;
                } else {
                    txtEmail.setError(null);
                }

                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgetPassword.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ForgetPassword.this, Login_Form.class);
                        } else {
                            Toast.makeText(ForgetPassword.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });


    }
}
