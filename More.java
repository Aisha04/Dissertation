package com.example.train;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class More extends Fragment {
 Button btnmap, btnticket, btnprofile;

    private MoreViewModel mViewModel;

    public static More newInstance() {
        return new More();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.more_fragment, container, false);

        btnmap = (Button)root.findViewById(R.id.map);
        btnticket = (Button)root.findViewById(R.id.ticket);
        btnprofile = (Button)root.findViewById(R.id.profile);

        ImageView iv_background = root.findViewById(R.id.iv_background);

        AnimationDrawable animationDrawable = (AnimationDrawable) iv_background.getDrawable();
        animationDrawable.start();


        btnmap.setOnClickListener(new View.OnClickListener() {
            @Override
          public void onClick(View v) {
              //  Intent i = new Intent(getActivity(), Map.class);
                //i.putExtra("some", "some data");
                //startActivity(i);
                //FragmentTransaction fr = getChildFragmentManager().beginTransaction();
                //fr.replace(R.id.navigation_notification, new MapClient());
                //fr.commit();
                //startActivity(new Intent(getActivity(), MapClient.class));

                startActivity(new Intent(getActivity(), ClientMap.class));
            }
        });

        btnticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ClientTicket.class));
            }
        });

        btnprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Profile.class));
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MoreViewModel.class);
        // TODO: Use the ViewModel
    }

}
