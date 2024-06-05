package com.example.memorauto.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TContactos")
public class Contacto {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nombre;
    private String tipo;
    private int telefono;
    private String direccion;

    //...

    public Contacto(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    //...

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
