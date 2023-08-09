package com.example.train;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class driver_list extends ArrayAdapter<QuestionDriverdb> {
    private Activity context;
    private List<QuestionDriverdb> ques;
    DatabaseReference databaseReference;
    TextView edtName, edtDate, txttime, spinner, spinner1, spinner2, spinner3;
    public driver_list(Activity context, List<QuestionDriverdb> ques, DatabaseReference databaseReference, TextView edtName, TextView edtDate, TextView spinner, TextView spinner1, TextView spinner2,  TextView spinner3) {
        super(context, R.layout.layout_driver_notification, ques);
        this.context=context;
        this.ques = ques;
        this.databaseReference = databaseReference;
        this.edtName = edtName;
        this.edtDate = edtDate;
        this.spinner = spinner;
        this.spinner1 = spinner1;
        this.spinner2 = spinner2;
        this.spinner3 = spinner3;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.layout_driver_notification, null, true);

        TextView textViewStation = (TextView) listViewItem.findViewById(R.id.txtTname2);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.txtTrain2);
        TextView textViewPlatform = (TextView) listViewItem.findViewById(R.id.txtplatformNumber);
        TextView textViewLateBy = (TextView) listViewItem.findViewById(R.id.txtLateby);
        TextView textViewReason = (TextView) listViewItem.findViewById(R.id.txtReason);
        TextView textViewEmail = (TextView) listViewItem.findViewById(R.id.txtemail);
        Button btnRep = (Button) listViewItem.findViewById(R.id.btnRep);
        Button btnDel = (Button) listViewItem.findViewById(R.id.btnDel);



        final QuestionDriverdb info = ques.get(position);
        textViewName.setText(info.getTrainNumber());
        textViewPlatform.setText(info.getPlatformNumber());
        textViewStation.setText(info.getStationName());
        textViewLateBy.setText(info.getLate());
        textViewReason.setText(info.getReason());
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
