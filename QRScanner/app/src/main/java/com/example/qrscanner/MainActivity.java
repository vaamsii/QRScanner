package com.example.qrscanner;

 import android.Manifest;
 import android.app.Activity;
 import android.content.Intent;
 import android.content.pm.PackageManager;
 import android.net.Uri;
 import android.os.Build;
 import android.os.Bundle;
 import android.os.Environment;
 import android.support.annotation.NonNull;
 import android.support.annotation.Nullable;
 import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
 import android.widget.Toast;
 import java.util.*;


 import java.io.BufferedReader;
 import java.io.File;
import java.io.FileNotFoundException;
 import java.io.FileReader;
 import java.io.IOException;
 import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_STORAGE = 1000;
    private static final int READ_REQUEST_CODE = 1000;


    public static TextView resultTextView;
    Button scan_button;
    Button file_picker;
    public static TextView text_pathShow;

    //public Intent myFileIntent = new Intent(MainActivity.this, ScanCodeActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //REQUEST PERMISSION
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){


        }


        resultTextView = (TextView) findViewById(R.id.result_text);
        scan_button = (Button) findViewById(R.id.btn_scan);
        file_picker = (Button) findViewById(R.id.btn_file);
        text_pathShow = (TextView) findViewById(R.id.txt_path);



        file_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();

            }
        });


//        file_picker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                myFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
//                myFileIntent.setType("*/*");
//                startActivityForResult(myFileIntent, 10);
//
//            }
//        });

        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), ScanCodeActivity.class));
            }


        });

    }

    //read the content from the file
    public static String readText(String input, String value) {
        File file = new File(input);
        StringBuilder text = new StringBuilder();
        String contents;
        String Result = "";
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            //List arrayLines = new ArrayList();
            while ((contents = br.readLine()) != null)
            {
                String[] values = contents.split( "," ) ;
                String qrNumber = values[0];
                String name = values[1];
                String snum = values[2];
                String events = values[3];

                Result = qrNumber + "," + name + "," + snum + "," + events;

                if (qrNumber.equals(value)) {
                    return Result;

                }
            }
        }
       catch (IOException e){
            e.printStackTrace();
        }
        return Result;
    }

    //select file from storage
    private void performFileSearch(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/*");
        startActivityForResult(intent,READ_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            if(data != null){
                //Uri uri = data.getData();
                String path = data.getData().getPath();
                //myFileIntent.putExtra(path,uri);

                //myFileIntent.putExtra(path,path);
                //startActivity(myFileIntent);

                Intent i = new Intent(this, ScanCodeActivity.class);
                i.putExtra("path", path);

                //path = path.substring(path.indexOf(":")+1);
                //Toast.makeText(this, ""+path, Toast.LENGTH_SHORT).show();
                //text_pathShow.setText(readText(path.toString()));
               text_pathShow.setText((path));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSION_REQUEST_STORAGE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Permission not Granted", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}

//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//
//        switch (requestCode) {
//            case 10:
//
//                if (resultCode == RESULT_OK) {
//                    String path = data.getData().getPath();
//                    text_pathShow.setText(path);
//                }
//
//        }
//    }

//    public static String searchFile(String value){
//
//        //File file = new File(Environment.getExternalStorageDirectory(),value);
//        File file = new File(String.valueOf(text_pathShow));
//        String Result = "";
//
//        try {
//            Scanner inputStream = new Scanner(file);
//            while (inputStream.hasNextLine()) {
//                String data = inputStream.nextLine();
//                // System.out.println(data);
//                String[] values = data.split(",");
//                String qrNumber = values[0];
//                String name = values[1];
//                String snum = values[2];
//                String events = values[3];
//
//                Result = qrNumber + "," + name + "," + snum + "," + events;
//
//                if (qrNumber.equals(value)) {
//                    return Result;
//
//                }
//            }
//            inputStream.close();
//
//        }
//         catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        return Result;
//    }
//
//
//}




