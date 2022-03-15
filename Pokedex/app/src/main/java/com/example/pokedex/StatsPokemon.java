package com.example.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokedex.Modelos_JSON.Pokemon;

public class StatsPokemon extends AppCompatActivity {

    private Pokemon pokemon;
    private Button btnVolver;
    private ImageView imgPokemon, imgPokemonShiny;
    private TextView txtNombrePokemon;
    private TextView txtVida, txtVelocidad, txtAtaque, txtDefensa;
    private TextView txtAtaqueEspecial, txtDefensaEspecial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_pokemon);
        inicializarComponentes();

        cargarStats();

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imgPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), pokemon.getName().toUpperCase(), Toast.LENGTH_SHORT).show();
            }
        });

        imgPokemonShiny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), pokemon.getName().toUpperCase()+" SHINY", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /***
     * Inicializa los componentes de la vista
     */
    private void inicializarComponentes(){
        Intent i = getIntent();
        pokemon = (Pokemon) i.getExtras().get("pokemon");

        imgPokemon = findViewById(R.id.imgPokemon3D);
        imgPokemonShiny = findViewById(R.id.imgPokemonHembra3D);
        txtNombrePokemon = findViewById(R.id.txtNombrePokemon);
        txtVida = findViewById(R.id.txtVida);
        txtVelocidad = findViewById(R.id.txtVelocidad);
        txtAtaque = findViewById(R.id.txtAltura);
        txtDefensa = findViewById(R.id.txtPeso);
        txtAtaqueEspecial = findViewById(R.id.txtAtaqueEspecial);
        txtDefensaEspecial = findViewById(R.id.txtDefensaEspecial);
        btnVolver = findViewById(R.id.btnVolver);
    }

    /***
     * Carga los stats de combate del pokemon
     */
    private void cargarStats(){
        txtNombrePokemon.setText(pokemon.getName().toUpperCase());

        //Cargo los stats a los componentes de la vista
        txtVida.setText(pokemon.getStats().get(0).getBaseStat().toString());
        txtAtaque.setText(pokemon.getStats().get(1).getBaseStat().toString());
        txtDefensa.setText(pokemon.getStats().get(2).getBaseStat().toString());
        txtAtaqueEspecial.setText(pokemon.getStats().get(3).getBaseStat().toString());
        txtDefensaEspecial.setText(pokemon.getStats().get(4).getBaseStat().toString());
        txtVelocidad.setText(pokemon.getStats().get(5).getBaseStat().toString());

        //Descargo las imagenes que se van a mostrar
        DescargarImagenAsyncTask tImagen = new DescargarImagenAsyncTask(imgPokemon);
        tImagen.execute(pokemon.getSprites().getOther().getHome().getFrontDefault()); //URL de la imagen

        DescargarImagenAsyncTask tImagenHembra = new DescargarImagenAsyncTask(imgPokemonShiny);
        tImagenHembra.execute(pokemon.getSprites().getOther().getHome().getFrontShiny()); //URL de la imagen
    }
}