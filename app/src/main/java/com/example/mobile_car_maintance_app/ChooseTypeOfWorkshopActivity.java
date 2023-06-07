package com.example.mobile_car_maintance_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class ChooseTypeOfWorkshopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_type_of_workshop);
    }

    public void searchForMechanicWorkshop(View v){
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=mechanika");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void searchForBodyWorkshop(View v){
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=blacharstwo");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void searchForPaintWorkshop(View v){
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=lakiernictwo");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void searchForDiagnosticStation(View v){
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=stacja+kontroli+pojazdow");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void searchForPetrolStation(View v){
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=stacja+paliw");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}