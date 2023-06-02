package com.example.mobile_car_maintance_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ProfileContentActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_content);
    }

    public void goToChoosingCar(View v) {
        Intent intent = new Intent(this, ChooseCarActivity.class);
        startActivity(intent);
    }

    public void goToAddingCar(View v) {
        Intent intent = new Intent(this, AddCarActivity.class);
        startActivity(intent);
    }

    public void goToWorkshopsList(View v) {
        Intent intent = new Intent(this, WorkshopsListActivity.class);
        startActivity(intent);
    }
}