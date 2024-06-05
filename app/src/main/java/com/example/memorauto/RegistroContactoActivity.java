package com.example.memorauto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.memorauto.db.database.AppDatabase;
import com.example.memorauto.db.entity.Contacto;
import com.example.memorauto.db.entity.Vehiculo;
import com.example.memorauto.db.entity.VehiculoContacto;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class RegistroContactoActivity extends AppCompatActivity {

    private EditText etNombre, etTelefono, etDireccion;
    private Spinner spTipo;
    public int idVehiculo;
    public VehiculoContacto vehiculoContacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_contacto);

        idVehiculo = getIntent().getIntExtra("SELECTED_VEHICLE", 0);
        configToolbar();
        configView();
    }

    private void configToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.arc_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Registro de contacto");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void configView() {
        etNombre = findViewById(R.id.arc_et_nombre);
        spTipo = findViewById(R.id.arc_sp_tipo);
        etTelefono = findViewById(R.id.arc_et_telefono);
        etDireccion = findViewById(R.id.arc_et_direccion);

        String[] opciones = {"ITV", "Cambio de aceite", "Filtro de aire", "Liquido de frenos", "Cambio de bateria", "Otros"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipo.setAdapter(adapter);

    }



    public void registrarContacto(View view) {
        if (etNombre.getText().toString().equals("") | spTipo.getSelectedItem().toString().equals("") | etTelefono.getText().toString().equals("")) {
            Toast.makeText(this, "NOMBRE, TIPO y TELEFONO son obligatorios para registrar un contacto", Toast.LENGTH_LONG).show();
        } else {
            Contacto contacto = new Contacto(etNombre.getText().toString(), spTipo.getSelectedItem().toString());
            if (!etTelefono.getText().toString().equals(""))
                contacto.setTelefono(Integer.parseInt(etTelefono.getText().toString()));
            if (!etDireccion.getText().toString().equals(""))
                contacto.setDireccion(etDireccion.getText().toString());

            EjecutarRegistro ejecutarRegistro = new EjecutarRegistro();
            ejecutarRegistro.execute(contacto);
        }
    }

    private class EjecutarRegistro extends AsyncTask<Contacto, Void, Void> {

        @Override
        protected Void doInBackground(Contacto... contactos) {
            AppDatabase.getAppDb(getApplicationContext()).contactoRepository().insert(contactos[0]);
            Contacto lastContacto = AppDatabase.getAppDb(getApplicationContext()).contactoRepository().findLastContacto();

            vehiculoContacto = new VehiculoContacto(idVehiculo, lastContacto.getId());
            AppDatabase.getAppDb(getApplicationContext()).vehiculoContactoRepository().insert(vehiculoContacto);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            Intent intent = new Intent(RegistroContactoActivity.this, ContactosActivity.class);
            intent.putExtra("SELECTED_VEHICLE", idVehiculo);
            startActivity(intent);
            finish();
        }
    }
}