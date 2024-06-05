package com.example.memorauto.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.memorauto.db.entity.Contacto;
import com.example.memorauto.db.entity.VehiculoContacto;

import java.util.List;

@Dao
public interface VehiculoContactoRepository {

    @Insert
    void insert(VehiculoContacto vehiculoContacto);

    @Query("SELECT * FROM TContactos INNER JOIN TVehiculosContactos ON TContactos.id=TVehiculosContactos.contactoId WHERE TVehiculosContactos.vehiculoId = :vehiculoId")
    List<Contacto> findContactosByVehiculo(int vehiculoId);
}
