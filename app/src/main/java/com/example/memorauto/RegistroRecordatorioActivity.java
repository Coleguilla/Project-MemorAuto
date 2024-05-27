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
import com.example.memorauto.db.entity.Recordatorio;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class RegistroRecordatorioActivity extends AppCompatActivity {

    private EditText etFAviso;
    private GregorianCalendar gcFechaAviso;
    public int idMantenimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_recordatorio);

        idMantenimiento = getIntent().getIntExtra("selectedVehicle", 0);
        configToolbar();
        configView();
    }

    private void configToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.arr_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Registro de recordatorio");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistroRecordatorioActivity.this, MantenimientosActivity.class);
                intent.putExtra("selectedVehicle", idMantenimiento);
                startActivity(intent);
            }
        });
    }

    private void configView() {
        etFAviso = findViewById(R.id.arr_et_fecha);
    }

    public void selectFecha(View view) {
        gcFechaAviso = new GregorianCalendar();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                etFAviso.setText(String.valueOf(day) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(year));
                gcFechaAviso = new GregorianCalendar(year, month, day);
            }
        }, gcFechaAviso.get(Calendar.YEAR), gcFechaAviso.get(Calendar.MONTH), gcFechaAviso.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    public void registrarRecordatorio(View view) {
        if (etFAviso.getText().toString().equals("")) {
            Toast.makeText(this, "FECHA es obligatoria para registrar un recordatorio", Toast.LENGTH_LONG).show();
        } else {
            Recordatorio recordatorio = new Recordatorio(gcFechaAviso, idMantenimiento);
            HiloSecundario hiloSecundario = new HiloSecundario(getApplicationContext(), view.getId(), recordatorio);
            hiloSecundario.start();

            Intent intent = new Intent(this, MantenimientosActivity.class);
            startActivity(intent);
            finish();

        }
    }
}