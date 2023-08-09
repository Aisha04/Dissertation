package com.example.train;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ScheduleUpdate extends AppCompatActivity implements TimerPickerFragment.TimePickerListener{
    private Button btnupdate;
    ImageButton btnTime;
    TextView edtTime, edtDate, txttime, txtTime;
    DatabaseReference databaseReference;
    Spinner spinner, spinner1, spinner2, spinner10;
    String id, stationName, trainNumber, date, arrivalTime,platformNumber, late, DestinationStation;
    private Calendar calendar;
    Context mContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_update);


        txtTime = (TextView)findViewById(R.id.txtlate3);
        btnTime = (ImageButton)findViewById(R.id.btnTime);
        btnupdate = (Button)findViewById(R.id.btnupdate4);
        edtTime = (TextView)findViewById(R.id.time);
        edtDate = (TextView)findViewById(R.id.date);
        spinner1 = (Spinner) findViewById(R.id.spinner2);
        spinner = (Spinner)findViewById(R.id.spinner1);
        spinner2 = (Spinner)findViewById(R.id.spinner);
        txttime = (TextView)findViewById(R.id.txtlate);
        spinner10 = (Spinner)findViewById(R.id.spinner10);




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
        txtTime.setText("");
        //txttime.setText(late);
       // String trainNumber = spinner.getSelectedItem().toString();
        //String stationName = spinner1.getSelectedItem().toString();
        //spinner.getSelectedItem(stationName);

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
                        edtTime.setText(hourString + ":" + minuteSting);


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
                String trainNumber = spinner1.getSelectedItem().toString();
                String date = edtDate.getText().toString();
                String arrivalTime = edtTime.getText().toString();
                String DestinationStation = spinner.getSelectedItem().toString();
                String platformNumber = spinner2.getSelectedItem().toString();
                String late = txtTime.getText().toString();
                String stationName = spinner10.getSelectedItem().toString();

                // Alldb station = new Alldb(id,StationNames,PlatformNumber);
                Infodb info = new Infodb(id, trainNumber,  date, arrivalTime, stationName, platformNumber, late, DestinationStation);
                databaseReference.setValue(info);
                Toast.makeText(ScheduleUpdate.this, "Successfully update", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Trains.class));

            }
        });


        station();
        retrieve();
        retrieve1();
        T1(); T2(); T3(); T4(); T5(); T6(); T7(); T8(); T9(); T10();
        T11(); T12(); T13(); T14(); T15(); T16(); T17(); T18(); T19(); T20();
        T21(); T22(); T23(); T24(); T25(); T26();

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        edtTime.setText(hourOfDay + ":" + minute);
    }


    public void station(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("StationName");
        Query query1 = databaseReference.orderByChild("stationName");
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("stationName").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner10.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void retrieve(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        Query query = databaseReference.orderByChild("trainNumber");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void retrieve1() {
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
                final String Phoenix[] = {"1(Port-Louis)", "2(Rose-Hill)"};
                final String PortLouis[] = {"1(Rose-Hill)", "2(Curepipe)"};
                final String QuatreBornes[] = {"1(St Jean)", "2(Vacoas)"};
                final String RoseHill[] = {"1(Vacoas)", "2(St Jean)"};
                final String Sadally[] = {"1(Phoenix)", "2(St Louis)"};
                final String StJean[] = {"1(Floreal)", "2(Phoenix"};
                final String StLouis[] = {"1(Sadally)", "2(Trianon)"};
                final String Trianon[] = {"1(Quatre-Bornes)", "2(Floreal)"};
                final String Vacoas[] = {"1(St Louis)", "2(Beau-Bassin)"};

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("stationName").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect = nameList.get(position);

                        if (position == 0) {
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, BeauBassin);
                            spinner1.setAdapter(arrayAdapter);

                            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String itemSelect = BeauBassin[position];
                                    if (position == 0){
                                        T1();
                                    }
                                    if (position == 1){
                                        T2();
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }
                        if (position == 1) {
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, Coromandel);
                            spinner1.setAdapter(arrayAdapter);

                            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String itemSelect = Coromandel[position];
                                    if (position == 0){
                                        T3();
                                    }
                                    if (position == 1){
                                        T4();
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }
                        if (position == 2) {
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, Curepipe);
                            spinner1.setAdapter(arrayAdapter);
                            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String itemSelect = Curepipe[position];
                                    if (position == 0){
                                        T5();
                                    }
                                    if (position == 1){
                                        T6();
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }
                        if (position == 3) {
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, Floreal);
                            spinner1.setAdapter(arrayAdapter);
                            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String itemSelect = Floreal[position];
                                    if (position == 0){
                                        T7();
                                    }
                                    if (position == 1){
                                        T8();
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                        if (position == 4) {
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, Phoenix);
                            spinner1.setAdapter(arrayAdapter);
                            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String itemSelect = Phoenix[position];
                                    if (position == 0){
                                        T9();
                                    }
                                    if (position == 1){
                                        T10();
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                        if (position == 5) {
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, PortLouis);
                            spinner1.setAdapter(arrayAdapter);
                            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                        if (position == 6) {
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, QuatreBornes);
                            spinner1.setAdapter(arrayAdapter);
                            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String itemSelect = QuatreBornes[position];
                                    if (position == 0){
                                        T13();
                                    }
                                    if (position == 1){
                                        T14();
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                        if (position == 7) {
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, RoseHill);
                            spinner1.setAdapter(arrayAdapter);
                            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String itemSelect = RoseHill[position];
                                    if (position == 0){
                                        T15();
                                    }
                                    if (position == 1){
                                        T16();
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                        if (position == 8) {
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, Sadally);
                            spinner1.setAdapter(arrayAdapter);
                            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String itemSelect = Sadally[position];
                                    if (position == 0){
                                        T17();
                                    }
                                    if (position == 1){
                                        T18();
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                        if (position == 9) {
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, StJean);
                            spinner1.setAdapter(arrayAdapter);
                            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String itemSelect = StJean[position];
                                    if (position == 0){
                                        T19();
                                    }
                                    if (position == 1){
                                        T20();
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                        if (position == 10) {
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, StLouis);
                            spinner1.setAdapter(arrayAdapter);
                            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String itemSelect = StLouis[position];
                                    if (position == 0){
                                        T21();
                                    }
                                    if (position == 1){
                                        T22();
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                        if (position == 11) {
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, Trianon);
                            spinner1.setAdapter(arrayAdapter);
                            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String itemSelect = Trianon[position];
                                    if (position == 0){
                                        T23();
                                    }
                                    if (position == 1){
                                        T24();
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                        if (position == 12) {
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, Vacoas);
                            spinner1.setAdapter(arrayAdapter);
                            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String itemSelect = Vacoas[position];
                                    if (position == 0){
                                        T25();
                                    }
                                    if (position == 1){
                                        T26();
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
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

    public void T1(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        Query query = databaseReference.orderByChild("trainNumber").startAt("0001").endAt("0002");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void T2(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        Query query = databaseReference.orderByChild("trainNumber").startAt("0003").endAt("0004");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void T3(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        Query query = databaseReference.orderByChild("trainNumber").startAt("0005").endAt("0006");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void T4(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        Query query = databaseReference.orderByChild("trainNumber").startAt("0007").endAt("0008");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void T5(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        Query query = databaseReference.orderByChild("trainNumber").startAt("0009").endAt("0010");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void T6(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        Query query = databaseReference.orderByChild("trainNumber").startAt("0011").endAt("0012");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void T7(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        Query query = databaseReference.orderByChild("trainNumber").startAt("0013").endAt("0014");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void T8(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        Query query = databaseReference.orderByChild("trainNumber").startAt("0015").endAt("0016");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void T9(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        Query query = databaseReference.orderByChild("trainNumber").startAt("0017").endAt("0018");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void T10(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        Query query = databaseReference.orderByChild("trainNumber").startAt("0019").endAt("0020");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

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
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void T13(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        Query query = databaseReference.orderByChild("trainNumber").startAt("0025").endAt("0026");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void T14(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        Query query = databaseReference.orderByChild("trainNumber").startAt("0027").endAt("0028");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void T15(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        Query query = databaseReference.orderByChild("trainNumber").startAt("0029").endAt("0030");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void T16(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        Query query = databaseReference.orderByChild("trainNumber").startAt("0031").endAt("0032");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void T17(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        Query query = databaseReference.orderByChild("trainNumber").startAt("0033").endAt("0034");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void T18(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        Query query = databaseReference.orderByChild("trainNumber").startAt("0035").endAt("0036");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void T19(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        Query query = databaseReference.orderByChild("trainNumber").startAt("0037").endAt("00038");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void T20(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        Query query = databaseReference.orderByChild("trainNumber").startAt("0039").endAt("0040");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void T21(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        Query query = databaseReference.orderByChild("trainNumber").startAt("0041").endAt("0042");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void T22(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        Query query = databaseReference.orderByChild("trainNumber").startAt("0043").endAt("0044");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void T23(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        Query query = databaseReference.orderByChild("trainNumber").startAt("0045").endAt("0046");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void T24(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        Query query = databaseReference.orderByChild("trainNumber").startAt("0047").endAt("0048");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void T25(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        Query query = databaseReference.orderByChild("trainNumber").startAt("0049").endAt("0050");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void T26(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        Query query = databaseReference.orderByChild("trainNumber").startAt("0051").endAt("0052");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScheduleUpdate.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
