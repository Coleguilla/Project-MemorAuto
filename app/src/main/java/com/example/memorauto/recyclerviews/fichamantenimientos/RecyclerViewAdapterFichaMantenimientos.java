package com.example.memorauto.recyclerviews.fichamantenimientos;

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
import com.example.memorauto.db.entity.Recordatorio;

import java.util.Calendar;
import java.util.List;

public class RecyclerViewAdapterFichaMantenimientos extends RecyclerView.Adapter<RecyclerViewAdapterFichaMantenimientos.MyViewHolder> {
    Context context;
    List<Mantenimiento> mantenimientos;
    private final RecyclerViewInterfaceFichaMantenimientos recyclerViewInterfaceFichaMantenimientos;

    public RecyclerViewAdapterFichaMantenimientos(Context context, List<Mantenimiento> mantenimientos, RecyclerViewInterfaceFichaMantenimientos recyclerViewInterfaceFichaMantenimientos) {
        this.context = context;
        this.mantenimientos = mantenimientos;
        this.recyclerViewInterfaceFichaMantenimientos = recyclerViewInterfaceFichaMantenimientos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_ficha_mantenimiento, parent, false);
        return new MyViewHolder(view, recyclerViewInterfaceFichaMantenimientos);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvNombre.setText(mantenimientos.get(position).getNombre());
        holder.tvTipo.setText(mantenimientos.get(position).getTipo());
        String cadenaFecha = mantenimientos.get(position).getFecha().get(Calendar.DAY_OF_MONTH)+"/"+(mantenimientos.get(position).getFecha().get(Calendar.MONTH) + 1)+"/"+mantenimientos.get(position).getFecha().get(Calendar.YEAR);
        holder.tvFecha.setText(cadenaFecha);
    }

    @Override
    public int getItemCount() {
        return mantenimientos.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvTipo, tvFecha;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterfaceFichaMantenimientos recyclerViewInterfaceFichaMantenimientos) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.rvfm_tv_nombre);
            tvTipo = itemView.findViewById(R.id.rvfm_tv_tipo);
            tvFecha = itemView.findViewById(R.id.rvfm_tv_fecha);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterfaceFichaMantenimientos != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterfaceFichaMantenimientos.onItemClick(pos);
                        }
                    }

                }
            });
        }
    }
}
