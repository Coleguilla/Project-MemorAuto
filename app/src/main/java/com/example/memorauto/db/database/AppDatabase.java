package com.example.memorauto.db.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.memorauto.db.dao.VehiculoRepository;
import com.example.memorauto.db.entity.Vehiculo;

@Database(entities = {Vehiculo.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract VehiculoRepository vehiculoRepository();

    private static AppDatabase INSTANCE;
    public static AppDatabase getAppDb(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "DB_memorauto_dev1").build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
