package com.ar.myfirstapp.elm;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.ar.myfirstapp.MainActivity;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by Arun Soman on 2/28/2017.
 */

public class ELMStreamLogger implements ELMStreamHandler, Closeable{
    private ELMDataManager dataManager = new ELMDataManager();
    //private File myFile;
    private FileOutputStream fOut;
    private OutputStreamWriter myOutWriter;

    public ELMStreamLogger(){
        /*
      File  myFile = new File(Environment.getExternalStorageDirectory().getPath()+"/streetHwak.txt");
            if(!myFile.exists()) {
                try {
                    myFile.createNewFile();
                } catch (IOException e) {
                    Toast.makeText(MainActivity.me, e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        try {
            fOut = new FileOutputStream(myFile);
            myOutWriter = new OutputStreamWriter(fOut);
        } catch (FileNotFoundException e) {
            Toast.makeText(MainActivity.me, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
*/
    }
    @Override
    public void handle(String line) {
        Log.e("Resp", line);
//        dataManager.addResponse(line);
        /*try {
            myOutWriter.append(line);
        } catch (Exception e) {
            Toast.makeText(MainActivity.me, e.getMessage(), Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public void close() throws IOException {
            /*
            myOutWriter.close();

            fOut.close();
             */
    }
}
