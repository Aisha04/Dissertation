package com.example.train;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class list_curepipe extends ArrayAdapter<Infodb> {
    private Activity context;
    private List<Infodb> infos;
    DatabaseReference databaseReference;
    TextView edtName, edtDate, txttime, txtDes;
    Spinner spinner, spinner1, spinner2;
    public list_curepipe(Activity context, List<Infodb> infos, DatabaseReference databaseReference, TextView edtName, TextView edtDate, Spinner spinner, Spinner spinner1, Spinner spinner2, TextView txttime, TextView txtDes) {
        super(context, R.layout.layout_curepipe, infos);
        this.context=context;
        this.infos = infos;
        this.databaseReference = databaseReference;
        this.edtName = edtName;
        this.edtDate = edtDate;
        this.spinner = spinner;
        this.spinner1 = spinner1;
        this.spinner2 = spinner2;
        this.txtDes = txtDes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.layout_curepipe, null, true);

        TextView textViewTrain = (TextView) listViewItem.findViewById(R.id.txttrain);
        TextView textViewPlatform = (TextView) listViewItem.findViewById(R.id.txtplatform2);

        TextView textViewTime = (TextView) listViewItem.findViewById(R.id.txtTime2);
        TextView textViewLate = (TextView)listViewItem.findViewById(R.id.txtlate2);


        final Infodb info = infos.get(position);
        textViewTrain.setText(info.getTrainNumber());

        textViewPlatform.setText(info.getPlatformNumber());

        textViewTime.setText(info.getArrivalTime());

        textViewLate.setText(info.getLate());


        return listViewItem;

    }
}
