package com.example.masodikzh_bm9pc7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class MessageActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private SharedPreferences prefs;

    SmsManager smsManager = SmsManager.getDefault();
    EditText editTextMessage, editTextRecipient, editTextRandomNumber, editTextMin,editTextMax;
    EditText txtphoneNo;
    EditText txtMessage;
    String phoneNo;
    String message;
    int tmp;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        editTextMessage = findViewById(R.id.txtMessage);
        editTextRecipient = findViewById(R.id.txtphoneNo);
        editTextRandomNumber = findViewById(R.id.editTextRandomNumber);
        editTextMin = findViewById(R.id.editTextMin);
        editTextMax = findViewById(R.id.editTextMax);

        txtphoneNo = (EditText) findViewById(R.id.txtphoneNo);
        txtMessage = (EditText) findViewById(R.id.txtMessage);
    }

    protected void sendSMSMessage() {
        phoneNo = txtphoneNo.getText().toString();
        message = txtMessage.getText().toString();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_SEND_SMS) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNo, null, message, null, null);
                Toast.makeText(getApplicationContext(), "SMS sent.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "SMS faild, please try again.", Toast.LENGTH_LONG).show();
            }
        }
    }


    public void writeToInternal(View view){
        String text = txtMessage.getText().toString();

        try {
            FileOutputStream fileOutputStream;
            fileOutputStream = openFileOutput("BM9PC7.txt",MODE_APPEND);
            fileOutputStream.write(text.getBytes(StandardCharsets.UTF_8));
            fileOutputStream.close();
            Toast.makeText(this,"File written successfully!",Toast.LENGTH_LONG).show();
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(View view){
        String phoneNumber = editTextRecipient.getText().toString();
        if (phoneNumber.length() == 11){
            Toast.makeText(this,"Message sent to" +phoneNumber.toString()+ "!", Toast.LENGTH_LONG).show();
            sendSMSMessage();
            writeToInternal(view);
        }
        else {
            Toast.makeText(this,"hibas telefonszam", Toast.LENGTH_LONG).show();
        }
    }

    public void randomNumberGenerate(View view){

        int max = Integer.parseInt(editTextMax.getText().toString());
        int min = Integer.parseInt(editTextMax.getText().toString());

        new Thread(new Runnable() {
            @Override
            public void run() {
                tmp = 0;
                Random random = new Random();
                for (int i = 0; i < 10; i++){
                    tmp += random.nextInt(max-min)+min;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    editTextRandomNumber.post(new Runnable() {
                        @Override
                        public void run() {
                            editTextRandomNumber.setText(String.valueOf(tmp));
                        }
                    });
                }
            }
        }).start();
    }

}