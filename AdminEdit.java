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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class AdminEdit extends AppCompatActivity {
    private static final Pattern FULLNAME_PATTERN =
            Pattern.compile("^(?=.*[a-zA-Z]).{2,60}$");
    private static final Pattern USERNAME_PATTERN =
            Pattern.compile("^(?=.*[a-zA-Z]).{2,35}$");
    TextView txtgender, txtid, txtAdmin;
    EditText txtname, txtemail, txtfull;
    private String Email, name, password;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private static final String USERS = "Client";
    private FirebaseAuth mAuth;
    FirebaseUser user;
    Button btnSave;
    private FirebaseAuth.AuthStateListener authListener;
    String DISPLAY_NAME = null;
    String id, Username, Gender, fullName, Email1, Admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit);

        txtAdmin = (TextView) findViewById(R.id.txtAdmin);
        //txtid = (TextView) findViewById(R.id.txtId);
        txtemail = (EditText) findViewById(R.id.email2);
        txtname = (EditText) findViewById(R.id.name);
        txtgender = (TextView) findViewById(R.id.txtgender);
        txtfull = (EditText) findViewById(R.id.txtfull);
        btnSave = (Button) findViewById(R.id.btn_save);

        Intent intent = getIntent();
        //id = intent.getStringExtra("id");
        Admin = intent.getStringExtra("Admin");

        Username = intent.getStringExtra("Username");
        fullName = intent.getStringExtra("fullName");
        Gender = intent.getStringExtra("Gender");
        Email1 = intent.getStringExtra("Email");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            String uid = user.getUid();

            //txtname.setText(name);

            //  txtid.setText(uid);

        }


        //  txtid.setText(uid);
        txtAdmin.setText(Admin);
        txtemail.setText(Email1);
        txtfull.setText(fullName);
        txtname.setText(Username);
        txtgender.setText(Gender);

        //get firebase auth instance
        mAuth = FirebaseAuth.getInstance();



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = txtfull.getText().toString().trim();
                String uname = txtname.getText().toString().trim();
                String emai = txtemail.getText().toString().trim();

                if(TextUtils.isEmpty(fname)){
                    // Toast.makeText(Signup_Form.this, "Please enter Email", Toast.LENGTH_LONG).show();
                    txtfull.setError("Please enter Fullname");
                    return;
                }else if (!FULLNAME_PATTERN.matcher(fname).matches()) {
                    txtfull.setError("Fullname should contains characters only");
                    return;
                } else {
                    txtfull.setError(null);
                }

                if(TextUtils.isEmpty(uname)){
                    // Toast.makeText(Signup_Form.this, "Please enter Email", Toast.LENGTH_LONG).show();
                    txtname.setError("Please enter Username");
                    return;
                }else if (!USERNAME_PATTERN.matcher(fname).matches()) {
                    txtname.setError("Please enter a valid username");
                    return;
                } else {
                    txtname.setError(null);
                }

                if(TextUtils.isEmpty(emai)){
                    // Toast.makeText(Signup_Form.this, "Please enter Email", Toast.LENGTH_LONG).show();
                    txtemail.setError("Please enter Email");
                    return;
                }else if (!Patterns.EMAIL_ADDRESS.matcher(emai).matches()) {
                    txtemail.setError("Please enter a valid email address");
                    return;
                } else {
                    txtemail.setError(null);
                }

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                user.updateEmail(txtemail.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(AdminEdit.this,
                                            "Email address updated",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child(user.getUid());
                String Admin = txtAdmin.getText().toString();
                // String key = databaseReference.push().getKey();
                String Username = txtname.getText().toString();
                String Email = txtemail.getText().toString();
                String fullName = txtfull.getText().toString();
                String Gender = txtgender.getText().toString();



                Admin info = new Admin( fullName, Username, Email, Gender, Admin);
                // databaseReference.child(key).setValue(info);
                databaseReference.setValue(info);
                Toast.makeText(AdminEdit.this, "Successfully update", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),AdminProfile.class));



            }



        });



    }
}
