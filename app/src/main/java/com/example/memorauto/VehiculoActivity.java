package com.example.memorauto;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memorauto.db.CargadorVehiculos;
import com.example.memorauto.db.entity.Vehiculo;
import com.example.memorauto.recyclerviews.fichamantenimientos.RecyclerViewAdapterFichaMantenimientos;
import com.example.memorauto.recyclerviews.fichamantenimientos.RecyclerViewInterfaceFichaMantenimientos;

import java.util.Calendar;

public class VehiculoActivity extends AppCompatActivity {

    private TextView tvMarca, tvModelo, tvFechaFabricacion, tvFechaCompra;
    private Vehiculo vehiculo;
    public int idVehiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo);

        idVehiculo = getIntent().getIntExtra("SELECTED_VEHICLE", 0);
        LeerVehiculo leerVehiculo = new LeerVehiculo();
        leerVehiculo.execute();
    }

    private void configToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.av_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(vehiculo.getNombre());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void configView() {
        tvMarca = findViewById(R.id.av_tv_marca);
        tvModelo = findViewById(R.id.av_tv_modelo);
        tvFechaFabricacion = findViewById(R.id.av_tv_fechafabricacion);
        tvFechaCompra = findViewById(R.id.av_tv_fechacompra);
    }

    private void rellenarFicha() {
        String cadenaFFabricacion, cadenaFCompra;
        if (vehiculo.getFecha_fabricacion() != null) {
            cadenaFFabricacion = vehiculo.getFecha_fabricacion().get(Calendar.DAY_OF_MONTH) + "/" + (vehiculo.getFecha_fabricacion().get(Calendar.MONTH) + 1) + "/" + vehiculo.getFecha_fabricacion().get(Calendar.YEAR);
        } else {
            cadenaFFabricacion = "Sin fecha";
        }
        if (vehiculo.getFecha_compra() != null) {
            cadenaFCompra = vehiculo.getFecha_compra().get(Calendar.DAY_OF_MONTH) + "/" + (vehiculo.getFecha_compra().get(Calendar.MONTH) + 1) + "/" + vehiculo.getFecha_compra().get(Calendar.YEAR);
        } else {
            cadenaFCompra = "Sin fecha";
        }

        tvMarca.setText(vehiculo.getMarca());
        tvModelo.setText(vehiculo.getModelo());
        tvFechaFabricacion.setText(cadenaFFabricacion);
        tvFechaCompra.setText(cadenaFCompra);
    }

    private void configRecyclerView(RecyclerViewInterfaceFichaMantenimientos rvifm) {
        RecyclerView recyclerView = findViewById(R.id.av_recyclerview);
        RecyclerViewAdapterFichaMantenimientos adapter = new RecyclerViewAdapterFichaMantenimientos(getApplicationContext(), vehiculo.getMantenimientos(), rvifm);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    public void lanzarMantenimientos(View view) {
        Intent intent = new Intent(this, MantenimientosActivity.class);
        intent.putExtra("SELECTED_VEHICLE", vehiculo.getId());
        startActivity(intent);
    }

    public void lanzarContactos(View view) {
        Intent intent = new Intent(this, ContactosActivity.class);
        intent.putExtra("SELECTED_VEHICLE", vehiculo.getId());
        startActivity(intent);
    }

    private class LeerVehiculo extends AsyncTask<Void, Void, Vehiculo> implements RecyclerViewInterfaceFichaMantenimientos {
        @Override
        protected Vehiculo doInBackground(Void... voids) {
            vehiculo = CargadorVehiculos.cargarVehiculo(getApplicationContext(), idVehiculo);
            return vehiculo;
        }

        @Override
        protected void onPostExecute(Vehiculo vehiculo) {
            configToolbar();
            configView();
            rellenarFicha();
            configRecyclerView(this);
        }

        @Override
        public void onItemClick(int position) {
            Intent intent = new Intent(VehiculoActivity.this, MantenimientosActivity.class);
            intent.putExtra("SELECTED_VEHICLE", vehiculo.getId());
            startActivity(intent);
        }
    }

}