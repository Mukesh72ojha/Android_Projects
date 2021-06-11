package com.example.multiscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    EditText name;
    public static final String EXTRA_NAME = "com.example.multiscreen.EXTRA.Name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void activityMove(View v){
        Toast.makeText(this, "Opening second activity", Toast.LENGTH_SHORT).show();
        Intent it = new Intent(this,MainActivity2.class);
        name = findViewById(R.id.Name);
        String str = name.getText().toString();
        it.putExtra(EXTRA_NAME, str);
        startActivity(it);
    }
}