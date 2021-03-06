package com.joaofabio.adhesive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

public class login extends AppCompatActivity {

    Intent ActivityTranslateTo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setStatusBarColor(getResources().getColor(R.color.activity_Color));

        Log.d("Here","Chegou");
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        if (bundle.getBoolean("NeedsError")){
            DialogManager dialog = new DialogManager();
            dialog.ErrorCode = bundle.getInt("ErrorCode");
            dialog.Critical = false;
            dialog.listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //
                }
            };
            dialog.show(getSupportFragmentManager(),"ErrorDialog");
        }

        Button Login = findViewById(R.id.button);
        Button Register = findViewById(R.id.button2);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityTranslateTo = new Intent(getApplicationContext(),LoginAction.class);
                startActivity(ActivityTranslateTo);
                finishAfterTransition();
            }
        });


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityTranslateTo = new Intent(getApplicationContext(),Register.class);
                startActivity(ActivityTranslateTo);
                finishAfterTransition();
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();

        DialogFragment Df = (DialogFragment) Objects.requireNonNull(this).getSupportFragmentManager().findFragmentByTag("LegalDialog");
        if (Df != null) Df.dismiss();

        Df = (DialogFragment) Objects.requireNonNull(this).getSupportFragmentManager().findFragmentByTag("ErrorDialog");
        if (Df != null) Df.dismiss();
    }

}
