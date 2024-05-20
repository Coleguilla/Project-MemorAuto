package com.example.memorauto;

import android.content.Context;
import android.util.Log;

import com.example.memorauto.db.database.AppDatabase;
import com.example.memorauto.db.entity.Vehiculo;

import java.util.List;

public class HiloSecundario extends Thread {

    public Context context;
    public int idBoton;
    public Vehiculo cliente;

    public HiloSecundario(Context context, int idBoton) {
        this.context = context;
        this.idBoton = idBoton;
    }

    public HiloSecundario(Context context, int idBoton, Vehiculo cliente) {
        this.context = context;
        this.idBoton = idBoton;
        this.cliente = cliente;
    }

    public void run() {
        AppDatabase conexionBD = AppDatabase.getAppDb(context);

        if (idBoton == R.id.btGuardar) {
            conexionBD.vehiculoRepository().insert(cliente);
        }
/*
        if (idBoton == R.id.btModificar) {
            Vehiculo clienteAModificar = conexionBD.vehiculoRepository().findById(cliente.getId());
            clienteAModificar.setNombre(cliente.getNombre());
            clienteAModificar.setMarca(cliente.getMarca());
            conexionBD.vehiculoRepository().update(clienteAModificar);
        }

        if (idBoton == R.id.btBorrar) {
          //  cliente = conexionBD.clienteRepository().findByName(cliente.getNombre());
          //  conexionBD.clienteRepository().delete(cliente);
            conexionBD.vehiculoRepository().deleteAll();
        }

        if (idBoton == R.id.btBuscarId) {
            cliente = conexionBD.vehiculoRepository().findById(cliente.getId());
            Log.d("CONSULTA", "ID: " + cliente.getId() + " Nombre: " + cliente.getNombre() + " Password: " + cliente.getMarca() + "\n");
        }

        if (idBoton == R.id.btBuscarNombre) {
            cliente = conexionBD.vehiculoRepository().findByName(cliente.getNombre());
            Log.d("CONSULTA", "ID: " + cliente.getId() + " Nombre: " + cliente.getNombre() + " Password: " + cliente.getMarca() + "\n");
        }
*/
        if (idBoton == R.id.btMostrarTodos) {
            List<Vehiculo> clientes = conexionBD.vehiculoRepository().findAll();
            for (Vehiculo cliente : clientes) {
                Log.d("CONSULTA", "ID: " + cliente.getId() + " Nombre: " + cliente.getNombre() + " Marca: " + cliente.getMarca() + " Modelo: " + cliente.getModelo() + "\n");
            }
        }

    }
}
