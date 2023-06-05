package com.example.mobile_car_maintance_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ChooseCarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_car);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        int loggedUserId = pref.getInt("logged_user_id", 999);

        SQLiteDatabase myDB = openOrCreateDatabase("database.db", MODE_PRIVATE, null);
        myDB.execSQL(
                "CREATE TABLE IF NOT EXISTS vehiclesDatabase (id integer primary key, profileId integer, make VARCHAR(200), model VARCHAR(200), productionYear VARCHAR(200), fuelType VARCHAR(100), displacement integer, power integer, numberPlate VARCHAR(25), vin VARCHAR(50))"
        );
        Cursor myCursor = myDB.rawQuery("select id, make, model, productionYear, fuelType, displacement, power, numberPlate, vin from vehiclesDatabase where profileId = ?",
                new String[]{String.valueOf(loggedUserId)});

        while (myCursor.moveToNext()) {
            int id = myCursor.getInt(0);
            String carMake = myCursor.getString(1);
            String carModel = myCursor.getString(2);
            String productionYear = myCursor.getString(3);
            String fuelType = myCursor.getString(4);
            int displacement = myCursor.getInt(5);
            int power = myCursor.getInt(6);
            String numberPlate = myCursor.getString(7);
            String vin = myCursor.getString(8);
            LinearLayout listLayout = findViewById(R.id.carListLayout);
            Button button = new Button(this);
            button.setText(carMake +" "+ carModel);
            listLayout.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToCarEntries(id, (carMake +" "+ carModel));
                }
            });
        }
        myCursor.close();
        myDB.close();
    }

    public void goToCarEntries(int id, String name) {
        Intent intent = new Intent(this, EntriesListActivity.class);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("chosen_car", name);
        editor.putInt("chosen_car_id", id);
        editor.apply();
        startActivity(intent);
    }
}