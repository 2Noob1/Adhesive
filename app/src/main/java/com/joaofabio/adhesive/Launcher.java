package com.joaofabio.adhesive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);


        Handler hnd = new Handler();
        hnd.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkNet();
            }
        },3000);
    }

    protected void checkNet(){
        NetworkManager NetMgr = new NetworkManager();
        if (NetMgr.CheckForInternetAccess(getApplicationContext())){
            FileManager Fm = new FileManager();
            Fm.openFile(getApplicationContext(),"session");
            if (Fm.checkforfile()){
                startActivity(new Intent(this,MainActivity.class));
            }else{
                startActivity(new Intent(this,login.class));
            }
            finishAfterTransition();
        }else{
            /*
            NotNetDialog dialog = new NotNetDialog();
            dialog.targetActivity = this;
            dialog.PositiveText = getResources().getString(R.string.dialog_noNetwork_positive);
            dialog.Title = getResources().getString(R.string.dialog_noNetwork_title);
            dialog.Message = getResources().getString(R.string.dialog_noNetwork_message);
            dialog.listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            };

            dialog.show(getSupportFragmentManager(),getResources().getString(R.string.dialog_noNetwork_title));
            */
        }

    }
}
