package com.example.memorauto.db.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.memorauto.db.entity.Vehiculo;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface VehiculoRepository {
    @Insert
    void insert(Vehiculo vehiculo);
    @Insert
    void insertAll(Vehiculo... vehiculos);

    @Query("SELECT * FROM TVehiculos")
    List<Vehiculo> findAll();
    @Query("SELECT * FROM TVehiculos where id LIKE :id")
    Vehiculo findById(int id);
    @Query("SELECT * FROM TVehiculos where nombre LIKE :nombre")
    Vehiculo findByName(String nombre);

    @Update
    void update(Vehiculo vehiculo);

    @Delete
    void delete(Vehiculo vehiculo);
    @Query("DELETE FROM TVehiculos")
    void deleteAll();
}
