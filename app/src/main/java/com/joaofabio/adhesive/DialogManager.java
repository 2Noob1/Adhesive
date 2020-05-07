package com.joaofabio.adhesive;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.lang.reflect.Field;
import java.util.Objects;

public class DialogManager extends DialogFragment {

    int ErrorCode;
    Activity targetActivity;
    boolean Critical;
    DialogInterface.OnClickListener listener;
    private String Title;
    private String Message;
    private String Positive;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        switch(ErrorCode){
            case 1:
                Title = getResources().getString(R.string.dialog_error_Title_1);
                Message = getResources().getString(R.string.dialog_error_Message_1);
                Positive = getResources().getString(R.string.dialog_error_Positive_1);
                break;
            case 2:
                Title = getResources().getString(R.string.dialog_error_Title_2);
                Message = getResources().getString(R.string.dialog_error_Message_2);
                Positive = getResources().getString(R.string.dialog_error_Positive_2);
                break;
            case 3:
                Title = getResources().getString(R.string.dialog_error_Title_3);
                Message = getResources().getString(R.string.dialog_error_Message_3);
                Positive = getResources().getString(R.string.dialog_error_Positive_3);
                break;
            case 4:
                Title = getResources().getString(R.string.dialog_error_Title_4);
                Message = getResources().getString(R.string.dialog_error_Message_4);
                Positive = getResources().getString(R.string.dialog_error_Positive_4);
                break;
            case 5:
                //Title = getResources().getString(R.string.dialog_error_Title_5);
                //Message = getResources().getString(R.string.dialog_error_Message_5);
                //Positive = getResources().getString(R.string.dialog_error_Positive_5);
                break;
            case 6:
                Title = getResources().getString(R.string.dialog_error_Title_6);
                Message = getResources().getString(R.string.dialog_error_Message_6);
                Positive = getResources().getString(R.string.dialog_error_Positive_6);
                break;
            case 7:
                Title = getResources().getString(R.string.dialog_error_Title_7);
                Message = getResources().getString(R.string.dialog_error_Message_7);
                Positive = getResources().getString(R.string.dialog_error_Positive_7);
                break;
            case 8:
                Title = getResources().getString(R.string.dialog_error_Title_8);
                Message = getResources().getString(R.string.dialog_error_Message_8);
                Positive = getResources().getString(R.string.dialog_error_Positive_8);
                break;
            case 9:
                //Title = getResources().getString(R.string.dialog_error_Title_9);
                //Message = getResources().getString(R.string.dialog_error_Message_9);
                //Positive = getResources().getString(R.string.dialog_error_Positive_9);
                break;
            case 10:
                //Title = getResources().getString(R.string.dialog_error_Title_10);
                //Message = getResources().getString(R.string.dialog_error_Message_10);
                //Positive = getResources().getString(R.string.dialog_error_Positive_10);
                break;
            case 11:
                //Title = getResources().getString(R.string.dialog_error_Title_11);
                //Message = getResources().getString(R.string.dialog_error_Message_11);
                //Positive = getResources().getString(R.string.dialog_error_Positive_11);
                break;
            case 12:
                //Title = getResources().getString(R.string.dialog_error_Title_12);
                //Message = getResources().getString(R.string.dialog_error_Message_12);
                //Positive = getResources().getString(R.string.dialog_error_Positive_12);
                break;
        }

        AlertDialog.Builder builder =  new AlertDialog.Builder(targetActivity);
        builder.setTitle(Title)
                .setMessage(Message)
                .setPositiveButton(Positive, listener);
        return builder.create();
    }

    public void onDismiss(@NonNull final DialogInterface dialog) {
       if (Critical){
           Objects.requireNonNull(getActivity()).finish();
       }
    }
}
