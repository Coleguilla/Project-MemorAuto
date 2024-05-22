package com.example.memorauto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configToolbar();
    }

    private void configToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.am_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Mis vehículos");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mp_registrar) {
            Intent i = new Intent(this, RegistroVehiculoActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
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

}
