package com.example.memorauto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.memorauto.db.CargadorVehiculos;
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

    private void configRecyclerView(RecyclerViewInterface rvi) {
        RecyclerView recyclerView = findViewById(R.id.am_recyclerview);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getApplicationContext(), vehiculos, rvi);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private class LeerVehiculos extends AsyncTask<Void, Void, List<Vehiculo>> implements RecyclerViewInterface {
        @Override
        protected List<Vehiculo> doInBackground(Void... voids) {
            vehiculos = CargadorVehiculos.cargarTodosVehiculos(getApplicationContext());
            return vehiculos;
        }

        @Override
        protected void onPostExecute(List<Vehiculo> vehiculos) {
            configToolbar();
            configRecyclerView(this);
        }

        @Override
        public void onItemClick(int position) {
            Intent intent = new Intent(MainActivity.this, VehiculoActivity.class);
            intent.putExtra("SELECTED_VEHICLE", vehiculos.get(position).getId());
            startActivity(intent);
        }
    }

}

