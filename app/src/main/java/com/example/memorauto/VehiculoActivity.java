package com.example.memorauto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.example.memorauto.db.entity.Vehiculo;

import java.util.Calendar;

public class VehiculoActivity extends AppCompatActivity {

    private TextView tvNombre, tvMarca, tvModelo, tvFechaFabricacion, tvFechaCompra;
    Vehiculo vehiculo;
    String cadenaFFabricacion;
    String cadenaFCompra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo);

        configView();
        vehiculo = (Vehiculo) getIntent().getSerializableExtra("hola");
        if (vehiculo.getFecha_fabricacion() != null){
            cadenaFFabricacion = vehiculo.getFecha_fabricacion().get(Calendar.DAY_OF_MONTH)+"/"+(vehiculo.getFecha_fabricacion().get(Calendar.MONTH) + 1)+"/"+vehiculo.getFecha_fabricacion().get(Calendar.YEAR);

        } else {cadenaFFabricacion = "Sin fecha";}
        if (vehiculo.getFecha_compra() != null) {
            cadenaFCompra = vehiculo.getFecha_compra().get(Calendar.DAY_OF_MONTH)+"/"+(vehiculo.getFecha_compra().get(Calendar.MONTH) + 1)+"/"+vehiculo.getFecha_compra().get(Calendar.YEAR);
        } else {cadenaFCompra = "Sin fecha";}

        configToolbar();

        tvMarca.setText(vehiculo.getMarca());
        tvModelo.setText(vehiculo.getModelo());
        tvFechaFabricacion.setText(cadenaFFabricacion);
        tvFechaCompra.setText(cadenaFCompra);
        //getSupportActionBar().setTitle(nombrarToolbar());
    }

    private String nombrarToolbar(){
        if (vehiculo.getNombre() != null) {
            return vehiculo.getNombre();
        } else {
            return "Vehiculo";
        }
    }

    private void configToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.av_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(nombrarToolbar());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void configView() {
       // tvNombre = findViewById(R.id.av);
        tvMarca = findViewById(R.id.av_tv_marca);
        tvModelo = findViewById(R.id.av_tv_modelo);
        tvFechaFabricacion = findViewById(R.id.av_tv_ffabricacion);
        tvFechaCompra = findViewById(R.id.av_tv_fcompra);
    }
}