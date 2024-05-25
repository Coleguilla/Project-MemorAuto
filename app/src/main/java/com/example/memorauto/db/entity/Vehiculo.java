package com.example.memorauto.db.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.List;


@Entity(tableName = "TVehiculos")
public class Vehiculo implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nombre;
    private String marca;
    private String modelo;
    private GregorianCalendar fecha_fabricacion;
    private GregorianCalendar fecha_compra;
    @Ignore
    private List<Mantenimiento> mantenimientos;

    //...

    public Vehiculo() {
    }

    public Vehiculo(String nombre, String marca) {
        this.nombre = nombre;
        this.marca = marca;
    }

    public Vehiculo(String nombre, String marca, String modelo) {
        this.nombre = nombre;
        this.marca = marca;
        this.modelo = modelo;
    }

    public Vehiculo(String nombre, String marca, String modelo, GregorianCalendar fecha_fabricacion, GregorianCalendar fecha_compra) {
        this.nombre = nombre;
        this.marca = marca;
        this.modelo = modelo;
        this.fecha_fabricacion = fecha_fabricacion;
        this.fecha_compra = fecha_compra;
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public GregorianCalendar getFecha_fabricacion() {
        return fecha_fabricacion;
    }

    public void setFecha_fabricacion(GregorianCalendar fecha_fabricacion) {
        this.fecha_fabricacion = fecha_fabricacion;
    }

    public GregorianCalendar getFecha_compra() {
        return fecha_compra;
    }

    public void setFecha_compra(GregorianCalendar fecha_compra) {
        this.fecha_compra = fecha_compra;
    }

    public List<Mantenimiento> getMantenimientos() {
        return mantenimientos;
    }

    public void setMantenimientos(List<Mantenimiento> mantenimientos) {
        this.mantenimientos = mantenimientos;
    }
}
