package com.joaofabio.adhesive;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class LoginAction extends AppCompatActivity {
    ProgressDialog dialogLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_action);

        Button donthaveaccountbtn = findViewById(R.id.button5); //redireciona para o fragment register
        donthaveaccountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
                finishAfterTransition();
            }
        });
        getWindow().setStatusBarColor(getResources().getColor(R.color.activity_Color));

        Button loginbtn = findViewById(R.id.button3); //botão que vai executar o login
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginCheck()){
                    EditText[] Fields = {
                            findViewById(R.id.editText),
                            findViewById(R.id.editText2)
                    };
                    String Email = Fields[0].getText().toString();
                    String Password = Fields[1].getText().toString();
                    boolean result = DoLogin(Email,Password);
                }
            }
        });
    }

    public boolean DoLogin(String Email,String Password){
        dialogLoading = ProgressDialog.show(this, getResources().getString(R.string.login_loadingpopup_title),
                getResources().getString(R.string.login_loadingpopup_message), true);
        doHttpLogin Request = new doHttpLogin();
        Request.execute("https://turma12i.com/JoaoFabio/FCT/LoginAndroid.php","POST",Email,Password);
        return true;
    }

    public void errorLogin(Integer ErrorId){
        //erro ao fazer o login
        DialogManager dialog = new DialogManager();
        dialog.targetActivity = this;
        dialog.ErrorCode = ErrorId;
        dialog.Critical = false;
        dialog.listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        };
        dialog.show(getSupportFragmentManager(),"ErrorDialog");
        dialogLoading.dismiss();
    }

    public boolean loginCheck(){
        EditText[] Fields = {
                findViewById(R.id.editText),
                findViewById(R.id.editText2)
        };

        for (EditText field : Fields) {
            if (field.getText().toString().equalsIgnoreCase("")) {
                DialogManager dialog = new DialogManager();
                dialog.targetActivity = this;
                dialog.ErrorCode = 1;
                dialog.Critical = false;
                dialog.listener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                };
                dialog.show(getSupportFragmentManager(),"ErrorDialog");
                return false;
            }
        }
        return true;
    }

    protected class doHttpLogin extends AsyncTask<String,Void,String>{
        Integer ErrorCode = 0;
        Boolean error = false;
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {
            String InputBuffer;
            String Result;

            try {
                //Sets the Url Resource
                URL targetUrl = new URL(strings[0]);
                //Opens a connection of type HttpUrlConnection
                HttpURLConnection Connection = (HttpURLConnection) targetUrl.openConnection();
                //Sets the Request Method
                Connection.setRequestMethod(strings[1]);
                //Sets Timeouts
                Connection.setDoOutput(false);
                Connection.setConnectTimeout(5000);
                Connection.setReadTimeout(5000);
                Connection.setRequestProperty("Email",strings[2]);
                Connection.setRequestProperty("Password",strings[3]);
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
            }catch (Exception e){
                error = true;
                return e.toString();
            }
            try{

                final JSONObject json = new JSONObject(Result);
                Iterator<String> iterator = json.keys();
                ArrayList<String> stringMap = new ArrayList<>();
                while(iterator.hasNext()){
                    String key = iterator.next();
                    JSONObject Object = json.getJSONObject(key);
                    //  get id from  issue
                    stringMap.add(Object.optString("Email"));
                    stringMap.add(Object.optString("ResultCode"));
                    stringMap.add(Object.optString("RsultType"));
                    stringMap.add(Object.optString("ResultDesc"));
                    stringMap.add(Object.optString("AuthToken"));
                }
                Log.d("Array",stringMap.toString());
                if (stringMap.get(1).toString().equalsIgnoreCase("9")){
                    Result = "Login Done";
                    error = false;
                }else{
                    ErrorCode = Integer.parseInt(stringMap.get(1).toString());
                    error = true;
                    Result = "Faild to Login in -> " + stringMap.get(3).toString();
                    return Result;
                }

                //O ficheiro SESSION tem os dados separados
                String filename = "session";
                String fileContents = "AuthKey:" + stringMap.get(4).toString() + "\nEmail:" + stringMap.get(0).toString();
                try (FileOutputStream fos = openFileOutput(filename, MODE_PRIVATE)) {
                    fos.write(fileContents.getBytes());
                }catch (Exception e){
                    error = true;
                    return e.toString();
                }
            }catch (Exception e){
                error = true;
                return e.toString();
            }
            return Result;
        }

        @Override
        protected void onPostExecute(String s) {
            //vai verificar se dá erro, caso contrário vai para o main activity
            Log.d("Lol",s);
            if ((error) && ErrorCode == 0){
                ErrorCode = 3;
            }
            if (error){
                Log.d("Error",s);
                errorLogin(ErrorCode);
            }else {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finishAfterTransition();
            }
        }
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
