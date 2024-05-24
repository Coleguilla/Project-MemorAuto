package com.example.memorauto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class VehiculoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo);

        int ey = getIntent().getIntExtra("hola", 0);

        TextView textView = findViewById(R.id.av_textView);
        textView.setText(String.valueOf(ey));

    }
}