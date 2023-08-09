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

public class client extends ArrayAdapter<Infodb> {

    private Activity context;
    private List<Infodb> infos;
    DatabaseReference databaseReference;
    TextView edtName, edtDate, txttime, txtDes, spinner10;
    Spinner spinner, spinner1, spinner2;

    public client(Activity context, List<Infodb> infos, DatabaseReference databaseReference, TextView edtName, TextView edtDate, Spinner spinner, Spinner spinner1, Spinner spinner2, TextView txttime, TextView txtDes) {
        super(context, R.layout.layout_info_list, infos);
        this.context=context;
        this.infos = infos;
        this.databaseReference = databaseReference;
        this.edtName = edtName;
        this.edtDate = edtDate;
        this.spinner = spinner;
        this.spinner1 = spinner1;
        this.spinner2 = spinner2;
        this.txtDes = txtDes;
        this.txttime = txttime;
            }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.layout_client, null, true);


        TextView textViewName = (TextView) listViewItem.findViewById(R.id.Tname);
        TextView textViewPlatform = (TextView) listViewItem.findViewById(R.id.txtPnumber);

        TextView textViewTime = (TextView) listViewItem.findViewById(R.id.txtTime);

        final Infodb info = infos.get(position);

        textViewName.setText(info.getTrainNumber());
        textViewPlatform.setText(info.getPlatformNumber());

        textViewTime.setText(info.getArrivalTime());


        return listViewItem;
    }
}
