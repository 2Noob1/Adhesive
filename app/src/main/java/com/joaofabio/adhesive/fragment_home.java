package com.joaofabio.adhesive;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class fragment_home extends Fragment {


    SliderView sliderView;
    List<ImageSliderModel> imageSliderModelList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {//fabio did this so ...
        super.onViewCreated(view, savedInstanceState);

        imageSliderModelList = new ArrayList<>();
        sliderView = view.findViewById(R.id.imageSlider);

        imageSliderModelList.add(new ImageSliderModel(R.drawable.a));
        imageSliderModelList.add(new ImageSliderModel(R.drawable.b));
        imageSliderModelList.add(new ImageSliderModel(R.drawable.c));

        sliderView.setSliderAdapter(new ImageSliderAdapter(view.getContext(),imageSliderModelList));

    }
}
