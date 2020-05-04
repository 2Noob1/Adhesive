package com.joaofabio.adhesive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginAction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_action);

        Button donthaveaccountbtn = findViewById(R.id.button5);
        donthaveaccountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
                finishAfterTransition();
            }
        });

        Button loginbtn = findViewById(R.id.button3);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginCheck()){
                    DoLogin();
                }
            }
        });
    }

    public boolean DoLogin(){
        Toast.makeText(this, "Login Attempt", Toast.LENGTH_SHORT).show();
        return true;
    }



    public boolean loginCheck(){
        EditText[] Fields = {
                findViewById(R.id.editText),
                findViewById(R.id.editText2)
        };

        for (int a = 0;a < Fields.length;a++){
            if (Fields[a].getText().toString().equalsIgnoreCase("")){
                NotNetDialog dialog = new NotNetDialog();
                dialog.targetActivity = this;
                dialog.PositiveText = getResources().getString(R.string.login_missingfield_positive);
                dialog.Title = getResources().getString(R.string.login_missingfields_title);
                dialog.Message = getResources().getString(R.string.login_missingfields_message);
                dialog.listener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //no Action
                    }
                };
                dialog.show(getSupportFragmentManager(),getResources().getString(R.string.login_missingfields_title));
                return false;
            }
        }
        return true;
    }
}
