package com.example.memorauto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.memorauto.db.database.AppDatabase;
import com.example.memorauto.db.entity.Contacto;
import com.example.memorauto.db.entity.Mantenimiento;
import com.example.memorauto.recyclerviews.contactos.RecyclerViewAdapterContactos;
import com.example.memorauto.recyclerviews.contactos.RecyclerViewInterfaceContactos;
import com.example.memorauto.recyclerviews.mantenimientos.RecyclerViewAdapterMantenimientos;
import com.example.memorauto.recyclerviews.mantenimientos.RecyclerViewInterfaceMantenimientos;

import java.util.List;

public class ContactosActivity extends AppCompatActivity {

    private List<Contacto> contactos;
    public int idVehiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);

        idVehiculo = getIntent().getIntExtra("SELECTED_VEHICLE", 0);
        LeerContactos leerContactos = new LeerContactos();
        leerContactos.execute();
    }

    private void configToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.ac_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Contactos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactosActivity.this, VehiculoActivity.class);
                intent.putExtra("SELECTED_VEHICLE", idVehiculo);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contactos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mc_nuevocontacto) {
            Log.d("TEST", "apretado boton nuevo contacto");
            Intent intent = new Intent(this, RegistroContactoActivity.class);
            intent.putExtra("SELECTED_VEHICLE", idVehiculo);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.mc_anadirexistente) {
            Log.d("TEST", "apretado boton contacto existente");
            Intent intent = new Intent(this, RegistroContactoExistenteActivity.class);
            intent.putExtra("SELECTED_VEHICLE", idVehiculo);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void configRecyclerView(RecyclerViewInterfaceContactos rvic) {
        RecyclerView recyclerView = findViewById(R.id.ac_recyclerview);
        registerForContextMenu(recyclerView);
        RecyclerViewAdapterContactos adapter = new RecyclerViewAdapterContactos(getApplicationContext(), contactos, rvic);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(ContactosActivity.this);
            builder.setTitle("Confirmación")
                    .setMessage("Vas a borrar este contacto para este vehículo, ¿Continuar?")
                    .setCancelable(true)
                    .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Integer idABorrar = contactos.get(viewHolder.getAdapterPosition()).getId();
                            BorrarContactos borrarContactos = new BorrarContactos();
                            borrarContactos.execute(idABorrar);
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

    private class LeerContactos extends AsyncTask<Void, Void, List<Contacto>> implements RecyclerViewInterfaceContactos{
        @Override
        protected List<Contacto> doInBackground(Void... voids) {
            contactos = AppDatabase.getAppDb(getApplicationContext()).vehiculoContactoRepository().findContactosByVehiculo(idVehiculo);
            return contactos;
        }

        @Override
        protected void onPostExecute(List<Contacto> contactos) {
            configToolbar();
            configRecyclerView(this);
        }

        @Override
        public void onItemClick(int position) {
        }
    }

    private class BorrarContactos extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... integers) {
            AppDatabase.getAppDb(getApplicationContext()).vehiculoContactoRepository().deleteById(integers[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            Toast.makeText(ContactosActivity.this, "Contacto borrado con éxito", Toast.LENGTH_SHORT).show();
        }
    }

}