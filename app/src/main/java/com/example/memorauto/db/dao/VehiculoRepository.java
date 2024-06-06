package com.example.memorauto.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.memorauto.db.entity.Vehiculo;

import java.util.List;

@Dao
public interface VehiculoRepository {
    @Insert
    void insert(Vehiculo vehiculo);

    @Query("SELECT * FROM TVehiculos")
    List<Vehiculo> findAll();
    @Query("SELECT * FROM TVehiculos where id LIKE :id")
    Vehiculo findById(int id);

    @Query("DELETE FROM TVehiculos where id LIKE :id")
    void deleteById(int id);
}
