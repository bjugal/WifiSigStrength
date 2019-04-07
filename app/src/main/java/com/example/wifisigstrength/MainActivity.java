package com.example.wifisigstrength;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    String op="";
    WifiManager wifi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button turnOnBtn = findViewById(R.id.turnOnBtn);
        final TextView opTextView = findViewById(R.id.opTextView);
        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if(!wifi.isWifiEnabled()) {
            op = "wifi disabled";
        }
        else {
            op = "wifi enabled";
        }
        opTextView.setText(op);

        turnOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!wifi.isWifiEnabled()) {
                    wifi.setWifiEnabled(true);
                    op = "wifi enabled";
                }
                else
                {
                    wifi.setWifiEnabled(false);
                    op = "wifi disabled";
                }
                opTextView.setText(op);
            }
        });

        Button checkSigBtn = findViewById(R.id.checkSigBtn);
        checkSigBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                int linkSpeed = wifiManager.getConnectionInfo().getRssi();
                System.out.println(linkSpeed);
                op=linkSpeed + " dBm";
                opTextView.setText(op);
            }
        });

        Button storeBtn = findViewById(R.id.storeBtn);
        storeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    store(getApplicationContext(),"file1.txt",op+"\n");
                }

            }
        });

        Button readBtn=findViewById(R.id.readBtn);
        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goNext=new Intent(MainActivity.this,NextActivity.class);
                startActivity(goNext);
            }
        });
    }

    public void store(Context context, String sFileName, String sBody) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile,true);
            writer.append(sBody);
            writer.flush();
            writer.close();
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
