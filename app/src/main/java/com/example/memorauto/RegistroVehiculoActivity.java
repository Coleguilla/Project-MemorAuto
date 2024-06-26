package com.example.memorauto;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.memorauto.db.database.AppDatabase;
import com.example.memorauto.db.entity.Vehiculo;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class RegistroVehiculoActivity extends AppCompatActivity {

    private EditText etNombre, etMarca, etModelo, etFechaFabricacion, etFechaCompra;
    private GregorianCalendar gcFFabricacion, gcFCompra;

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

    public void selectFFabricacion(View view) {
        gcFFabricacion = new GregorianCalendar();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                String cadenaFFabricacion = day + "/" + (month + 1) + "/" + year;
                etFechaFabricacion.setText(cadenaFFabricacion);
                gcFFabricacion = new GregorianCalendar(year, month, day);
            }
        }, gcFFabricacion.get(Calendar.YEAR), gcFFabricacion.get(Calendar.MONTH), gcFFabricacion.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    public void selectFCompra(View view) {
        gcFCompra = new GregorianCalendar();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                String cadenaFCompra = day + "/" + (month + 1) + "/" + year;
                etFechaCompra.setText(cadenaFCompra);
                gcFCompra = new GregorianCalendar(year, month, day);
            }
        }, gcFCompra.get(Calendar.YEAR), gcFCompra.get(Calendar.MONTH), gcFCompra.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    public void registrarVehiculo(View view) {
        if (etNombre.getText().toString().equals("") | etMarca.getText().toString().equals("") | etModelo.getText().toString().equals("")) {
            Toast.makeText(this, "NOMBRE, MARCA y MODELO son obligatorios para registrar un vehículo", Toast.LENGTH_LONG).show();
        } else {
            Vehiculo vehiculo = new Vehiculo(etNombre.getText().toString(), etMarca.getText().toString(), etModelo.getText().toString());
            if (gcFFabricacion != null) vehiculo.setFecha_fabricacion(gcFFabricacion);
            if (gcFCompra != null) vehiculo.setFecha_compra(gcFCompra);

            EjecutarRegistro ejecutarRegistro = new EjecutarRegistro();
            ejecutarRegistro.execute(vehiculo);
        }
    }

    private class EjecutarRegistro extends AsyncTask<Vehiculo, Void, Void> {
        @Override
        protected Void doInBackground(Vehiculo... vehiculos) {
            AppDatabase.getAppDb(getApplicationContext()).vehiculoRepository().insert(vehiculos[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            Intent intent = new Intent(RegistroVehiculoActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}