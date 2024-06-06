package com.example.memorauto;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.memorauto.db.database.AppDatabase;
import com.example.memorauto.db.entity.Mantenimiento;
import com.example.memorauto.db.entity.Recordatorio;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class RegistroRecordatorioActivity extends AppCompatActivity {

    private EditText etFAviso;
    private TextView tvConsejo;
    private GregorianCalendar gcFechaAviso;
    public int idMantenimiento;
    public int idVehiculo;
    public Mantenimiento mantenimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_recordatorio);

        idMantenimiento = getIntent().getIntExtra("SELECTED_MAINTENANCE", 0);
        idVehiculo = getIntent().getIntExtra("SELECTED_VEHICLE", 0);
        mantenimiento = (Mantenimiento) getIntent().getSerializableExtra("SELECTED_OBJECT");
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
                intent.putExtra("SELECTED_VEHICLE", idVehiculo);
                startActivity(intent);
            }
        });
    }

    private void configView() {
        etFAviso = findViewById(R.id.arr_et_fecha);
        tvConsejo = findViewById(R.id.arr_tv_consejo);

        if (mantenimiento != null && mantenimiento.getTipo().equals("ITV")) {
            fechaRecomendada(mantenimiento.getFecha(), 12);
            String cadenaFRecomendada = "Tu mantenimiento es de tipo: \n" + mantenimiento.getTipo().toUpperCase() + "\n\n" + "Recomendamos que el recordatorio sea: \n" + convertirFechaString(gcFechaAviso);
            tvConsejo.setText(cadenaFRecomendada);
        } else if (mantenimiento != null && mantenimiento.getTipo().equals("Cambio de aceite")) {
            fechaRecomendada(mantenimiento.getFecha(), 18);
            String cadenaFRecomendada = "Tu mantenimiento es de tipo: \n" + mantenimiento.getTipo().toUpperCase() + "\n\n" + "Recomendamos que el recordatorio sea: \n" + convertirFechaString(gcFechaAviso);
            tvConsejo.setText(cadenaFRecomendada);
        } else if (mantenimiento != null && mantenimiento.getTipo().equals("Filtro de aire")) {
            fechaRecomendada(mantenimiento.getFecha(), 12);
            String cadenaFRecomendada = "Tu mantenimiento es de tipo: \n" + mantenimiento.getTipo().toUpperCase() + "\n\n" + "Recomendamos que el recordatorio sea: \n" + convertirFechaString(gcFechaAviso);
            tvConsejo.setText(cadenaFRecomendada);
        } else if (mantenimiento != null && mantenimiento.getTipo().equals("Liquido de frenos")) {
            fechaRecomendada(mantenimiento.getFecha(), 24);
            String cadenaFRecomendada = "Tu mantenimiento es de tipo: \n" + mantenimiento.getTipo().toUpperCase() + "\n\n" + "Recomendamos que el recordatorio sea: \n" + convertirFechaString(gcFechaAviso);
            tvConsejo.setText(cadenaFRecomendada);
        } else if (mantenimiento != null && mantenimiento.getTipo().equals("Cambio de bateria")) {
            fechaRecomendada(mantenimiento.getFecha(), 48);
            String cadenaFRecomendada = "Tu mantenimiento es de tipo: \n" + mantenimiento.getTipo().toUpperCase() + "\n\n" + "Recomendamos que el recordatorio sea: \n" + convertirFechaString(gcFechaAviso);
            tvConsejo.setText(cadenaFRecomendada);
        } else {
            gcFechaAviso = new GregorianCalendar();
            tvConsejo.setText("");
        }
    }

    private void fechaRecomendada(GregorianCalendar gc, int cantidad) {
        gcFechaAviso = new GregorianCalendar(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DAY_OF_MONTH));
        gcFechaAviso.add(Calendar.MONTH, cantidad);
    }

    private String convertirFechaString(GregorianCalendar gc) {
        return gc.get(Calendar.DAY_OF_MONTH) + "/" + (gc.get(Calendar.MONTH) + 1) + "/" + gc.get(Calendar.YEAR);
    }

    public void selectFecha(View view) {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                String cadenaFAviso = day + "/" + (month + 1) + "/" + year;
                etFAviso.setText(cadenaFAviso);
                gcFechaAviso = new GregorianCalendar(year, month, day);
            }
        }, gcFechaAviso.get(Calendar.YEAR), gcFechaAviso.get(Calendar.MONTH), gcFechaAviso.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    public void registrarRecordatorio(View view) {
        if (etFAviso.getText().toString().equals("")) {
            Toast.makeText(this, "FECHA es obligatoria para registrar un recordatorio", Toast.LENGTH_LONG).show();
        } else {
            EjecutarRegistro ejecutarRegistro = new EjecutarRegistro();
            ejecutarRegistro.execute(mantenimiento);
        }
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
            intent.putExtra("SELECTED_VEHICLE", idVehiculo);
            startActivity(intent);
            finish();
        }
    }

}