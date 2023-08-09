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

public class InfoList extends ArrayAdapter<Infodb> {
    private Activity context;
    private List<Infodb> infos;
    DatabaseReference databaseReference;
    TextView edtName, edtDate, txttime, txtDes;
    Spinner spinner, spinner1, spinner2;

    public InfoList(Activity context, List<Infodb> infos, DatabaseReference databaseReference, TextView edtName, TextView edtDate, Spinner spinner, Spinner spinner1, Spinner spinner2, TextView txttime, TextView txtDes) {
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
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.layout_info_list, null, true);

        TextView textViewStation = (TextView) listViewItem.findViewById(R.id.txtSname);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.Tname);
        TextView textViewPlatform = (TextView) listViewItem.findViewById(R.id.txtPnumber);
        TextView textViewDate = (TextView) listViewItem.findViewById(R.id.txtDate);
        TextView textViewTime = (TextView) listViewItem.findViewById(R.id.txtTime);
        TextView textViewLate = (TextView)listViewItem.findViewById(R.id.txtlate);
        TextView textViewDes = (TextView)listViewItem.findViewById(R.id.txtDesname);
        Button btnLate = (Button)listViewItem.findViewById(R.id.btnLate);
        Button btnDelete = (Button) listViewItem.findViewById(R.id.btnDelete3);
        Button btnEdit = (Button) listViewItem.findViewById(R.id.btnEdit3);


        final Infodb info = infos.get(position);
        textViewName.setText(info.getTrainNumber());
        textViewPlatform.setText(info.getPlatformNumber());
        textViewDate.setText(info.getDate());
        textViewTime.setText(info.getArrivalTime());
        textViewStation.setText(info.getDestinationStation());
        textViewLate.setText(info.getLate());
        textViewDes.setText(info.getStationName());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(info.getId()).removeValue();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  edtName.setText(station.getStationName());
                //   edtPlatform.setText(station.getPlatformNumber());

                //   Station.stationId = station.getId();
                Intent i = new Intent(v.getContext(), ScheduleUpdate.class);
                i.putExtra("id", info.getId());
                i.putExtra("stationName", info.getStationName());
                i.putExtra("trainNumber", info.getTrainNumber());
                i.putExtra("date", info.getDate());
                i.putExtra("arrivalTime", info.getArrivalTime());
                i.putExtra("platformNumber", info.getPlatformNumber());
                i.putExtra("late", info.getLate());
                i.putExtra("DestinationStation", info.getDestinationStation());
                v.getContext().startActivity(i);


            }
        });

        btnLate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  edtName.setText(station.getStationName());
                //   edtPlatform.setText(station.getPlatformNumber());

                //   Station.stationId = station.getId();
                Intent i = new Intent(v.getContext(), Late.class);
                i.putExtra("id", info.getId());
                i.putExtra("stationName", info.getStationName());
                i.putExtra("trainNumber", info.getTrainNumber());
                i.putExtra("date", info.getDate());
                i.putExtra("arrivalTime", info.getArrivalTime());
                i.putExtra("platformNumber", info.getPlatformNumber());
                i.putExtra("late", info.getLate());
                i.putExtra("DestinationStation", info.getDestinationStation());
                v.getContext().startActivity(i);


            }
        });

        return listViewItem;
    }
}
