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
import com.example.memorauto.db.entity.Mantenimiento;

import java.util.Calendar;
import java.util.List;

public class RecyclerViewAdapterMantenimientos extends RecyclerView.Adapter<RecyclerViewAdapterMantenimientos.MyViewHolder> {
    Context context;
    List<Mantenimiento> mantenimientos;
    private final RecyclerViewInterfaceMantenimientos recyclerViewInterfaceMantenimientos;

    public RecyclerViewAdapterMantenimientos(Context context, List<Mantenimiento> mantenimientos, RecyclerViewInterfaceMantenimientos recyclerViewInterfaceMantenimientos) {
        this.context = context;
        this.mantenimientos = mantenimientos;
        this.recyclerViewInterfaceMantenimientos = recyclerViewInterfaceMantenimientos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_mantenimiento, parent, false);
        return new MyViewHolder(view, recyclerViewInterfaceMantenimientos);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvNombre.setText(mantenimientos.get(position).getNombre());
        holder.tvTipo.setText(mantenimientos.get(position).getTipo());
        String cadenaFecha = mantenimientos.get(position).getFecha().get(Calendar.DAY_OF_MONTH)+"/"+(mantenimientos.get(position).getFecha().get(Calendar.MONTH) + 1)+"/"+mantenimientos.get(position).getFecha().get(Calendar.YEAR);
        holder.tvFecha.setText(cadenaFecha);
        holder.tvOdometro.setText(String.valueOf(mantenimientos.get(position).getOdometro()));
        holder.ivAlerta.setImageResource(R.drawable.icon_alert);
    }

    @Override
    public int getItemCount() {
        return mantenimientos.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvTipo, tvFecha, tvOdometro;
        ImageView ivAlerta;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterfaceMantenimientos recyclerViewInterfaceMantenimientos) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.rvm_tv_nombre);
            tvTipo = itemView.findViewById(R.id.rvm_tv_tipo);
            tvFecha = itemView.findViewById(R.id.rvm_tv_fecha);
            tvOdometro = itemView.findViewById(R.id.rvm_tv_odometro);
            ivAlerta = itemView.findViewById(R.id.rvm_imageview);

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
