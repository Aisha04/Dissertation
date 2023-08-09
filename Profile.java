package com.example.train;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    TextView txtname, txtemail, txtgender, txtfull, txtid, txtAdmin;
    private String Email, name, password;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private static final String USERS = "Client";
    private FirebaseAuth mAuth;
    FirebaseUser user;
    Button btnLogout, btnCPW;
    ImageButton btnEdit,btndelete;
    String DISPLAY_NAME = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        txtAdmin = (TextView) findViewById(R.id.txtAdmin);
        //txtid = (TextView) findViewById(R.id.txtId);
        txtemail = (TextView) findViewById(R.id.email2);
        txtname = (TextView) findViewById(R.id.name);
        txtgender = (TextView) findViewById(R.id.txtgender);
        txtfull = (TextView) findViewById(R.id.txtfull);
        btnLogout = (Button) findViewById(R.id.btn_logout);
        btnEdit = (ImageButton)findViewById(R.id.btn_edit);
        btnCPW = (Button)findViewById(R.id.btn_cPW);
        btndelete = (ImageButton)findViewById(R.id.btn_delete);

        ImageView iv_background = findViewById(R.id.iv_background);

        AnimationDrawable animationDrawable = (AnimationDrawable) iv_background.getDrawable();
        animationDrawable.start();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            // String name = user.getDisplayName()
            String email = user.getEmail();


            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();

            //txtname.setText(name);
            txtemail.setText(email);


        }

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Client");


        Query query = databaseReference.orderByChild("Email").equalTo(user.getEmail());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    // String name = ds.child("Email").getValue(String.class);
                    // if (ds.child("Email").getValue().equals(txtemail)) {
                    // txtname.setText(ds.child("Username").getValue(String.class));
                    String Username = "" + ds.child("Username").getValue();
                    String Gender = "" + ds.child("Gender").getValue();
                    String fullName = "" + ds.child("fullName").getValue();
                    //  String id = "" + ds.child("id").getValue();
                    String Admin = "" + ds.child("Admin").getValue();
                    //String email = "" + ds.child("Email").getValue();
                    // String id = databaseReference.push().getKey();
                    txtname.setText(Username);
                    txtgender.setText(Gender);
                    txtfull.setText(fullName);
                    //  txtid.setText(id);
                    txtAdmin.setText(Admin);
                    // txtemail.setText(Email);

                    // }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(getApplicationContext(),Login_Form.class));
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(v.getContext(), ClientEdit.class);

                i.putExtra("Username", txtname.getText().toString());
                i.putExtra("Email", txtemail.getText().toString());
                i.putExtra("fullName", txtfull.getText().toString());
                i.putExtra("Gender", txtgender.getText().toString());
                //  i.putExtra("id", txtid.getText().toString());
                i.putExtra("Admin", txtAdmin.getText().toString());

                v.getContext().startActivity(i);

                //  startActivity(new Intent(getApplicationContext(),DriverEdit.class));
            }
        });

        btnCPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ClientPW.class));
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Profile.this, "Your profile is deleted:( Create a account now!", Toast.LENGTH_SHORT).show();
                                databaseReference.child(user.getUid()).removeValue();
                                startActivity(new Intent(getApplicationContext(),Login_Form.class));
                            } else {
                                Toast.makeText(Profile.this, "Failed to delete your account!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i= new Intent(Profile.this,Main2Activity.class);
        startActivity(i);
        finish();
    }
        }


