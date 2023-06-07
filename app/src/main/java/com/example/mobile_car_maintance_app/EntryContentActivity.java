package com.example.mobile_car_maintance_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class EntryContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_content);

        Intent intent = getIntent();
        String entryName = intent.getStringExtra("entry_name");
        Bundle b = getIntent().getExtras();
        int entryId = b.getInt("entry_id");
        TextView title = findViewById(R.id.entryContentTitle);
        title.setText(entryName);

        String[] columns = {"category", "date", "mileage", "cost", "place", "items"};
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(entryId)};

        SQLiteDatabase myDB = openOrCreateDatabase("database.db", MODE_PRIVATE, null);
        Cursor myCursor = myDB.query("entriesDatabase", columns, selection, selectionArgs, null, null, null);

        TextView category = findViewById(R.id.category);
        TextView date = findViewById(R.id.date);
        TextView mileage = findViewById(R.id.mileage);
        TextView cost = findViewById(R.id.cost);
        TextView place = findViewById(R.id.place);
        TextView items = findViewById(R.id.items);

        if (myCursor.moveToFirst()) {
            category.setText("Kategoria:  "+myCursor.getString(0));
            date.setText("Data:  "+myCursor.getString(1));
            mileage.setText("Przebieg:  "+myCursor.getString(2)+" km");
            cost.setText("Koszt:  "+myCursor.getString(3)+" zł");
            place.setText("Miejsce:  "+myCursor.getString(4));
            items.setText("Pozycje:  "+myCursor.getString(5));
        }
        myCursor.close();
        myDB.close();
    }
}