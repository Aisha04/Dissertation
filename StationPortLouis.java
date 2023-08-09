package com.example.train;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DigitalClock;
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

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import android.widget.TextClock;
import android.widget.Toast;

public class StationPortLouis extends AppCompatActivity {
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
        setContentView(R.layout.activity_station_port_louis);

        infos = new ArrayList<Infodb>();
        tClock = (TextClock) findViewById(R.id.textClock1);
       list = (ListView)findViewById(R.id.listPortlouis);
       tD = (TextView)findViewById(R.id.actualDate);
       tT = (TextView)findViewById(R.id.actualTime);
        btn = (Button)findViewById(R.id.btnGet);
        //DigitalClock simpleDigitalClock = (DigitalClock)findViewById(R.id.simpleDigitalClock); // initiate a digital clock
      // simpleDigitalClock.setTextColor(Color.RED);

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("d/M/yyyy");
        String formattedDate = df.format(c);

        tD.setText(formattedDate);
       // int noOfSecond = 1;
      //  new Handler().postDelayed(new Runnable() {

        //    @Override
          //  public void run() {
                //TODO Set your button auto perform click.
          //      for(int i=0;i<4; i++){
            //        btn.performClick();
              //  }
                //btn.performClick();
            //}
        //},  1000);

runHandler();
        //Date t = Calendar.getInstance().getTime();

       // SimpleDateFormat dt = new SimpleDateFormat("HH:mm");
    //    String formattedDate2 = dt.format(t);

      //  tT.setText(formattedDate);

       // Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+4:00"));
        //Date currentLocalTime = cal.getTime();
        //DateFormat date = new SimpleDateFormat("HH:mm a");
// you can get seconds by adding  "...:ss" to it
        //date.setTimeZone(TimeZone.getTimeZone("GMT+4:00"));

      //  S//tring localTime = date.format(currentLocalTime);
       // tT.setText(simpleDigitalClock);

        //Intent intent = getIntent();
        //finish();
        //startActivity(intent);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // tT.setText(tClock.getText());
                final String b = tClock.getText().toString();
                tT.setText(b);
               // try {

                    // SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    //String currentDateandTime = sdf.format(new Date());
                 //   SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                   // Date date1 = format.parse(b);

                    // Date date = sdf.parse(currentDateandTime);
               //     Calendar calendar = Calendar.getInstance();
                 //   calendar.setTime(date1);
                   // calendar.add(Calendar.HOUR, 1);
                  //  String strdate = format.format(calendar.getTime());
                    //tT.setText(strdate);

                //} catch (Exception exception) {

                 //   Toast.makeText(StationPortLouis.this, "Unable to find difference", Toast.LENGTH_SHORT).show();
                //}
                final String y = tClock.getText().toString();
                final String actualDate = tD.getText().toString();
                final String z= tT.getText().toString();

                databaseReference = FirebaseDatabase.getInstance().getReference("Info");
                // Query query1 = databaseReference.orderByChild("date").startAt("1/3/2020").endAt("2/3/2020").limitToFirst(15);
                //Query query1 = databaseReference.orderByChild("date").equalTo(actualDate);

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


                            //if(info.getArrivalTime().toString().trim().length() >=  tClock.getText().toString().trim().length()){
                            final String Timedb = info.getArrivalTime().toString();




                            //  try {

                            //    String t1 = info.getArrivalTime().toString();

                            //String t2 = time2.getText().toString();

                            //  SimpleDateFormat format = new SimpleDateFormat("HH:mm");

                            //SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                            //Date date1 = format.parse(t1);
                            // Date date2 = format.parse(y);

                            //  int value = date1.compareTo(date2);

                            //  if ((date1.getTime() >= date2.getTime())){
                            //  if (info.getDate().equals(actualDate)) {

                            //    infos.add(info);
                            //  }
                            //  Toast.makeText(StationPortLouis.this, "t1 is greater", Toast.LENGTH_SHORT).show();
                            //}
                            // else if (value == 0){
                            //   if (info.getDate().equals(actualDate)) {

                            //     infos.add(info);
                            //}
                            //Toast.makeText(StationPortLouis.this, "t1 is equal to y", Toast.LENGTH_SHORT).show();
                            // }


                            //    long difference = Math.abs(date1.getTime() - date2.getTime());

                            //int numOfDays = (int) (difference / (1000 * 60 * 60 * 24));
                            // int hours = (int) (difference / (1000 * 60 * 60));
                            // int minutes = (int) (difference / (1000 * 60));
                            //int seconds = (int) (difference/ (1000));


                            //Long hours = TimeUnit.MILLISECONDS.toHours(difference);
                            //Long minutes = TimeUnit.MILLISECONDS.toMinutes(difference);
                            //long difference1 = ( hours +  minutes);
                            //  String dayDifference = Long.toString(difference1 );
                            //txttime.setText( dayDifference+ "mins");


                            //txttime.setText(null);
                            // if ((date1.getTime() == date2.getTime())){
                            // if (difference >= 0) {
                            //   if (info.getDate().equals(actualDate)) {

                            //infos.add(info);
                            //}
                            // }
                            // }


                            // } catch (Exception exception) {

                            //   Toast.makeText(StationPortLouis.this, "Unable to find difference", Toast.LENGTH_SHORT).show();
                            //}

                            //    int value = Timedb.compareTo(y);




                           if (info.getDate().equals(actualDate)) {
                               if (info.getStationName().equals("Port-Louis")) {

                                    infos.add(info);
                                }

                            }
                            // infos.add(info);

                        }



                        final list_portlouis infoAdapter = new list_portlouis(StationPortLouis.this, infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, txtDes);
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
        Intent i= new Intent(StationPortLouis.this,StationDisplay.class);
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
