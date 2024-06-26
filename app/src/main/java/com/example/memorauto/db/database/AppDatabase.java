package com.example.memorauto.db.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.memorauto.db.dao.ContactoRepository;
import com.example.memorauto.db.dao.MantenimientoRepository;
import com.example.memorauto.db.dao.RecordatorioRepository;
import com.example.memorauto.db.dao.VehiculoContactoRepository;
import com.example.memorauto.db.dao.VehiculoRepository;
import com.example.memorauto.db.entity.Contacto;
import com.example.memorauto.db.entity.Mantenimiento;
import com.example.memorauto.db.entity.Recordatorio;
import com.example.memorauto.db.entity.Vehiculo;
import com.example.memorauto.db.entity.VehiculoContacto;

@Database(entities = {Vehiculo.class, Mantenimiento.class, Recordatorio.class, Contacto.class, VehiculoContacto.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract VehiculoRepository vehiculoRepository();
    public abstract MantenimientoRepository mantenimientoRepository();
    public abstract RecordatorioRepository recordatorioRepository();
    public abstract ContactoRepository contactoRepository();
    public abstract VehiculoContactoRepository vehiculoContactoRepository();

    private static AppDatabase INSTANCE;
    public static AppDatabase getAppDb(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "DB_memorauto_prod_v1").build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
