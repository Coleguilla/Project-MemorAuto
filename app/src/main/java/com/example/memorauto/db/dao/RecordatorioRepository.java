package com.example.memorauto.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.memorauto.db.entity.Recordatorio;

import java.util.List;

@Dao
public interface RecordatorioRepository {
    @Insert
    void insert(Recordatorio recordatorio);

    @Query("SELECT * FROM TRecordatorios where mantenimientoId LIKE :mantenimientoId")
    List<Recordatorio> findByMantenimientoId(int mantenimientoId);
    @Query("SELECT * FROM TRecordatorios")
    List<Recordatorio> findAll();

    @Query("DELETE FROM TRecordatorios where id LIKE :id")
    void deleteById(int id);
}
