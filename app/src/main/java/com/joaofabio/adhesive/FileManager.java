package com.joaofabio.adhesive;

import android.app.Application;
import android.content.Context;

import java.io.File;

public class FileManager {

    private File file = null;


    public boolean checkforfile(){
        if (file == null){
            throw new NullPointerException("No File Was Selected, cannot test an null file");
        }
        if (file.exists()){
            return true;
        }else{
            return false;
        }
    }

    public boolean openFile(Context context,String... strings){
        file = new File(context.getFilesDir() + "/" +strings[0]);
        if (file == null){
            return false;
        }else{
            return true;
        }
    }

    public boolean removeFile(){
        return file.delete();
    }

}
