package com.example.memorauto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.memorauto.db.database.AppDatabase;
import com.example.memorauto.db.entity.Mantenimiento;
import com.example.memorauto.db.entity.Vehiculo;
import com.example.memorauto.recyclerviews.mainactivity.RecyclerViewAdapter;
import com.example.memorauto.recyclerviews.mainactivity.RecyclerViewInterface;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<Vehiculo> vehiculos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configToolbar();
        LeerVehiculos leerVehiculos = new LeerVehiculos();
        leerVehiculos.execute();
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
            Intent intent = new Intent(this, RegistroVehiculoActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private class LeerVehiculos extends AsyncTask<Void, Void, List<Vehiculo>> implements RecyclerViewInterface {
        @Override
        protected List<Vehiculo> doInBackground(Void... voids) {
            vehiculos = AppDatabase.getAppDb(getApplicationContext()).vehiculoRepository().findAll();
            return vehiculos;
        }

        @Override
        protected void onPostExecute(List<Vehiculo> vehiculos) {
            RecyclerView recyclerView = findViewById(R.id.am_recyclerview);
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(getApplicationContext(), vehiculos, this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        }

        @Override
        public void onItemClick(int position) {
            Intent intent = new Intent(MainActivity.this, VehiculoActivity.class);
            intent.putExtra("selectedVehicle", vehiculos.get(position).getId()); //REVISTAR ESTO
            startActivity(intent);
        }
    }
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

        public void mostrarTodos(View view) {
            HiloSecundario hiloSecundario = new HiloSecundario(getApplicationContext(), view.getId());
            hiloSecundario.start();
        }
    */

