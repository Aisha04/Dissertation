package com.example.train;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class client_List extends ArrayAdapter<Questiondb> {
    private Activity context;
    private List<Questiondb> ques;
    DatabaseReference databaseReference;
    TextView edtName, edtDate, txttime, spinner, spinner1, spinner2;
   // Spinner spinner, spinner1, spinner2;
    public client_List(Activity context, List<Questiondb> ques, DatabaseReference databaseReference, TextView edtName, TextView edtDate, TextView spinner, TextView spinner1, TextView spinner2, TextView txttime) {
        super(context, R.layout.layout_client_notification, ques);
        this.context=context;
        this.ques = ques;
        this.databaseReference = databaseReference;
        this.edtName = edtName;
        this.edtDate = edtDate;
        this.spinner = spinner;
        this.spinner1 = spinner1;
        this.spinner2 = spinner2;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.layout_client_notification, null, true);

        TextView textViewStation = (TextView) listViewItem.findViewById(R.id.txtTname2);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.txtTrain2);
        TextView textViewPlatform = (TextView) listViewItem.findViewById(R.id.txtplatformNumber);
        TextView textViewQuestion = (TextView) listViewItem.findViewById(R.id.txtQuestion);
        TextView textViewEmail = (TextView) listViewItem.findViewById(R.id.txtemail);
        Button btnRep = (Button) listViewItem.findViewById(R.id.btnRep);
        Button btnDel = (Button) listViewItem.findViewById(R.id.btnDel);



        final Questiondb info = ques.get(position);
        textViewName.setText(info.getTrainNumber());
        textViewPlatform.setText(info.getPlatformNumber());
        textViewStation.setText(info.getStationName());
        textViewQuestion.setText(info.getQuestion());
        textViewEmail.setText(info.getEmail());

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(info.getId()).removeValue();
            }
        });


        btnRep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Replyclient.class);

                i.putExtra("id", info.getId());

                i.putExtra("email", info.getEmail());

                v.getContext().startActivity(i);
            }
        });

        return listViewItem;
    }
}
