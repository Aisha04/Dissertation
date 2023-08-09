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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class createTrain extends AppCompatActivity {
    private Button btnsave, btnUpdate;
    EditText edtrain, edtPlatform;
    DatabaseReference databaseReference;
    Spinner spinner, spinner2;
    ListView listTrain;
    List<traindb> trains;
    // List<Alldb> trains;
    public static String trainId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_train);


        trains = new ArrayList<traindb>();
        //trains = new ArrayList<Alldb>();
        spinner = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner6);
        btnUpdate = (Button) findViewById(R.id.btnupdate);
        btnsave = (Button) findViewById(R.id.btnsave);
        edtrain = (EditText) findViewById(R.id.trainNumber);
        edtPlatform = (EditText) findViewById(R.id.platform);
        listTrain = (ListView) findViewById(R.id.listTrain);




        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference("Train");
                //databaseReference = FirebaseDatabase.getInstance().getReference("All");
                String trainNumber = edtrain.getText().toString();
                //String platformNumber = edtPlatform.getText().toString();
                String stationName = spinner.getSelectedItem().toString();
                String platformNumber = spinner2.getSelectedItem().toString();

                if (TextUtils.isEmpty(trainId)) {
                    //save
                    String id = databaseReference.push().getKey();
                    traindb train = new traindb(id, stationName, trainNumber, platformNumber);
                    databaseReference.child(id).setValue(train);

                    Toast.makeText(createTrain.this, "Successfully created", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Train.class));
                }
                // else {
                //update
                // databaseReference.child(stationId).child("stationName").setValue(stationName);
                // databaseReference.child(stationId).child("platformNumber").setValue(platformNumber);
                //  Toast.makeText(Station.this, "Successfully Update", Toast.LENGTH_SHORT).show();

                // }

                edtrain.setText(null);


            }
        });
        retrieve();
      //  retrieve1();
        //retrieve2();
        //Curepipe();
        //Floreal();
    }


    public void retrieve(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("StationName");

        Query query = databaseReference.orderByChild("stationName");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final List<String> nameList = new ArrayList<>();

                final String BeauBassin[] = {"1(Trianon)", "2(Quatre-Bornes)"};
                final String Coromandel[] = {"1(Port-Louis)", "6(Curepipe)"};
                final String Curepipe[] = {"1(Coromandel)", "2(Port-Louis)"};
                final String Floreal[] = {"1(Curepipe)", "2(Coromandal)"};
                final String Phoenix[] = {"3(Port-Louis)", "2(Rose-Hill)"};
                final String PortLouis[] = {"1(Rose-Hill)", "2(Curepipe)"};
                final String QuatreBornes[] = {"1(St Jean)", "2(Vacoas)"};
                final String RoseHill[] = {"1(Vacoas)", "2(St Jean)"};
                final String Sadally[] = {"1(Phoenix)", "2(St Louis)"};
                final String StJean[] = {"1(Floreal)", "2(Phoenix"};
                final String StLouis[] = {"1(Sadally)", "2(Trianon)"};
                final String Trianon[] = {"1(Quatre-Bornes)", "2(Floreal)"};
                final String Vacoas[] = {"1(St Louis)", "2(Beau-Bassin)"};

                for (DataSnapshot data: dataSnapshot.getChildren()){
                    String name = data.child("stationName").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(createTrain.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect = nameList.get(position);


                        if (position==0){
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(createTrain.this, android.R.layout.simple_spinner_item, BeauBassin);
                            spinner2.setAdapter(arrayAdapter);
                        }
                        if (position==1){
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(createTrain.this, android.R.layout.simple_spinner_item, Coromandel);
                            spinner2.setAdapter(arrayAdapter);
                        }
                        if (position==2){
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(createTrain.this, android.R.layout.simple_spinner_item, Curepipe);
                            spinner2.setAdapter(arrayAdapter);
                        }
                        if (position==3){
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(createTrain.this, android.R.layout.simple_spinner_item, Floreal);
                            spinner2.setAdapter(arrayAdapter);
                        }
                        if (position==4){
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(createTrain.this, android.R.layout.simple_spinner_item, Phoenix);
                            spinner2.setAdapter(arrayAdapter);
                        }
                        if (position==5){
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(createTrain.this, android.R.layout.simple_spinner_item, PortLouis);
                            spinner2.setAdapter(arrayAdapter);
                        }
                        if (position==6){
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(createTrain.this, android.R.layout.simple_spinner_item, QuatreBornes);
                            spinner2.setAdapter(arrayAdapter);
                        }
                        if (position==7){
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(createTrain.this, android.R.layout.simple_spinner_item, RoseHill);
                            spinner2.setAdapter(arrayAdapter);
                        }
                        if (position==8){
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(createTrain.this, android.R.layout.simple_spinner_item, Sadally);
                            spinner2.setAdapter(arrayAdapter);
                        }
                        if (position==9){
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(createTrain.this, android.R.layout.simple_spinner_item, StJean);
                            spinner2.setAdapter(arrayAdapter);
                        }
                        if (position==10){
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(createTrain.this, android.R.layout.simple_spinner_item, StLouis);
                            spinner2.setAdapter(arrayAdapter);
                        }
                        if (position==11){
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(createTrain.this, android.R.layout.simple_spinner_item, Trianon);
                            spinner2.setAdapter(arrayAdapter);
                        }
                        if (position==12){
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(createTrain.this, android.R.layout.simple_spinner_item, Vacoas);
                            spinner2.setAdapter(arrayAdapter);
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void retrieve1(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("stations");

        Query query = databaseReference.orderByChild("platformNumber").startAt("2(Port-Louis)").endAt("6(Curepipe)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    String name = data.child("platformNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(createTrain.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void retrieve2(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("stations");

        Query query = databaseReference.orderByChild("platformNumber").startAt("1(Port-Louis)").endAt("2(Curepipe)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    String name = data.child("platformNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(createTrain.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void Curepipe(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("stations");

        Query query = databaseReference.orderByChild("platformNumber").startAt("1(Coromandel)").endAt("2(Port-Louis)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    String name = data.child("platformNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(createTrain.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void Floreal(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("stations");

        Query query = databaseReference.orderByChild("platformNumber").startAt("1(Curepipe)").endAt("2(Coromandel)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    String name = data.child("platformNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(createTrain.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}




