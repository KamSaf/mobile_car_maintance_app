package com.example.mobile_car_maintance_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CarInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info);

        TextView title = findViewById(R.id.carDetailsTitle);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        String givenName = pref.getString("chosen_car", "N/A");
        title.setText(givenName);

        int givenCarId = pref.getInt("chosen_car_id", 999);

        String[] columns = {"make", "model", "productionYear", "fuelType", "displacement", "power", "numberPlate", "vin"};
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(givenCarId)};

        SQLiteDatabase myDB = openOrCreateDatabase("database.db", MODE_PRIVATE, null);
        Cursor myCursor = myDB.query("vehiclesDatabase", columns, selection, selectionArgs, null, null, null);

        TextView carMake = findViewById(R.id.textViewMake);
        TextView carModel = findViewById(R.id.textViewModel);
        TextView carProductionYear = findViewById(R.id.textViewProductionYear);
        TextView carFuelType = findViewById(R.id.textViewFuelType);
        TextView carDisplacement = findViewById(R.id.textViewDisplacement);
        TextView carPower = findViewById(R.id.textViewPower);
        TextView carNumberPlate = findViewById(R.id.textViewNumberPlate);
        TextView carVin = findViewById(R.id.textViewVin);

        if (myCursor.moveToFirst()) {
            carMake.setText("Marka:  "+myCursor.getString(0));
            carModel.setText("Model:  "+myCursor.getString(1));
            carProductionYear.setText("Rok produkcji:  "+myCursor.getString(2));
            carFuelType.setText("Paliwo:  "+myCursor.getString(3));
            carDisplacement.setText("Pojemność:  "+myCursor.getString(4)+" cm3");
            carPower.setText("Moc:  "+myCursor.getString(5)+" KM");
            carNumberPlate.setText("Nr rejestracyjny:  "+myCursor.getString(6));
            carVin.setText("Nr VIN:  "+myCursor.getString(7));
        }
        myDB.close();
    }

    public void deleteCar(View v) {
        SQLiteDatabase myDB = openOrCreateDatabase("database.db", MODE_PRIVATE, null);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        int loggedUserId = pref.getInt("logged_user_id", 999);
        int chosenCarId = pref.getInt("chosen_car_id", 999);

        myDB.delete("vehiclesDatabase", "id=?", new String[]{String.valueOf(chosenCarId)});
        myDB.delete("entriesDatabase", "carId=?", new String[]{String.valueOf(chosenCarId)});
        myDB.close();
        Intent intent = new Intent(this, ChooseProfileActivity.class);
        startActivity(intent);
    }
}