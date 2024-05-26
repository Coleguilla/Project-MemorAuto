package com.example.memorauto.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.memorauto.db.entity.Mantenimiento;

import java.util.List;

@Dao
public interface MantenimientoRepository {
    @Insert
    void insert(Mantenimiento mantenimiento);

    @Query("SELECT * FROM TMantenimientos where vehiculoId LIKE :vehiculoId")
    List<Mantenimiento> findByVehiculoId(int vehiculoId);

    @Query("SELECT * FROM TMantenimientos")
    List<Mantenimiento> findAll();
}
