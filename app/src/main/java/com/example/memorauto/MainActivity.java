package com.example.memorauto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.memorauto.db.entity.Vehiculo;

import java.util.GregorianCalendar;


public class MainActivity extends AppCompatActivity {

    private EditText etNombre, etMarca, etModelo;
    private TextView tvFecha;
    GregorianCalendar gcFFabricacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        configView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void configView() {
        etNombre = findViewById(R.id.etNombre);
        etMarca = findViewById(R.id.etMarca);
        etModelo = findViewById(R.id.etModelo);
        tvFecha = findViewById(R.id.tvFecha);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_favorite) {
            Intent i = new Intent(this, NuevoVehiculoActivity.class );
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void guardar(View view) {
        Vehiculo vehiculo = new Vehiculo(etNombre.getText().toString(), etMarca.getText().toString(), etModelo.getText().toString());
        if (gcFFabricacion != null) vehiculo.setFecha_fabricacion(gcFFabricacion);
        HiloSecundario hiloSecundario = new HiloSecundario(getApplicationContext(), view.getId(), vehiculo);
        hiloSecundario.start();
    }

    /*
        public void modificar(View view) {
            Vehiculo cliente = new Vehiculo(etNombre.getText().toString(), etMarca.getText().toString());
            cliente.setId(Integer.parseInt(etModelo.getText().toString()));
            HiloSecundario hiloSecundario = new HiloSecundario(getApplicationContext(), view.getId(), cliente);
            hiloSecundario.start();
        }

        public void borrar(View view) {
            Vehiculo cliente = new Vehiculo(etNombre.getText().toString(), etMarca.getText().toString());
            HiloSecundario hiloSecundario = new HiloSecundario(getApplicationContext(), view.getId(), cliente);
            hiloSecundario.start();
        }

        public void buscarID(View view) {
            Vehiculo cliente = new Vehiculo();
            cliente.setId(Integer.parseInt(etBuscarId.getText().toString()));
            HiloSecundario hiloSecundario = new HiloSecundario(getApplicationContext(), view.getId(), cliente);
            hiloSecundario.start();
        }

        public void buscarNombre(View view) {
            Vehiculo cliente = new Vehiculo();
            cliente.setNombre(etBuscarNombre.getText().toString());
            HiloSecundario hiloSecundario = new HiloSecundario(getApplicationContext(), view.getId(), cliente);
            hiloSecundario.start();
        }
    */
    public void mostrarTodos(View view) {
        HiloSecundario hiloSecundario = new HiloSecundario(getApplicationContext(), view.getId());
        hiloSecundario.start();
    }

    public void fechaClic(View view) {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                tvFecha.setText(String.valueOf(day) + "/" + String.valueOf(month+1) + "/" + String.valueOf(year));
                gcFFabricacion = new GregorianCalendar(year, month, day);
            }
        }, 2024, 4, 20);
        dialog.show();
    }

}
