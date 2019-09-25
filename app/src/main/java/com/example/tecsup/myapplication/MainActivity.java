package com.example.tecsup.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarException;

public class MainActivity extends AppCompatActivity {
    JSONArray libros;
    EditText buscar;
    Button boton;
    ListView listas;
    List<String>titulos;
    ArrayAdapter<String>arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       buscar=findViewById(R.id.buscar);
        boton=findViewById(R.id.button2);
        listas=findViewById(R.id.lista);

        titulos=new ArrayList<>();
        arrayAdapter=new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_activated_1,titulos);
        listas.setAdapter(arrayAdapter);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager c = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = c.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                if (info == null) {
                    Toast.makeText(MainActivity.this, "Active su Wifi", Toast.LENGTH_LONG).show();
                } else if (!info.isConnected()) {
                    Toast.makeText(MainActivity.this, "Error, no tiene internet", Toast.LENGTH_LONG).show();
                }

                final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?";
                final String QUERY_PARAM = "q";
                final String MAX_RESULTS = "maxResults";
                final String PRINT_TYPE = "printType";
                Uri builtURI = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_PARAM, buscar.getText().toString())
                        .appendQueryParameter(MAX_RESULTS, "10")
                        .appendQueryParameter(PRINT_TYPE, "books")
                        .build();

                try {
                    URL requestURL = new URL(builtURI.toString());
                    TaskCargarLibros task = new TaskCargarLibros(MainActivity.this);
                    task.execute(requestURL);

                } catch (Exception e) {
                    Log.e("ErrorInternet", e.toString());
                    e.printStackTrace();
                }
            }
        });
    }
    public void LlenarLibros(JSONArray i){
        libros= i;
        for(int a=0;a<i.length();a++){
            String titulo=  null;
            try{
                titulo=i.getJSONObject(a).getJSONObject("volumeinfo").getString("title");
                titulos.add(titulo);
            }catch (JSONException e){}
            arrayAdapter.notifyDataSetChanged();
        }
    }
}

