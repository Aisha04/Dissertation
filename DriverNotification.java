package com.example.train;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DriverNotification extends AppCompatActivity {
    private Button btnsave, btnUpdate, btnDate, btnTime, btnSchedule;
    TextView edtTime, edtDate, txttime, spinner, spinner1, spinner2, spinner3;
    DatabaseReference databaseReference;
    // Spinner spinner, spinner1, spinner2;
    ListView list;
    List<QuestionDriverdb> infos;
    // List<stationdb>all;
    EditText edtNumber, edtName, edtPlatform;
    private EditText mSearch;
    private ImageButton btnSearch;
       // ListView listschedule;
    //List<scheduledb> schedules;

    public static String infoId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_notification);

        infos = new ArrayList<QuestionDriverdb>();

        mSearch = (EditText)findViewById(R.id.search_field);
        btnSearch = (ImageButton)findViewById(R.id.imageButton);
        list = (ListView)findViewById(R.id.listDriver);

    }

    protected  void onStart(){
        super.onStart();

        databaseReference = FirebaseDatabase.getInstance().getReference("DriverIssues");
        Query query1 = databaseReference.orderByChild("stationName").limitToFirst(15);
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                infos.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    keys.add(ds.getKey());
                    QuestionDriverdb info = ds.getValue(QuestionDriverdb.class);
                    infos.add(info);

                    // Alldb f1 = ds.getValue(Alldb.class);
                    //all.add(f1);
                }

                driver_list infoAdapter = new driver_list(DriverNotification.this, infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2,  spinner3);

                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent i= new Intent(DriverNotification.this,MainActivity.class);
        startActivity(i);
        finish();
    }

}
