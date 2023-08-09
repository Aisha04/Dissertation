package com.example.train;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class DriverMap extends AppCompatActivity {
    ImageButton btnport, btnbeau, btncoromandel, btnfloreal, btnphoenix, btncurepipe, btnquatre, btnrosehill,btnsadally, btnstjean, btnstlouis, btntrianon, btnvacoas, btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_map);

        btnback = (ImageButton)findViewById(R.id.back);
        btnport = (ImageButton)findViewById(R.id.portlouis);
        btnbeau = (ImageButton)findViewById(R.id.beaubassin);
        btncoromandel = (ImageButton)findViewById(R.id.coromandel);
        btnfloreal = (ImageButton)findViewById(R.id.floreal);
        btnphoenix = (ImageButton)findViewById(R.id.phoenix);
        btncurepipe = (ImageButton)findViewById(R.id.curepipe);
        btnquatre = (ImageButton)findViewById(R.id.quatre);
        btnrosehill = (ImageButton)findViewById(R.id.rosehill);
        btnsadally = (ImageButton)findViewById(R.id.sadally);
        btnstjean = (ImageButton)findViewById(R.id.stjean);
        btnstlouis = (ImageButton)findViewById(R.id.stlouis);
        btntrianon = (ImageButton)findViewById(R.id.trianon);
        btnvacoas = (ImageButton)findViewById(R.id.vacoas);

        // Bundle bundle = getIntent().getExtras();
        //if(bundle != null) {
        //   if (bundle.getString("some") != null) {
        //     Toast.makeText(getApplicationContext(), "data:" + bundle.getString("some"), Toast.LENGTH_SHORT).show();

        //}
        //}



        btnport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PortLouis3.class));
            }
        });

        btnbeau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BeauBassin2.class));
            }
        });

        btncoromandel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Coromandel2.class));
            }
        });

        btnfloreal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Floreal2.class));
            }
        });

        btnphoenix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Phoenix2.class));
            }
        });

        btncurepipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Curepipe2.class));
            }
        });

        btnquatre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), QuatreBornes2.class));
            }
        });

        btnrosehill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RoseHill2.class));
            }
        });

        btnsadally.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Sadally2.class));
            }
        });

        btnstjean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StJean2.class));
            }
        });

        btnstlouis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StLouis2.class));
            }
        });

        btntrianon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Trianon2.class));
            }
        });

        btnvacoas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Vacoas2.class));
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Main3Activity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i= new Intent(DriverMap.this,Main3Activity.class);
        startActivity(i);
        finish();
    }
}
