package com.example.rituapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class cityfinder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cityfinder);

        final EditText editText = findViewById(R.id.searchCity);

        editText.setOnEditorActionListener((v, actionId, event) -> {
            String newCity = editText.getText().toString();
            Intent intent = new Intent(cityfinder.this,MainActivity.class);
            intent.putExtra("City",newCity);
            startActivity(intent);
            return false;
        });
    }
}