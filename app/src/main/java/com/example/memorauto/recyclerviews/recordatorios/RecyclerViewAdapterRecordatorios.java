package com.example.memorauto.recyclerviews.recordatorios;

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

public class RecyclerViewAdapterRecordatorios extends RecyclerView.Adapter<RecyclerViewAdapterRecordatorios.MyViewHolder> {
    Context context;
    List<Recordatorio> recordatorios;

    public RecyclerViewAdapterRecordatorios(Context context, List<Recordatorio> recordatorios) {
        this.context = context;
        this.recordatorios = recordatorios;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_recordatorio, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String cadenaFecha = recordatorios.get(position).getFechaAviso().get(Calendar.DAY_OF_MONTH)+"/"+(recordatorios.get(position).getFechaAviso().get(Calendar.MONTH) + 1)+"/"+recordatorios.get(position).getFechaAviso().get(Calendar.YEAR);
        holder.tvFAviso.setText(cadenaFecha);
        holder.ivAlerta.setImageResource(R.drawable.icon_alert);
    }

    @Override
    public int getItemCount() {
        return recordatorios.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvFAviso;
        ImageView ivAlerta;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFAviso = itemView.findViewById(R.id.rvr_tv_fAviso);
            ivAlerta = itemView.findViewById(R.id.rvr_imageview);
        }
    }
}
