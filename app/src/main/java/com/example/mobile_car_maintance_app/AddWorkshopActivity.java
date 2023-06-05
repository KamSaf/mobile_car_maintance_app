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

public class AddWorkshopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workshop);
    }

    public void createWorkshop(View v){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        int loggedUserId = pref.getInt("logged_user_id", 999);

        SQLiteDatabase myDB = openOrCreateDatabase("database.db", MODE_PRIVATE, null);
        myDB.execSQL(
                "CREATE TABLE IF NOT EXISTS workshopsDatabase (id integer primary key, profileId integer, name VARCHAR(200), address VARCHAR(200), sector VARCHAR(200), city VARCHAR(100), phoneNumber VARCHAR(50))"
        );
        ContentValues workshop = new ContentValues();
        EditText inputName = findViewById(R.id.workshopName);
        EditText inputAddress = findViewById(R.id.workshopAddress);
        EditText inputCity = findViewById(R.id.workshopCity);
        EditText inputSector = findViewById(R.id.workshopSector);
        EditText inputPhoneNumber = findViewById(R.id.workshopPhoneNumber);
        workshop.put("name", inputName.getText().toString());
        workshop.put("profileId", loggedUserId);
        workshop.put("address", inputAddress.getText().toString());
        workshop.put("city", inputCity.getText().toString());
        workshop.put("sector", inputSector.getText().toString());
        workshop.put("phoneNumber", inputPhoneNumber.getText().toString());
        try{
            myDB.insert("workshopsDatabase", null, workshop);
            Toast.makeText(this, "Pomyślnie dodano warsztat.", Toast.LENGTH_SHORT).show();
        }catch(Exception ex){
            Toast.makeText(this, "Wystąpił błąd przy dodawaniu warsztatu.", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(this, WorkshopsListActivity.class);
        startActivity(intent);
    }
}