package com.example.train;

import androidx.lifecycle.ViewModelProviders;

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

import com.google.android.gms.common.api.Api;
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

public class ClientTrain extends Fragment {
    private Button btnsave, btnUpdate, btnDate, btnTime, btnSchedule;
    TextView edtTime, edtDate, txttime, spinner10, tD;
    DatabaseReference databaseReference;
    Spinner spinner, spinner1, spinner2;
    ListView list;
   // List<clientdb> infos;
    List<Infodb>infos;
    // List<stationdb>all;
    EditText edtNumber, edtName, edtPlatform;
    private EditText mSearch;
    private ImageButton btnSearch;
    Calender calender;
    private int dia,mes,ano,hora,minutos;


    // ListView listschedule;
    //List<scheduledb> schedules;

    public static String infoId;
    private ClientTrainViewModel mViewModel;

    public static ClientTrain newInstance() {
        return new ClientTrain();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.client_train_fragment, container, false);

        //infos = new ArrayList<clientdb>();
        infos = new ArrayList<Infodb>();

        tD = (TextView)root.findViewById(R.id.actualDate);
        list = (ListView)root.findViewById(R.id.listTrains);

        spinner = (Spinner)root.findViewById(R.id.spinner10);
        spinner2 = (Spinner)root.findViewById(R.id.spinner11);

