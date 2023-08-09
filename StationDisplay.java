package com.example.train;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class StationDisplay extends AppCompatActivity {
    private Button btn_pl, b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12;
    TextView edtTime, edtDate, txttime;
    DatabaseReference databaseReference;
    Spinner spinner, spinner1, spinner2;
    ListView list;
    List<Infodb> infos;
    // List<stationdb>all;
    EditText edtNumber, edtName, edtPlatform;
    private EditText mSearch;
    private ImageButton btnSearch;
    public static String infoId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_display);

        infos = new ArrayList<Infodb>();

       btn_pl = (Button) findViewById(R.id.btn_pl);
       b1 = (Button)findViewById(R.id.btnstlouis);
       b2 = (Button)findViewById(R.id.btncoro);
       b3 =(Button)findViewById(R.id.btnbeau);
       b4 = (Button)findViewById(R.id.btnrose);
       b5=(Button)findViewById(R.id.btnquatre);
       b6=(Button)findViewById(R.id.stJean);
       b7= (Button)findViewById(R.id.btntrianon);
       b8=(Button)findViewById(R.id.btnphoenix);
       b9=(Button)findViewById(R.id.btnvacoas);
       b10=(Button)findViewById(R.id.btnSadally);
       b11=(Button)findViewById(R.id.btnfloreal);
       b12=(Button)findViewById(R.id.btnCurepipe);

       btn_pl.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(), StationPortLouis.class));
           }
       });

       b1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(), StationStLouis.class));
           }
       });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StationCoromodal.class));
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StationBeauBassin.class));
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StationRosehill.class));
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StationQuatre.class));
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StationStJean.class));
            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StationTrianon.class));
            }
        });

        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StationPheonix.class));
            }
        });

        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StationVacoas.class));
            }
        });

        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StationSadally.class));
            }
        });

        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StationFloreal.class));
            }
        });

        b12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StationCurepipe.class));
            }
        });

    }
}
