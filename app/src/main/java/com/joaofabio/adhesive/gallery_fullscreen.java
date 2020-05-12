package com.joaofabio.adhesive;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class gallery_fullscreen extends AppCompatActivity {

    SliderView sliderView;
    List<ImageSliderModel> imageSliderModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_fullscreen);

        imageSliderModelList = new ArrayList<>();
        sliderView = findViewById(R.id.imageSlider2);



        //bunlde
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        final Integer ProjectCode = bundle.getInt("ProductCode");

        //Ui Itens
        final ImageView projectImage = findViewById(R.id.gallery_projectimage);

        switch(ProjectCode){
            case 1:
                projectImage.setImageDrawable(getResources().getDrawable(R.drawable.p1, getApplicationContext().getTheme()));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p1_1));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p1_2));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p1_3));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p1_5));
                sliderView.setSliderAdapter(new ImageSliderAdapter(this,imageSliderModelList));
                break;
            case 2:
                projectImage.setImageDrawable(getResources().getDrawable(R.drawable.p2, getApplicationContext().getTheme()));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p2_1));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p2_2));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p2_3));
                sliderView.setSliderAdapter(new ImageSliderAdapter(this,imageSliderModelList));
                break;
            case 3:
                projectImage.setImageDrawable(getResources().getDrawable(R.drawable.p3, getApplicationContext().getTheme()));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p3_1));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p3_2));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p3_3));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p3_4));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p3_5));
                sliderView.setSliderAdapter(new ImageSliderAdapter(this,imageSliderModelList));
                break;
            case 4:
                projectImage.setImageDrawable(getResources().getDrawable(R.drawable.p4, getApplicationContext().getTheme()));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p4_1));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p4_2));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p4_3));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p4_4));
                sliderView.setSliderAdapter(new ImageSliderAdapter(this,imageSliderModelList));
                break;
            case 5:
                projectImage.setImageDrawable(getResources().getDrawable(R.drawable.p5, getApplicationContext().getTheme()));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p5_1));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p5_2));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p5_3));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p5_4));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p5_5));
                sliderView.setSliderAdapter(new ImageSliderAdapter(this,imageSliderModelList));
                break;
        }

    }
}
