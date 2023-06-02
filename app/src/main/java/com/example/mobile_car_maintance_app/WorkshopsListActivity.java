package com.example.mobile_car_maintance_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WorkshopsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshops_list);
    }

    public void goToAddWorkshop(View v) {
        Intent intent = new Intent(this, AddWorkshopActivity.class);
        startActivity(intent);
    }

    public void goToWorkshopDetails(View v) {
        Intent intent = new Intent(this, WorkshopDetailsActivity.class);
        startActivity(intent);
    }

}