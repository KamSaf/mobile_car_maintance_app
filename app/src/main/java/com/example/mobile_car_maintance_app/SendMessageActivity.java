package com.example.mobile_car_maintance_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SendMessageActivity extends AppCompatActivity {

    final private int sendSmsCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
    }

    private void requestSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.SEND_SMS)) {
            new AlertDialog.Builder(this)
                    .setTitle("Potrzebne uprawnienia")
                    .setMessage("Uprawnienia są potrzebne do wysłania wiadomości tekstowej.")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(SendMessageActivity.this, new String[]{android.Manifest.permission.SEND_SMS}, sendSmsCode);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, sendSmsCode);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == sendSmsCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Uprawnienia udzielone", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Brak uprawnień", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void sendSMS(View v){
        EditText messageContent = findViewById(R.id.message);
        Intent parentIntent = getIntent();
        String phoneNumber = parentIntent.getStringExtra("phone_number");

        if (ContextCompat.checkSelfPermission(SendMessageActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, messageContent.getText().toString(), null, null);
            Toast.makeText(this, "Wiadomość wysłana!", Toast.LENGTH_SHORT).show();
        }
        else{
            requestSmsPermission();
        }
    }

}