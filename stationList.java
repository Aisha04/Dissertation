package com.example.train;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class stationList extends ArrayAdapter<stationdb> {

    private Activity context;
    private List<stationdb> stations;
   //private List<Alldb> stations;
    DatabaseReference databaseReference;
    EditText edtName, edtPlatform;
    Spinner spinner;

   public stationList(Activity context, List<stationdb> stations, DatabaseReference databaseReference, Spinner spinner, EditText edtPlatform){
       super(context, R.layout.layout_station_list, stations);
       this.context=context;
       this.stations = stations;
       this.databaseReference = databaseReference;
       this.spinner = spinner;
       this.edtPlatform = edtPlatform;

   }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.layout_schedule_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.txtname);
        TextView textViewPlatform = (TextView) listViewItem.findViewById(R.id.txtplatform);
        Button btnDelete = (Button) listViewItem.findViewById(R.id.btnDelete);
        Button btnEdit = (Button) listViewItem.findViewById(R.id.btnEdit);

        //final Alldb station = stations.get(position);
       final stationdb station = stations.get(position);
        textViewName.setText(station.getStationName());
        textViewPlatform.setText(station.getPlatformNumber());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(station.getId()).removeValue();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  edtName.setText(station.getStationName());
             //   edtPlatform.setText(station.getPlatformNumber());

             //   Station.stationId = station.getId();
                Intent i = new Intent(v.getContext(), StationUpdate.class);
               i.putExtra("id", station.getId());
                i.putExtra("stationName", station.getStationName());
                i.putExtra("platformNumber", station.getPlatformNumber());
                v.getContext().startActivity(i);


            }
        });

return listViewItem;
    }


}
