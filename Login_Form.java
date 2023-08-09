package com.example.train;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Login_Form extends AppCompatActivity {
   // private static final Pattern PASSWORD_PATTERN = Pattern.compile("(?=.*[0-9])" +
     //      "(?=.*[a-z])" +
       //    "(?=.*[A-Z])" +
         //   "(?=.*[@#$%^&-+=()])" +
           // "(?=\\\\S+$)" +
           //".{8,20}");
   private static final Pattern PASSWORD_PATTERN =
           Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@@#$%^&*_=+-]).{6,15}$"
                   //"^" +
                   //"(?=.*[0-9])" +         //at least 1 digit
                   //"(?=.*[a-z])" +         //at least 1 lower case letter
                 //  "(?=.*[A-Z])" +         //at least 1 upper case letter
                 //  "(?=.*[a-zA-Z])"    +  //any letter
                   //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                   //"(?=\\S+$)" +           //no white spaces
                   //".{4,}"                //at least 4 characters
                  // "$"
                  // "[a-z]" + "[A-Z]" + ".{4,10}"
                    );
    private Button b1, b2, b3;
    private ImageButton btnshow;

    private EditText txtEmail, txtPassword;
    private FirebaseAuth firebaseAuth;
    Spinner spinner;
    TextView txtType;
    private List<Infodb> info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__form);
       // getSupportActionBar().setTitle("Login Form");

        btnshow = (ImageButton)findViewById(R.id.btnshow);

        spinner = (Spinner) findViewById(R.id.type);
        txtType = (TextView) findViewById(R.id.txttype);

        b1 = (Button)findViewById(R.id.btn_signup);

        b2=(Button)findViewById(R.id.btn_login);
        b3 = (Button)findViewById(R.id.btn_forget);

        txtEmail=(EditText)findViewById(R.id.txt_email);
        txtPassword=(EditText)findViewById(R.id.txt_password);

        firebaseAuth = FirebaseAuth.getInstance();




        //  if(firebaseAuth.getCurrentUser() != null){
        //    finish();
          //  startActivity(new Intent(getApplicationContext(), Login_Form.class));
        //}

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();

              //  if (txtType.getText()== null){
                   // spinner.setError("Please select one character");
                //    ((TextView)spinner.getChildAt(0)).setError("Message");
                //}
              // ((TextView)spinner.getSelectedView()).setError("Error message");

                    if (TextUtils.isEmpty(email)) {
                        // Toast.makeText(Login_Form.this, "Please enter Email", Toast.LENGTH_LONG).show();
                        txtEmail.setError("Please enter Email");
                        return;
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        txtEmail.setError("Please enter a valid email address");
                        return;
                    } else {
                        txtEmail.setError(null);
                    }
              //  final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\\\S+$).{8,20}";
                    if (TextUtils.isEmpty(password)) {
                        //  Toast.makeText(Login_Form.this, "Please enter Password", Toast.LENGTH_LONG).show();
                        txtPassword.setError("Please enter Password");
                        return;
                    }//else if (!PASSWORD_PATTERN.matcher(password).matches()){
                       // txtPassword.setError("Password should contains Capitals, small letters, number and special character");
                        //return;
                    //}
                else {
                        txtPassword.setError(null);
                    }

                    // else if (!PASSWORD_PATTERN.matcher(password).matches()) {
                       // txtPassword.setError("Password should contains Capitals, small letters, munber and special character");
                        //return;
                    //} else {
                     //   txtPassword.setError(null);

                    //}

               // if(password.length()<6){
                   // Toast.makeText(Login_Form.this, "Password too short", Toast.LENGTH_SHORT).show();

               // }


                firebaseAuth.signInWithEmailAndPassword(email, password)


                        .addOnCompleteListener(Login_Form.this, new OnCompleteListener<AuthResult>() {
                            @Override

                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful() && txtType.getText().toString().equals("Admin")) {
                                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    }else{
                                        Toast.makeText(Login_Form.this, "Please verify your email address", Toast.LENGTH_LONG).show();
                                    }


                                } else if (task.isSuccessful() && txtType.getText().toString().equals("Client")){
                                    //userProfile();
                                 //  FirebaseUser user = firebaseAuth.getCurrentUser();
                                    //String email = user.getEmail();
                                    //String uid = user.getUid();

                                    //FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    //DatabaseReference reference = database.getReference().child("Client");

                                //    String userId = authResult.getUser().getUid();

                                  //  String userId = task.getResult().getUser().getUid();
                                   // finish();

                                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                        FirebaseUser user = task.getResult().getUser();

                                        startActivity(new Intent(getApplicationContext(),Main2Activity.class));
                                    }else{
                                        Toast.makeText(Login_Form.this, "Please verify your email address", Toast.LENGTH_LONG).show();
                                    }


                                } else if (task.isSuccessful() && txtType.getText().toString().equals("Driver")) {
                                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                        startActivity(new Intent(getApplicationContext(),Main3Activity.class));
                                    }else{
                                        Toast.makeText(Login_Form.this, "Please verify your email address", Toast.LENGTH_LONG).show();
                                    }

                                }else if (task.isSuccessful() && txtType.getText().toString().equals("Station")) {
                                    startActivity(new Intent(getApplicationContext(),StationDisplay.class));
                                }



                                else {
                                   Toast.makeText(Login_Form.this, "Login Fail", Toast.LENGTH_SHORT).show();

                                }


                            }
                        });




            }
        });


btnshow.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        //txtPassword.transformationMethod = HideReturnsTransformationMethod.getInstance();
       // txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

       if(txtPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance()))

      //  if (txtPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_PASSWORD)
       {
            btnshow.setImageResource(R.drawable.hide);
            txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
       // else if (txtPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_PASSWORD && btnshow.isPressed()){
        else{
            btnshow.setImageResource(R.drawable.eye);
            txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
});

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userProfile();
                Intent intent = new Intent(Login_Form.this, Signup_Form.class);
                startActivity(intent);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Form.this, ForgetPassword.class);
                startActivity(intent);
            }
        });

        retrieve();

    }


    public  void  retrieve(){

        // final String People[] = {"--Select one--", "Adult", "Student", "Child"};


        final List<String> People = new ArrayList<>();

        People.clear();

        People.add(0, "--Select one--");
        People.add(1, "Admin");
        People.add(2, "Client");
        People.add(3, "Driver");
        People.add(4, "Station");


        final ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(Login_Form.this, android.R.layout.simple_spinner_item, People);
        spinner.setAdapter(arrayAdapter3);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // People.clear();
                //  String itemSelect3 = People[position];


                String itemSelect3 = People.get(position);
                if (parent.getItemAtPosition(position).equals("--Select one--")){


                }
                if (position == 1){
                    txtType.setText("Admin");

                }
                if (position == 2){

                    txtType.setText("Client");

                }
                if (position == 3){

                    txtType.setText("Driver");

                }
                if (position == 4){

                    txtType.setText("Station");

                }
                            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    //set User Display Name
    private void userProfile(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                    .setDisplayName(txtEmail.getText().toString().trim())
                    .build();
            user.updateProfile(profileUpdate)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                       if (task.isSuccessful()){
                           Log.d("TESTING", "User Profile Update");
                       }
                        }
                    });



        }
    }


}
