package com.example.memorauto.db.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.*;

import java.util.GregorianCalendar;

@Entity(tableName = "TRecordatorios", foreignKeys = @ForeignKey(entity = Mantenimiento.class, parentColumns = "id", childColumns = "mantenimientoId", onDelete = CASCADE))
public class Recordatorio {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private GregorianCalendar fechaAviso;
    private int mantenimientoId;

    public Recordatorio(GregorianCalendar fechaAviso, int mantenimientoId) {
        this.fechaAviso = fechaAviso;
        this.mantenimientoId = mantenimientoId;
    }

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
}
