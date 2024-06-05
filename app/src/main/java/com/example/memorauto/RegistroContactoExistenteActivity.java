package com.example.memorauto;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.example.memorauto.db.entity.Contacto;
import com.example.memorauto.db.entity.VehiculoContacto;
import com.example.memorauto.recyclerviews.contactos.RecyclerViewAdapterContactos;
import com.example.memorauto.recyclerviews.contactos.RecyclerViewInterfaceContactos;

import java.util.List;

public class RegistroContactoExistenteActivity extends AppCompatActivity {

    private List<Contacto> contactos;
    public int idVehiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_contacto_existente);

        idVehiculo = getIntent().getIntExtra("SELECTED_VEHICLE", 0);
        LeerContactos leerContactos = new LeerContactos();
        leerContactos.execute();
    }

    private void configToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.arce_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Lista de contactos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistroContactoExistenteActivity.this, ContactosActivity.class);
                intent.putExtra("SELECTED_VEHICLE", idVehiculo);
                startActivity(intent);
            }
        });
    }

    private void configRecyclerView(RecyclerViewInterfaceContactos rvic) {
        RecyclerView recyclerView = findViewById(R.id.arce_recyclerview);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(RegistroContactoExistenteActivity.this);
            builder.setTitle("Confirmación")
                    .setMessage("Vas a borrar este contacto para todos los vehículos, ¿Continuar?")
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
            contactos = AppDatabase.getAppDb(getApplicationContext()).contactoRepository().findAll();
            return contactos;
        }

        @Override
        protected void onPostExecute(List<Contacto> contactos) {
            configToolbar();
            configRecyclerView(this);
        }

        @Override
        public void onItemClick(int position) {
            VehiculoContacto vehiculoContacto = new VehiculoContacto(idVehiculo, contactos.get(position).getId());
            EjecutarRegistro ejecutarRegistro = new EjecutarRegistro();
            ejecutarRegistro.execute(vehiculoContacto);

        }
    }

    private class EjecutarRegistro extends AsyncTask<VehiculoContacto, Void, Void> {

        @Override
        protected Void doInBackground(VehiculoContacto... vehiculosContactos) {
            AppDatabase.getAppDb(getApplicationContext()).vehiculoContactoRepository().insert(vehiculosContactos[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            Intent intent = new Intent(RegistroContactoExistenteActivity.this, ContactosActivity.class);
            intent.putExtra("SELECTED_VEHICLE", idVehiculo);
            startActivity(intent);
        }
    }

    private class BorrarContactos extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... integers) {
            AppDatabase.getAppDb(getApplicationContext()).contactoRepository().deleteById(integers[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            Toast.makeText(RegistroContactoExistenteActivity.this, "Contacto borrado con éxito", Toast.LENGTH_SHORT).show();
        }
    }

}