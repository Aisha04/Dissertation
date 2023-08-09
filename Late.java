package com.example.train;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Late extends AppCompatActivity {
    private Button btnupdate, btnLate, btnCal;
    ImageButton btnTime;
    TextView edtTime, edtDate, edtLate, txttime, spinner, spinner2, spinner1, time2, spinner10;
    DatabaseReference databaseReference;

    String id, stationName, trainNumber, date, arrivalTime, platformNumber, late, DestinationStation;
    Context mContext = this;
    private Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_late);

        btnTime = (ImageButton)findViewById(R.id.btnTime);
        btnupdate = (Button)findViewById(R.id.btnupdate4);
        edtTime = (TextView)findViewById(R.id.time);
        edtDate = (TextView)findViewById(R.id.date);
        txttime = (TextView)findViewById(R.id.txttime);
        spinner1 = (TextView) findViewById(R.id.spinner);
        spinner = (TextView)findViewById(R.id.spinner1);
        spinner2 = (TextView)findViewById(R.id.spinner2);
        time2 = (TextView)findViewById(R.id.time2);
        btnCal = (Button)findViewById(R.id.btnCalculate);
        spinner10 = (TextView)findViewById(R.id.spinner10);



        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        //stationName = intent.getStringExtra("stationName");
        stationName = intent.getStringExtra("stationName");
        trainNumber = intent.getStringExtra("trainNumber");
        date = intent.getStringExtra("date");
        arrivalTime = intent.getStringExtra("arrivalTime");
        late = intent.getStringExtra("late");
        platformNumber = intent.getStringExtra("platformNumber");
        DestinationStation = intent.getStringExtra("DestinationStation");




        edtTime.setText(arrivalTime);
        edtDate.setText(date);
        spinner.setText(stationName);
        spinner2.setText(platformNumber);
        spinner1.setText(trainNumber);
        spinner10.setText(DestinationStation);

       // txttime.setText(late);
        // String trainNumber = spinner.getSelectedItem().toString();
        //String stationName = spinner1.getSelectedItem().toString();



        calendar = Calendar.getInstance();

        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int min = calendar.get(Calendar.MINUTE);

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  DialogFragment timePickerFragment = new TimerPickerFragment();
                //timePickerFragment.setCancelable(false);
                //timePickerFragment.show(getSupportFragmentManager(),"timePicker");

                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        String hourString;
                        if (hourOfDay < 10) {
                            hourString = "0" + hourOfDay;
                        } else {
                            hourString = "" + hourOfDay;
                        }

                        String minuteSting;
                        if (minute < 10) {
                            minuteSting = "0" + minute;
                        } else {
                            minuteSting = "" + minute;
                        }
                        time2.setText(hourString + ":" + minuteSting);


                        // time.setText(new StringBuilder().append(hour).append(" : ").append(min)
                        //   .append(" ").append(format));


                    }
                },hour, min, android.text.format.DateFormat.is24HourFormat(mContext));
                timePickerDialog.show();

            }
        });




        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Info").child(id);
                //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("All").child(id);
                //String trainNumber = spinner1.getSelectedItem().toString();
                String trainNumber = spinner1.getText().toString();
                String date = edtDate.getText().toString();
                String arrivalTime = edtTime.getText().toString();
               // String stationName = spinner.getSelectedItem().toString();
                String DestinationStation = spinner.getText().toString();
              //  String platformNumber = spinner2.getSelectedItem().toString();
                String platformNumber = spinner2.getText().toString();
                // Alldb station = new Alldb(id,StationNames,PlatformNumber);
                String late = txttime.getText().toString();
                String stationName = spinner10.getText().toString();

                Infodb info = new Infodb(id, trainNumber,  date, arrivalTime, stationName, platformNumber, late, DestinationStation);
                databaseReference.setValue(info);
                Toast.makeText(Late.this, "Successfully update", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Trains.class));


            }
        });



        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcTime();
            }
        });



    }

    //@Override
   // public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //time2.setText(hourOfDay + ":" + minute);

    //}



    public void calcTime() {
        try {
            //int startTime = edtTime.getText().length();
            String t1 = edtTime.getText().toString();
            // int startTime = Integer.parseInt(t1);
            //int endTime = time2.getText().length();
            String t2 = time2.getText().toString();
            //int endTime = Integer.parseInt(t2);

            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            Date date1 = format.parse(t1);
            Date date2 = format.parse(t2);

            long difference = Math.abs(date1.getTime() - date2.getTime());

            int numOfDays = (int) (difference / (1000 * 60 * 60 * 24));
           //  int hours = (int) (difference / (1000 * 60 * 60));
             //int minutes = (int) (difference / (1000 * 60));
            int seconds = (int) (difference/ (1000));

           // long difference = date1.getTime() - date2.getTime();
            //int days = (int) (difference / (1000*60*60*24));
            //int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60 * 24));
            //int  min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);

             Long hours = TimeUnit.MILLISECONDS.toHours(difference);
           Long minutes = TimeUnit.MILLISECONDS.toMinutes(difference);

            if ((date1.getTime() > date2.getTime())) {

                   long difference2 = ((hours + minutes));
                    String dayDifference2 = Long.toString(difference2);
                  txttime.setText("-" + dayDifference2 + "mins");

            }


            if ((date1.getTime() < date2.getTime())){
              //  if (difference >=59) {

               //     //difference1 = ((hours + minutes) - 1);
                  long  difference1 = ((hours + minutes));
                    String dayDifference = Long.toString(difference1 );
                    txttime.setText("+" +dayDifference + "mins");

            }


        } catch (Exception exception) {

            Toast.makeText(Late.this, "Unable to find difference", Toast.LENGTH_SHORT).show();
        }

    }


    }

