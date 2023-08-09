package com.example.train;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddStation extends AppCompatActivity {
    private Button btnsave, btnUpdate;
    EditText edtName, edtPlatform;
    DatabaseReference databaseReference;
    ListView liststation;
    List<addNewStation> stations;
    //List<Alldb> stations;
    public static String stationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_station);

        stations = new ArrayList<addNewStation>();
        // stations = new ArrayList<Alldb>();

        btnUpdate = (Button)findViewById(R.id.btnupdate);
        btnsave = (Button)findViewById(R.id.btnsave);
        edtName = (EditText)findViewById(R.id.Sname);
        edtPlatform = (EditText)findViewById(R.id.platform);
        liststation = (ListView)findViewById(R.id.listStation);



        databaseReference = FirebaseDatabase.getInstance().getReference("StationName");
//databaseReference = FirebaseDatabase.getInstance().getReference("All");
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String stationName = edtName.getText().toString();
              //  String platformNumber = edtPlatform.getText().toString();

                if (TextUtils.isEmpty(stationId)){
                    //save
                    String id = databaseReference.push().getKey();
                    addNewStation station = new addNewStation(id, stationName);
                    databaseReference.child(id).setValue(station);

                    Toast.makeText(AddStation.this, "Successfully created", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(getApplicationContext(), Station.class));
                }
                // else {
                //update
                // databaseReference.child(stationId).child("stationName").setValue(stationName);
                // databaseReference.child(stationId).child("platformNumber").setValue(platformNumber);
                //  Toast.makeText(Station.this, "Successfully Update", Toast.LENGTH_SHORT).show();

                // }

                edtName.setText(null);
                //edtPlatform.setText(null);

            }
        });




    }
}
