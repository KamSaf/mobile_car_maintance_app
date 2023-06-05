package com.example.mobile_car_maintance_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddCarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
    }


    public void addCar(View v){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        int loggedUserId = pref.getInt("logged_user_id", 999);

        SQLiteDatabase myDB = openOrCreateDatabase("database.db", MODE_PRIVATE, null);
        myDB.execSQL(
                "CREATE TABLE IF NOT EXISTS vehiclesDatabase (id integer primary key, profileId integer, make VARCHAR(200), model VARCHAR(200), productionYear VARCHAR(200), fuelType VARCHAR(100), displacement integer, power integer, numberPlate VARCHAR(25), vin VARCHAR(50))"
        );
        ContentValues vehicle = new ContentValues();
        EditText inputMake = findViewById(R.id.editTextCarMake);
        EditText inputModel = findViewById(R.id.editTextCarModel);
        EditText inputProductionYear = findViewById(R.id.editTextPrYear);
        EditText inputFuelType = findViewById(R.id.editTextFuel);
        EditText inputDisplacement = findViewById(R.id.editTextDisplacement);
        EditText inputPower = findViewById(R.id.ediTextPower);
        EditText inputNumberPlate = findViewById(R.id.editTextNumberPlate);
        EditText inputVin = findViewById(R.id.editTextVin);
        vehicle.put("make", inputMake.getText().toString());
        vehicle.put("profileId", loggedUserId);
        vehicle.put("model", inputModel.getText().toString());
        vehicle.put("productionYear", inputProductionYear.getText().toString());
        vehicle.put("fuelType", inputFuelType.getText().toString());
        vehicle.put("displacement", inputDisplacement.getText().toString());
        vehicle.put("power", inputPower.getText().toString());
        vehicle.put("numberPlate", inputNumberPlate.getText().toString());
        vehicle.put("vin", inputVin.getText().toString());
        try{
            myDB.insert("vehiclesDatabase", null, vehicle);
            Toast.makeText(this, "Pomyślnie dodano pojazd.", Toast.LENGTH_SHORT).show();
        }catch(Exception ex){
            Toast.makeText(this, "Wystąpił błąd przy dodawaniu pojazdu.", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(this, ChooseCarActivity.class);
        startActivity(intent);
    }
}