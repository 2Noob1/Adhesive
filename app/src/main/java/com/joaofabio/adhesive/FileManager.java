package com.joaofabio.adhesive;

import android.app.Application;
import android.content.Context;

import java.io.File;

public class FileManager {

    private File file = null;

    //checks for file pretty obvious
    public boolean checkforfile(){
        if (file == null){
            throw new NullPointerException("No File Was Selected, cannot test an null file");
        }
        return file.exists();
    }

    public boolean openFile(Context context,String... strings){//there must be a better way to get a
        file = new File(context.getFilesDir() + "/" +strings[0]);
        if (file == null){
            return false;
        }else{
            return true;
        }
    }

    public boolean removeFile(){
        //Pretty simple
        return file.delete();
    }

}
