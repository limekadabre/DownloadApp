package com.example.downloadapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.view.ContentInfo;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FilenameUtils;

public class MainActivity extends AppCompatActivity {

    String[] urls = {
            "http://www.africau.edu/images/default/sample.pdf",
            "https://images.pexels.com/photos/60597/dahlia-red-blossom-bloom-60597.jpeg?cs=srgb&dl=pexels-pixabay-60597.jpg&fm=jpg",
            "https://images.pexels.com/photos/65894/peacock-pen-alluring-yet-lure-65894.jpeg?cs=srgb&dl=pexels-pixabay-65894.jpg&fm=jpg",
            "https://www.orimi.com/pdf-test.pdf",
            "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"
    };
    Button download;
    private static final int PERMISSION_STORAGE_CODE = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        download = findViewById(R.id.download);

        TextView textViewToChange1 = (TextView) findViewById(R.id.textView8);
        textViewToChange1.setText(urls[0]);
        TextView textViewToChange2 = (TextView) findViewById(R.id.textView10);
        textViewToChange2.setText(urls[1]);
        TextView textViewToChange3 = (TextView) findViewById(R.id.textView11);
        textViewToChange3.setText(urls[2]);
        TextView textViewToChange4 = (TextView) findViewById(R.id.textView12);
        textViewToChange4.setText(urls[3]);
        TextView textViewToChange5 = (TextView) findViewById(R.id.textView13);
        textViewToChange5.setText(urls[4]);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_STORAGE_CODE);
                    } else {
                        for (int i=0;i<urls.length;i++){
                            startDownloading(urls[i]);
                        }
                    }
                } else {
                    for (int i=0;i<urls.length;i++){
                        startDownloading(urls[i]);
                    }
                }




            }
        });
    }


    public void startDownloading(String url) {
        //created download request
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url));

        // Allow all network types to download files
        request.setAllowedNetworkTypes((DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE));
//        String fileName = FilenameUtils.getName(url.getPath());
        String filename=URLUtil.guessFileName(url, null, null);
        request.setTitle(filename);  //set title in download notification
        request.setDescription("Downloading file");

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, ""+System.currentTimeMillis());
         //get download service and enqueue file
        DownloadManager manager=(DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

    }


    public  void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case PERMISSION_STORAGE_CODE:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    for (int i=0;i<urls.length;i++){
                        startDownloading(urls[i]);

                    }
                }
                else {
                    Toast.makeText(this , "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
    }
}

}