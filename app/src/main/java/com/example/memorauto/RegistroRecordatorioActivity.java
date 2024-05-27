package com.example.memorauto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.memorauto.db.database.AppDatabase;
import com.example.memorauto.db.entity.Mantenimiento;
import com.example.memorauto.db.entity.Recordatorio;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class RegistroRecordatorioActivity extends AppCompatActivity {

    private EditText etFAviso;
    private GregorianCalendar gcFechaAviso;
    public int idMantenimiento;
    public int idVehiculo;
    public Mantenimiento mantenimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_recordatorio);

        idMantenimiento = getIntent().getIntExtra("selectedMantenimiento", 0);
        idVehiculo = getIntent().getIntExtra("selectedVehicle", 0);
        mantenimiento = (Mantenimiento) getIntent().getSerializableExtra("selectedObject");
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
                intent.putExtra("selectedVehicle", idVehiculo);
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

    private class EjecutarRegistro extends AsyncTask<Mantenimiento, Void, Void> {

        @Override
        protected Void doInBackground(Mantenimiento... mantenimientos) {
            if (mantenimiento != null) {
                AppDatabase.getAppDb(getApplicationContext()).mantenimientoRepository().insert(mantenimientos[0]);
                mantenimiento = AppDatabase.getAppDb(getApplicationContext()).mantenimientoRepository().findLastMantenimiento();

                Recordatorio recordatorio = new Recordatorio(gcFechaAviso, mantenimiento.getId());
                AppDatabase.getAppDb(getApplicationContext()).recordatorioRepository().insert(recordatorio);
            } else {
                Recordatorio recordatorio = new Recordatorio(gcFechaAviso, idMantenimiento);
                AppDatabase.getAppDb(getApplicationContext()).recordatorioRepository().insert(recordatorio);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {

            Intent intent = new Intent(RegistroRecordatorioActivity.this, MantenimientosActivity.class);
            intent.putExtra("selectedVehicle", idVehiculo);
            startActivity(intent);
            finish();
        }
    }

    public void registrarRecordatorio(View view) {
        if (etFAviso.getText().toString().equals("")) {
            Toast.makeText(this, "FECHA es obligatoria para registrar un recordatorio", Toast.LENGTH_LONG).show();
        } else {
            EjecutarRegistro ejecutarRegistro = new EjecutarRegistro();
            ejecutarRegistro.execute(mantenimiento);

        }
    }
}