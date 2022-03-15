package com.example.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pokedex.Modelos_JSON.Ability;
import com.example.pokedex.Modelos_JSON.Pokemon;
import com.example.pokedex.Modelos_JSON.Types;

import java.io.Serializable;

public class InformacionPokemon extends AppCompatActivity {

    private Pokemon pokemon;
    private TextView txtNombre,txtAltura, txtPeso, txtTipos, txtHabilidades;
    private ImageView imagenPokemon;
    private Button btnVolver, btnStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_pokemon);
        inicializarComponentes();

        Intent i = getIntent();

        pokemon = (Pokemon) i.getExtras().get("pokemon");

        cargarDatos();

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Stats del pokemon
                Intent i = new Intent(getApplicationContext(), StatsPokemon.class);

                i.putExtra("pokemon", (Serializable) pokemon);
                startActivity(i);
            }
        });

    }

    /***
     * Inicializa los componentes
     */
    private void inicializarComponentes(){
        txtNombre = findViewById(R.id.txtNombrePokemon);
        txtAltura = findViewById(R.id.txtAltura);
        txtPeso = findViewById(R.id.txtPeso);
        txtTipos = findViewById(R.id.txtTipo);
        imagenPokemon = findViewById(R.id.imgPokemon);
        btnVolver = findViewById(R.id.btnVolver);
        btnStats = findViewById(R.id.btnStats);
        txtHabilidades = findViewById(R.id.txtHabilidades);
    }

    /***
     * Carga los datos del pokemon a los componentes de la vista
     */
    private void cargarDatos(){
        txtNombre.setText(pokemon.getName().toUpperCase());
        txtAltura.setText(pokemon.getHeight()/10+" m");
        txtPeso.setText(pokemon.getWeight().toString()+" kg");

        for(Ability ability : pokemon.getAbilities())
        {
            txtHabilidades.setText(txtHabilidades.getText()+"\n"+ability.getAbility().getName());
        }

        for(Types tipo : pokemon.getTypes())
        {
            txtTipos.setText(txtTipos.getText()+"\n"+tipo.getType().getName());
        }

        DescargarImagenAsyncTask tImagen = new DescargarImagenAsyncTask(imagenPokemon);
        tImagen.execute(pokemon.getSprites().getOther().getOfficialArtwork().getFrontDefault());//URL de la imagen pequeña

    }
}