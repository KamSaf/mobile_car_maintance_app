package com.example.mobile_car_maintance_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

public class AddEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        SQLiteDatabase myDB = openOrCreateDatabase("database.db", MODE_PRIVATE, null);
        myDB.execSQL(
                "CREATE TABLE IF NOT EXISTS entriesDatabase (id integer primary key, profileId integer, carId integer, category VARCHAR(50),date VARCHAR(50), mileage integer, name VARCHAR(100), place VARCHAR(100), cost integer, items VARCHAR(500))"
        );
        Button button = findViewById(R.id.newItemButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });

    }

    public void addItem(){
        LinearLayout entryItemsListLayout = findViewById(R.id.entryItemsList);
        EditText newItem = new EditText(this);
        newItem.setHint("Pozycja");
        entryItemsListLayout.addView(newItem);
    }

    public void createEntry(View v){
        SQLiteDatabase myDB = openOrCreateDatabase("database.db", MODE_PRIVATE, null);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        int loggedUserId = pref.getInt("logged_user_id", 999);
        int chosenCarId = pref.getInt("chosen_car_id", 999);
        ContentValues entry = new ContentValues();
        RadioButton service = findViewById(R.id.radioButtonService);
        RadioButton expense = findViewById(R.id.radioButtonExpense);
        RadioButton other = findViewById(R.id.radioButtonOther);
        String category = "N/A";
        if (service.isChecked())
            category = service.getText().toString();
        else if (expense.isChecked())
            category = expense.getText().toString();
        else if (other.isChecked())
            category = other.getText().toString();
        EditText date = findViewById(R.id.editTextDate);
        EditText mileage = findViewById(R.id.editTextMileage);
        EditText name = findViewById(R.id.editTextName);
        EditText place = findViewById(R.id.editTextPlace);
        EditText cost = findViewById(R.id.editTextCost);
        LinearLayout itemsList = findViewById(R.id.entryItemsList);
        StringBuilder sB = new StringBuilder();
        for(int i=0; i<itemsList.getChildCount(); i++){
            sB.append(i+1);
            sB.append(". ");
            sB.append(itemsList.getChildAt(i));
            sB.append("\t");
        }
        entry.put("category", category);
        entry.put("date", date.getText().toString());
        entry.put("mileage", mileage.getText().toString());
        entry.put("name", name.getText().toString());
        entry.put("place", place.getText().toString());
        entry.put("cost", cost.getText().toString());
        entry.put("carId", chosenCarId);
        entry.put("profileId", loggedUserId);
        entry.put("items", sB.toString());
        try{
            myDB.insert("entriesDatabase", null, entry);
            Toast.makeText(this, "Pomyślnie dodano wpis.", Toast.LENGTH_SHORT).show();
        }catch(Exception ex){
            Toast.makeText(this, "Wystąpił błąd przy dodawaniu wpisu.", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(this, EntriesListActivity.class);
        startActivity(intent);
    }
}