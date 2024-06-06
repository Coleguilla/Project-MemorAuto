package com.example.memorauto;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memorauto.db.database.AppDatabase;
import com.example.memorauto.db.entity.Mantenimiento;
import com.example.memorauto.recyclerviews.mantenimientos.RecyclerViewAdapterMantenimientos;
import com.example.memorauto.recyclerviews.mantenimientos.RecyclerViewInterfaceMantenimientos;

import java.util.List;

public class MantenimientosActivity extends AppCompatActivity {

    private List<Mantenimiento> mantenimientos;
    public int idVehiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimientos);

        idVehiculo = getIntent().getIntExtra("SELECTED_VEHICLE", 0);
        LeerMantenimientos leerMantenimientos = new LeerMantenimientos();
        leerMantenimientos.execute();
    }

    private void configToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.amant_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Mantenimientos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MantenimientosActivity.this, VehiculoActivity.class);
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
            Intent intent = new Intent(this, RegistroMantenimientoActivity.class);
            intent.putExtra("SELECTED_VEHICLE", idVehiculo);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void configRecyclerView(RecyclerViewInterfaceMantenimientos rvim) {
        RecyclerView recyclerView = findViewById(R.id.amant_recyclerview);
        RecyclerViewAdapterMantenimientos adapter = new RecyclerViewAdapterMantenimientos(getApplicationContext(), mantenimientos, rvim);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
    }

    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MantenimientosActivity.this);
            builder.setTitle("Confirmación")
                    .setMessage("Vas a borrar este mantenimiento, ¿Continuar?")
                    .setCancelable(true)
                    .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Integer idABorrar = mantenimientos.get(viewHolder.getAdapterPosition()).getId();
                            BorrarMantenimientos borrarMantenimientos = new BorrarMantenimientos();
                            borrarMantenimientos.execute(idABorrar);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .show();
        }
    };

    private class LeerMantenimientos extends AsyncTask<Void, Void, List<Mantenimiento>> implements RecyclerViewInterfaceMantenimientos {
        @Override
        protected List<Mantenimiento> doInBackground(Void... voids) {
            mantenimientos = AppDatabase.getAppDb(getApplicationContext()).mantenimientoRepository().findByVehiculoId(idVehiculo);
            return mantenimientos;
        }

        @Override
        protected void onPostExecute(List<Mantenimiento> mantenimientos) {
            configToolbar();
            configRecyclerView(this);
        }

        @Override
        public void onItemClick(int position) {
            Intent intent = new Intent(MantenimientosActivity.this, RecordatoriosActivity.class);
            intent.putExtra("SELECTED_MAINTENANCE", mantenimientos.get(position).getId());
            intent.putExtra("SELECTED_VEHICLE", idVehiculo);
            startActivity(intent);
        }
    }

    private class BorrarMantenimientos extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... integers) {
            AppDatabase.getAppDb(getApplicationContext()).mantenimientoRepository().deleteById(integers[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            Toast.makeText(MantenimientosActivity.this, "Mantenimiento borrado con éxito", Toast.LENGTH_SHORT).show();
        }
    }

}