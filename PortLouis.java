package com.example.train;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PortLouis extends AppCompatActivity {
    private Button btnsend;
    TextView edtTime, edtDate, txttime;
    DatabaseReference databaseReference;
    Spinner spinner, spinner1, spinner2, spinner4;
    ListView list;
    List<Infodb> infos;
    // List<stationdb>all;
    EditText edtNumber, edtName, edtPlatform;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_port_louis);

        spinner2 = (Spinner) findViewById(R.id.spinnerlate);
        spinner = (Spinner)findViewById(R.id.spinnerPlatform);
        spinner1 = (Spinner) findViewById(R.id.spinnertrain);
        spinner4 = (Spinner) findViewById(R.id.spinnerReason);


        final String Late[] = {"5 mins", "10 mins", "15 mins", "20 mins", "25 mins", "30 mins"};

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(PortLouis.this, android.R.layout.simple_spinner_item, Late);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(arrayAdapter);

        final String Reason[] = {"Break Down", "Accident", "Heavy Rainfall", "Traffic"};

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(PortLouis.this, android.R.layout.simple_spinner_item, Reason);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(arrayAdapter1);

        retrieve1();
    }



    public void retrieve1() {


             final String PortLouis[] = {"1(Rose-Hill)", "2(Curepipe)"};

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(PortLouis.this, android.R.layout.simple_spinner_item, PortLouis);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect = PortLouis[position];

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

        Query query = databaseReference.orderByChild("trainNumber").startAt("0021").endAt("0022");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(PortLouis.this, android.R.layout.simple_spinner_item, nameList);
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

        Query query = databaseReference.orderByChild("trainNumber").startAt("0023").endAt("0024");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(PortLouis.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }






}
