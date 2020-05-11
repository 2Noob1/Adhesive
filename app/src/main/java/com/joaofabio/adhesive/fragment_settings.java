package com.joaofabio.adhesive;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class fragment_settings extends Fragment {
    
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
                                FileManager Fm = new FileManager();
                                Fm.openFile(Objects.requireNonNull(getContext()),"session");
                                if (!Fm.removeFile()){
                                    DialogManager Dm = new DialogManager();
                                    Dm.Critical = false;
                                    Dm.ErrorCode = 13;
                                    Dm.listener = new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    };
                                    Dm.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(),"ErrorDialog");
                                }
                                else{
                                    startActivity(new Intent(getContext(),Launcher.class));
                                }
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
}
