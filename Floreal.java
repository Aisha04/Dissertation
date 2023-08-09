package com.example.train;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Floreal extends AppCompatActivity {
    private Button btnsend, btncancel;
    TextView edtTime, edtDate, txttime, txtstation,txtemail;
    DatabaseReference databaseReference;
    Spinner spinner, spinner1, spinner2, spinner4;
    ListView list;
    List<Questiondb> infos;

    EditText edtNumber, edtName, edtPlatform;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    public static String infoId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floreal);

        infos = new ArrayList<Questiondb>();

        spinner2 = (Spinner) findViewById(R.id.spinnerlate);
        spinner = (Spinner)findViewById(R.id.spinnerPlatform);
        spinner1 = (Spinner) findViewById(R.id.spinnertrain);
        txtstation = (TextView)findViewById(R.id.txtstation);
        btnsend = (Button) findViewById(R.id.btnSend);
        btncancel = (Button)findViewById(R.id.btnCancel);

        txtemail = (TextView)findViewById(R.id.txtEmail);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            // String name = user.getDisplayName();
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

        final String question[] = {"why train is late?", "Last Train time", "First Train time"};

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Floreal.this, android.R.layout.simple_spinner_item, question);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(arrayAdapter);

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference("Question");
                String trainNumber = spinner1.getSelectedItem().toString();
                String stationName = txtstation.getText().toString();
                String question = spinner2.getSelectedItem().toString();
                String platformNumber = spinner.getSelectedItem().toString();
                String Email = txtemail.getText().toString();


                if (TextUtils.isEmpty(infoId)){
                    //save
                    String id = databaseReference.push().getKey();
                    Questiondb info = new Questiondb(id, trainNumber, stationName, platformNumber, question, Email);
                    databaseReference.child(id).setValue(info);

                    Toast.makeText(Floreal.this, "Successfully created", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), ClientMap.class));
                }
                // else {
                //update
                // databaseReference.child(stationId).child("stationName").setValue(stationName);
                // databaseReference.child(stationId).child("platformNumber").setValue(platformNumber);
                //  Toast.makeText(Station.this, "Successfully Update", Toast.LENGTH_SHORT).show();

                // }



            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ClientMap.class));
            }
        });

        retrieve1();

    }


    public void retrieve1() {


        final String Floreal[] = {"1(Curepipe)", "2(Coromandal)"};

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Floreal.this, android.R.layout.simple_spinner_item, Floreal);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelect = Floreal[position];

                if (position == 0){
                    T11();
                }
                if (position == 1){
                    T12();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void T11(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

       // Query query = databaseReference.orderByChild("trainNumber").startAt("0013").endAt("0014");
        Query query = databaseReference.orderByChild("platformNumber").equalTo("1(Curepipe)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Floreal.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void T12(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        //Query query = databaseReference.orderByChild("trainNumber").startAt("0015").endAt("0016");
        Query query = databaseReference.orderByChild("platformNumber").equalTo("2(Coromandal)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Floreal.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
