package com.example.pokedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pokedex.Modelos_JSON.Pokemon;
import com.example.pokedex.Modelos_JSON.Pokemons.Pokemons;

import java.io.Serializable;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity{

    private RecyclerView recycler;
    private LinkedList<Pokemon> pokemons;
    private Button btnSiguiente, btnAnterior;
    private Pokemons pokemonsAPI;
    private ObtenerPokemonsAsyncTask tareaPokemons;
    private View.OnClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarComponentes();

        recycler.setLayoutManager(
                new GridLayoutManager(this, 3));

        //Le añado un separador entre items para que se distinga uno de otro
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycler.getContext(), 1);
        recycler.addItemDecoration(dividerItemDecoration);

        cargarDatosLista();
        AdapterDatos adapterDatos = new AdapterDatos(pokemons);

         listener = new View.OnClickListener() {
            public void onClick(View view) {
                //Recupero el pokemon pulsado e inicio una nueva activity con su informacion
                Pokemon pokemonSeleccionado = pokemons.get(recycler.getChildAdapterPosition(view));

                Intent i = new Intent(getApplicationContext(), InformacionPokemon.class);
                i.putExtra("pokemon", (Serializable) pokemonSeleccionado);

                startActivity(i);
            }
        };
        adapterDatos.setOnClickListener(listener);

        recycler.setAdapter(adapterDatos);

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Carga los siguiente 9 pokemons

                pokemons = new LinkedList<>();

                //Si no hay siguiente se pone invisible el boton
                if (tareaPokemons.getPokemonsApi() == null) {
                    btnSiguiente.setVisibility(View.INVISIBLE);
                    return;
                }

                tareaPokemons = new ObtenerPokemonsAsyncTask(pokemons, pokemonsAPI, tareaPokemons.getPokemonsApi().getNext());
                tareaPokemons.execute();

                AdapterDatos adapterDatos = new AdapterDatos(pokemons);

                adapterDatos.setOnClickListener(listener);

                recycler.setAdapter(adapterDatos);

                btnAnterior.setVisibility(View.VISIBLE);

            }
        });

        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Carga los 9 pokemons anteriores
                pokemons = new LinkedList<>();

                //Si no hay anterior, se pone invisible el boton
                if (tareaPokemons.getPokemonsApi().getPrevious() == null){
                    btnAnterior.setVisibility(View.INVISIBLE);
                    tareaPokemons = new ObtenerPokemonsAsyncTask(pokemons, pokemonsAPI, tareaPokemons.getPokemonsApi().getNext());
                    tareaPokemons.execute();
                    return;
                }

                tareaPokemons = new ObtenerPokemonsAsyncTask(pokemons, pokemonsAPI, tareaPokemons.getPokemonsApi().getPrevious().toString());
                tareaPokemons.execute();

                AdapterDatos adapterDatos = new AdapterDatos(pokemons);

                adapterDatos.setOnClickListener(listener);

                recycler.setAdapter(adapterDatos);

                btnSiguiente.setVisibility(View.VISIBLE);
            }
        });
    }

    private void inicializarComponentes() {
        recycler = findViewById(R.id.recycler);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnAnterior = findViewById(R.id.btnAnterior);

        pokemons = new LinkedList<>();
        pokemonsAPI = new Pokemons();
    }

    private void cargarDatosLista() {
        tareaPokemons = new ObtenerPokemonsAsyncTask(pokemons, pokemonsAPI);
        tareaPokemons.execute();
        try {
            Toast.makeText(getApplicationContext(), "Espere mientras se carga la pokedex ...", Toast.LENGTH_LONG).show();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}