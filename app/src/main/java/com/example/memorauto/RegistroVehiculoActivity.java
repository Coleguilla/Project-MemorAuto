package com.example.memorauto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.GregorianCalendar;

public class RegistroVehiculoActivity extends AppCompatActivity {

    private EditText etNombre, etMarca, etModelo, etFechaFabricacion, etFechaCompra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_vehiculo);
        configToolbar();
        configView();
    }
    
    private void configToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.arv_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Registro de vehículo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void configView() {
        etNombre = findViewById(R.id.arv_et_nombre);
        etMarca = findViewById(R.id.arv_et_marca);
        etModelo = findViewById(R.id.arv_et_modelo);
        etFechaFabricacion = findViewById(R.id.arv_et_fechafabricacion);
        etFechaCompra = findViewById(R.id.arv_et_fechacompra);
    }
    public void fecha(View view) {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                etFechaFabricacion.setText(String.valueOf(day) + "/" + String.valueOf(month+1) + "/" + String.valueOf(year));
                //gcFFabricacion = new GregorianCalendar(year, month, day);
            }
        }, 2024, 4, 20);
        dialog.show();
    }
}