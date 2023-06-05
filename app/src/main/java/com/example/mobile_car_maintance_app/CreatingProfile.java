package com.example.mobile_car_maintance_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreatingProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_profile);
    }



    public void createProfile(View v){
        SQLiteDatabase myDB = openOrCreateDatabase("database.db", MODE_PRIVATE, null);
        myDB.execSQL(
                "CREATE TABLE IF NOT EXISTS profilesDatabase (id integer primary key, name VARCHAR(100))"
        );
        myDB.execSQL(
                "CREATE TABLE IF NOT EXISTS vehiclesDatabase (id integer primary key, profileId integer, make VARCHAR(200), model VARCHAR(200), productionYear VARCHAR(200), fuelType VARCHAR(100), displacement integer, power integer, numberPlate VARCHAR(25), vin VARCHAR(50))"
        );
        myDB.execSQL(
                "CREATE TABLE IF NOT EXISTS workshopsDatabase (id integer primary key, profileId integer, name VARCHAR(200), address VARCHAR(200), sector VARCHAR(200), city VARCHAR(100), phoneNumber VARCHAR(50))"
        );
        ContentValues profile = new ContentValues();
        EditText inputName = findViewById(R.id.profileName);
        profile.put("name", inputName.getText().toString());
        try{
            myDB.insert("profilesDatabase", null, profile);
            Toast.makeText(this, "Pomyślnie utworzono profil.", Toast.LENGTH_SHORT).show();
        }catch(Exception ex){
            Toast.makeText(this, "Wystąpił błąd przy tworzeniu profilu.", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}