package com.example.tecsup.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.jar.JarException;

public class MainActivity extends AppCompatActivity {
    int libros;
    TextView ejemplos;
    Button boton;
    ListView listas;
   JSONObject getJSONObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ejemplos=findViewById(R.id.ejemplos);
        boton=findViewById(R.id.button2);
        listas=findViewById(R.id.lista);





        ConnectivityManager c = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = c.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (info == null) {
            Toast.makeText(this, "Active su Wifi", Toast.LENGTH_LONG).show();
        } else if (!info.isConnected()) {
            Toast.makeText(this, "Error, no tiene internet", Toast.LENGTH_LONG).show();
        }

        final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?";
        final String QUERY_PARAM = "q";
        final String MAX_RESULTS = "maxResults";
        final String PRINT_TYPE = "printType";
        Uri builtURI = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, "pride+prejudice")
                .appendQueryParameter(MAX_RESULTS, "10")
                .appendQueryParameter(PRINT_TYPE, "books")
                .build();

        try {
            URL requestURL = new URL(builtURI.toString());
            TaskCargarLibros task = new TaskCargarLibros(this);
            task.execute(requestURL);


        } catch (Exception e) {
            Log.e("ErrorInternet", e.toString());
            e.printStackTrace();
        }
    }
    public void LlenarLibros(JSONArray libros){

        try {
            ejemplos.setText(libros.getJSONObject("volumeInfo").getJSONObject("title"));

        }catch (JarException e){
            e.printStackTrace();
        }
    }
}

