package com.example.wifisigstrength;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class NextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        TextView textView=findViewById(R.id.textView);
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, "file1.txt");
            FileReader reader = new FileReader(gpxfile);
            BufferedReader bfr = new BufferedReader(reader);
            String op="",st="";
            while ((st= bfr.readLine()) != null){
                op=op+st+"\n";
            }
            textView.setText(op);
            } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
