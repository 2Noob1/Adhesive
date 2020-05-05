package com.joaofabio.adhesive;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {

    ViewFlipper v_flipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int images [] = {R.drawable.download, R.drawable.downlxoad, R.drawable.x};

        v_flipper = findViewById(R.id.v_flipper);

        //loop
        for(int image: images){
            flipperImages(image);
        }


    }

    public void flipperImages (int image){

        ImageView imageView = new ImageView( this);
        imageView.setBackgroundResource(image);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(4000); //4 seg.
        v_flipper.setAutoStart(true);

        //animation
        v_flipper.setInAnimation(this, android.R.anim.slide_in_left );
        v_flipper.setOutAnimation(this, android.R.anim.slide_out_right );
    }
}
