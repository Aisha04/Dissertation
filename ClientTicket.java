package com.example.train;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class ClientTicket extends AppCompatActivity {
    private Button btncalculate, btncan;
    TextView txtprice, txtpeople, txtplat;
    DatabaseReference databaseReference;
    Spinner spinner, spinner1, spinner2, spinner3, spinner4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_ticket);


        spinner2 = (Spinner) findViewById(R.id.selector);
       // spinner = (Spinner)findViewById(R.id.station1);
        //spinner1 = (Spinner) findViewById(R.id.platform);
        spinner3 = (Spinner)findViewById(R.id.station2);
        spinner4 = (Spinner) findViewById(R.id.platform2);
        txtprice = (TextView) findViewById(R.id.txtprice);
        txtpeople = (TextView) findViewById(R.id.people);
        txtplat = (TextView)findViewById(R.id.plat);
        btncalculate = (Button)findViewById(R.id.btnCal);
        ImageView iv_background = findViewById(R.id.iv_background);
        btncan = (Button)findViewById(R.id.btnCan);

        AnimationDrawable animationDrawable = (AnimationDrawable) iv_background.getDrawable();
        animationDrawable.start();
        //retrieve1();
        retrieve();
        retrieve2();

        btncan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Main2Activity.class));
            }
        });

        btncalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //txtpeople.setText("Adult");
               // if ( txtplat.getText().toString().equals("1(Trianon)") && txtpeople.getText().toString().equals("Adult")) {
                 // txtprice.setText("30");
                //}
                if ( txtplat.getText().toString().equals("1(Trianon)") && txtpeople.getText().toString().equals("Adult")) {
                    txtprice.clearComposingText();
                    txtprice.setText("35");
                }

                if ( txtplat.getText().toString().equals("2(Quatre-Bornes)") && txtpeople.getText().toString().equals("Adult")) {
                    txtprice.clearComposingText();
                    txtprice.setText("30");
                }
                if ( txtplat.getText().toString().equals("1(Port-Louis)") && txtpeople.getText().toString().equals("Adult")) {
                    txtprice.setText("20");
                }
                if ( txtplat.getText().toString().equals("6(Curepipe)") && txtpeople.getText().toString().equals("Adult")) {
                    txtprice.setText("35");
                }
                if ( txtplat.getText().toString().equals("1(Coromandel)") && txtpeople.getText().toString().equals("Adult")) {
                    txtprice.setText("35");
                }
                if ( txtplat.getText().toString().equals("2(Port-Louis)") && txtpeople.getText().toString().equals("Adult")) {
                    txtprice.setText("40");
                }
                if ( txtplat.getText().toString().equals("1(Curepipe)") && txtpeople.getText().toString().equals("Adult")) {
                    txtprice.setText("15");
                }
                if ( txtplat.getText().toString().equals("2(Coromandal)") && txtpeople.getText().toString().equals("Adult")) {
                    txtprice.setText("35");
                }
                if ( txtplat.getText().toString().equals("1(Port-Louis)") && txtpeople.getText().toString().equals("Adult")) {
                    txtprice.setText("35");
                }
                if ( txtplat.getText().toString().equals("2(Rose-Hill)") && txtpeople.getText().toString().equals("Adult")) {
                    txtprice.setText("30");
                }
                if ( txtplat.getText().toString().equals("1(Rose-Hill)") && txtpeople.getText().toString().equals("Adult")) {
                    txtprice.setText("35");
                }
                if ( txtplat.getText().toString().equals("2(Curepipe)") && txtpeople.getText().toString().equals("Adult")) {
                    txtprice.setText("40");
                }
                if ( txtplat.getText().toString().equals("1(St Jean)") && txtpeople.getText().toString().equals("Adult")) {
                    txtprice.setText("20");
                }
                if ( txtplat.getText().toString().equals("2(Vacoas)") && txtpeople.getText().toString().equals("Adult")) {
                    txtprice.setText("30");
                }
                if ( txtplat.getText().toString().equals("1(Vacoas)") && txtpeople.getText().toString().equals("Adult")) {
                    txtprice.setText("35");
                }
                if ( txtplat.getText().toString().equals("2(St Jean)") && txtpeople.getText().toString().equals("Adult")) {
                    txtprice.setText("20");
                }
                if ( txtplat.getText().toString().equals("1(Phoenix)") && txtpeople.getText().toString().equals("Adult")) {
                    txtprice.setText("30");
                }
                if ( txtplat.getText().toString().equals("2(St Louis)") && txtpeople.getText().toString().equals("Adult")) {
                    txtprice.setText("35");
                }
                if ( txtplat.getText().toString().equals("1(Floreal)") && txtpeople.getText().toString().equals("Adult")) {
                    txtprice.setText("30");
                }
                if ( txtplat.getText().toString().equals("2(Phoenix)") && txtpeople.getText().toString().equals("Adult")) {
                    txtprice.setText("25");
                }
                if ( txtplat.getText().toString().equals("1(Sadally)") && txtpeople.getText().toString().equals("Adult")) {
                    txtprice.setText("35");
                }
                if ( txtplat.getText().toString().equals("2(Trianon)") && txtpeople.getText().toString().equals("Adult")) {
                    txtprice.setText("30");
                }
                if ( txtplat.getText().toString().equals("1(Quatre-Bornes)") && txtpeople.getText().toString().equals("Adult")) {
                    txtprice.setText("25");
                }
                if ( txtplat.getText().toString().equals("2(Floreal)") && txtpeople.getText().toString().equals("Adult")) {
                    txtprice.setText("30");
                }
                if ( txtplat.getText().toString().equals("1(St Louis)") && txtpeople.getText().toString().equals("Adult")) {
                    txtprice.setText("35");
                }
                if ( txtplat.getText().toString().equals("2(Beau-Bassin)") && txtpeople.getText().toString().equals("Adult")) {
                    txtprice.setText("25");
                }
                if (txtpeople.getText().toString().equals("Student")){
                    txtprice.setText("20");
                }
                if ( txtplat.getText().toString().equals("1(St Jean)") && txtpeople.getText().toString().equals("Student")) {
                    txtprice.setText("10");
                }
                if ( txtplat.getText().toString().equals("1(Curepipe)") && txtpeople.getText().toString().equals("Student")) {
                    txtprice.setText("10");
                }
                if ( txtplat.getText().toString().equals("2(St Jean)") && txtpeople.getText().toString().equals("Student")) {
                    txtprice.setText("15");
                }
                if (txtpeople.getText().toString().equals("Child")){
                    txtprice.setText("15");
                }
                if ( txtplat.getText().toString().equals("1(St Jean)") && txtpeople.getText().toString().equals("Student")) {
                    txtprice.setText("7");
                }
                if ( txtplat.getText().toString().equals("1(Curepipe)") && txtpeople.getText().toString().equals("Student")) {
                    txtprice.setText("7");
                }
                if ( txtplat.getText().toString().equals("2(St Jean)") && txtpeople.getText().toString().equals("Student")) {
                    txtprice.setText("10");
                }

            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent i= new Intent(ClientTicket.this,Main2Activity.class);
        startActivity(i);
        finish();
    }

    public  void  retrieve(){

       // final String People[] = {"--Select one--", "Adult", "Student", "Child"};
        final List<String> People = new ArrayList<>();

        People.clear();

        People.add(0, "--Select one--");
        People.add(1, "Adult");
        People.add(2, "Student");
        People.add(3, "Child");

        final ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(ClientTicket.this, android.R.layout.simple_spinner_item, People);
        spinner2.setAdapter(arrayAdapter3);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // People.clear();
              //  String itemSelect3 = People[position];
                String itemSelect3 = People.get(position);
                if (parent.getItemAtPosition(position).equals("--Select one--")){

              }
                if (position == 1){
                    txtpeople.setText("Adult");

                }
                if (position == 2){

                    txtpeople.setText("Student");

                }
                if (position == 3){

                    txtpeople.setText("Child");
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    public void firebaseSearch1(){
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
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ClientTicket.this, android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner3.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void retrieve2() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("StationName");

        Query query = databaseReference.orderByChild("stationName");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList2 = new ArrayList<>();
                nameList2.add(0, "Choose a Station");


                final String BeauBassin[] = {"1(Trianon)", "2(Quatre-Bornes)"};
                final String Coromandel[] = {"1(Port-Louis)", "6(Curepipe)"};
                final String Curepipe[] = {"1(Coromandel)", "2(Port-Louis)"};
                final String Floreal[] = {"1(Curepipe)", "2(Coromandal)"};
                final String Phoenix[] = {"1(Port-Louis)", "2(Rose-Hill)"};
                final String PortLouis[] = {"1(Rose-Hill)", "2(Curepipe)"};
                final String QuatreBornes[] = {"1(St Jean)", "2(Vacoas)"};
                final String RoseHill[] = {"1(Vacoas)", "2(St Jean)"};
                final String Sadally[] = {"1(Phoenix)", "2(St Louis)"};
                final String StJean[] = {"1(Floreal)", "2(Phoenix)"};
                final String StLouis[] = {"1(Sadally)", "2(Trianon)"};
                final String Trianon[] = {"1(Quatre-Bornes)", "2(Floreal)"};
                final String Vacoas[] = {"1(St Louis)", "2(Beau-Bassin)"};

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("stationName").getValue(String.class);
                    nameList2.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ClientTicket.this, android.R.layout.simple_spinner_item, nameList2);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner3.setAdapter(arrayAdapter);


                spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect2 = nameList2.get(position);

                        if (parent.getItemAtPosition(position).equals("Choose a Station")){

                           // firebaseSearch1();
                        }

                        if (position == 1) {
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ClientTicket.this, android.R.layout.simple_spinner_item, BeauBassin);
                            spinner4.setAdapter(arrayAdapter);
                            spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                 //   String itemSelect3 = nameList2.get(position).toString();
                                    String itemSelect3 = BeauBassin[position].toString();
                                    txtplat.setText(itemSelect3);
                                    //txtprice.setText("");
                                  // if(position == 0){
                                    //    if (txtpeople.getText().toString().equals("Adult")) {

                                      //      txtprice.setText("30");
                                        //}

                                    //}
                                    //if(position == 1){

                                      //  txtprice.setText("25");

                                    //}

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });





                        }
                        if (position == 2) {
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ClientTicket.this, android.R.layout.simple_spinner_item, Coromandel);
                            spinner4.setAdapter(arrayAdapter);


                            spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    //   String itemSelect3 = nameList2.get(position).toString();
                                    String itemSelect3 = Coromandel[position].toString();
                                    txtplat.setText(itemSelect3);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });


                        }
                        if (position == 3) {
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ClientTicket.this, android.R.layout.simple_spinner_item, Curepipe);
                            spinner4.setAdapter(arrayAdapter);
                            spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    //   String itemSelect3 = nameList2.get(position).toString();
                                    String itemSelect3 = Curepipe[position].toString();
                                    txtplat.setText(itemSelect3);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }
                        if (position == 4) {
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ClientTicket.this, android.R.layout.simple_spinner_item, Floreal);
                            spinner4.setAdapter(arrayAdapter);
                            spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    //   String itemSelect3 = nameList2.get(position).toString();
                                    String itemSelect3 = Floreal[position].toString();
                                    txtplat.setText(itemSelect3);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }
                        if (position == 5) {
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ClientTicket.this, android.R.layout.simple_spinner_item, Phoenix);
                            spinner4.setAdapter(arrayAdapter);
                            spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    //   String itemSelect3 = nameList2.get(position).toString();
                                    String itemSelect3 = Phoenix[position].toString();
                                    txtplat.setText(itemSelect3);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }
                        if (position == 6) {
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ClientTicket.this, android.R.layout.simple_spinner_item, PortLouis);
                            spinner4.setAdapter(arrayAdapter);
                            spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    //   String itemSelect3 = nameList2.get(position).toString();
                                    String itemSelect3 = PortLouis[position].toString();
                                    txtplat.setText(itemSelect3);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }
                        if (position == 7) {
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ClientTicket.this, android.R.layout.simple_spinner_item, QuatreBornes);
                            spinner4.setAdapter(arrayAdapter);
                            spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    //   String itemSelect3 = nameList2.get(position).toString();
                                    String itemSelect3 = QuatreBornes[position].toString();
                                    txtplat.setText(itemSelect3);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }
                        if (position == 8) {
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ClientTicket.this, android.R.layout.simple_spinner_item, RoseHill);
                            spinner4.setAdapter(arrayAdapter);
                            spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    //   String itemSelect3 = nameList2.get(position).toString();
                                    String itemSelect3 = RoseHill[position].toString();
                                    txtplat.setText(itemSelect3);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }
                        if (position == 9) {
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ClientTicket.this, android.R.layout.simple_spinner_item, Sadally);
                            spinner4.setAdapter(arrayAdapter);
                            spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    //   String itemSelect3 = nameList2.get(position).toString();
                                    String itemSelect3 = Sadally[position].toString();
                                    txtplat.setText(itemSelect3);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }
                        if (position == 10) {
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ClientTicket.this, android.R.layout.simple_spinner_item, StJean);
                            spinner4.setAdapter(arrayAdapter);
                            spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    //   String itemSelect3 = nameList2.get(position).toString();
                                    String itemSelect3 = StJean[position].toString();
                                    txtplat.setText(itemSelect3);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }
                        if (position == 11) {
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ClientTicket.this, android.R.layout.simple_spinner_item, StLouis);
                            spinner4.setAdapter(arrayAdapter);
                            spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    //   String itemSelect3 = nameList2.get(position).toString();
                                    String itemSelect3 = StLouis[position].toString();
                                    txtplat.setText(itemSelect3);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }
                        if (position == 12) {
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ClientTicket.this, android.R.layout.simple_spinner_item, Trianon);
                            spinner4.setAdapter(arrayAdapter);
                            spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    //   String itemSelect3 = nameList2.get(position).toString();
                                    String itemSelect3 = Trianon[position].toString();
                                    txtplat.setText(itemSelect3);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });


                        }
                        if (position == 13) {
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ClientTicket.this, android.R.layout.simple_spinner_item, Vacoas);
                            spinner4.setAdapter(arrayAdapter);
                            spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    //   String itemSelect3 = nameList2.get(position).toString();
                                    String itemSelect3 = Vacoas[position].toString();
                                    txtplat.setText(itemSelect3);

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

}
