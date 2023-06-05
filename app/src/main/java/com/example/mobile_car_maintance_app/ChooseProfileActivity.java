package com.example.mobile_car_maintance_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChooseProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_profile);

        SQLiteDatabase myDB = openOrCreateDatabase("database.db", MODE_PRIVATE, null);
        myDB.execSQL(
                "CREATE TABLE IF NOT EXISTS profilesDatabase (id integer primary key, name VARCHAR(100))"
        );
        Cursor myCursor = myDB.rawQuery("select id, name from profilesDatabase",
                null);

        while (myCursor.moveToNext()) {
            int id = myCursor.getInt(0);
            String name = myCursor.getString(1);
            LinearLayout listLayout = findViewById(R.id.profilesListLayout);
            Button button = new Button(this);
            button.setText(name);
            listLayout.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToProfile(id, name);
                }
            });
        }
        myCursor.close();
        myDB.close();
    }


    public void goToProfile(int id, String name) {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("logged_user_id", id);
        editor.putString("logged_user_name", name);
        editor.apply();
        Intent intent = new Intent(this, ProfileContentActivity.class);
        startActivity(intent);
    }
}