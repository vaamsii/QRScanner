package com.example.qrscanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView ScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScannerView = new ZXingScannerView(this);
        setContentView(ScannerView);
    }

    @Override
    public void handleResult(Result result) {

        //Intent i = getIntent();
        //String p = i.getData().getPath();
        Bundle  extras = getIntent().getExtras();
        if(extras != null){
            String value = extras.getString("path");
            String s = MainActivity.readText(value, result.getText());
            MainActivity.resultTextView.setText(s);

        }
        //if(p != null) {

            //String s = MainActivity.readText(p, result.getText());

            //String s = (String) MainActivity.searchFile(result.getText());
            //String s = MainActivity.readText(result.getText());

            //String S = MainActivity.myFileIntent.getData().getPath();
            //MainActivity.resultTextView.setText(result.getText());

            onBackPressed();
        }



    @Override
    protected void onPause() {
        super.onPause();
        ScannerView.stopCamera();

    }

    @Override
    protected void onResume(){
        super.onResume();
        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }




}
