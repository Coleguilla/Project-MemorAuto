package com.example.memorauto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memorauto.db.entity.Vehiculo;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<Vehiculo> vehiculos;

    public RecyclerViewAdapter(Context context, ArrayList<Vehiculo> vehiculos) {
        this.context = context;
        this.vehiculos = vehiculos;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_vehiculo, parent, false);
        return new RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.tvNombre.setText(vehiculos.get(position).getNombre());
        holder.tvMarca.setText(vehiculos.get(position).getMarca());
        holder.tvModelo.setText(vehiculos.get(position).getModelo());
        holder.ivAuto.setImageResource(R.drawable.icon_car);
    }

    @Override
    public int getItemCount() {
        return vehiculos.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombre, tvMarca, tvModelo;
        ImageView ivAuto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.rvv_tv_nombre);
            tvMarca = itemView.findViewById(R.id.rvv_tv_marca);
            tvModelo = itemView.findViewById(R.id.rvv_tv_modelo);
            ivAuto = itemView.findViewById(R.id.rvv_imageview);
        }
    }
}
