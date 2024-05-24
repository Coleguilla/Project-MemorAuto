package com.example.memorauto;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memorauto.db.database.AppDatabase;
import com.example.memorauto.db.entity.Vehiculo;

import java.util.List;

public class HiloSecundario extends Thread {

    public Context context;
    public int idBoton;
    public Vehiculo vehiculo;

    public HiloSecundario(Context context, int idBoton) {
        this.context = context;
        this.idBoton = idBoton;
    }

    public HiloSecundario(Context context, int idBoton, Vehiculo vehiculo) {
        this.context = context;
        this.idBoton = idBoton;
        this.vehiculo = vehiculo;
    }

    public void run() {
        AppDatabase conexionBD = AppDatabase.getAppDb(context);

        if (idBoton == R.id.arv_bt_registrar) {
            conexionBD.vehiculoRepository().insert(vehiculo);
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

        if (idBoton == R.id.btMostrarTodos) {
            List<Vehiculo> vehiculos = conexionBD.vehiculoRepository().findAll();
            for (Vehiculo vehiculo : vehiculos) {
                if (vehiculo.getFecha_fabricacion() != null && vehiculo.getFecha_compra() != null) {
                    int anyoFab = vehiculo.getFecha_fabricacion().get(Calendar.YEAR);
                    int mesFab = vehiculo.getFecha_fabricacion().get(Calendar.MONTH) + 1;
                    int diaFab = vehiculo.getFecha_fabricacion().get(Calendar.DAY_OF_MONTH);
                    int anyoComp = vehiculo.getFecha_compra().get(Calendar.YEAR);
                    int mesComp = vehiculo.getFecha_compra().get(Calendar.MONTH) + 1;
                    int diaComp = vehiculo.getFecha_compra().get(Calendar.DAY_OF_MONTH);
                    Log.d("CONSULTA", "ID: " + vehiculo.getId() + " |Nombre: " + vehiculo.getNombre() + " |Marca: " + vehiculo.getMarca() + " |Modelo: " + vehiculo.getModelo() +
                            " |FFabricacion: " + diaFab + "/" + mesFab + "/" + anyoFab + " |FCompra: " + diaComp + "/" + mesComp + "/" + anyoComp + "\n");
                } else {
                    Log.d("CONSULTA", "ID: " + vehiculo.getId() + " |Nombre: " + vehiculo.getNombre() + " |Marca: " + vehiculo.getMarca() + " |Modelo: " + vehiculo.getModelo() +
                            " |FFabricacion: sin datos |FCompra: sin datos " + "\n");
                }
            }
        }*/

    }
}
