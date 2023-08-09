package com.example.train;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ClientMap extends AppCompatActivity {
    ImageButton btnport, btnbeau, btncoromandel, btnfloreal, btnphoenix, btncurepipe, btnquatre, btnrosehill,btnsadally, btnstjean, btnstlouis, btntrianon, btnvacoas, btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_map);

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
        btnback = (ImageButton)findViewById(R.id.back);

        // Bundle bundle = getIntent().getExtras();
        //if(bundle != null) {
        //   if (bundle.getString("some") != null) {
        //     Toast.makeText(getApplicationContext(), "data:" + bundle.getString("some"), Toast.LENGTH_SHORT).show();

        //}
        //}

btnback.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(), Main2Activity.class));
       // FragmentManager fm = getSupportFragmentManager();
       // More fragment = new More();
      //  Fragment fragment = null;
       // fragment = new More();
       // fm.beginTransaction().replace(R.id.container, fragment).commit();
    }
});

        btnport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), portlouis2.class));
            }
        });

        btnbeau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BeauBassin.class));
            }
        });

        btncoromandel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Coromandel.class));
            }
        });

        btnfloreal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Floreal.class));
            }
        });

        btnphoenix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Phoenix.class));
            }
        });

        btncurepipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Curepipe.class));
            }
        });

        btnquatre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), QuatreBornes.class));
            }
        });

        btnrosehill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RoseHill.class));
            }
        });

        btnsadally.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Sadally.class));
            }
        });

        btnstjean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StJean.class));
            }
        });

        btnstlouis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StLouis.class));
            }
        });

        btntrianon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Trianon.class));
            }
        });

        btnvacoas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Vacoas.class));
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent i= new Intent(ClientMap.this,Main2Activity.class);
        startActivity(i);
        finish();
    }
}
