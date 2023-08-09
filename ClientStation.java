package com.example.train;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ClientStation extends Fragment {
    private Button btnsave, btnUpdate, btnDate, btnTime, btnSchedule;
    TextView edtTime, edtDate, txttime, dbtrain, tD;
    DatabaseReference databaseReference;
    Spinner spinner, spinner1, spinner2;
    ListView list;

    List<Infodb>infos;

    EditText edtNumber, edtName, edtPlatform;
    private EditText mSearch;
    private ImageButton btnSearch;
    Calender calender;
    private int dia,mes,ano,hora,minutos;


    // ListView listschedule;
    //List<scheduledb> schedules;

    public static String infoId;
    private ClientStationViewModel mViewModel;

    public static ClientStation newInstance() {
        return new ClientStation();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.client_station_fragment, container, false);


        infos = new ArrayList<Infodb>();


        list = (ListView)root.findViewById(R.id.listStation);


        tD = (TextView)root.findViewById(R.id.actualDate);
        edtTime = (TextView)root.findViewById(R.id.txttime);
        edtDate = (TextView)root.findViewById(R.id.txtdate);
        //spinner2 = (Spinner) root.findViewById(R.id.spinner11);
        //spinner = (Spinner)root.findViewById(R.id.spinner12);
        //spinner1 = (Spinner) root.findViewById(R.id.spinner13);
        list = (ListView)root.findViewById(R.id.listStation);
        txttime = (TextView) root.findViewById(R.id.txtLate);

        spinner2 = (Spinner)root.findViewById(R.id.spinner11);
        spinner = (Spinner)root.findViewById(R.id.spinner12);
        spinner1 = (Spinner)root.findViewById(R.id.spinner13);

        dbtrain = (TextView)root.findViewById(R.id.dbtrain);

        mSearch = (EditText)root.findViewById(R.id.search_field);
        btnSearch = (ImageButton)root.findViewById(R.id.imageButton);

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("d/M/yyyy");
        String formattedDate = df.format(c);

        tD.setText(formattedDate);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = mSearch.getText().toString();
                // String searchText1 = mSearch.getText().toString();
                //String searchText2 = mSearch.getText().toString();



               firebaseSearch(searchText);
               mSearch.getText().clear();
                // firebaseSearch1(searchText);


            }
        });


        retrieve();


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ClientStationViewModel.class);
        // TODO: Use the ViewModel
    }


    @Override
    public void onStart() {
        super.onStart();

      //  databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        //Query query1 = databaseReference.orderByChild("date").equalTo(actualDate);
        final String actualDate = tD.getText().toString();
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query1 = databaseReference.orderByChild("date").equalTo(actualDate);
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                infos.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    keys.add(ds.getKey());
                    Infodb info = ds.getValue(Infodb.class);
                    infos.add(info);


                }

                clientStation_List infoAdapter = new clientStation_List(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime);
                list.setAdapter(infoAdapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch(String searchText){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("date").startAt(searchText).endAt(searchText + "").limitToFirst(15);


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                clientStation_List infoAdapter = new clientStation_List(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public void retrieve() {


            databaseReference = FirebaseDatabase.getInstance().getReference().child("StationName");

            Query query = databaseReference.orderByChild("stationName");
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final List<String> nameList = new ArrayList<>();
                    nameList.add(0, "Choose a Station");


                    final String BeauBassin[] = {"Choose Platform", "1(Trianon)", "2(Quatre-Bornes)"};
                    final String Coromandel[] = {"Choose Platform", "1(Port-Louis)", "6(Curepipe)"};
                    final String Curepipe[] = {"Choose Platform", "1(Coromandel)", "2(Port-Louis)"};
                    final String Floreal[] = {"Choose Platform", "1(Curepipe)", "2(Coromandal)"};
                    final String Phoenix[] = {"Choose Platform", "3(Port-Louis)", "2(Rose-Hill)"};
                    final String PortLouis[] = {"Choose Platform", "1(Rose-Hill)", "2(Curepipe)"};
                    final String QuatreBornes[] = {"Choose Platform", "1(St Jean)", "2(Vacoas)"};
                    final String RoseHill[] = {"Choose Platform", "1(Vacoas)", "2(St Jean)"};
                    final String Sadally[] = {"Choose Platform", "1(Phoenix)", "2(St Louis)"};
                    final String StJean[] = {"Choose Platform", "1(Floreal)", "2(Phoenix)"};
                    final String StLouis[] = {"Choose Platform", "1(Sadally)", "2(Trianon)"};
                    final String Trianon[] = {"Choose Platform", "1(Quatre-Bornes)", "2(Floreal)"};
                    final String Vacoas[] = {"Choose Platform", "1(St Louis)", "2(Beau-Bassin)"};

                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        String name = data.child("stationName").getValue(String.class);
                        nameList.add(name);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameList);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(arrayAdapter);

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String itemSelect = nameList.get(position);

                            if (parent.getItemAtPosition(position).equals("Choose a Station")) {

                              //  firebaseSearch1();
                                //onStart();
                            }

                            if (position == 1) {
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, BeauBassin);
                                spinner1.setAdapter(arrayAdapter);

                                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String itemSelect = BeauBassin[position];
                                        if (position == 1) {
                                            T1();
                                        }
                                        if (position == 2) {
                                            T2();
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            }
                            if (position == 2) {
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Coromandel);
                                spinner1.setAdapter(arrayAdapter);

                                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String itemSelect = Coromandel[position];
                                        if (position == 1) {
                                            T3();
                                        }
                                        if (position == 2) {
                                            T4();
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            }
                            if (position == 3) {
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Curepipe);
                                spinner1.setAdapter(arrayAdapter);
                                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String itemSelect = Curepipe[position];
                                        if (position == 1) {
                                            T5();
                                        }
                                        if (position == 2) {
                                            T6();
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            }
                            if (position == 4) {
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Floreal);
                                spinner1.setAdapter(arrayAdapter);
                                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String itemSelect = Floreal[position];
                                        if (position == 1) {
                                            T7();
                                        }
                                        if (position == 2) {
                                            T8();
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }
                            if (position == 5) {
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Phoenix);
                                spinner1.setAdapter(arrayAdapter);
                                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String itemSelect = Phoenix[position];
                                        if (position == 1) {
                                            T9();
                                        }
                                        if (position == 2) {
                                            T10();
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }
                            if (position == 6) {
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, PortLouis);
                                spinner1.setAdapter(arrayAdapter);
                                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String itemSelect = PortLouis[position];
                                        if (position == 1) {
                                            T11();
                                        }
                                        if (position == 2) {
                                            T12();
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }
                            if (position == 7) {
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, QuatreBornes);
                                spinner1.setAdapter(arrayAdapter);
                                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String itemSelect = QuatreBornes[position];
                                        if (position == 1) {
                                            T13();
                                        }
                                        if (position == 2) {
                                            T14();
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }
                            if (position == 8) {
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, RoseHill);
                                spinner1.setAdapter(arrayAdapter);
                                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String itemSelect = RoseHill[position];
                                        if (position == 1) {
                                            T15();
                                        }
                                        if (position == 2) {
                                            T16();
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }
                            if (position == 9) {
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Sadally);
                                spinner1.setAdapter(arrayAdapter);
                                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String itemSelect = Sadally[position];
                                        if (position == 1) {
                                            T17();
                                        }
                                        if (position == 2) {
                                            T18();
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }
                            if (position == 10) {
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, StJean);
                                spinner1.setAdapter(arrayAdapter);
                                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String itemSelect = StJean[position];
                                        if (position == 1) {
                                            T19();
                                        }
                                        if (position == 2) {
                                            T20();
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }
                            if (position == 11) {
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, StLouis);
                                spinner1.setAdapter(arrayAdapter);
                                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String itemSelect = StLouis[position];
                                        if (position == 1) {
                                            T21();
                                        }
                                        if (position == 2) {
                                            T22();
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }
                            if (position == 12) {
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Trianon);
                                spinner1.setAdapter(arrayAdapter);
                                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String itemSelect = Trianon[position];
                                        if (position == 1) {
                                            T23();
                                        }
                                        if (position == 2) {
                                            T24();
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }
                            if (position == 13) {
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Vacoas);
                                spinner1.setAdapter(arrayAdapter);
                                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String itemSelect = Vacoas[position];
                                        if (position == 1) {
                                            T25();
                                        }
                                        if (position == 2) {
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
        //  Query query = databaseReference.orderByChild("trainNumber").startAt("0001").endAt("0002");
        Query query = databaseReference.orderByChild("platformNumber").equalTo("1(Trianon)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                nameList.add(0, "Choose a Train");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);

                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // String itemSelect = nameList.get(position);
                        // if (parent.getItemAtPosition(position).equals("Choose a Station")){

                        // firebaseSearch1();
                        //   }

                        //if (position == 0){
                        // dbtrain.setText("0001");
                        dbtrain.setText(spinner2.getSelectedItem().toString());
                        if (mSearch.getText().toString().trim().length() == 0){
                            firebaseSearch2();
                        }else if (mSearch.getText().toString().trim().length() >0) {
                            firebaseSearch3();
                        }
                        //  }
                        //   if (position == 1){
                        //     Search2();
                        // }
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

    public void T2(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        // Query query = databaseReference.orderByChild("trainNumber").startAt("0003").endAt("0004");
        Query query = databaseReference.orderByChild("platformNumber").equalTo("2(Quatre-Bornes)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                nameList.add(0, "Choose a Train");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect = nameList.get(position);
                        //  if (position == 0){
                        //    firebaseSearch3();
                        //}
                        //if (position == 1){
                        //  firebaseSearch4();
                        //}

                        dbtrain.setText(spinner2.getSelectedItem().toString());
                        // firebaseSearch2();
                        if (mSearch.getText().toString().trim().length() == 0){
                            firebaseSearch2();
                        }else if (mSearch.getText().toString().trim().length() >0) {
                            firebaseSearch3();
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


    public void T3(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        //   Query query = databaseReference.orderByChild("trainNumber").startAt("0005").endAt("0006");
        Query query = databaseReference.orderByChild("platformNumber").equalTo("1(Port-Louis)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                nameList.add(0, "Choose a Train");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect = nameList.get(position);
                        //if (position == 0){
                        //  firebaseSearch5();
                        //}
                        //if (position == 1){
                        //  firebaseSearch6();
                        //}
                        dbtrain.setText(spinner2.getSelectedItem().toString());
                        //firebaseSearch2();
                        if (mSearch.getText().toString().trim().length() == 0){
                            firebaseSearch2();
                        }else if (mSearch.getText().toString().trim().length() >0) {
                            firebaseSearch3();
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

    public void T4(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        //Query query = databaseReference.orderByChild("trainNumber").startAt("0007").endAt("0008");
        Query query = databaseReference.orderByChild("platformNumber").equalTo("6(Curepipe)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                nameList.add(0, "Choose a Train");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect = nameList.get(position);
                        // if (position == 0){
                        //   firebaseSearch7();
                        //}
                        //if (position == 1){
                        //  firebaseSearch8();
                        //}
                        dbtrain.setText(spinner2.getSelectedItem().toString());
                        // firebaseSearch2();
                        if (mSearch.getText().toString().trim().length() == 0){
                            firebaseSearch2();
                        }else if (mSearch.getText().toString().trim().length() >0) {
                            firebaseSearch3();
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

    public void T5(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        //  Query query = databaseReference.orderByChild("trainNumber").startAt("0009").endAt("0010");
        Query query = databaseReference.orderByChild("platformNumber").equalTo("1(Coromandel)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                nameList.add(0, "Choose a Train");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect = nameList.get(position);
                        //if (position == 0){
                        //  firebaseSearch10();
                        //}
                        //if (position == 1){
                        //   firebaseSearch11();
                        //}
                        dbtrain.setText(spinner2.getSelectedItem().toString());
                        // firebaseSearch2();
                        if (mSearch.getText().toString().trim().length() == 0){
                            firebaseSearch2();
                        }else if (mSearch.getText().toString().trim().length() >0) {
                            firebaseSearch3();
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

    public void T6(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        // Query query = databaseReference.orderByChild("trainNumber").startAt("0011").endAt("0012");
        Query query = databaseReference.orderByChild("platformNumber").equalTo("2(Port-Louis)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                nameList.add(0, "Choose a Train");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect = nameList.get(position);
                        //if (position == 0){
                        //  firebaseSearch12();
                        //}
                        //if (position == 1){
                        //  firebaseSearch13();
                        //}
                        dbtrain.setText(spinner2.getSelectedItem().toString());
                        //firebaseSearch2();
                        if (mSearch.getText().toString().trim().length() == 0){
                            firebaseSearch2();
                        }else if (mSearch.getText().toString().trim().length() >0) {
                            firebaseSearch3();
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


    public void T7(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        //Query query = databaseReference.orderByChild("trainNumber").startAt("0013").endAt("0014");
        Query query = databaseReference.orderByChild("platformNumber").equalTo("1(Curepipe)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                nameList.add(0, "Choose a Train");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect = nameList.get(position);
                        //if (position == 0){
                        //  firebaseSearch14();
                        //}
                        //if (position == 1){
                        //  firebaseSearch15();
                        //}
                        dbtrain.setText(spinner2.getSelectedItem().toString());
                        //firebaseSearch2();
                        if (mSearch.getText().toString().trim().length() == 0){
                            firebaseSearch2();
                        }else if (mSearch.getText().toString().trim().length() >0) {
                            firebaseSearch3();
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

    public void T8(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        //  Query query = databaseReference.orderByChild("trainNumber").startAt("0015").endAt("0016");
        Query query = databaseReference.orderByChild("platformNumber").equalTo("2(Coromandal)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                nameList.add(0, "Choose a Train");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect = nameList.get(position);
                        //if (position == 0){
                        //  firebaseSearch16();
                        //}
                        //if (position == 1){
                        //  firebaseSearch17();
                        //}
                        dbtrain.setText(spinner2.getSelectedItem().toString());
                        //firebaseSearch2();
                        if (mSearch.getText().toString().trim().length() == 0){
                            firebaseSearch2();
                        }else if (mSearch.getText().toString().trim().length() >0) {
                            firebaseSearch3();
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

    public void T9(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        // Query query = databaseReference.orderByChild("trainNumber").startAt("0017").endAt("0018");
        Query query = databaseReference.orderByChild("platformNumber").equalTo("3(Port-Louis)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                nameList.add(0, "Choose a Train");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect = nameList.get(position);
                        //if (position == 0){
                        //  firebaseSearch18();
                        //}
                        //if (position == 1){
                        //  firebaseSearch19();
                        //}
                        dbtrain.setText(spinner2.getSelectedItem().toString());
                        //firebaseSearch2();
                        if (mSearch.getText().toString().trim().length() == 0){
                            firebaseSearch2();
                        }else if (mSearch.getText().toString().trim().length() >0) {
                            firebaseSearch3();
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

    public void T10(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        //Query query = databaseReference.orderByChild("trainNumber").startAt("0019").endAt("0020");
        Query query = databaseReference.orderByChild("platformNumber").equalTo("2(Rose-Hill)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                nameList.add(0, "Choose a Train");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect = nameList.get(position);
                        // if (position == 0){
                        //    firebaseSearch19();
                        //}
                        //if (position == 1){
                        //  firebaseSearch20();
                        //}
                        dbtrain.setText(spinner2.getSelectedItem().toString());
                        //firebaseSearch2();
                        if (mSearch.getText().toString().trim().length() == 0){
                            firebaseSearch2();
                        }else if (mSearch.getText().toString().trim().length() >0) {
                            firebaseSearch3();
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


    public void T11(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        //Query query = databaseReference.orderByChild("trainNumber").startAt("0021").endAt("0022");
        Query query = databaseReference.orderByChild("platformNumber").equalTo("1(Rose-Hill)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                nameList.add(0, "Choose a Train");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect = nameList.get(position);
                        // if (position == 0){
                        //   firebaseSearch21();
                        //}
                        //if (position == 1){
                        //  firebaseSearch22();
                        //}
                        dbtrain.setText(spinner2.getSelectedItem().toString());
                        //firebaseSearch2();
                        if (mSearch.getText().toString().trim().length() == 0){
                            firebaseSearch2();
                        }else if (mSearch.getText().toString().trim().length() >0) {
                            firebaseSearch3();
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

    public void T12(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        // Query query = databaseReference.orderByChild("trainNumber").startAt("0023").endAt("0024");
        Query query = databaseReference.orderByChild("platformNumber").equalTo("2(Curepipe)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                nameList.add(0, "Choose a Train");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect = nameList.get(position);
                        // if (position == 0){
                        //   firebaseSearch23();
                        //}
                        //if (position == 1){
                        //  firebaseSearch24();
                        //}
                        dbtrain.setText(spinner2.getSelectedItem().toString());
                        // firebaseSearch2();
                        if (mSearch.getText().toString().trim().length() == 0){
                            firebaseSearch2();
                        }else if (mSearch.getText().toString().trim().length() >0) {
                            firebaseSearch3();
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

    public void T13(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        //  Query query = databaseReference.orderByChild("trainNumber").startAt("0025").endAt("0026");
        Query query = databaseReference.orderByChild("platformNumber").equalTo("1(St Jean)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                nameList.add(0, "Choose a Train");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect = nameList.get(position);
                        //  if (position == 0){
                        //    firebaseSearch25();
                        //}
                        //if (position == 1){
                        //  firebaseSearch26();
                        //}
                        dbtrain.setText(spinner2.getSelectedItem().toString());
                        // firebaseSearch2();
                        if (mSearch.getText().toString().trim().length() == 0){
                            firebaseSearch2();
                        }else if (mSearch.getText().toString().trim().length() >0) {
                            firebaseSearch3();
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

    public void T14(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        //  Query query = databaseReference.orderByChild("trainNumber").startAt("0027").endAt("0028");
        Query query = databaseReference.orderByChild("platformNumber").equalTo("2(Vacoas)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                nameList.add(0, "Choose a Train");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect = nameList.get(position);
                        //if (position == 0){
                        //  firebaseSearch27();
                        //}
                        //if (position == 1){
                        //  firebaseSearch28();
                        //}
                        dbtrain.setText(spinner2.getSelectedItem().toString());
                        //firebaseSearch2();
                        if (mSearch.getText().toString().trim().length() == 0){
                            firebaseSearch2();
                        }else if (mSearch.getText().toString().trim().length() >0) {
                            firebaseSearch3();
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


    public void T15(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        // Query query = databaseReference.orderByChild("trainNumber").startAt("0029").endAt("0030");
        Query query = databaseReference.orderByChild("platformNumber").equalTo("1(Vacoas)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                nameList.add(0, "Choose a Train");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect = nameList.get(position);
                        //if (position == 0){
                        //  firebaseSearch29();
                        //}
                        //if (position == 1){
                        //  firebaseSearch30();
                        //}
                        dbtrain.setText(spinner2.getSelectedItem().toString());
                        //firebaseSearch2();
                        if (mSearch.getText().toString().trim().length() == 0){
                            firebaseSearch2();
                        }else if (mSearch.getText().toString().trim().length() >0) {
                            firebaseSearch3();
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

    public void T16(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        //Query query = databaseReference.orderByChild("trainNumber").startAt("0031").endAt("0032");
        Query query = databaseReference.orderByChild("platformNumber").equalTo("2(St Jean)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                nameList.add(0, "Choose a Train");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect = nameList.get(position);
                        // if (position == 0){
                        //   firebaseSearch31();
                        //}
                        //if (position == 1){
                        //  firebaseSearch32();
                        //}
                        dbtrain.setText(spinner2.getSelectedItem().toString());
                        //firebaseSearch2();
                        if (mSearch.getText().toString().trim().length() == 0){
                            firebaseSearch2();
                        }else if (mSearch.getText().toString().trim().length() >0) {
                            firebaseSearch3();
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


    public void T17(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        //Query query = databaseReference.orderByChild("trainNumber").startAt("0033").endAt("0034");
        Query query = databaseReference.orderByChild("platformNumber").equalTo("1(Phoenix)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                nameList.add(0, "Choose a Train");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect = nameList.get(position);
                        // if (position == 0){
                        //   firebaseSearch33();
                        //}
                        //if (position == 1){
                        //  firebaseSearch34();
                        //}
                        dbtrain.setText(spinner2.getSelectedItem().toString());
                        //  firebaseSearch2();
                        if (mSearch.getText().toString().trim().length() == 0){
                            firebaseSearch2();
                        }else if (mSearch.getText().toString().trim().length() >0) {
                            firebaseSearch3();
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

    public void T18(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        // Query query = databaseReference.orderByChild("trainNumber").startAt("0035").endAt("0036");
        Query query = databaseReference.orderByChild("platformNumber").equalTo("2(St Louis)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                nameList.add(0, "Choose a Train");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect = nameList.get(position);
                        // if (position == 0){
                        //   firebaseSearch35();
                        //}
                        //if (position == 1){
                        //  firebaseSearch36();
                        //}
                        dbtrain.setText(spinner2.getSelectedItem().toString());
                        //firebaseSearch2();
                        if (mSearch.getText().toString().trim().length() == 0){
                            firebaseSearch2();
                        }else if (mSearch.getText().toString().trim().length() >0) {
                            firebaseSearch3();
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


    public void T19(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        //  Query query = databaseReference.orderByChild("trainNumber").startAt("0037").endAt("00038");
        Query query = databaseReference.orderByChild("platformNumber").equalTo("1(Floreal)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                nameList.add(0, "Choose a Train");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect = nameList.get(position);
                        //  if (position == 0){
                        //    firebaseSearch37();
                        //}
                        //if (position == 1){
                        //  firebaseSearch38();
                        //}
                        dbtrain.setText(spinner2.getSelectedItem().toString());
                        // firebaseSearch2();
                        if (mSearch.getText().toString().trim().length() == 0){
                            firebaseSearch2();
                        }else if (mSearch.getText().toString().trim().length() >0) {
                            firebaseSearch3();
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

    public void T20(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        // Query query = databaseReference.orderByChild("trainNumber").startAt("0039").endAt("0040");
        Query query = databaseReference.orderByChild("platformNumber").equalTo("2(Phoenix)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                nameList.add(0, "Choose a Train");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect = nameList.get(position);
                        // if (position == 0){
                        //   firebaseSearch39();
                        //}
                        //if (position == 1){
                        //  firebaseSearch40();
                        //}
                        dbtrain.setText(spinner2.getSelectedItem().toString());
                        //firebaseSearch2();
                        if (mSearch.getText().toString().trim().length() == 0){
                            firebaseSearch2();
                        }else if (mSearch.getText().toString().trim().length() >0) {
                            firebaseSearch3();
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

    public void T21(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        //Query query = databaseReference.orderByChild("trainNumber").startAt("0041").endAt("0042");
        Query query = databaseReference.orderByChild("platformNumber").equalTo("1(Sadally)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                nameList.add(0, "Choose a Train");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect = nameList.get(position);
                        // if (position == 0){
                        //   firebaseSearch41();
                        //}
                        //if (position == 1){
                        //  firebaseSearch42();
                        //}
                        dbtrain.setText(spinner2.getSelectedItem().toString());
                        //firebaseSearch2();
                        if (mSearch.getText().toString().trim().length() == 0){
                            firebaseSearch2();
                        }else if (mSearch.getText().toString().trim().length() >0) {
                            firebaseSearch3();
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

    public void T22(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        // Query query = databaseReference.orderByChild("trainNumber").startAt("0043").endAt("0044");
        Query query = databaseReference.orderByChild("platformNumber").equalTo("2(Trianon)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                nameList.add(0, "Choose a Train");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect = nameList.get(position);
                        // if (position == 0){
                        //   firebaseSearch43();
                        //}
                        //if (position == 1){
                        //  firebaseSearch44();
                        //}
                        dbtrain.setText(spinner2.getSelectedItem().toString());
                        //firebaseSearch2();
                        if (mSearch.getText().toString().trim().length() == 0){
                            firebaseSearch2();
                        }else if (mSearch.getText().toString().trim().length() >0) {
                            firebaseSearch3();
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


    public void T23(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        //  Query query = databaseReference.orderByChild("trainNumber").startAt("0045").endAt("0046");
        Query query = databaseReference.orderByChild("platformNumber").equalTo("1(Quatre-Bornes)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                nameList.add(0, "Choose a Train");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect = nameList.get(position);
                        //if (position == 0){
                        //   firebaseSearch45();
                        //}
                        //if (position == 1){
                        //  firebaseSearch46();
                        //}
                        dbtrain.setText(spinner2.getSelectedItem().toString());
                        //firebaseSearch2();
                        if (mSearch.getText().toString().trim().length() == 0){
                            firebaseSearch2();
                        }else if (mSearch.getText().toString().trim().length() >0) {
                            firebaseSearch3();
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

    public void T24(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        //  Query query = databaseReference.orderByChild("trainNumber").startAt("0047").endAt("0048");
        Query query = databaseReference.orderByChild("platformNumber").equalTo("2(Floreal)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                nameList.add(0, "Choose a Train");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect = nameList.get(position);
                        // if (position == 0){
                        //   firebaseSearch47();
                        //}
                        //if (position == 1){
                        //  firebaseSearch48();
                        //}
                        dbtrain.setText(spinner2.getSelectedItem().toString());
                        // firebaseSearch2();
                        if (mSearch.getText().toString().trim().length() == 0){
                            firebaseSearch2();
                        }else if (mSearch.getText().toString().trim().length() >0) {
                            firebaseSearch3();
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

    public void T25(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        // Query query = databaseReference.orderByChild("trainNumber").startAt("0049").endAt("0050");
        Query query = databaseReference.orderByChild("platformNumber").equalTo("1(St Louis)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                nameList.add(0, "Choose a Train");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect = nameList.get(position);
                        // if (position == 0){
                        //   firebaseSearch49();
                        //}
                        //if (position == 1){
                        //  firebaseSearch50();
                        //}
                        dbtrain.setText(spinner2.getSelectedItem().toString());
                        //firebaseSearch2();
                        if (mSearch.getText().toString().trim().length() == 0){
                            firebaseSearch2();
                        }else if (mSearch.getText().toString().trim().length() >0) {
                            firebaseSearch3();
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

    public void T26(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Train");

        //  Query query = databaseReference.orderByChild("trainNumber").startAt("0051").endAt("0052");
        Query query = databaseReference.orderByChild("platformNumber").equalTo("2(Beau-Bassin)");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> nameList = new ArrayList<>();
                nameList.add(0, "Choose a Train");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect = nameList.get(position);
                        //  if (position == 0){
                        //    firebaseSearch51();
                        //}
                        //if (position == 1){
                        //  firebaseSearch52();
                        //}
                        dbtrain.setText(spinner2.getSelectedItem().toString());
                        //firebaseSearch2();
                        if (mSearch.getText().toString().trim().length() == 0){
                            firebaseSearch2();
                        }else if (mSearch.getText().toString().trim().length() >0) {
                            firebaseSearch3();
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


    private void firebaseSearch1(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Info");
        Query query = databaseReference.orderByChild("destinatonStation");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                infos.clear();

                for (DataSnapshot ds: dataSnapshot.getChildren()){

                    Infodb train = ds.getValue(Infodb.class);

                    infos.add(train);


                }


                clientStation_List infoAdapter = new clientStation_List(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime);
                list.setAdapter(infoAdapter);

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void firebaseSearch2(){
        dbtrain.setText(spinner2.getSelectedItem().toString());
        mSearch.getText().toString();
        final String trainNumber = dbtrain.getText().toString().trim();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Info");
        //Query query = databaseReference.orderByChild("trainNumber").equalTo("0001");

        Query query = databaseReference.orderByChild("trainNumber");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    // String train1 = dataSnapshot.child("trainNumber").getValue().toString();
                    String train1 = ds.child("trainNumber").getValue(String.class);
                    if (trainNumber.equals(train1)) {
                        Infodb train = ds.getValue(Infodb.class);

                        infos.add(train);

                    }

                }
                clientStation_List infoAdapter = new clientStation_List(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch3(){
        dbtrain.setText(spinner2.getSelectedItem().toString());
        final String search = mSearch.getText().toString();
        final String trainNumber = dbtrain.getText().toString().trim();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Info");
        //Query query = databaseReference.orderByChild("trainNumber").equalTo("0001");

        Query query = databaseReference.orderByChild("trainNumber");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                infos.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    // String train1 = dataSnapshot.child("trainNumber").getValue().toString();
                    String train1 = ds.child("trainNumber").getValue(String.class);
                    if (trainNumber.equals(train1)) {
                        Infodb train = ds.getValue(Infodb.class);
                        if (train.getDate().equals(search)) {

                            infos.add(train);
                        }
                    }

                }


                clientStation_List infoAdapter = new clientStation_List(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
