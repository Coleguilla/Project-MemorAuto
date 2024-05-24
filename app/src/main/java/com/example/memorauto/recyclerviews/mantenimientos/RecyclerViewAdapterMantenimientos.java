package com.example.memorauto.recyclerviews.mantenimientos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memorauto.R;
import com.example.memorauto.db.entity.Vehiculo;

import java.util.List;

public class RecyclerViewAdapterMantenimientos extends RecyclerView.Adapter<RecyclerViewAdapterMantenimientos.MyViewHolder> {
    Context context;
    List<Vehiculo> vehiculos;
    private final RecyclerViewInterfaceMantenimientos recyclerViewInterfaceMantenimientos;

    public RecyclerViewAdapterMantenimientos(Context context, List<Vehiculo> vehiculos, RecyclerViewInterfaceMantenimientos recyclerViewInterfaceMantenimientos) {
        this.context = context;
        this.vehiculos = vehiculos;
        this.recyclerViewInterfaceMantenimientos = recyclerViewInterfaceMantenimientos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_vehiculo, parent, false);
        return new MyViewHolder(view, recyclerViewInterfaceMantenimientos);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
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

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterfaceMantenimientos recyclerViewInterfaceMantenimientos) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.rvv_tv_nombre);
            tvMarca = itemView.findViewById(R.id.rvv_tv_marca);
            tvModelo = itemView.findViewById(R.id.rvv_tv_modelo);
            ivAuto = itemView.findViewById(R.id.rvv_imageview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterfaceMantenimientos != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterfaceMantenimientos.onItemClick(pos);
                        }
                    }

                }
            });
        }
    }
}
