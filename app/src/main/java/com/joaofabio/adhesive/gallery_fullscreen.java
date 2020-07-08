package com.joaofabio.adhesive;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Button contacts = findViewById(R.id.contacts);
        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.needsChange = true;
                MainActivity.ToWhere = 1;
                finish();
            }
        });

        getWindow().setStatusBarColor(getResources().getColor(R.color.secoundaryColor));




        //bunlde
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        final int ProjectCode = bundle.getInt("ProductCode");

        //Ui Itens
        final ImageView projectImage = findViewById(R.id.gallery_projectimage);
        final TextView client = findViewById(R.id.clientText);
        final TextView project = findViewById(R.id.projectText);
        final TextView services = findViewById(R.id.servicesText);

        switch(ProjectCode){
            case 1:
                projectImage.setImageDrawable(getResources().getDrawable(R.drawable.p1, getApplicationContext().getTheme()));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p1_1)); //apresentar o IMAGESLIDERMOEL
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p1_2));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p1_3));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p1_5));
                sliderView.setSliderAdapter(new ImageSliderAdapter(this,imageSliderModelList));
                client.setText(getResources().getString(R.string.gallery_project1_title));
                project.setText(getResources().getString(R.string.gallery_project1_Message));
                services.setText(getResources().getString(R.string.gallery_project1_services));
                break;
            case 2:
                projectImage.setImageDrawable(getResources().getDrawable(R.drawable.p2, getApplicationContext().getTheme()));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p2_1));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p2_2));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p2_3));
                sliderView.setSliderAdapter(new ImageSliderAdapter(this,imageSliderModelList));
                client.setText(getResources().getString(R.string.gallery_project2_title));
                project.setText(getResources().getString(R.string.gallery_project2_message));
                services.setText(getResources().getString(R.string.gallery_project2_services));
                break;
            case 3:
                projectImage.setImageDrawable(getResources().getDrawable(R.drawable.p3, getApplicationContext().getTheme()));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p3_1));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p3_2));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p3_3));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p3_4));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p3_5));
                sliderView.setSliderAdapter(new ImageSliderAdapter(this,imageSliderModelList));
                client.setText(getResources().getString(R.string.gallery_project3_title));
                project.setText(getResources().getString(R.string.gallery_project3_message));
                services.setText(getResources().getString(R.string.gallery_project3_services));
                break;
            case 4:
                projectImage.setImageDrawable(getResources().getDrawable(R.drawable.p4, getApplicationContext().getTheme()));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p4_1));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p4_2));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p4_3));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p4_4));
                sliderView.setSliderAdapter(new ImageSliderAdapter(this,imageSliderModelList));
                client.setText(getResources().getString(R.string.gallery_project4_title));
                project.setText(getResources().getString(R.string.gallery_project4_message));
                services.setText(getResources().getString(R.string.gallery_project4_services));
                break;
            case 5:
                projectImage.setImageDrawable(getResources().getDrawable(R.drawable.p5, getApplicationContext().getTheme()));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p5_1));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p5_2));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p5_3));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p5_4));
                imageSliderModelList.add(new ImageSliderModel(R.drawable.p5_5));
                sliderView.setSliderAdapter(new ImageSliderAdapter(this,imageSliderModelList));
                client.setText(getResources().getString(R.string.gallery_project5_title));
                project.setText(getResources().getString(R.string.gallery_project5_message));
                services.setText(getResources().getString(R.string.gallery_project5_services));
                break;
        }

    }
}
