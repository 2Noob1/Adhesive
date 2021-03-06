package com.joaofabio.adhesive;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.*;
import androidx.fragment.app.DialogFragment;
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

public class fragment_settings extends Fragment {

    private ProgressDialog dialogLoading = null;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (dialogLoading != null) {
            dialogLoading.dismiss();
            dialogLoading = null;
        }
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Legal Stuff
        final Button osbutton = view.findViewById(R.id.osbutton); //OpenSource
        osbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogManager Dm = new DialogManager();
                Dm.Critical = false;
                Dm.ErrorCode = 14;
                Dm.listener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
                Dm.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "LegalDialog");
                Dm = null;
            }
        });

        final Button deleteAccount = view.findViewById(R.id.deleteAccount); //removeAccount
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                DeleteAccount req = new DeleteAccount();
                                ArrayList list = getFileKeys("session");
                                req.execute("https://turma12i.com/JoaoFabio/FCT/removeUser.php", "POST", list.get(0).toString(), list.get(1).toString());
                                dialogLoading = ProgressDialog.show(getContext(), getResources().getString(R.string.dialog_deactivatingtitle), getResources().getString(R.string.dialog_deactivatingmessage), true);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getResources().getString(R.string.deactivte_title)).setMessage(getResources().getString(R.string.deativate_message)).setPositiveButton(getResources().getString(R.string.deactivate_positive), dialogClickListener).setNegativeButton(getResources().getString(R.string.deactivate_negatove), dialogClickListener).show();
            }
        });

        final Button changepassword = view.findViewById(R.id.chagepasswordbtn); //change Password
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getResources().getString(R.string.Settings_NewPassword));
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);
                builder.setPositiveButton(getResources().getString(R.string.Accept_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ChangePassword Chng = new ChangePassword();
                        ArrayList list = getFileKeys("session");
                        Chng.execute("https://turma12i.com/JoaoFabio/FCT/ChangePassword.php", "POST", list.get(0).toString(), list.get(1).toString(), input.getText().toString());
                        dialogLoading = ProgressDialog.show(getContext(), getResources().getString(R.string.changepassword_dialog), getResources().getString(R.string.changepassword_dialog_Message), true);
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.Cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        final Button changeEmail = view.findViewById(R.id.changeemail); //changeEmail
        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getResources().getString(R.string.Settings_NewEmail));
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                builder.setView(input);
                builder.setPositiveButton(getResources().getString(R.string.Accept_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ChangeEmail Chng = new ChangeEmail();
                        ArrayList list = getFileKeys("session");
                        Chng.execute("https://turma12i.com/JoaoFabio/FCT/changeEmail.php", "POST", list.get(0).toString(), list.get(1).toString(), input.getText().toString());
                        dialogLoading = ProgressDialog.show(getContext(), getResources().getString(R.string.changepassword_dialog), getResources().getString(R.string.changepassword_dialog_Message), true);
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.Cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        final Button closed = view.findViewById(R.id.closedsource); //closedSource
        closed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogManager Dm = new DialogManager();
                Dm.Critical = false;
                Dm.ErrorCode = 15;
                Dm.listener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                };
                Dm.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "LegalDialog");
                Dm = null;
            }
        });

        final Button userAgreement = view.findViewById(R.id.useterms); //EULAS
        userAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogManager Dm = new DialogManager();
                Dm.Critical = false;
                Dm.ErrorCode = 17;
                Dm.listener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
                Dm.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "LegalDialog");
                Dm = null;
            }
        });

        final Button privacy = view.findViewById(R.id.privacy); //Privacy
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogManager Dm = new DialogManager();
                Dm.Critical = false;
                Dm.ErrorCode = 16;
                Dm.listener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                };
                Dm.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "LegalDialog");
                Dm = null;
            }
        });

        //Logut
        final Button LogoutButton = view.findViewById(R.id.logout); //
        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                doLogout request = new doLogout();
                                ArrayList < String > Data = getFileKeys("session");
                                request.execute("https://turma12i.com/JoaoFabio/FCT/LogoutAndroid.php", "POST", Data.get(0), Data.get(1));
                                dialogLoading = ProgressDialog.show(getContext(), getResources().getString(R.string.logout_title), getResources().getString(R.string.logout_message), true);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getResources().getString(R.string.dialog_logout_title)).setMessage(getResources().getString(R.string.dialog_logout_message)).setPositiveButton(getResources().getString(R.string.dialog_logout_positive), dialogClickListener).setNegativeButton(getResources().getString(R.string.dialog_logout_negative), dialogClickListener).show();
            }
        });
    }

    private void ChangePasswordFailed() {
        dialogLoading.dismiss();
    }

    @SuppressLint("StaticFieldLeak")
    public class doLogout extends AsyncTask < String,
            Void,
            String > {
        boolean Invalid = false;
        boolean Error = false;

        protected String doInBackground(String...strings) {
            String InputBuffer;
            String Result;

            try {
                Log.d("Params", strings[0]);
                Log.d("Params", strings[2]);
                Log.d("Params", strings[3]);
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
                Connection.setRequestProperty("Key", strings[2]);
                Connection.setRequestProperty("Email", strings[3]);
                Log.d("Request", Connection.getRequestProperties().toString());
                //Connect
                Connection.connect();
                //Initialize Buffers
                InputStreamReader in = new InputStreamReader(Connection.getInputStream());
                StringBuilder sb = new StringBuilder();
                BufferedReader Buffer = new BufferedReader( in );
                //Reads Contents
                while ((InputBuffer = Buffer.readLine()) != null) {
                    sb.append(InputBuffer);
                }
                //Closes Buffers
                in .close(); in = null;
                Buffer.close();
                Buffer = null;
                InputBuffer = null;
                //returns
                Result = sb.toString();
                Log.d("Lol", Result);

                if (Result.equals("valid")) {
                    Result = "valid";
                    Error = false;
                    Log.d("lolo", String.valueOf(Error));
                } else if (Result.equalsIgnoreCase("error")) {
                    Result = "error";
                    Error = true;
                    Log.d("lolo", String.valueOf(Error));
                } else {
                    Result = "invalid";
                    Invalid = true;
                    Log.d("lolo", String.valueOf(Invalid));
                }
            } catch (Exception e) {
                Error = true;
                return e.toString();
            }
            return Result;
        }

        protected void onPostExecute(String s) {
            if (Invalid) {
                Intent newActivity = new Intent(getContext(), login.class);
                newActivity.putExtra("NeedsError", true);
                newActivity.putExtra("ErrorCode", 5);
                startActivity(newActivity);
            } else {
                if (Error) {
                    DialogManager Dm = new DialogManager();
                    Dm.Critical = false;
                    Dm.ErrorCode = 12;
                    Dm.listener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
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
                            public void onClick(DialogInterface dialog, int which) {}
                        };
                        Dm.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "ErrorDialog");
                    } else {
                        startActivity(new Intent(getContext(), Launcher.class));
                    }
                }
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class ChangePassword extends AsyncTask < String,
            Void,
            String > {
        boolean Invalid = false;
        boolean Error = false;

        protected String doInBackground(String...strings) {
            String InputBuffer;
            String Result;

            try {
                Log.d("Params", strings[0]);
                Log.d("Params", strings[2]);
                Log.d("Params", strings[3]);
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
                Connection.setRequestProperty("Key", strings[2]);
                Connection.setRequestProperty("Email", strings[3]);
                Connection.setRequestProperty("Password", strings[4]);
                Log.d("Request", Connection.getRequestProperties().toString());
                //Connect
                Connection.connect();
                //Initialize Buffers
                InputStreamReader in = new InputStreamReader(Connection.getInputStream());
                StringBuilder sb = new StringBuilder();
                BufferedReader Buffer = new BufferedReader( in );
                //Reads Contents
                while ((InputBuffer = Buffer.readLine()) != null) {
                    sb.append(InputBuffer);
                }
                //Closes Buffers
                in .close(); in = null;
                Buffer.close();
                Buffer = null;
                InputBuffer = null;
                //returns
                Result = sb.toString();
                Log.d("Lol", Result);

                if (Result.equals("valid")) {
                    Result = "valid";
                    Error = false;
                    Log.d("lolo", String.valueOf(Error));
                } else if (Result.equalsIgnoreCase("error")) {
                    Result = "error";
                    Error = true;
                    Log.d("lolo", String.valueOf(Error));
                } else {
                    Result = "invalid";
                    Invalid = true;
                    Log.d("lolo", String.valueOf(Invalid));
                }
            } catch (Exception e) {
                Error = true;
                return e.toString();
            }
            return Result;
        }

        protected void onPostExecute(String s) {
            if (Invalid) {
                Intent newActivity = new Intent(getContext(), login.class);
                newActivity.putExtra("NeedsError", true);
                newActivity.putExtra("ErrorCode", 5);
                startActivity(newActivity);
            } else {
                if (Error) {
                    DialogManager Dm = new DialogManager();
                    Dm.Critical = false;
                    Dm.ErrorCode = 21;
                    Dm.listener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    };
                    Dm.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "ErrorDialog");
                    ChangePasswordFailed();
                } else {
                    DialogManager Dm = new DialogManager();
                    Dm.Critical = false;
                    Dm.ErrorCode = 22;
                    Dm.listener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    };
                    Dm.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "ErrorDialog");
                    ChangePasswordFailed();
                }
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class ChangeEmail extends AsyncTask < String,
            Void,
            String > {
        boolean Invalid = false;
        boolean Error = false;

        protected String doInBackground(String...strings) {
            String InputBuffer;
            String Result;

            try {
                Log.d("Params", strings[0]);
                Log.d("Params", strings[2]);
                Log.d("Params", strings[3]);
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
                Connection.setRequestProperty("Key", strings[2]);
                Connection.setRequestProperty("Email", strings[3]);
                Connection.setRequestProperty("newEmail", strings[4]);
                Log.d("Request", Connection.getRequestProperties().toString());
                //Connect
                Connection.connect();
                //Initialize Buffers
                InputStreamReader in = new InputStreamReader(Connection.getInputStream());
                StringBuilder sb = new StringBuilder();
                BufferedReader Buffer = new BufferedReader( in );
                //Reads Contents
                while ((InputBuffer = Buffer.readLine()) != null) {
                    sb.append(InputBuffer);
                }
                //Closes Buffers
                in .close(); in = null;
                Buffer.close();
                Buffer = null;
                InputBuffer = null;
                //returns
                Result = sb.toString();
                Log.d("Lol", Result);

                if (Result.equals("valid")) {
                    Result = "valid";
                    Error = false;
                    Log.d("lolo", String.valueOf(Error));
                } else if (Result.equalsIgnoreCase("error")) {
                    Result = "error";
                    Error = true;
                    Log.d("lolo", String.valueOf(Error));
                } else {
                    Result = "invalid";
                    Invalid = true;
                    Log.d("lolo", String.valueOf(Invalid));
                }
            } catch (Exception e) {
                Error = true;
                return e.toString();
            }
            return Result;
        }

        protected void onPostExecute(String s) {
            if (Invalid) {
                Intent newActivity = new Intent(getContext(), login.class);
                newActivity.putExtra("NeedsError", true);
                newActivity.putExtra("ErrorCode", 5);
                startActivity(newActivity);
            } else {
                if (Error) {
                    DialogManager Dm = new DialogManager();
                    Dm.Critical = false;
                    Dm.ErrorCode = 24;
                    Dm.listener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    };
                    Dm.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "ErrorDialog");
                    ChangePasswordFailed();
                } else {
                    DialogManager Dm = new DialogManager();
                    Dm.Critical = false;
                    Dm.ErrorCode = 23;
                    Dm.listener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FileManager Fm = new FileManager();
                            Fm.openFile(Objects.requireNonNull(getContext()), "session");
                            Fm.removeFile();
                            startActivity(new Intent(getContext(), Launcher.class));
                        }
                    };
                    Dm.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "ErrorDialog");
                    ChangePasswordFailed();
                }
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class DeleteAccount extends AsyncTask < String,
            Void,
            String > {
        boolean Invalid = false;
        boolean Error = false;

        protected String doInBackground(String...strings) {
            String InputBuffer;
            String Result;

            try {
                Log.d("Params", strings[0]);
                Log.d("Params", strings[2]);
                Log.d("Params", strings[3]);
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
                Connection.setRequestProperty("Key", strings[2]);
                Connection.setRequestProperty("Email", strings[3]);
                Log.d("Request", Connection.getRequestProperties().toString());
                //Connect
                Connection.connect();
                //Initialize Buffers
                InputStreamReader in = new InputStreamReader(Connection.getInputStream());
                StringBuilder sb = new StringBuilder();
                BufferedReader Buffer = new BufferedReader( in );
                //Reads Contents
                while ((InputBuffer = Buffer.readLine()) != null) {
                    sb.append(InputBuffer);
                }
                //Closes Buffers
                in .close(); in = null;
                Buffer.close();
                Buffer = null;
                InputBuffer = null;
                //returns
                Result = sb.toString();
                Log.d("Lol", Result);

                if (Result.equals("valid")) {
                    Result = "valid";
                    Error = false;
                    Log.d("lolo", String.valueOf(Error));
                } else if (Result.equalsIgnoreCase("error")) {
                    Result = "error";
                    Error = true;
                    Log.d("lolo", String.valueOf(Error));
                } else {
                    Result = "invalid";
                    Invalid = true;
                    Log.d("lolo", String.valueOf(Invalid));
                }
            } catch (Exception e) {
                Error = true;
                return e.toString();
            }
            return Result;
        }

        protected void onPostExecute(String s) {
            if (Invalid) {
                Intent newActivity = new Intent(getContext(), login.class);
                newActivity.putExtra("NeedsError", true);
                newActivity.putExtra("ErrorCode", 5);
                startActivity(newActivity);
            } else {
                if (Error) {
                    DialogManager Dm = new DialogManager();
                    Dm.Critical = false;
                    Dm.ErrorCode = 26;
                    Dm.listener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    };
                    Dm.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "ErrorDialog");
                    ChangePasswordFailed();
                } else {
                    DialogManager Dm = new DialogManager();
                    Dm.Critical = false;
                    Dm.ErrorCode = 25;
                    Dm.listener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FileManager Fm = new FileManager();
                            Fm.openFile(Objects.requireNonNull(getContext()), "session");
                            Fm.removeFile();
                            startActivity(new Intent(getContext(), Launcher.class));
                        }
                    };
                    Dm.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "ErrorDialog");
                    ChangePasswordFailed();
                }
            }
        }
    }

    //Explicado pelo fabio
    private ArrayList < String > getFileKeys(String FileName) {
        ArrayList < String > list = new ArrayList < String > ();
        File file = new File(Objects.requireNonNull(getContext()).getFilesDir().toString() + "session");
        int LineCounter = 0;
        FileInputStream fis = null;
        try {
            fis = getContext().openFileInput("session");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert fis != null;
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
            int c;
            int d = 0;
            for (c = 0; c < contents.length(); c++) {
                if (contents.charAt(c) == '\n') {
                    d = 1;
                    continue;
                } else {
                    array[d] = array[d] + contents.charAt(c);
                }
            }
            for (int h = 0; h < array.length; h++) {
                Log.d("LINE70", "Array[" + h + "] -> " + array[h].replace("null", ""));
                array[h] = array[h].replace("null", "");
                int t = -1;
                String Buffer = null;
                for (c = 0; c < array[h].length(); c++) {
                    if (array[h].charAt(c) == ':') {
                        assert Buffer != null;
                        Log.d("LINE76", "Buffer -> " + Buffer.replace("null", ""));
                        Buffer = Buffer.replace("null", "");
                        c = 9999;
                    } else {
                        Buffer = Buffer + array[h].charAt(c);
                    }
                }
                String Dump = array[h];
                Dump = Dump.replace(Buffer + ":", "");
                assert Buffer != null;
                list.add(Dump.replace(Buffer, ""));
                Buffer = null;
            }
        }
        return list;
    }

    @Override
    public void onResume() {
        super.onResume();

        DialogFragment Df = (DialogFragment) Objects.requireNonNull(getActivity()).getSupportFragmentManager().findFragmentByTag("LegalDialog");
        if (Df != null) Df.dismiss();

        Df = (DialogFragment) Objects.requireNonNull(getActivity()).getSupportFragmentManager().findFragmentByTag("ErrorDialog");
        if (Df != null) Df.dismiss();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        DialogFragment Df = (DialogFragment) Objects.requireNonNull(getActivity()).getSupportFragmentManager().findFragmentByTag("LegalDialog");
        if (Df != null) Df.dismiss();

        Df = (DialogFragment) Objects.requireNonNull(getActivity()).getSupportFragmentManager().findFragmentByTag("ErrorDialog");
        if (Df != null) Df.dismiss();
    }
}