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

public class clientStation_List extends ArrayAdapter<Infodb> {

    private Activity context;
    private List<Infodb> infos;
    DatabaseReference databaseReference;
    TextView edtName, edtDate, txttime;
    Spinner spinner, spinner1, spinner2;

    public clientStation_List(Activity context, List<Infodb> infos, DatabaseReference databaseReference, TextView edtName, TextView edtDate, Spinner spinner, Spinner spinner1, Spinner spinner2, TextView txttime) {
        super(context, R.layout.layout_clientstation, infos);
        this.context=context;
        this.infos = infos;
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

        View listViewItem = inflater.inflate(R.layout.layout_clientstation, null, true);

        TextView textViewStation = (TextView) listViewItem.findViewById(R.id.Sname);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.Tname);
        TextView textViewPlatform = (TextView) listViewItem.findViewById(R.id.txtPnumber);
        TextView textViewDate = (TextView) listViewItem.findViewById(R.id.txtdate);
        TextView textViewTime = (TextView) listViewItem.findViewById(R.id.txtTime);
        TextView textViewLate = (TextView)listViewItem.findViewById(R.id.txtLate);


        final Infodb info = infos.get(position);
        textViewName.setText(info.getTrainNumber());
        textViewPlatform.setText(info.getPlatformNumber());
        textViewDate.setText(info.getDate());
        textViewTime.setText(info.getArrivalTime());
        textViewStation.setText(info.getStationName());
        textViewLate.setText(info.getLate());



        return listViewItem;
    }
}
