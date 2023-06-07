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
import android.widget.TextView;

public class EntriesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entries_list);
        TextView title = findViewById(R.id.entriesListTitle);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        String givenName = pref.getString("chosen_car", "N/A");
        int loggedUserId = pref.getInt("logged_user_id", 999);
        title.setText(givenName);

        SQLiteDatabase myDB = openOrCreateDatabase("database.db", MODE_PRIVATE, null);
        myDB.execSQL(
                "CREATE TABLE IF NOT EXISTS entriesDatabase (id integer primary key, profileId integer, carId integer, category VARCHAR(50),date VARCHAR(50), mileage integer, name VARCHAR(100), place VARCHAR(100), cost integer, items VARCHAR(500))"
        );
        Cursor myCursor = myDB.rawQuery("select id, name, date from entriesDatabase where profileId = ?",
                new String[]{String.valueOf(loggedUserId)});
        StringBuilder entryName = new StringBuilder();
        while (myCursor.moveToNext()) {
            int id = myCursor.getInt(0);
            String name = myCursor.getString(1);
            String date = myCursor.getString(2);
            LinearLayout listLayout = findViewById(R.id.entriesListLayout);
            Button button = new Button(this);
            entryName.append(name);
            entryName.append(" ");
            entryName.append(date);
            button.setText(entryName.toString());
            listLayout.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToEntry(id, entryName.toString());
                }
            });
            entryName.setLength(0);
        }
        myCursor.close();
        myDB.close();
    }

    public void goToCarInfo(View v) {
        Intent intent = new Intent(this, CarInfoActivity.class);
        startActivity(intent);
    }

    public void goToEntry(int id, String name) {
        Intent intent = new Intent(this, EntryContentActivity.class);
        intent.putExtra("entry_name", name);
        intent.putExtra("entry_id", id);
        startActivity(intent);
    }

    public void addNewEntry(View v) {
        Intent intent = new Intent(this, AddEntryActivity.class);
        startActivity(intent);
    }

}