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
import android.view.View;

import com.example.memorauto.db.database.AppDatabase;
import com.example.memorauto.db.entity.Mantenimiento;
import com.example.memorauto.db.entity.Recordatorio;
import com.example.memorauto.recyclerviews.mainactivity.RecyclerViewAdapter;
import com.example.memorauto.recyclerviews.mainactivity.RecyclerViewInterface;
import com.example.memorauto.recyclerviews.mantenimientos.RecyclerViewAdapterMantenimientos;
import com.example.memorauto.recyclerviews.mantenimientos.RecyclerViewInterfaceMantenimientos;
import com.example.memorauto.recyclerviews.recordatorios.RecyclerViewAdapterRecordatorios;

import java.util.List;

public class RecordatoriosActivity extends AppCompatActivity {

    private List<Recordatorio> recordatorios;
    public int idMantenimiento;
    public int idVehiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordatorios);

        idMantenimiento = getIntent().getIntExtra("SELECTED_MAINTENANCE", 0);
        idVehiculo = getIntent().getIntExtra("SELECTED_VEHICLE", 0);
        LeerRecordatorios leerRecordatorios = new LeerRecordatorios();
        leerRecordatorios.execute();
    }

    private void configToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.ar_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Recordatorios");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecordatoriosActivity.this, MantenimientosActivity.class);
                intent.putExtra("SELECTED_VEHICLE", idVehiculo);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mp_registrar) {
            Intent intent = new Intent(this, RegistroRecordatorioActivity.class);
            intent.putExtra("SELECTED_MAINTENANCE", idMantenimiento);
            intent.putExtra("SELECTED_VEHICLE", idVehiculo);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void configRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.ar_recyclerview);
        RecyclerViewAdapterRecordatorios adapter = new RecyclerViewAdapterRecordatorios(getApplicationContext(), recordatorios);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private class LeerRecordatorios extends AsyncTask<Void, Void, List<Recordatorio>> {
        @Override
        protected List<Recordatorio> doInBackground(Void... voids) {
            recordatorios = AppDatabase.getAppDb(getApplicationContext()).recordatorioRepository().findByMantenimientoId(idMantenimiento);
            return recordatorios;
        }

        @Override
        protected void onPostExecute(List<Recordatorio> recordatorios) {
            configToolbar();
            configRecyclerView();
        }
    }

}