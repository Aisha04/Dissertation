package com.example.train;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class mmm extends AppCompatActivity {
    private Button btnsave, btnUpdate, btnDate, btnTime, btnSchedule;
    TextView edtTime, edtDate, txttime, spinner10;
    DatabaseReference databaseReference;
    Spinner spinner, spinner1, spinner2;
    ListView list;
   // List<clientdb> infos;
    List<Infodb> infos;
    // List<stationdb>all;
    EditText edtNumber, edtName, edtPlatform;
    private EditText mSearch;
    private ImageButton btnSearch;
    Calender calender;
    private int dia,mes,ano,hora,minutos;


    // ListView listschedule;
    //List<scheduledb> schedules;

    public static String infoId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mmm);

       // infos = new ArrayList<clientdb>();
        infos = new ArrayList<Infodb>();

        list = (ListView)findViewById(R.id.listTrains);
    }


    @Override
    public void onStart() {
        super.onStart();

        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query1 = databaseReference.orderByChild("date").startAt("1/3/2020").endAt("2/3/2020").limitToFirst(15);
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                infos.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    keys.add(ds.getKey());
                   Infodb info = ds.getValue(Infodb.class);
                    infos.add(info);

                    // Alldb f1 = ds.getValue(Alldb.class);
                    //all.add(f1);
                }

                // client infoAdapter = new client(ClientTrain.this, infos, databaseReference, edtDate, edtTime, txttime);
                //list.setAdapter(infoAdapter);




                client infoAdapter = new client(mmm.this, infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
