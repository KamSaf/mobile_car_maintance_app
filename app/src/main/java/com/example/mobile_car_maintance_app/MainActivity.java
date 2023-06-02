package com.example.mobile_car_maintance_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToChoosingProfile(View v) {
        Intent intent = new Intent(this, ChooseProfileActivity.class);
        startActivity(intent);
    }

    public void goToCreatingProfile(View v) {
        Intent intent = new Intent(this, CreatingProfile.class);
        startActivity(intent);
    }
}