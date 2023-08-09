package com.example.train;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DriverEdit2 extends AppCompatActivity {
    Button btnSave;
    private EditText oldEmail, newEmail, password, newPassword;
    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_edit2);



        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(DriverEdit2.this, DriverProfile.class));
                    finish();
                }
            }
        };


        oldEmail = (EditText) findViewById(R.id.old_email);
        newEmail = (EditText) findViewById(R.id.new_email);
        btnSave = (Button) findViewById(R.id.btn_save);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // if (user != null && !newEmail.getText().toString().trim().equals("")) {
                 //   user.updateEmail(newEmail.getText().toString().trim())
                   //         .addOnCompleteListener(new OnCompleteListener<Void>() {
                     //           @Override
                       //         public void onComplete(@NonNull Task<Void> task) {
                         //           if (task.isSuccessful()) {
                           //             Toast.makeText(DriverEdit2.this, "Email address is updated. Please sign in with new email id!", Toast.LENGTH_LONG).show();


                             //       } else {
                               //         Toast.makeText(DriverEdit2.this, "Failed to update email!", Toast.LENGTH_LONG).show();

                                 //   }
                                //}
                           // });
               // }
                   // else if (newEmail.getText().toString().trim().equals("")) {
                   // newEmail.setError("Enter email");

               // }

           //     String email = newEmail.getText().toString();

         //       FirebaseUser users = auth.getCurrentUser();

          //      users.updateEmail(email)
              //          .addOnCompleteListener(new OnCompleteListener<Void>() {
                //            @Override
                    //        public void onComplete(@NonNull Task<Void> task) {
                      //         if (task.isSuccessful()) {
                         //           Toast.makeText(DriverEdit2.this,
                              //              "Email address updated",
                            //                Toast.LENGTH_SHORT).show();
                            //    }
                           // }
                        //});

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                user.updateEmail(newEmail.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(DriverEdit2.this,
                                                         "Email address updated",
                                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }

        });


    }



}
