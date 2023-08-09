package com.example.train;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Replyclient extends AppCompatActivity {
   EditText edTo, edSubject, etMessage;
   Button btSend;
    String id, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replyclient);

        edTo = findViewById(R.id.ed_to);
        edSubject = findViewById(R.id.et_subject);
        etMessage = findViewById(R.id.et_message);

        btSend = findViewById(R.id.bt_send);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        email = intent.getStringExtra("email");

        edTo.setText(email);

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + edTo.getText().toString()));
                intent.putExtra(Intent.EXTRA_SUBJECT, edSubject.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT, etMessage.getText().toString());
                startActivity(intent);
            }
        });

    }

}