        spinner1 = (Spinner)root.findViewById(R.id.spinner13);
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
               // firebaseSearch1(searchText);


            }
        });

      //  retrieve();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ClientTrainViewModel.class);
        // TODO: Use the ViewModel



    }

    @Override
    public void onStart() {
        super.onStart();

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

                    // Alldb f1 = ds.getValue(Alldb.class);
                    //all.add(f1);
                }

                // client infoAdapter = new client(ClientTrain.this, infos, databaseReference, edtDate, edtTime, txttime);
                //list.setAdapter(infoAdapter);


                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void firebaseSearch(String searchText){
        final String trainNumber = mSearch.getText().toString().trim();
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        //Query query = databaseReference.orderByChild("platformNumber").startAt(searchText).endAt(searchText + "").limitToFirst(15);
        Query query = databaseReference.orderByChild("Info");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    //String train1 = data.child("trainNumber").getValue(String.class);
                   // if (trainNumber.equals(train1)) {
                     //   Infodb info = data.getValue(Infodb.class);
                       // infos.add(info);

                    //}
                    Infodb info = data.getValue(Infodb.class);
                    if (info.getTrainNumber().equals(trainNumber)) {

                        infos.add(info);
                    }
                    else if (info.getPlatformNumber().equals(trainNumber)){
                        infos.add(info);
                    }
                    //infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);



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
                //final List<String> nameList = new ArrayList<>();
                final List<String> trains = new ArrayList<>();
                trains.add(0, "Choose a Train");

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("trainNumber").getValue(String.class);
                    //nameList.add(name);
                    trains.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, trains);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);


                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        firebaseSearch1();
                        String itemSelect = trains.get(position);
                        if (parent.getItemAtPosition(position).equals("Choose a Train")){
                            firebaseSearch1();
                        }
                        if (position == 1){
                            firebaseSearch2();
                        }
                        if (position == 2){
                            Search2();
                        }
                        if (position == 3){
                            firebaseSearch3();
                        }
                        if (position == 4){
                            firebaseSearch4();
                        }
                        if (position == 5){
                            firebaseSearch5();
                        }
                        if (position == 6){
                            firebaseSearch6();
                        }
                        if (position == 7){
                            firebaseSearch7();
                        }
                        if (position == 8){
                            firebaseSearch8();
                        }
                        if (position == 9){
                            firebaseSearch9();
                        }
                        if (position == 10){
                            firebaseSearch10();
                        }
                        if (position == 11){
                            firebaseSearch11();
                        }
                        if (position == 12){
                            firebaseSearch12();
                        }
                        if (position == 13){
                            firebaseSearch13();
                        }
                        if (position == 14){
                            firebaseSearch14();
                        }
                        if (position == 15){
                            firebaseSearch15();
                        }
                        if (position == 16){
                            firebaseSearch16();
                        }
                        if (position == 17){
                            firebaseSearch17();
                        }
                        if (position == 18){
                            firebaseSearch18();
                        }
                        if (position == 19){
                            firebaseSearch19();
                        }
                        if (position == 20){
                            firebaseSearch20();
                        }
                        if (position == 21){
                            firebaseSearch21();
                        }
                        if (position == 22){
                            firebaseSearch22();
                        }
                        if (position == 23){
                            firebaseSearch23();
                        }
                        if (position == 24){
                            firebaseSearch24();
                        }
                        if (position == 25){
                            firebaseSearch25();
                        }
                        if (position == 26){
                            firebaseSearch26();
                        }
                        if (position == 27){
                            firebaseSearch27();
                        }
                        if (position == 28){
                            firebaseSearch28();
                        }
                        if (position == 29){
                            firebaseSearch29();
                        }
                        if (position == 30){
                            firebaseSearch30();
                        }
                        if (position == 31){
                            firebaseSearch31();
                        }
                        if (position == 32){
                            firebaseSearch32();
                        }
                        if (position == 33){
                            firebaseSearch33();
                        }
                        if (position == 34){
                            firebaseSearch34();
                        }
                        if (position == 35){
                            firebaseSearch35();
                        }
                        if (position == 36){
                            firebaseSearch36();
                        }
                        if (position == 37){
                            firebaseSearch37();
                        }
                        if (position == 38){
                            firebaseSearch38();
                        }
                        if (position == 39){
                            firebaseSearch39();
                        }
                        if (position == 40){
                            firebaseSearch40();
                        }
                        if (position == 41){
                            firebaseSearch41();
                        }
                        if (position == 42){
                            firebaseSearch42();
                        }
                        if (position == 43){
                            firebaseSearch43();
                        }
                        if (position == 44){
                            firebaseSearch44();
                        }
                        if (position == 45){
                            firebaseSearch45();
                        }
                        if (position == 46){
                            firebaseSearch46();
                        }
                        if (position == 47){
                            firebaseSearch47();
                        }
                        if (position == 48){
                            firebaseSearch48();
                        }
                        if (position == 49){
                            firebaseSearch49();
                        }
                        if (position == 50){
                            firebaseSearch50();
                        }
                        if (position == 51){
                            firebaseSearch51();
                        }
                        if (position == 52){
                            firebaseSearch52();
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
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void firebaseSearch2(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0001");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void Search2(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0002");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch3(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0003");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch4(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0004");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void firebaseSearch5(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0005");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch6(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0006");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch7(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0007");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch8(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0008");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch9(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0009");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch10(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0010");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch11(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0011");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch12(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0012");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch13(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0013");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch14(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0014");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch15(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0015");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void firebaseSearch16(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0016");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch17(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0017");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch18(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0018");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch19(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0019");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void firebaseSearch20(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0020");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void firebaseSearch21(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0021");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch22(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0022");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch23(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0023");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch24(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0024");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void firebaseSearch25(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0025");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch26(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0026");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch27(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0027");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch28(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0028");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch29(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0029");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch30(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0030");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch31(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0031");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch32(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0032");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch33(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0033");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch34(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0034");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch35(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0035");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch36(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0036");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch37(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0037");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch38(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0038");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void firebaseSearch39(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0039");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch40(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0040");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void firebaseSearch41(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0041");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch42(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0042");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch43(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0043");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch44(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0044");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch45(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0045");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch46(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0046");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch47(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0047");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch48(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0048");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch49(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0049");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch50(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0050");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firebaseSearch51(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0051");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void firebaseSearch52(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Info");
        Query query = databaseReference.orderByChild("trainNumber").equalTo("0052");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infos.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Infodb info = data.getValue(Infodb.class);
                    infos.add(info);
                }

                client infoAdapter = new client(getActivity(), infos, databaseReference, edtDate, edtTime, spinner, spinner1, spinner2, txttime, spinner10);
                list.setAdapter(infoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
