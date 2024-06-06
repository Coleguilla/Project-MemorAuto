package com.example.memorauto.db.entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.GregorianCalendar;

@Entity(tableName = "TRecordatorios", foreignKeys = @ForeignKey(entity = Mantenimiento.class, parentColumns = "id", childColumns = "mantenimientoId", onDelete = CASCADE))
public class Recordatorio {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private GregorianCalendar fechaAviso;
    private int mantenimientoId;
    @Ignore
    private Mantenimiento mantenimiento;

    //...

    public Recordatorio(GregorianCalendar fechaAviso, int mantenimientoId) {
        this.fechaAviso = fechaAviso;
        this.mantenimientoId = mantenimientoId;
    }

    //...

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GregorianCalendar getFechaAviso() {
        return fechaAviso;
    }

    public void setFechaAviso(GregorianCalendar fechaAviso) {
        this.fechaAviso = fechaAviso;
    }

    public int getMantenimientoId() {
        return mantenimientoId;
    }

    public void setMantenimientoId(int mantenimientoId) {
        this.mantenimientoId = mantenimientoId;
    }

    public Mantenimiento getMantenimiento() {
        return mantenimiento;
    }

    public void setMantenimiento(Mantenimiento mantenimiento) {
        this.mantenimiento = mantenimiento;
    }
}
