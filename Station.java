package com.example.train;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class Station extends AppCompatActivity {
    private Button btnsave, btnUpdate;
    ImageButton btncreate;
     EditText edtName, edtPlatform;
    DatabaseReference databaseReference;
    ListView liststation;
    List<stationdb> stations;
    //List<Alldb> stations;
    Spinner spinner;
    private EditText mSearch;
    private ImageButton btnSearch;
    public static String stationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);

        stations = new ArrayList<stationdb>();
        // stations = new ArrayList<Alldb>();

        spinner = (Spinner) findViewById(R.id.spinner5);
        btnUpdate = (Button)findViewById(R.id.btnupdate);
        btnsave = (Button)findViewById(R.id.btnsave);
        edtName = (EditText)findViewById(R.id.Sname);
        edtPlatform = (EditText)findViewById(R.id.platform);
        liststation = (ListView)findViewById(R.id.listStation);
        //btncreate = (Button)findViewById(R.id.createStation);
        btncreate = (ImageButton)findViewById(R.id.createStation);
        mSearch = (EditText)findViewById(R.id.search_field);
        btnSearch = (ImageButton)findViewById(R.id.imageButton);




        databaseReference = FirebaseDatabase.getInstance().getReference("stations");
       //btnUpdate.setOnClickListener(new View.OnClickListener() {
            //@Override
           // public void onClick(View v) {
              //  String stationName = edtName.getText().toString();
             //   String platformNumber = edtPlatform.getText().toString();
                //update
               // databaseReference.child(stationId).child("stationName").setValue(stationName);
               // databaseReference.child(stationId).child("platformNumber").setValue(platformNumber);
                //Toast.makeText(Station.this, "Successfully Update", Toast.LENGTH_SHORT).show();
           // }
       // });

        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CreateStation.class));
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = mSearch.getText().toString();
                firebaseSearch(searchText);
            }
        });


    }


    @Override
    public void onBackPressed() {
        Intent i= new Intent(Station.this,MainActivity.class);
        startActivity(i);
        finish();
    }


    protected  void onStart(){
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                stations.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    keys.add(ds.getKey());
                    stationdb station = ds.getValue(stationdb.class);
                    //stationdb station = ds.child("station").getValue(stationdb.class);
                    stations.add(station);

                   //Alldb station = ds.getValue(Alldb.class);
                    //stations.add(station);
                }

                stationList stationAdapter = new stationList(Station.this, stations, databaseReference, spinner, edtPlatform);
                liststation.setAdapter(stationAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void firebaseSearch(String searchText) {
        databaseReference = FirebaseDatabase.getInstance().getReference("stations");
        Query query = databaseReference.orderByChild("stationName").startAt(searchText).endAt(searchText + "");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                stations.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    stationdb station = ds.getValue(stationdb.class);
                    //stationdb station = ds.child("station").getValue(stationdb.class);
                    stations.add(station);
                }

                stationList stationAdapter = new stationList(Station.this, stations, databaseReference, spinner, edtPlatform);
                liststation.setAdapter(stationAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
