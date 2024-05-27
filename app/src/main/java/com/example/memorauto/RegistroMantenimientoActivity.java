package com.example.memorauto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.memorauto.db.entity.Mantenimiento;
import com.example.memorauto.db.entity.Vehiculo;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class RegistroMantenimientoActivity extends AppCompatActivity {

    private EditText etNombre, etTipo, etFecha, etOdometro;
    private GregorianCalendar gcFecha;
    public int idVehiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_mantenimiento);

        idVehiculo = getIntent().getIntExtra("selectedVehicle", 0);
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
                intent.putExtra("selectedVehicle", idVehiculo);
                startActivity(intent);
            }
        });
    }

    private void configView() {
        etNombre = findViewById(R.id.arm_et_nombre);
        etTipo = findViewById(R.id.arm_et_tipo);
        etFecha = findViewById(R.id.arm_et_fecha);
        etOdometro = findViewById(R.id.arm_et_odometro);
    }

    public void selectFecha(View view) {
        gcFecha = new GregorianCalendar();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                etFecha.setText(String.valueOf(day) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(year));
                gcFecha = new GregorianCalendar(year, month, day);
            }
        }, gcFecha.get(Calendar.YEAR), gcFecha.get(Calendar.MONTH), gcFecha.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    public void registrarMantenimiento(View view) {
        if (etNombre.getText().toString().equals("") | etTipo.getText().toString().equals("") | etFecha.getText().toString().equals("")) {
            Toast.makeText(this, "NOMBRE, TIPO y FECHA son obligatorios para registrar un mantenimiento", Toast.LENGTH_LONG).show();
        } else {
            Mantenimiento mantenimiento = new Mantenimiento(etNombre.getText().toString(), etTipo.getText().toString(), gcFecha, idVehiculo);
            if (!etOdometro.getText().toString().equals("")) mantenimiento.setOdometro(Integer.parseInt(etOdometro.getText().toString()));
            HiloSecundario hiloSecundario = new HiloSecundario(getApplicationContext(), view.getId(), mantenimiento);
            hiloSecundario.start();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        }
    }
}