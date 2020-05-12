package com.joaofabio.adhesive;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;


public class fragment_gallery extends Fragment {
    public int f = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button[] moreinfos = {
            view.findViewById(R.id.project1),
            view.findViewById(R.id.project2),
            view.findViewById(R.id.project3),
            view.findViewById(R.id.project4),
            view.findViewById(R.id.project5)
        };
        moreinfos[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openFullscreen(1);
                }
            });

        moreinfos[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFullscreen(2);
            }
        });

        moreinfos[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFullscreen(3);
            }
        });

        moreinfos[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFullscreen(4);
            }
        });

        moreinfos[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFullscreen(5);
            }
        });

    }

    public void openFullscreen(int code){
        Intent newActivity = new Intent(getContext(),gallery_fullscreen.class);
        newActivity.putExtra("ProductCode",code);
        startActivity(newActivity);
    }


}
