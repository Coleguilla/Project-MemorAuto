package com.example.memorauto.db.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.*;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.List;

@Entity(tableName = "TMantenimientos", foreignKeys = @ForeignKey(entity = Vehiculo.class, parentColumns = "id", childColumns = "vehiculoId", onDelete = CASCADE))
public class Mantenimiento implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nombre;
    private String tipo;
    private GregorianCalendar fecha;
    private int odometro;
    private int vehiculoId;
    @Ignore
    private List<Recordatorio> recordatorios;
    @Ignore
    private Vehiculo vehiculo;

    //...

    public Mantenimiento(String nombre, String tipo, GregorianCalendar fecha, int vehiculoId) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.fecha = fecha;
        this.vehiculoId = vehiculoId;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public GregorianCalendar getFecha() {
        return fecha;
    }

    public void setFecha(GregorianCalendar fecha) {
        this.fecha = fecha;
    }

    public int getOdometro() {
        return odometro;
    }

    public void setOdometro(int odometro) {
        this.odometro = odometro;
    }

    public int getVehiculoId() {
        return vehiculoId;
    }

    public void setVehiculoId(int vehiculoId) {
        this.vehiculoId = vehiculoId;
    }

    public List<Recordatorio> getRecordatorios() {
        return recordatorios;
    }

    public void setRecordatorios(List<Recordatorio> recordatorios) {
        this.recordatorios = recordatorios;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }
}
