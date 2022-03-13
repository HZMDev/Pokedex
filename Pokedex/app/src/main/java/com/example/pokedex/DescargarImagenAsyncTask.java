package com.example.pokedex;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DescargarImagenAsyncTask extends AsyncTask<String, Void, Bitmap> {

    private ImageView img;

    public DescargarImagenAsyncTask(ImageView img) {
        this.img = img;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String url = strings[0];

        return descargaImagen(url);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        img.setImageBitmap(bitmap);
        //Log.v("INF", "termino la descarga");

    }

    private Bitmap descargaImagen(String baseUrl) {
        Bitmap miBitmap = null;
        try {
            //Se define el objeto URL que sirve para localizar el recurso
            URL url = new URL(baseUrl);
            //Se crea y configura un objeto de conexión HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //Vamos a usar la conexión para leer datos
            connection.setDoInput(true);
            //Realiza la petición del recurso (conecta)
            connection.connect();
            //Recibe la respuesta de la petición en un flujo InputStream
            InputStream input = connection.getInputStream();
            //Decodifica o transforma el InputStream a un objeto BitMap
            miBitmap = BitmapFactory.decodeStream(input);
            //Si quisiéramos escalar el bitmap para que no ocupe tanto en memoria
            //Bitmap miBitmapEsc = Bitmap.createScaledBitmap(miBitmap,200,200,true);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return miBitmap;
    }
}
