package com.limeparallelogram.imgtransmitproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {


    public static final int STATUS_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button click = (Button)findViewById(R.id.button);
        click.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            STATUS_CODE);
                } else {
                    send sendcode = new send();
                    sendcode.execute();

                }

            }
        });


    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case STATUS_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // TODO run your code
                } else {
                    // TODO show warning
                }
            }
        }
    }


}
class send extends AsyncTask<Void,Void,Void> {
    static Socket s;
    @Override
    protected Void doInBackground(Void...params){
        try {
            s = new Socket("192.168.2.78",8000);
            File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File photoPath = new File(directory, "frog-face_1f438.png");
            InputStream input = new FileInputStream(photoPath.getAbsolutePath());
            try {
                try {
                    int bytesRead;
                    while ((bytesRead = input.read()) != -1) {
                        ByteArrayOutputStream bout = new ByteArrayOutputStream();
                        bout.write(bytesRead);
                        s.getOutputStream().write(bout.toByteArray());
                    }
                } finally {
                    s.getOutputStream().flush();
                    s.close();
                }
            } finally {
                input.close();
            }
            FileReader fr = new FileReader(photoPath.getAbsolutePath());


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
