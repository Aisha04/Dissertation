package com.example.train;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StationRosehill extends AppCompatActivity {
    private Button btnsave, btnUpdate, btnDate, btnTime, btnSchedule, btn;
    TextView edtTime, edtDate, txttime, tD, tT, txtDes;
    private TextClock tClock;
    DatabaseReference databaseReference;
    Spinner spinner, spinner1, spinner2;
    ListView list;
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
        setContentView(R.layout.activity_station_rosehill);

        infos = new ArrayList<Infodb>();
        tClock = (TextClock) findViewById(R.id.textClock1);
        list = (ListView)findViewById(R.id.listRosehill);
        tD = (TextView)findViewById(R.id.actualDate);
        tT = (TextView)findViewById(R.id.actualTime);
        btn = (Button)findViewById(R.id.btnGet);

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("d/M/yyyy");
        String formattedDate = df.format(c);

        tD.setText(formattedDate);


        runHandler();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // tT.setText(tClock.getText());
                final String b = tClock.getText().toString();
                tT.setText(b);

                final String y = tClock.getText().toString();
                final String actualDate = tD.getText().toString();
                final String z= tT.getText().toString();

                databaseReference = FirebaseDatabase.getInstance().getReference("Info");
                Query query1 = databaseReference.orderByChild("arrivalTime").startAt(z);
                query1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {



                        infos.clear();
                        List<String> keys = new ArrayList<>();

                        for (DataSnapshot ds: dataSnapshot.getChildren()){
                            keys.add(ds.getKey());
                            //String actualTime = simpleDigitalClock.getText().toString();
                            //String name = data.child("trainNumber").getValue(String.class);
                            Infodb info = ds.getValue(Infodb.class);

                            final String Timedb = info.getArrivalTime().toString();

                            if (info.getDate().equals(actualDate)) {
                                if (info.getStationName().equals("Rose-Hill")) {

                                    infos.add(info);
                                }

                            }
                            // infos.add(info);

                        }


                        final list_rosehill infoAdapter = new list_rosehill(StationRosehill.this, infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, txtDes);
                        list.setAdapter(infoAdapter);

                        final Handler handler = new Handler();
                        handler.postDelayed( new Runnable() {

                            @Override

                            public void run() {
                                int i = 0;
                                i++;

                                if(i<1000000)
                                    infoAdapter.notifyDataSetChanged();
                                handler.postDelayed( this, 60 * 1000 );
                            }
                        }, 60 * 1000 );

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i= new Intent(StationRosehill.this,StationDisplay.class);
        startActivity(i);
        finish();
    }

    protected  void onStart(){
        super.onStart();
        //tT.setText(tClock.getText());
        final String y = tClock.getText().toString();
        final String actualDate = tD.getText().toString();
        final String z= tT.getText().toString();
        //final String b = tClock.getText().toString();

    }
    private static int i = 0;

    void runHandler()
    {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                i++;

                if(i<1000000)
                    runHandler();
                btn.performClick();
                // Generate_Ballon();
            }
        }, 1000);
    }
}
