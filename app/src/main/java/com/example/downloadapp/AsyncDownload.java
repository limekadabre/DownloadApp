package com.example.downloadapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.URLUtil;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class AsyncDownload extends AsyncTask {


    String root = Environment.getExternalStorageDirectory().toString();
    @Override
    protected Object doInBackground(Object[] files) {

        for(int i =0;i< files.length;i++){
            String pdf = files[i].toString();

            String filename= URLUtil.guessFileName(pdf, null, null);

            try {
                URL url = new URL(pdf);
                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();

                InputStream inputStream = new BufferedInputStream(url.openStream(),8192);
                OutputStream outputStream = new FileOutputStream(root+"/Download/"+filename);

                byte data[] = new byte[1024];
                long total = 0;
                int count;
                while ((count = inputStream.read(data)) != -1) {
                    total += count;
                    outputStream.write(data, 0, count);
                }

                outputStream.flush();
                outputStream.close();
                inputStream.close();


            }catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        }
        return null;
    }

}