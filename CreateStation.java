package com.example.train;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CreateStation extends AppCompatActivity {
    private Button btnsave, btnUpdate;
    EditText edtName, edtPlatform;
    DatabaseReference databaseReference;
    ListView liststation;
    List<stationdb> stations;
    Spinner spinner;
    //List<Alldb> stations;
    public static String stationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_station);


        stations = new ArrayList<stationdb>();
        // stations = new ArrayList<Alldb>();
        spinner = (Spinner) findViewById(R.id.spinner5);
        btnUpdate = (Button)findViewById(R.id.btnupdate);
        btnsave = (Button)findViewById(R.id.btnsave);
        edtName = (EditText)findViewById(R.id.Sname);
        edtPlatform = (EditText)findViewById(R.id.platform);
        liststation = (ListView)findViewById(R.id.listStation);



        databaseReference = FirebaseDatabase.getInstance().getReference("stations");

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference("stations");
                //String stationName = edtName.getText().toString();
                String stationName = spinner.getSelectedItem().toString();
                String platformNumber = edtPlatform.getText().toString();

                if (TextUtils.isEmpty(stationId)){
                    //save
                    String id = databaseReference.push().getKey();
                    stationdb station = new stationdb(id, stationName, platformNumber);
                    databaseReference.child(id).setValue(station);

                    Toast.makeText(CreateStation.this, "Successfully created", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Station.class));
                }



                // else {
                //update
                // databaseReference.child(stationId).child("stationName").setValue(stationName);
                // databaseReference.child(stationId).child("platformNumber").setValue(platformNumber);
                //  Toast.makeText(Station.this, "Successfully Update", Toast.LENGTH_SHORT).show();

                // }

               // edtName.setText(null);
                edtPlatform.setText(null);

            }
        });

        retrieve();
    }

    public void retrieve(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("StationName");

        Query query = databaseReference.orderByChild("stationName");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    String name = data.child("stationName").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CreateStation.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
