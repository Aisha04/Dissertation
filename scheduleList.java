package com.example.train;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class scheduleList extends ArrayAdapter<scheduledb> {
    private Activity context;
    private List<scheduledb> schedules;
    DatabaseReference databaseReference;
    TextView edtName, edtDate;
    Spinner spinner;

    public scheduleList(Activity context, List<scheduledb> schedules, DatabaseReference databaseReference, TextView edtName, TextView edtDate, Spinner spinner) {
        super(context, R.layout.layout_schedule_list, schedules);
        this.context=context;
        this.schedules = schedules;
        this.databaseReference = databaseReference;
        this.edtName = edtName;
        this.edtDate = edtDate;
        this.spinner = spinner;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.layout_station_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.Tname);
        TextView textViewDate = (TextView) listViewItem.findViewById(R.id.txtDate);
        TextView textViewTime = (TextView) listViewItem.findViewById(R.id.txtTime);

        Button btnDelete = (Button) listViewItem.findViewById(R.id.btnDelete3);
        Button btnEdit = (Button) listViewItem.findViewById(R.id.btnEdit3);


        final scheduledb schedule = schedules.get(position);
        textViewName.setText(schedule.getTrainNumber());
        textViewDate.setText(schedule.getDate());
        textViewTime.setText(schedule.getArrivalTime());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(schedule.getId()).removeValue();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  edtName.setText(station.getStationName());
                //   edtPlatform.setText(station.getPlatformNumber());

                //   Station.stationId = station.getId();
                Intent i = new Intent(v.getContext(), StationUpdate.class);
                i.putExtra("id", schedule.getId());
               // i.putExtra("stationName", schedule.getStationName());
              //  i.putExtra("platformNumber", schedule.getPlatformNumber());
                v.getContext().startActivity(i);


            }
        });

        return listViewItem;
    }


}
