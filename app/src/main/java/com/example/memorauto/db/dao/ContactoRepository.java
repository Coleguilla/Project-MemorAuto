package com.example.memorauto.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.memorauto.db.entity.Contacto;

import java.util.List;

@Dao
public interface ContactoRepository {

    @Insert
    void insert(Contacto contacto);

    @Query("SELECT * FROM TContactos")
    List<Contacto> findAll();

    @Query("SELECT * FROM TContactos WHERE id = (SELECT MAX(id) FROM TContactos)")
    Contacto findLastContacto();

    @Query("DELETE FROM TContactos where id LIKE :id")
    void deleteById(int id);
}
