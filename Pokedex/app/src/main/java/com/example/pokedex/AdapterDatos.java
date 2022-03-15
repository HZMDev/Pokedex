package com.example.pokedex;


import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex.Modelos_JSON.Pokemon;

import java.util.LinkedList;
import java.util.Locale;

public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolderDatos> implements View.OnClickListener {

    private LinkedList<Pokemon> listDatos;
    private View.OnClickListener listener;

    public AdapterDatos(LinkedList<Pokemon> listDatos){
        this.listDatos=listDatos;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_list_pokemon, null, false);

        view.setOnClickListener(this); //Le añado a la vista el listener del viewholder
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.asignarDatos(listDatos.get(position));
    }

    public void setOnClickListener(View.OnClickListener listener)
    {
        this.listener = listener;

    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
        {
           listener.onClick(view);
        }
    }


    //HOLDER
    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        private TextView txtNombrePoke;
        private ImageView imgPoke;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            txtNombrePoke=itemView.findViewById(R.id.txtNombrePoke);
            imgPoke = itemView.findViewById(R.id.imgPoke);

        }
        @SuppressLint("ResourceType")
        public void asignarDatos(Pokemon r) {
            txtNombrePoke.setText(r.getName().toUpperCase());

            DescargarImagenAsyncTask tImagen = new DescargarImagenAsyncTask(imgPoke);
            tImagen.execute(r.getSprites().getFrontDefault());//URL de la imagen pequeña
        }
    }

}
