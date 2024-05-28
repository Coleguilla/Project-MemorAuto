package com.example.memorauto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.memorauto.db.entity.Mantenimiento;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class RegistroMantenimientoActivity extends AppCompatActivity {

    private EditText etNombre, etFecha, etOdometro;
    private Spinner spTipo;
    private GregorianCalendar gcFecha;
    public int idVehiculo;
    Mantenimiento mantenimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_mantenimiento);

        idVehiculo = getIntent().getIntExtra("SELECTED_VEHICLE", 0);
        configToolbar();
        configView();
    }

    private void configToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.arm_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Registro de mantenimiento");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistroMantenimientoActivity.this, MantenimientosActivity.class);
                intent.putExtra("SELECTED_VEHICLE", idVehiculo);
                startActivity(intent);
            }
        });
    }

    private void configView() {
        etNombre = findViewById(R.id.arm_et_nombre);
        spTipo = findViewById(R.id.arm_sp_tipo);
        etFecha = findViewById(R.id.arm_et_fecha);
        etOdometro = findViewById(R.id.arm_et_odometro);

        String[] opciones = {"ITV", "Cambio de aceite", "Filtro de aire", "Liquido de frenos", "Cambio de bateria", "Otros"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipo.setAdapter(adapter);
    }

    public void selectFecha(View view) {
        gcFecha = new GregorianCalendar();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                String cadenaFecha = day + "/" + (month + 1) + "/" + year;
                etFecha.setText(cadenaFecha);
                gcFecha = new GregorianCalendar(year, month, day);
            }
        }, gcFecha.get(Calendar.YEAR), gcFecha.get(Calendar.MONTH), gcFecha.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    public void registrarMantenimiento(View view) {
        if (etNombre.getText().toString().equals("") | spTipo.getSelectedItem().toString().equals("") | etFecha.getText().toString().equals("")) {
            Toast.makeText(this, "NOMBRE, TIPO y FECHA son obligatorios para registrar un mantenimiento", Toast.LENGTH_LONG).show();
        } else {
            mantenimiento = new Mantenimiento(etNombre.getText().toString(), spTipo.getSelectedItem().toString(), gcFecha, idVehiculo);
            if (!etOdometro.getText().toString().equals(""))
                mantenimiento.setOdometro(Integer.parseInt(etOdometro.getText().toString()));

            Intent intent = new Intent(RegistroMantenimientoActivity.this, RegistroRecordatorioActivity.class);
            intent.putExtra("SELECTED_VEHICLE", idVehiculo);
            intent.putExtra("SELECTED_OBJECT", mantenimiento);
            startActivity(intent);
            finish();
        }
    }

}