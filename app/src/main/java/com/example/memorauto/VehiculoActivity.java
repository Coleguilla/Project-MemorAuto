package com.example.memorauto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.memorauto.db.database.AppDatabase;
import com.example.memorauto.db.entity.Vehiculo;
import com.example.memorauto.recyclerviews.mainactivity.RecyclerViewAdapter;
import com.example.memorauto.recyclerviews.mainactivity.RecyclerViewInterface;

import java.util.Calendar;
import java.util.List;

public class VehiculoActivity extends AppCompatActivity {

    private TextView tvMarca, tvModelo, tvFechaFabricacion, tvFechaCompra;
    private Vehiculo vehiculo;
    private String cadenaFFabricacion;
    private String cadenaFCompra;
    public int idVehiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo);

        idVehiculo = getIntent().getIntExtra("selectedVehicle", 0);
        LeerVehiculo leerVehiculo = new LeerVehiculo();
        leerVehiculo.execute();
    }

    private void rellenarFicha() {
        if (vehiculo.getFecha_fabricacion() != null){
            cadenaFFabricacion = vehiculo.getFecha_fabricacion().get(Calendar.DAY_OF_MONTH)+"/"+(vehiculo.getFecha_fabricacion().get(Calendar.MONTH) + 1)+"/"+vehiculo.getFecha_fabricacion().get(Calendar.YEAR);
        } else {cadenaFFabricacion = "Sin fecha";}
        if (vehiculo.getFecha_compra() != null) {
            cadenaFCompra = vehiculo.getFecha_compra().get(Calendar.DAY_OF_MONTH)+"/"+(vehiculo.getFecha_compra().get(Calendar.MONTH) + 1)+"/"+vehiculo.getFecha_compra().get(Calendar.YEAR);
        } else {cadenaFCompra = "Sin fecha";}

        tvMarca.setText(vehiculo.getMarca());
        tvModelo.setText(vehiculo.getModelo());
        tvFechaFabricacion.setText(cadenaFFabricacion);
        tvFechaCompra.setText(cadenaFCompra);
    }

    private void configToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.av_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(vehiculo.getNombre());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void configView() {
        tvMarca = findViewById(R.id.rvm_tv_nombre);
        tvModelo = findViewById(R.id.rvm_tv_tipo);
        tvFechaFabricacion = findViewById(R.id.rvm_tv_fecha);
        tvFechaCompra = findViewById(R.id.rvm_tv_odometro);
    }

    public void lanzarMantenimientos (View view) {
        Intent intent = new Intent(this, MantenimientosActivity.class);
        intent.putExtra("selectedVehicle", vehiculo.getId());
        startActivity(intent);
    }

    private class LeerVehiculo extends AsyncTask<Void, Void, Vehiculo> {
        @Override
        protected Vehiculo doInBackground(Void... voids) {
            vehiculo = AppDatabase.getAppDb(getApplicationContext()).vehiculoRepository().findById(idVehiculo);
            return vehiculo;
        }

        @Override
        protected void onPostExecute(Vehiculo vehiculo) {
            configToolbar();
            configView();
            rellenarFicha();
        }
    }

}