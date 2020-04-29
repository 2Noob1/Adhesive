package com.joaofabio.adhesive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class login extends AppCompatActivity {

    Intent ActivityTranslateTo = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button Login = findViewById(R.id.button);
        Button Register = findViewById(R.id.button2);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityTranslateTo = new Intent(getApplicationContext(),LoginAction.class);
                startActivity(ActivityTranslateTo);
            }
        });


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityTranslateTo = new Intent(getApplicationContext(),Register.class);
                startActivity(ActivityTranslateTo);
            }
        });

    }
}
