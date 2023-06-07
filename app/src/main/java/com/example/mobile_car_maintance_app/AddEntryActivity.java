package com.example.mobile_car_maintance_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.IOException;

public class AddEntryActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 3;
    private static final int CAPTURE_CODE = 4;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        SQLiteDatabase myDB = openOrCreateDatabase("database.db", MODE_PRIVATE, null);
        myDB.execSQL(
                "CREATE TABLE IF NOT EXISTS entriesDatabase (id integer primary key, profileId integer, carId integer, category VARCHAR(50),date VARCHAR(50), mileage integer, name VARCHAR(100), place VARCHAR(100), cost integer, items VARCHAR(500), imageUri VARCHAR(150))"
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
        newItem.setId(View.generateViewId());
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
        sB.append("\n");
        for(int i=0; i<itemsList.getChildCount(); i++){
            EditText item = findViewById(itemsList.getChildAt(i).getId());
            sB.append("\n");
            sB.append(i+1);
            sB.append(". ");
            sB.append(item.getText().toString());
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
        entry.put("imageUri", String.valueOf(imageUri));
        try{
            myDB.insert("entriesDatabase", null, entry);
            Toast.makeText(this, "Pomyślnie dodano wpis.", Toast.LENGTH_SHORT).show();
        }catch(Exception ex){
            Toast.makeText(this, "Wystąpił błąd przy dodawaniu wpisu.", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(this, EntriesListActivity.class);
        startActivity(intent);
    }

    public void takePicture(View v){
        if(checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            openCamera();
        } else{
            String[] permission = {android.Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(permission, PERMISSION_CODE);
        }
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "new image");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(camIntent, CAPTURE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if( requestCode == PERMISSION_CODE && grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            openCamera();
        }
        else{
            Toast.makeText(this, "Brak uprawnień.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap;
        if (resultCode == RESULT_OK) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ImageView imageView = findViewById(R.id.imageInput);
            imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 960, 960, false));
        }
    }




}