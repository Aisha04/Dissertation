package com.example.train;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Signup_Form extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@@#$%^&*_=+-]).{6,15}$");
    private static final Pattern FULLNAME_PATTERN =
            Pattern.compile("^(?=.*[a-zA-Z]).{2,60}$");
    private static final Pattern USERNAME_PATTERN =
            Pattern.compile("^(?=.*[a-zA-Z]).{2,35}$");
private EditText ed1, ed2, ed3, ed4, ed5;
private Button btn;
private RadioButton radMale, radFemale;
    private ImageButton btnshow, btnshow2;
private FirebaseAuth firebaseAuth;
DatabaseReference databaseReference;
FirebaseDatabase firebaseDatabase;
FirebaseUser user;
    Spinner spinner;
    TextView txtType;
    String gender="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup__form);
       // getSupportActionBar().setTitle("Signup Form");

        spinner = (Spinner) findViewById(R.id.type);
        txtType = (TextView) findViewById(R.id.txttype);

        ed1=(EditText)findViewById(R.id.fullname);
        ed2=(EditText)findViewById(R.id.username);
        ed3=(EditText)findViewById(R.id.email);
        ed4=(EditText)findViewById(R.id.password);
        ed5=(EditText)findViewById(R.id.cpassword);
        btn=(Button)findViewById(R.id.btn_register);
        radMale=(RadioButton)findViewById(R.id.radio_male);
        radFemale=(RadioButton)findViewById(R.id.radio_female);

        btnshow = (ImageButton)findViewById(R.id.btnshow1);
        btnshow2 = (ImageButton)findViewById(R.id.btnshow2);

