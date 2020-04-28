package com.joaofabio.adhesive;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.fragment.app.DialogFragment;

public class NotNetDialog extends DialogFragment {

    public String Title;
    public String Message;
    public String PositiveText;
    public DialogInterface.OnClickListener listener;
    public Activity targetActivity;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder =  new AlertDialog.Builder(targetActivity);
        builder.setTitle(Title)
                .setMessage(Message)
                .setPositiveButton(PositiveText, listener);
        return builder.create();
    }
}
