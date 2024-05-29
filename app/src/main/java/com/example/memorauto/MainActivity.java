package com.example.memorauto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.memorauto.db.CargadorVehiculos;
import com.example.memorauto.db.entity.Mantenimiento;
import com.example.memorauto.db.entity.Recordatorio;
import com.example.memorauto.db.entity.Vehiculo;
import com.example.memorauto.recyclerviews.mainactivity.RecyclerViewAdapter;
import com.example.memorauto.recyclerviews.mainactivity.RecyclerViewInterface;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author Jose David Álvarez Sánchez
 * Proyecto del Ciclo de Desarrollo de Aplicaciones Multiplataforma (2022-2024)
 * Bajo licencia Creative commons CC BY-SA 4.0
 */

public class MainActivity extends AppCompatActivity {

    private final String CHANNEL_ID = "MemorAuto";
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
        for (Vehiculo vehiculo : vehiculos) {
            for (Mantenimiento mantenimiento : vehiculo.getMantenimientos()) {
                for (Recordatorio recordatorio : mantenimiento.getRecordatorios()) {
                    if (compararFechas(recordatorio.getFechaAviso())) {
                        Log.d("QUIEN", "El vehiculo: " + vehiculo.getNombre() + ", el mantenimiento: " + mantenimiento.getNombre() + ", de tipo: " + mantenimiento.getTipo());
                        notificar(vehiculo.getId(), vehiculo.getNombre(), mantenimiento.getNombre(), mantenimiento.getTipo());
                    }
                }
            }
        }
    }

    private void notificar(int idVehiculo, String nombreVehiculo, String nombreMant, String tipoMant) {
        String cadenaNotificacion = "Tienes un recordatorio para tu vehículo " + nombreVehiculo.toUpperCase() + ". Revisa el mantenimiento \"" + nombreMant.toUpperCase() + "\", de tipo " + tipoMant.toUpperCase();
        Intent intent = new Intent(MainActivity.this, VehiculoActivity.class);
        intent.putExtra("SELECTED_VEHICLE", idVehiculo);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_MUTABLE);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Recordatorio")
                .setContentText(cadenaNotificacion)
                .setVibrate(new long[]{100, 250, 100, 500})
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(1, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
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
            createNotificationChannel();
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

