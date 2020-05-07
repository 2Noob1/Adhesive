package com.joaofabio.adhesive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

// ola //
        Handler hnd = new Handler();
        hnd.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (checkNet()){
                    checkLogin();
                }
            }
        },1000);
    }

    protected void checkLogin(){
        FileManager Fm = new FileManager();
        Fm.openFile(getApplicationContext(),"session");
        if (Fm.checkforfile()){
            startActivity(new Intent(this,MainActivity.class));
        }else{
            startActivity(new Intent(this,login.class));
        }
        finishAfterTransition();
    }

    protected boolean checkNet(){
        NetworkManager NetMgr = new NetworkManager();
        if (NetMgr.CheckForInternetAccess(getApplicationContext())){
            Log.d("ss","ss");
            return true;
        }else{

            DialogManager dialog = new DialogManager();
            dialog.targetActivity = this;
            dialog.ErrorCode = 4;
            dialog.Critical = true;
            dialog.listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            };

            dialog.show(getSupportFragmentManager(),"ErrorDialog");

            return false;
        }

    }
}
