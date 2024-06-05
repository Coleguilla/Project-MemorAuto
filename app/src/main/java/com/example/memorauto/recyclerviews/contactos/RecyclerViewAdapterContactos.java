package com.example.memorauto.recyclerviews.contactos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memorauto.R;
import com.example.memorauto.db.entity.Contacto;
import com.example.memorauto.db.entity.Mantenimiento;
import com.example.memorauto.recyclerviews.mantenimientos.RecyclerViewInterfaceMantenimientos;

import java.util.Calendar;
import java.util.List;

public class RecyclerViewAdapterContactos extends RecyclerView.Adapter<RecyclerViewAdapterContactos.MyViewHolder> {
    Context context;
    List<Contacto> contactos;
    private final RecyclerViewInterfaceContactos recyclerViewInterfaceContactos;

    public RecyclerViewAdapterContactos(Context context, List<Contacto> contactos, RecyclerViewInterfaceContactos recyclerViewInterfaceContactos) {
        this.context = context;
        this.contactos = contactos;
        this.recyclerViewInterfaceContactos = recyclerViewInterfaceContactos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_contacto, parent, false);
        return new MyViewHolder(view, recyclerViewInterfaceContactos);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvNombre.setText(contactos.get(position).getNombre());
        holder.tvTipo.setText(contactos.get(position).getTipo());
        holder.tvTelefono.setText(String.valueOf(contactos.get(position).getTelefono()));
        holder.tvDireccion.setText(contactos.get(position).getDireccion());
    }

    @Override
    public int getItemCount() {
        return contactos.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvTipo, tvTelefono, tvDireccion;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterfaceContactos recyclerViewInterfaceContactos) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.rvc_tv_nombre);
            tvTipo = itemView.findViewById(R.id.rvc_tv_tipo);
            tvTelefono = itemView.findViewById(R.id.rvc_tv_telefono);
            tvDireccion = itemView.findViewById(R.id.rvc_tv_direccion);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterfaceContactos != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterfaceContactos.onItemClick(pos);
                        }
                    }

                }
            });
        }
    }
}
