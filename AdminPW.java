package com.example.train;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class AdminPW extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@@#$%^&*_=+-]).{4,15}$");
    EditText txtold, txtnew;
    private String Email, name, password;
    private ImageButton btnshow, btnshow2;
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
        setContentView(R.layout.activity_admin_pw);


        txtold = (EditText) findViewById(R.id.old_pass);
        txtnew = (EditText) findViewById(R.id.new_pass);

        btnSave = (Button) findViewById(R.id.btn_save);

        btnshow = (ImageButton)findViewById(R.id.btnshow1);
        btnshow2 = (ImageButton)findViewById(R.id.btnshow2);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            String uid = user.getUid();

            //txtname.setText(name);

        }
        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //txtPassword.transformationMethod = HideReturnsTransformationMethod.getInstance();
                // txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                if(txtold.getTransformationMethod().equals(PasswordTransformationMethod.getInstance()))

                //  if (txtPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_PASSWORD)
                {
                    btnshow.setImageResource(R.drawable.hide);
                    txtold.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                // else if (txtPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_PASSWORD && btnshow.isPressed()){
                else{
                    btnshow.setImageResource(R.drawable.eye);
                    txtold.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btnshow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //txtPassword.transformationMethod = HideReturnsTransformationMethod.getInstance();
                // txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                if(txtnew.getTransformationMethod().equals(PasswordTransformationMethod.getInstance()))

                //  if (txtPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_PASSWORD)
                {
                    btnshow2.setImageResource(R.drawable.hide);
                    txtnew.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                // else if (txtPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_PASSWORD && btnshow.isPressed()){
                else{
                    btnshow2.setImageResource(R.drawable.eye);
                    txtnew.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cpass = txtold.getText().toString().trim();
                String password = txtnew.getText().toString().trim();

                if (TextUtils.isEmpty(password)) {
                    //  Toast.makeText(Login_Form.this, "Please enter Password", Toast.LENGTH_LONG).show();
                    txtnew.setError("Please enter Password");
                    return;
                }else if (!PASSWORD_PATTERN.matcher(password).matches()){
                    txtnew.setError("Password should contains Capitals, small letters, number and special character");
                    return;
                } else {
                    txtnew.setError(null);
                }

                if (TextUtils.isEmpty(cpass)) {
                    //  Toast.makeText(Login_Form.this, "Please enter Password", Toast.LENGTH_LONG).show();
                    txtold.setError("Please enter Password");
                    return;
                }else if (!PASSWORD_PATTERN.matcher(cpass).matches()){
                    txtold.setError("Password should contains Capitals, small letters, number and special character");
                    return;
                } else {
                    txtold.setError(null);
                }

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                user.updatePassword(txtnew.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(AdminPW.this,"Password updated", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),AdminProfile.class));
                                }
                            }
                        });
            }
        });





    }
}
