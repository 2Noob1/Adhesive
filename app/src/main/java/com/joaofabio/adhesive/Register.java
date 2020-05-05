package com.joaofabio.adhesive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.CollationElementIterator;
import java.util.Calendar;
import java.util.Objects;

public class Register extends AppCompatActivity {
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

        tv = findViewById(R.id.editText7);
        tv.setFocusable(false);
        tv.setClickable(true);

    }

    public void doRegister(){
        if (!checkforemptyfields()){
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
            dialog.show(getSupportFragmentManager(), getResources().getString(R.string.login_missingfields_title));
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
            tv.setText(day + "/" + (month + 1) + "/" + year);
        }
    }

}
