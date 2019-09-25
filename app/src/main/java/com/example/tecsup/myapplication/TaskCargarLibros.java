package com.example.tecsup.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.jar.JarException;

public class TaskCargarLibros extends AsyncTask<URL,Integer, String> {
    Context context;
    MainActivity actividad;

    public TaskCargarLibros(Context context) {
        this.context = context;
        actividad=(MainActivity)context;
    }

    @Override
    protected String doInBackground(URL... urls) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) urls[0].openConnection();
            conn.setReadTimeout(20000 /* milliseconds */);
            conn.setConnectTimeout(20000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            InputStream is = conn.getInputStream();
            String text = new Scanner(is).useDelimiter("\\A").next();
            return text;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject objecto = new JSONObject(s);
            JSONArray libros = objecto.getJSONArray("items");
            actividad.LlenarLibros(libros);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}