package com.example.mobile_car_maintance_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MaintanceEntriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintance_entries);
    }

    public void goToCarInfo(View v) {
        Intent intent = new Intent(this, CarInfoActivity.class);
        startActivity(intent);
    }

    public void goToEntry(View v) {
        Intent intent = new Intent(this, EntryContentActivity.class);
        startActivity(intent);
    }

    public void addNewEntry(View v) {
        Intent intent = new Intent(this, AddEntryActivity.class);
        startActivity(intent);
    }

}