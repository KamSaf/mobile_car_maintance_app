package com.example.mobile_car_maintance_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WorkshopDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_details);

        Intent intent = getIntent();
        String workshopName = intent.getStringExtra("workshop_name");
        Bundle b = getIntent().getExtras();
        int givenId = b.getInt("workshop_id");
        TextView title = findViewById(R.id.workshopDetailsTitle);
        title.setText(workshopName);

        String[] columns = {"address", "city", "sector", "phoneNumber"};
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(givenId)};

        SQLiteDatabase myDB = openOrCreateDatabase("database.db", MODE_PRIVATE, null);
        Cursor myCursor = myDB.query("workshopsDatabase", columns, selection, selectionArgs, null, null, null);

        TextView sector = findViewById(R.id.sector);
        TextView address = findViewById(R.id.address);
        TextView city = findViewById(R.id.city);
        TextView phoneNumber = findViewById(R.id.phoneNumber);

        if (myCursor.moveToFirst()) {
            sector.setText("Specjalizacja:  "+myCursor.getString(0));
            address.setText("Adres:  "+myCursor.getString(1));
            city.setText("Miejscowość:  "+myCursor.getString(2));
            phoneNumber.setText("Numer tel.:  "+myCursor.getString(3));
        }
        myCursor.close();
        myDB.close();
    }

    public void deleteWorkshop(View v){
        SQLiteDatabase myDB = openOrCreateDatabase("database.db", MODE_PRIVATE, null);
        Bundle b = getIntent().getExtras();
        int givenId = b.getInt("workshop_id");
        myDB.delete("workshopsDatabase", "id=?", new String[]{String.valueOf(givenId)});
        myDB.close();
        Intent intent = new Intent(this, WorkshopsListActivity.class);
        startActivity(intent);
    }
}