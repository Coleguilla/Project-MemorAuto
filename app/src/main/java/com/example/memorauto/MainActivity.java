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

import com.example.memorauto.db.CargadorVehiculos;
import com.example.memorauto.db.entity.Vehiculo;
import com.example.memorauto.recyclerviews.mainactivity.RecyclerViewAdapter;
import com.example.memorauto.recyclerviews.mainactivity.RecyclerViewInterface;

import java.util.Calendar;
import java.util.GregorianCalendar;
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

    private boolean compararFechas(GregorianCalendar fechaRecordatorio) {
        GregorianCalendar fechaActual = new GregorianCalendar();

        fechaActual.set(Calendar.HOUR_OF_DAY, 0);
        fechaActual.set(Calendar.MINUTE, 0);
        fechaActual.set(Calendar.SECOND, 0);
        fechaActual.set(Calendar.MILLISECOND, 0);

        fechaRecordatorio.set(Calendar.HOUR_OF_DAY, 0);
        fechaRecordatorio.set(Calendar.MINUTE, 0);
        fechaRecordatorio.set(Calendar.SECOND, 0);
        fechaRecordatorio.set(Calendar.MILLISECOND, 0);

        if (fechaActual.equals(fechaRecordatorio)) {
            Log.d("ALARMA", "La notificacion es real");
            return true;
        } else {
            Log.d("OFF", "No pasa nada");
            return false;
        }
    }

    private void comprobarRecordatorios() {
        for (int i=0; i<vehiculos.size(); i++){
            for (int e=0; e<vehiculos.get(i).getMantenimientos().size(); e++){
                for (int d=0; d<vehiculos.get(i).getMantenimientos().get(e).getRecordatorios().size(); d++){
                    if (compararFechas(vehiculos.get(i).getMantenimientos().get(e).getRecordatorios().get(d).getFechaAviso())) {
                        Log.d("QUIEN", "El vehiculo: " + vehiculos.get(i).getNombre()+ ", el mantenimiento: "+vehiculos.get(i).getMantenimientos().get(e).getNombre()+", de tipo: "+vehiculos.get(i).getMantenimientos().get(e).getTipo());
                    }
                }
            }
        }
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
            comprobarRecordatorios();
        }

        @Override
        public void onItemClick(int position) {
            Intent intent = new Intent(MainActivity.this, VehiculoActivity.class);
            intent.putExtra("SELECTED_VEHICLE", vehiculos.get(position).getId());
            startActivity(intent);
        }
    }

}

