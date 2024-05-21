package com.example.memorauto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class NuevoVehiculoActivity extends AppCompatActivity {

    private EditText etNombre2, etMarca2, etModelo2;
    private TextView tvFecha2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_vehiculo);
        Toolbar myToolbar2 = (Toolbar) findViewById(R.id.my_toolbar2);
        setSupportActionBar(myToolbar2);
        getSupportActionBar().setTitle("Prueba 2");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        configView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void configView() {
        etNombre2 = findViewById(R.id.etNombre);
        etMarca2 = findViewById(R.id.etMarca);
        etModelo2 = findViewById(R.id.etModelo);
        tvFecha2 = findViewById(R.id.tvFecha);

    }

}