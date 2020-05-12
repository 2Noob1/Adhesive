package com.joaofabio.adhesive;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class fragment_settings extends Fragment {

    ProgressDialog dialogLoading;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button LogoutButton = view.findViewById(R.id.logout);
        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                doLogout request = new doLogout();
                                ArrayList<String> Data = getFileKeys("session");
                                request.execute("https://turma12i.com/JoaoFabio/FCT/LogoutAndroid.php","POST",Data.get(0),Data.get(1));
                                dialogLoading = ProgressDialog.show(getContext(), getResources().getString(R.string.logout_title),
                                        getResources().getString(R.string.logout_message), true);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getResources().getString(R.string.dialog_logout_title)).setMessage(getResources().getString(R.string.dialog_logout_message)).setPositiveButton(getResources().getString(R.string.dialog_logout_positive), dialogClickListener)
                        .setNegativeButton(getResources().getString(R.string.dialog_logout_negative), dialogClickListener).show();
            }
        });
    }

    public class doLogout extends AsyncTask<String,Void,String> {
        boolean Invalid = false;
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
                else if (Result.equalsIgnoreCase("error")) {
                    Result = "error";
                    Error = true;
                    Log.d("lolo", String.valueOf(Error));
                }
                else{
                    Result = "invalid";
                    Invalid = true;
                    Log.d("lolo", String.valueOf(Invalid));
                }
            }catch (Exception e){
                Error = true;
                return e.toString();
            }
            return Result;
        }

        protected void onPostExecute(String s) {
            if (Invalid){
                Intent newActivity = new Intent(getContext(),login.class);
                newActivity.putExtra("NeedsError",true);
                newActivity.putExtra("ErrorCode",5);
                startActivity(newActivity);
            }else {
                if (Error) {
                    DialogManager Dm = new DialogManager();
                    Dm.Critical = false;
                    Dm.ErrorCode = 12;
                    Dm.listener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    };
                    Dm.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "ErrorDialog");
                } else {
                    FileManager Fm = new FileManager();
                    Fm.openFile(Objects.requireNonNull(getContext()), "session");
                    if (!Fm.removeFile()) {
                        DialogManager Dm = new DialogManager();
                        Dm.Critical = false;
                        Dm.ErrorCode = 12;
                        Dm.listener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        };
                        Dm.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "ErrorDialog");
                    } else {
                        startActivity(new Intent(getContext(), Launcher.class));
                    }
                }
            }
        }
    }



    protected ArrayList<String> getFileKeys(String FileName){
        ArrayList<String> list = new ArrayList<String>();
        File file = new File( getContext().getFilesDir().toString() + "session");
        int LineCounter = 0;
        FileInputStream fis = null;
        try {
            fis = getContext().openFileInput("session");
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
