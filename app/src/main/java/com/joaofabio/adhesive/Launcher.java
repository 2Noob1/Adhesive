package com.joaofabio.adhesive;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

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
        try{
            Fm.openFile(getApplicationContext(),"session");
        }catch (Exception e){
            Log.d("ll",e.toString());
        }
        if (Fm.checkforfile()){
            doHttpRequest request = new doHttpRequest();
            ArrayList<String> Data = getFileKeys("session");
            request.execute("https://turma12i.com/JoaoFabio/FCT/loginCheckAndroid.php","POST",Data.get(0),Data.get(1));
        }else{
            Intent newActivity = new Intent(this,login.class);
            newActivity.putExtra("NeedsError",false);
            newActivity.putExtra("ErrorCode",0);
            startActivity(newActivity);
        }
    }


    public void DoneChecking(boolean valid){
        if (!valid){
            Log.d("ss","ss1");
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }else{
            File dir = getFilesDir();
            File file = new File(dir, "session");
            file.delete();
            Log.d("ss","ss2");
            Intent newActivity = new Intent(this,login.class);
            newActivity.putExtra("NeedsError",true);
            newActivity.putExtra("ErrorCode",5);
            startActivity(newActivity);
        }
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


    public class doHttpRequest extends AsyncTask<String,Void,String>{
        boolean Error = false;

        protected String doInBackground(String... strings) {
            String InputBuffer;
            String Result;

            try {
                Log.d("Params",strings[0]);
                Log.d("Params",strings[2]);
                Log.d("Params",strings[3]);
                //Sets the Url Resource
                URL targetUrl = new URL(strings[0]);
                //Opens a connection of type HttpUrlConnection
                HttpURLConnection Connection = (HttpURLConnection) targetUrl.openConnection();
                //Sets the Request Method
                Connection.setRequestMethod(strings[1]);
                //Sets Timeouts
                Connection.setDoOutput(false);
                Connection.setConnectTimeout(1200);
                Connection.setReadTimeout(1200);
                Connection.setRequestProperty("Key",strings[2]);
                Connection.setRequestProperty("Email",strings[3]);
                Log.d("Request",Connection.getRequestProperties().toString());
                //Connect
                Connection.connect();
                //Initialize Buffers
                InputStreamReader in = new InputStreamReader(Connection.getInputStream());
                StringBuilder sb = new StringBuilder();
                BufferedReader Buffer = new BufferedReader(in);
                //Reads Contents
                while ((InputBuffer = Buffer.readLine()) != null){
                    sb.append(InputBuffer);
                }
                //Closes Buffers
                in.close();
                in = null;
                Buffer.close();
                Buffer = null;
                InputBuffer = null;
                //returns
                Result = sb.toString();
                Log.d("Lol",Result);

                if (Result.equals("valid")) {
                    Result = "valid";
                    Error = false;
                    Log.d("lolo", String.valueOf(Error));
                }
                else{
                    Result = "invalid";
                    Error = true;
                    Log.d("lolo", String.valueOf(Error));
                }
            }catch (Exception e){
                Error = true;
                return e.toString();
            }
            return Result;
        }

        protected void onPostExecute(String s) {
            DoneChecking(Error);
        }
    }

    protected ArrayList<String> getFileKeys(String FileName){
        ArrayList<String> list = new ArrayList<String>();
        File file = new File(getFilesDir().toString() + FileName);
        int LineCounter = 0;
        FileInputStream fis = null;
        try {
            fis = openFileInput("session");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
                LineCounter++;
            }
        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
        } finally {
            String contents = stringBuilder.toString();
            String[] array = new String[LineCounter];
            int c ;
            int d = 0;
            for (c = 0;c < contents.length();c++){
                if (contents.charAt(c) == '\n'){
                    d = 1;
                    continue;
                }else{
                    array[d] = array[d] + contents.charAt(c);
                }
            }
            for (int h = 0; h < array.length;h++){
                Log.d("LINE70","Array[" + h +"] -> " + array[h].replace("null",""));
                array[h] = array[h].replace("null","");
                int t = -1;
                String Buffer = null;
                for (c = 0;c < array[h].length();c++){
                    if (array[h].charAt(c) == ':'){
                        Log.d("LINE76","Buffer -> " + Buffer.replace("null",""));
                        Buffer = Buffer.replace("null","");
                        c = 9999;
                    }else{
                        Buffer = Buffer + array[h].charAt(c);
                    }
                }
                String Dump = array[h];
                Dump = Dump.replace(Buffer + ":","");
                list.add(Dump.replace(Buffer,""));
                Buffer = null;
            }
        }
        return list;
    }
}
