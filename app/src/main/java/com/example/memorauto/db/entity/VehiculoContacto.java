package com.example.memorauto.db.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "TVehiculosContactos", primaryKeys = {"vehiculoId", "contactoId"}, foreignKeys = {
        @ForeignKey(entity = Vehiculo.class, parentColumns = "id", childColumns = "vehiculoId"),
        @ForeignKey(entity = Contacto.class, parentColumns = "id", childColumns = "contactoId")
})
public class VehiculoContacto {
    private int vehiculoId;
    private int contactoId;

    //...

    public VehiculoContacto(int vehiculoId, int contactoId) {
        this.vehiculoId = vehiculoId;
        this.contactoId = contactoId;
    }

    //...

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
}
