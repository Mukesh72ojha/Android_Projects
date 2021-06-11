package com.example.unitconverter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private TextView result;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button convert = findViewById(R.id.Convert);
        editText = findViewById(R.id.editText);
        result = findViewById(R.id.result);
        convert.setOnClickListener(v -> {
            //Toast.makeText(MainActivity.this, "Result shown", Toast.LENGTH_SHORT).show();
            String str = editText.getText().toString();
            int num = Integer.parseInt(str);
            double pound = num * 2.205;
            result.setText(""+ pound);
        });
    }
}