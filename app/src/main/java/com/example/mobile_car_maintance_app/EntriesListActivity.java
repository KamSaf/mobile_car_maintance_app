package com.example.mobile_car_maintance_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EntriesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entries_list);
        TextView title = findViewById(R.id.entriesListTitle);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        String givenName = pref.getString("chosen_car", "N/A");
        title.setText(givenName);
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