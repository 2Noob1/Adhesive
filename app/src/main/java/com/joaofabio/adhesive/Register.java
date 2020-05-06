package com.joaofabio.adhesive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.CollationElementIterator;
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

        Button alreadyhaveaccountbtn = findViewById(R.id.button7);
        alreadyhaveaccountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginAction.class));
                finishAfterTransition();
            }
        });

        Button Register = findViewById(R.id.button6);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
            }
        });

        tv = findViewById(R.id.editText7);
        tv.setFocusable(false);
        tv.setClickable(true);

    }

    public void doRegister(){
        dialogLoading = ProgressDialog.show(this, getResources().getString(R.string.register_progressTitle),
                getResources().getString(R.string.register_progressMessage), true);
        if (!checkforemptyfields()){
            NotNetDialog dialog = new NotNetDialog();
            dialog.targetActivity = this;
            dialog.PositiveText = getResources().getString(R.string.register_dialogmissingitems_positive);
            dialog.Title = getResources().getString(R.string.register_dialogmissingitems_title);
            dialog.Message = getResources().getString(R.string.register_dialogmissingitems_message);
            dialog.listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //no Action
                }
            };
            dialog.show(getSupportFragmentManager(), getResources().getString(R.string.register_dialogmissingitems_title));
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
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    public boolean checkforemptyfields(){
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
            if (tvBuffer.getText().toString().equalsIgnoreCase("")){
                return false;
            }
        }

        if (!Radios[0].isChecked() && !Radios[1].isChecked()){
            return false;
        }

        return true;
    }

    public void ErroRegistering(){
        NotNetDialog dialog = new NotNetDialog();
        dialog.targetActivity = this;
        dialog.PositiveText = getResources().getString(R.string.register_failed_positive);
        dialog.Title = getResources().getString(R.string.register_failed_title);
        dialog.Message = getResources().getString(R.string.register_failed_message);
        dialog.listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //no Action
            }
        };
        dialog.show(getSupportFragmentManager(), getResources().getString(R.string.register_failed_title));
        dialogLoading.dismiss();
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        EditText tv;

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
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
                Connection.setConnectTimeout(1200);
                Connection.setReadTimeout(1200);

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
                Iterator iterator = json.keys();
                ArrayList stringMap = new ArrayList();
                while(iterator.hasNext()){
                    String key = (String)iterator.next();
                    JSONObject Object = json.getJSONObject(key);
                    //  get id from  issue
                    stringMap.add(Object.optString("Email"));
                    stringMap.add(Object.optString("ResultCode"));
                    stringMap.add(Object.optString("RsultType"));
                    stringMap.add(Object.optString("ResultDesc"));
                    stringMap.add(Object.optString("AuthToken"));
                }
                Log.d("Array",stringMap.toString());
                if (stringMap.get(1).toString().equalsIgnoreCase("200")){
                    result = "Register Done";
                    Error = false;
                }else{
                    Error = true;
                    result = "Failed to Register -> " + stringMap.get(3).toString();
                    return result;
                }

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
                Log.d("Error",s);
                ErroRegistering();
            }else {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finishAfterTransition();
            }
        }
    }
}
