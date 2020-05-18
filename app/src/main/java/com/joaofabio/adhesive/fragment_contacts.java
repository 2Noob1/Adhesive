package com.joaofabio.adhesive;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

public class fragment_contacts extends Fragment implements OnMapReadyCallback {
    int id;
    ProgressDialog dialogLoading;
    GoogleMap map;
    MapView mapFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contacts, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapFragment = view.findViewById(R.id.map);
        assert mapFragment != null;
        mapFragment.onCreate(savedInstanceState);
        mapFragment.getMapAsync(this);

        Button sendEmail = view.findViewById(R.id.sendEmail);
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList list = getFileKeys("session");
                doHttpRequest request = new doHttpRequest();
                EditText Assunto = Objects.requireNonNull(getActivity()).findViewById(R.id.assunto);
                EditText Messagem = getActivity().findViewById(R.id.mensagem);
                request.execute("https://turma12i.com/JoaoFabio/FCT/sendMail.php","POST",list.get(0).toString(),list.get(1).toString(),Assunto.getText().toString(),Messagem.getText().toString());
                dialogLoading = ProgressDialog.show(getContext(), getResources().getString(R.string.sendmessage_loadinTittle),
                        getResources().getString(R.string.sendmessage_loadinMessage), true);
            }
        });


        Button call1 = view.findViewById(R.id.call1);
        call1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = 1;
                if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CALL_PHONE},
                            1);
                }else{
                    doCall();
                }
            }
        });
        Button call2 = view.findViewById(R.id.call2);
        call2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = 2;
                if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CALL_PHONE},
                            1);
                }else{
                    doCall();
                }
            }
        });
        Button call3 = view.findViewById(R.id.call3);
        call3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = 3;
                if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CALL_PHONE},
                            1);
                }else{
                    doCall();
                }
            }
        });
    }

    public void doCall(){
        String CallNumber;
        Intent call = new Intent(Intent.ACTION_CALL);
        switch(id){
            case 1:
                CallNumber = "+351" + getResources().getString(R.string.call1);
                Log.d("Call",CallNumber);
                call.setData(Uri.parse("tel:"+CallNumber));
                break;
            case 2:
                CallNumber = "+351" + getResources().getString(R.string.call2);
                Log.d("Call",CallNumber);
                call.setData(Uri.parse("tel:"+CallNumber));
                break;
            case 3:
                CallNumber = "+351" + getResources().getString(R.string.call3);
                Log.d("Call",CallNumber);
                call.setData(Uri.parse("tel:"+CallNumber));
                break;
        }
        startActivity(call);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng Adhesive = new LatLng(38.63663505470294,-9.105647762351694);
        map.addMarker(new MarkerOptions().position(Adhesive).title("Adhesisve"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Adhesive,15));
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapFragment.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapFragment.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapFragment.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapFragment.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapFragment.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapFragment.onLowMemory();
    }

    public void finish(){
        dialogLoading.dismiss();
    }

    public class doHttpRequest extends AsyncTask<String,Void,String>{
        Boolean Error = false;
        Integer ErrorCode = 0;
        Boolean Invalid = false;
        @Override
        protected void onPreExecute() {
            Log.d(this.toString(),"Initialized");
        }

        @Override
        protected String doInBackground(String... strings) {
            String Result  = "";
            String Buffer;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod(strings[1]);

                connection.setRequestProperty("Key",strings[2]);
                connection.setRequestProperty("Email",strings[3]);
                connection.setRequestProperty("Assunto",strings[4]);
                connection.setRequestProperty("Mensagem",strings[5]);

                Log.d(this.toString(),connection.getRequestProperties().toString());

                connection.connect();
                InputStreamReader in = new InputStreamReader(connection.getInputStream());
                StringBuilder sb = new StringBuilder();
                BufferedReader buffer = new BufferedReader(in);

                while((Buffer = buffer.readLine())!=null){
                    sb.append(Buffer);
                }

                Result = sb.toString();

                if (Result.equals("valid")) {
                    Result = "valid";
                    Error = false;
                    Log.d("lolo", String.valueOf(Error));
                }
                else if (Result.equalsIgnoreCase("error")) {
                    Result = "error";
                    Error = true;
                    Log.d("lolo", String.valueOf(Error));
                    return Result;
                }
                else{
                    Result = "invalid";
                    Error = true;
                    Log.d("lolo", String.valueOf(Error));
                    return Result;
                }
            }catch (Exception e){
                Error = true;
                Log.d(this.toString(),e.toString());
            }
            return Result;
        }

        @Override
        protected void onPostExecute(String s) {
            finish();
            if (Invalid){
                Intent newActivity = new Intent(getContext(),login.class);
                newActivity.putExtra("NeedsError",true);
                newActivity.putExtra("ErrorCode",5);
                startActivity(newActivity);
            }else {
                if (Error) {
                    DialogManager Dm = new DialogManager();
                    Dm.Critical = false;
                    Dm.ErrorCode = 18;
                    Dm.listener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    };
                    Dm.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "ErrorDialog");
                } else {
                    DialogManager Dm = new DialogManager();
                    Dm.Critical = false;
                    Dm.ErrorCode = 19;
                    Dm.listener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    };
                    Dm.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "ErrorDialog");
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