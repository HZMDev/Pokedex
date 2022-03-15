package com.example.pokedex;

import android.os.AsyncTask;
import android.util.Log;
import com.example.pokedex.Modelos_JSON.Pokemon;

import com.example.pokedex.Modelos_JSON.Pokemon;
import com.example.pokedex.Modelos_JSON.Pokemons.Pokemons;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.LinkedList;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.util.EntityUtils;

//1.- DoInBackground 2.- ProgressUpdate 3.- onPostExecute
public class ObtenerPokemonsAsyncTask extends AsyncTask<String, Void, LinkedList<Pokemon>> {

    private LinkedList<Pokemon> pokemons;
    private String url = "https://pokeapi.co/api/v2/pokemon?limit=9";
    private Pokemons pokemonsApi;

    public ObtenerPokemonsAsyncTask(LinkedList<Pokemon> pokemons, Pokemons pokemonsApi)
    {
        this.pokemons = pokemons;
        this.pokemonsApi = pokemonsApi;
    }
    public ObtenerPokemonsAsyncTask(LinkedList<Pokemon> pokemons, Pokemons pokemonsApi, String url)
    {
        this.pokemons = pokemons;
        this.pokemonsApi = pokemonsApi;

        if(url != null)
        {
            this.url = url;
        }
    }

    public Pokemons getPokemonsApi() {
        return pokemonsApi;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected LinkedList<Pokemon> doInBackground(String... strings) {

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(
                this.url);
                httpGet.setHeader("content-type", "application/json");

        try {
            HttpResponse resp = httpClient.execute(httpGet);
            String respStr = EntityUtils.toString(resp.getEntity());

            //Se crea el objeto GSON que nos permitira parsear el JSON a Objetos
            Gson gson = new GsonBuilder().create();

            pokemonsApi = gson.fromJson(respStr, Pokemons.class);

            for (int i = 0; i<pokemonsApi.getResults().size();i++) {
                //Obtenemos la url del pokemon
                String urlPokemon = pokemonsApi.getResults().get(i).getUrl();

                //Obtenemos el pokemon buscando en su url
                httpGet = new HttpGet(
                        urlPokemon);
                httpGet.setHeader("content-type", "application/json");

                resp = httpClient.execute(httpGet);
                respStr = EntityUtils.toString(resp.getEntity());

                //Tenemos el pokemon ya parseado
                Pokemon pokemon = gson.fromJson(respStr, Pokemon.class);

                //Añado a la lista
                pokemons.add(pokemon);
            }

        }
        catch(Exception ex)
        {
            Log.e("ServicioRest", "Error!", ex);
        }

        return pokemons;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(LinkedList<Pokemon> pokemons) {
        super.onPostExecute(pokemons);
    }
}

