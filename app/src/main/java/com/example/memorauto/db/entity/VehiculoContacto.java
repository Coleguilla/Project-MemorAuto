package com.example.memorauto.db.entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "TVehiculosContactos", foreignKeys = {
        @ForeignKey(entity = Vehiculo.class, parentColumns = "id", childColumns = "vehiculoId", onDelete = CASCADE),
        @ForeignKey(entity = Contacto.class, parentColumns = "id", childColumns = "contactoId", onDelete = CASCADE)
})
public class VehiculoContacto {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int vehiculoId;
    private int contactoId;
    @Ignore
    private Vehiculo vehiculo;
    @Ignore
    private Contacto contacto;

    //...

    public VehiculoContacto(int vehiculoId, int contactoId) {
        this.vehiculoId = vehiculoId;
        this.contactoId = contactoId;
    }

    //...

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVehiculoId() {
        return vehiculoId;
    }

    public void setVehiculoId(int vehiculoId) {
        this.vehiculoId = vehiculoId;
    }

    public int getContactoId() {
        return contactoId;
    }

    public void setContactoId(int contactoId) {
        this.contactoId = contactoId;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Contacto getContacto() {
        return contacto;
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }
}
