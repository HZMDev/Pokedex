package com.example.pokedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pokedex.Modelos_JSON.Pokemon;
import com.example.pokedex.Modelos_JSON.Pokemons.Pokemons;

import java.io.Serializable;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity{

    private RecyclerView recycler;
    private LinkedList<Pokemon> pokemons;
    private Button btnSiguiente, btnAnterior, btnQuitarFiltro, btnBuscar;
    private Pokemons pokemonsAPI;
    private ObtenerPokemonsAsyncTask tareaPokemons;
    private View.OnClickListener listenerRecyclerView;

    private EditText etAlturaPokemon;

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
        listenerRecyclerView = new View.OnClickListener() {
            public void onClick(View view) {
                //Recupero el pokemon pulsado e inicio una nueva activity con su informacion
                Pokemon pokemonSeleccionado = pokemons.get(recycler.getChildAdapterPosition(view));

                Intent i = new Intent(getApplicationContext(), InformacionPokemon.class);
                i.putExtra("pokemon", (Serializable) pokemonSeleccionado);

                startActivity(i);
            }
        };

        mostrarPokemons(pokemons);

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

                cargarDatosLista(tareaPokemons.getPokemonsApi().getNext());

                mostrarPokemons(pokemons);

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
                    return;
                }

                cargarDatosLista(tareaPokemons.getPokemonsApi().getPrevious().toString());

                mostrarPokemons(pokemons);

                btnSiguiente.setVisibility(View.VISIBLE);
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Compruebo que el edit text del filtro no este vacio
                if(etAlturaPokemon.getText().toString().equalsIgnoreCase(""))
                {
                    return;
                }

                try {
                    //Busco por nombre de pokemon y lo cargo
                    mostrarPokemons(buscarPokemonsPorAltura(etAlturaPokemon.getText().toString()));
                    btnSiguiente.setVisibility(View.INVISIBLE);

                }catch(NumberFormatException ex)
                {
                    Toast.makeText(getApplicationContext(), "El formato es con numeros y punto '.'", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnQuitarFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etAlturaPokemon.setText("");

                mostrarPokemons(pokemons);

                btnSiguiente.setVisibility(View.VISIBLE);

                Toast.makeText(getApplicationContext(), "Filtro quitado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /***
     * Se pasa la lista de pokemons al adapter, añade su listener y le manda los datos al recycler view
     * @param pokemons
     */
    private void mostrarPokemons(LinkedList<Pokemon> pokemons){
        AdapterDatos adapterDatos = new AdapterDatos(pokemons);
        adapterDatos.setOnClickListener(listenerRecyclerView);

        recycler.setAdapter(adapterDatos);
    }

    /***
     * Inicializa los componentes de la vista
     */
    private void inicializarComponentes() {
        recycler = findViewById(R.id.recycler);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnAnterior = findViewById(R.id.btnAnterior);
        btnQuitarFiltro = findViewById(R.id.btnQuitar);
        btnBuscar = findViewById(R.id.btnBuscar);
        etAlturaPokemon = findViewById(R.id.txtAlturaPokemon);

        pokemons = new LinkedList<>();
        pokemonsAPI = new Pokemons();
    }

    /***
     * Carga los pokemons de la lista
     */
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

    /***
     * Carga los pokemons de la vista, pasando la url leer los datos
     * @param moverse
     */
    private void cargarDatosLista(String moverse) {
        tareaPokemons = new ObtenerPokemonsAsyncTask(pokemons, pokemonsAPI, moverse);
        tareaPokemons.execute();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mostrarPokemons(pokemons);
    }

    /***
     * Permite buscar pokemons dada su altura, mayor o igual a ella
     * @param alturaPoke
     * @return Lista de pokemons ya filtrados por altura
     * @throws NumberFormatException
     */
    private LinkedList<Pokemon> buscarPokemonsPorAltura(String alturaPoke) throws NumberFormatException{
        LinkedList<Pokemon> pokemonsNombre = new LinkedList<>();

        alturaPoke = alturaPoke.toLowerCase();

        for (Pokemon pokemon : pokemons){
            if(pokemon.getHeight()/10 >= Double.parseDouble(alturaPoke))
            {
                pokemonsNombre.add(pokemon);
            }
        }
        return pokemonsNombre;
    }

}