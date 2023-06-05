package com.example.mobile_car_maintance_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ProfileContentActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_content);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        String loggedUserName = pref.getString("logged_user_name", "N/A");
        TextView title = findViewById(R.id.TextView);
        title.setText("Wybrany profil - "+loggedUserName);
    }

    public void deleteProfile(View v){
        SQLiteDatabase myDB = openOrCreateDatabase("database.db", MODE_PRIVATE, null);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        int loggedUserId = pref.getInt("logged_user_id", 999);

        myDB.delete("profilesDatabase", "id=?", new String[]{String.valueOf(loggedUserId)});
        myDB.close();
        Intent intent = new Intent(this, ChooseProfileActivity.class);
        startActivity(intent);
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