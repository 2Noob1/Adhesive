package com.joaofabio.adhesive;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SliderView sliderView;
    List<ImageSliderModel>imageSliderModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageSliderModelList = new ArrayList<>();
        sliderView = findViewById(R.id.imageSlider);

        imageSliderModelList.add(new ImageSliderModel(R.drawable.a));
        imageSliderModelList.add(new ImageSliderModel(R.drawable.b));
        imageSliderModelList.add(new ImageSliderModel(R.drawable.c));

        sliderView.setSliderAdapter(new ImageSliderAdapter(this,imageSliderModelList));
    }
}