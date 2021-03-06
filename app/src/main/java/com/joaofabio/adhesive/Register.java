package com.joaofabio.adhesive;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Objects;

public class Register extends AppCompatActivity {
    ProgressDialog dialogLoading;
    EditText tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button alreadyhaveaccountbtn = findViewById(R.id.button7); //botão que vai redirecionar para o fragment login
        alreadyhaveaccountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginAction.class));
                finishAfterTransition();
            }
        });

        getWindow().setStatusBarColor(getResources().getColor(R.color.activity_Color));
        Button Register = findViewById(R.id.button6); //Botão que faz o registo
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
            }
        });

        tv = findViewById(R.id.editText7); //calendário
        tv.setFocusable(false);
        tv.setClickable(true);

    }

    public void doRegister(){ //classe que vai fazer o registo
        Log.d("sss","sss");
        dialogLoading = ProgressDialog.show(this, getResources().getString(R.string.register_progressTitle),
                getResources().getString(R.string.register_progressMessage), true);
        if (!checkforemptyfields()){
            //verificar se os campos estão vazios
            DialogManager dialog = new DialogManager();
            dialog.targetActivity = this;
            dialog.ErrorCode = 1;
            dialog.Critical = false;
            dialog.listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //
                }
            };

            dialog.show(getSupportFragmentManager(),"ErrorDialog");
            dialogLoading.dismiss();
        }else{
            doHttpRequest request = new doHttpRequest();
            String Sex = "";
            if (findViewById(R.id.radioButton).isClickable()){
                Sex = "M";
            }else{
                Sex = "F";
            }
            EditText[] fields = {
                    findViewById(R.id.editText6),//Nome
                    findViewById(R.id.editText3),//Email
                    findViewById(R.id.editText4),//Password
                    findViewById(R.id.editText7)//Data Nascimento
            };

            request.execute("https://turma12i.com/JoaoFabio/FCT/RegisterAndroid.php","POST",fields[0].getText().toString(),fields[1].getText().toString(),fields[2].getText().toString(),fields[3].getText().toString(),Sex);
         }
    }

    public void toggledatefragmment(View v) {
        //novo fragment do calendario
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    public boolean checkforemptyfields(){
        //verificar se os campos estão vazios
        TextView[] fields = {
                findViewById(R.id.editText6),//Nome
                findViewById(R.id.editText3),//Email
                findViewById(R.id.editText4),//Password
                findViewById(R.id.editText7)//Data Nascimento
        };

        RadioButton[] Radios = {
            findViewById(R.id.radioButton),//Masculino
            findViewById(R.id.radioButton2)//Feminino
        };

        for (TextView tvBuffer : fields){
            if (tvBuffer.getText().toString().isEmpty()){
                return false;
            }
        }

        if (!Radios[0].isChecked() && !Radios[1].isChecked()){
            return false;
        }

        return true;
    }

    public void ErrorRegistering(Integer ErrorId){
        //erro ao registar
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

    public static class DatePickerFragment extends DialogFragment
        //calendário
            implements DatePickerDialog.OnDateSetListener {

        EditText tv;

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            //funcionamento do calendário
            tv = Objects.requireNonNull(getActivity()).findViewById(R.id.editText7);
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            tv.setText(year + "-" + (month + 1) + "-" + day);
        }
    }



    public class doHttpRequest extends AsyncTask<String,Void,String> {
        Boolean Error = false;
        Integer ErrorCode = 0;
        @Override
        protected void onPreExecute() {
            Log.d("Async Task",this.toString());
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";

            try{
                //sets url
                URL url = new URL(strings[0]);

                //Opens Connection
                HttpURLConnection Connection = (HttpURLConnection) url.openConnection();
                //Method
                Connection.setRequestMethod(strings[1]);
                //Timeouts
                Connection.setConnectTimeout(5000);
                Connection.setReadTimeout(5000);

                //Headers
                Connection.setRequestProperty("Name",strings[2]);
                Connection.setRequestProperty("Email",strings[3]);
                Connection.setRequestProperty("Password",strings[4]);
                Connection.setRequestProperty("DataNascimento",strings[5]);
                Connection.setRequestProperty("Sex",strings[6]);
                Log.d("Teste",Connection.getRequestProperties().toString());

                //Initializes Buffers
                InputStreamReader in = new InputStreamReader(Connection.getInputStream());
                BufferedReader reader = new BufferedReader(in);
                StringBuilder sb = new StringBuilder();
                String Buffer = "";

                //Utilizes buffers
                while((Buffer = reader.readLine()) != null){
                    sb.append(Buffer);
                }

                //Nulls out the buffer
                in = null;
                reader = null;
                Buffer = null;

                result = sb.toString();
                sb = null;
            }catch (Exception e){
                Error = true;
                return e.toString();
            }

            Log.d("RegisterDEBUG",result);
            try{
                final JSONObject json = new JSONObject(result);
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
                if (stringMap.get(1).toString().equalsIgnoreCase("10")){
                    result = "Register Done";
                    Error = false;
                }else{
                    Error = true;
                    ErrorCode = Integer.parseInt(stringMap.get(1).toString());
                    result = "Failed to Register -> " + stringMap.get(3).toString();
                    return result;
                }

                //there are so many secure ways to do this inseted o securing a encripted key
                String filename = "session";
                String fileContents = "AuthKey:" + stringMap.get(4).toString() + "\nEmail:" + stringMap.get(0).toString();
                try (FileOutputStream fos = openFileOutput(filename, MODE_PRIVATE)) {
                    fos.write(fileContents.getBytes());
                }catch (Exception e){
                    Error = true;
                    return e.toString();
                }
            }catch (Exception e){
                Error = true;
                return e.toString();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("Lol",s);
            if (Error){
                Log.d("Error",ErrorCode.toString());
                ErrorRegistering(ErrorCode);
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
