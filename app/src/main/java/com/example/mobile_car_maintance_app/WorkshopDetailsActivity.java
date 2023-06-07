package com.example.mobile_car_maintance_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WorkshopDetailsActivity extends AppCompatActivity {

    static private String phoneNumberString = "N/A";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_details);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        String workshopName = pref.getString("chosen_workshop", "N/A");

        int givenId = pref.getInt("chosen_workshop_id", 999);

        TextView title = findViewById(R.id.workshopDetailsTitle);
        title.setText(workshopName);

        String[] columns = {"sector", "address", "city", "phoneNumber"};
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
            phoneNumberString = myCursor.getString(3);
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

    public void callWorkshop(View v){

    }

    public void sendMessageToWorkshop(View v){
        Intent intent = new Intent(this, SendMessageActivity.class);
        intent.putExtra("phone_number", phoneNumberString);
        startActivity(intent);
    }

    public void showOnMap(View v){

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        int givenId = pref.getInt("chosen_workshop_id", 999);
        String[] columns = {"address", "city"};
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(givenId)};
        SQLiteDatabase myDB = openOrCreateDatabase("database.db", MODE_PRIVATE, null);
        Cursor myCursor = myDB.query("workshopsDatabase", columns, selection, selectionArgs, null, null, null);
        String address = "";
        String city = "";
        if (myCursor.moveToFirst()) {
            address = myCursor.getString(0);
            city = myCursor.getString(1);
        }
        myCursor.close();
        myDB.close();
        String uri = "geo:0,0?q=" +
                address +
                ", " +
                city;
        Uri gmmIntentUri = Uri.parse(uri);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}