databaseReference = FirebaseDatabase.getInstance().getReference("Admin");
        firebaseAuth = FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               // final String id = databaseReference.push().getKey();
               // final String id = user.getUid();
               // final String id = firebaseAuth.getUid();
                final String fname = ed1.getText().toString();
              final String uname = ed2.getText().toString();
                final String emai = ed3.getText().toString();
                String pass = ed4.getText().toString();
                String cpass = ed5.getText().toString();
                final String type = spinner.getSelectedItem().toString();

                if(radMale.isChecked()){
                    gender="Male";
                }
                if (radFemale.isChecked()){
                    gender="Female";
                }

                if (!radMale.isChecked() && !radFemale.isChecked()){
                    radFemale.setError("Select Item");
                }else
                {
                    radFemale.setError(null);
                }

                ((TextView)spinner.getSelectedView()).setError("Error message");
                if(TextUtils.isEmpty(fname)){
                   // Toast.makeText(Signup_Form.this, "Please enter Email", Toast.LENGTH_LONG).show();
                    ed1.setError("Please enter Fullname");
                    return;
                }else if (!FULLNAME_PATTERN.matcher(fname).matches()) {
                    ed1.setError("Fullname should contains characters only");
                    return;
                } else {
                    ed1.setError(null);
                }

                if(TextUtils.isEmpty(uname)){
                    // Toast.makeText(Signup_Form.this, "Please enter Email", Toast.LENGTH_LONG).show();
                    ed2.setError("Please enter Username");
                    return;
                }else if (!USERNAME_PATTERN.matcher(fname).matches()) {
                    ed2.setError("Please enter a valid username");
                    return;
                } else {
                    ed2.setError(null);
                }

                if(TextUtils.isEmpty(emai)){
                   // Toast.makeText(Signup_Form.this, "Please enter Email", Toast.LENGTH_LONG).show();
                    ed3.setError("Please enter Email");
                    return;
                }else if (!Patterns.EMAIL_ADDRESS.matcher(emai).matches()) {
                    ed3.setError("Please enter a valid email address");
                    return;
                } else {
                    ed3.setError(null);
                }

                if(TextUtils.isEmpty(pass)){
                   // Toast.makeText(Signup_Form.this, "Please enter Password", Toast.LENGTH_LONG).show();
                    ed4.setError("Please enter Password");
                    return;
                }else if (!PASSWORD_PATTERN.matcher(pass).matches()){
                    ed4.setError("Password should contains Capitals, small letters, number and special character");
                    return;
                } else {
                    ed4.setError(null);
                }

                if(TextUtils.isEmpty(cpass)){
                   // Toast.makeText(Signup_Form.this, "Please enter Confirm Password", Toast.LENGTH_LONG).show();
                    ed5.setError("Please enter Confirm Password");
                    return;
                }else if (!PASSWORD_PATTERN.matcher(cpass).matches()){
                    ed5.setError("Confirm password should match password above");
                    return;
                } else {
                    ed4.setError(null);
                }

                if (txtType== null){

                }



                if (pass.equals(cpass)){
                    firebaseAuth.createUserWithEmailAndPassword(emai, pass)
                            .addOnCompleteListener(Signup_Form.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful() && txtType.getText().toString().equals("Admin")) {
                                        //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                        //Toast.makeText(Signup_Form.this, "Registration Completed", Toast.LENGTH_SHORT).show();

                                        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Admin information = new Admin(


                                                            fname,
                                                            uname,
                                                            emai,
                                                            gender,
                                                            type

                                                    );

                                                    FirebaseDatabase.getInstance().getReference("Admin")
                                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                            .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            Toast.makeText(Signup_Form.this, "Registered sucessfully. Please check your email for verification", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(getApplicationContext(), Login_Form.class));
                                                        }
                                                    });

                                                }
                                            }
                                        });



                                    } else if (task.isSuccessful() && txtType.getText().toString().equals("Client")) {
                                        //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                        //Toast.makeText(Signup_Form.this, "Registration Completed", Toast.LENGTH_SHORT).show();
                                        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Admin information = new Admin(


                                                            fname,
                                                            uname,
                                                            emai,
                                                            gender,
                                                            type

                                                    );

                                                    FirebaseDatabase.getInstance().getReference("Client")
                                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                            .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            Toast.makeText(Signup_Form.this, "Registered sucessfully. Please check your email for verification", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(getApplicationContext(), Login_Form.class));
                                                        }
                                                    });

                                                }
                                            }
                                        });

                                    }else if (task.isSuccessful() && txtType.getText().toString().equals("Driver")) {
                                        //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                        //Toast.makeText(Signup_Form.this, "Registration Completed", Toast.LENGTH_SHORT).show();
                                        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Admin information = new Admin(


                                                            fname,
                                                            uname,
                                                            emai,
                                                            gender,
                                                            type

                                                    );

                                                    FirebaseDatabase.getInstance().getReference("Driver")
                                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                            .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            Toast.makeText(Signup_Form.this, "Registered sucessfully. Please check your email for verification", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(getApplicationContext(), Login_Form.class));
                                                        }
                                                    });

                                                }
                                            }
                                        });



                                    }

                                    else {
                                        Toast.makeText(Signup_Form.this, "Registration Failed", Toast.LENGTH_SHORT).show();

                                    }

                                    // ...
                                }
                            });
                }


            }
        });

        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //txtPassword.transformationMethod = HideReturnsTransformationMethod.getInstance();
                // txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                if(ed4.getTransformationMethod().equals(PasswordTransformationMethod.getInstance()))

                //  if (txtPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_PASSWORD)
                {
                    btnshow.setImageResource(R.drawable.hide);
                    ed4.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                // else if (txtPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_PASSWORD && btnshow.isPressed()){
                else{
                    btnshow.setImageResource(R.drawable.eye);
                    ed4.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btnshow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //txtPassword.transformationMethod = HideReturnsTransformationMethod.getInstance();
                // txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                if(ed5.getTransformationMethod().equals(PasswordTransformationMethod.getInstance()))

                //  if (txtPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_PASSWORD)
                {
                    btnshow2.setImageResource(R.drawable.hide);
                    ed5.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                // else if (txtPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_PASSWORD && btnshow.isPressed()){
                else{
                    btnshow2.setImageResource(R.drawable.eye);
                    ed5.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });


        retrieve();

    }

    public  void  retrieve(){

        // final String People[] = {"--Select one--", "Adult", "Student", "Child"};


        final List<String> People = new ArrayList<>();

        People.clear();

        People.add(0, "--Select one--");
        People.add(1, "User");
        //People.add(2, "Client");
       // People.add(3, "Driver");


        final ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(Signup_Form.this, android.R.layout.simple_spinner_item, People);
        spinner.setAdapter(arrayAdapter3);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // People.clear();
                //  String itemSelect3 = People[position];


                String itemSelect3 = People.get(position);
                if (parent.getItemAtPosition(position).equals("--Select one--")){


                }
                //if (position == 1){
                  //  txtType.setText("Admin");

                //}
                if (position == 1){

                    txtType.setText("Client");

                }
               // if (position == 3){

//                    txtType.setText("Driver");

  //              }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


}
