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

public class WorkshopsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshops_list);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        int loggedUserId = pref.getInt("logged_user_id", 999);

        SQLiteDatabase myDB = openOrCreateDatabase("database.db", MODE_PRIVATE, null);
        myDB.execSQL(
                "CREATE TABLE IF NOT EXISTS workshopsDatabase (id integer primary key, profileId integer, name VARCHAR(200), address VARCHAR(200), sector VARCHAR(200), city VARCHAR(100), phoneNumber VARCHAR(50))"
        );
        Cursor myCursor = myDB.rawQuery("select id, name from workshopsDatabase where profileId = ?",
                new String[]{String.valueOf(loggedUserId)});

        while (myCursor.moveToNext()) {
            int id = myCursor.getInt(0);
            String name = myCursor.getString(1);
            LinearLayout listLayout = findViewById(R.id.workshopsListLayout);
            Button button = new Button(this);
            button.setText(name);
            listLayout.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToWorkshopDetails(id, name);
                }
            });
        }
        myCursor.close();
        myDB.close();
    }

    public void goToAddWorkshop(View v) {
        Intent intent = new Intent(this, AddWorkshopActivity.class);
        startActivity(intent);
    }

    public void goToWorkshopDetails(int id, String name) {
        Intent intent = new Intent(this, WorkshopDetailsActivity.class);
        intent.putExtra("workshop_name", name);
        intent.putExtra("workshop_id", id);
        startActivity(intent);
    }

}