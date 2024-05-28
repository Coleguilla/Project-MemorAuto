package com.example.memorauto.db;

import android.content.Context;

import com.example.memorauto.db.database.AppDatabase;
import com.example.memorauto.db.entity.Mantenimiento;
import com.example.memorauto.db.entity.Vehiculo;

import java.util.List;

public class CargadorVehiculos {

    public static Vehiculo cargarVehiculo(Context context, int id) {
        Vehiculo vehiculo = AppDatabase.getAppDb(context).vehiculoRepository().findById(id);
        vehiculo.setMantenimientos(AppDatabase.getAppDb(context).mantenimientoRepository().findByVehiculoId(vehiculo.getId()));
        for (Mantenimiento mantenimiento: vehiculo.getMantenimientos()) {
            mantenimiento.setRecordatorios(AppDatabase.getAppDb(context).recordatorioRepository().findByMantenimientoId(mantenimiento.getId()));
        }
        return vehiculo;
    }

    public static List<Vehiculo> cargarTodosVehiculos(Context context) {
        List<Vehiculo> vehiculos = AppDatabase.getAppDb(context).vehiculoRepository().findAll();
        for (Vehiculo vehiculo : vehiculos) {
            vehiculo.setMantenimientos(AppDatabase.getAppDb(context).mantenimientoRepository().findByVehiculoId(vehiculo.getId()));
            for (Mantenimiento mantenimiento: vehiculo.getMantenimientos()) {
                mantenimiento.setRecordatorios(AppDatabase.getAppDb(context).recordatorioRepository().findByMantenimientoId(mantenimiento.getId()));
            }
        }
        return vehiculos;
    }

}
