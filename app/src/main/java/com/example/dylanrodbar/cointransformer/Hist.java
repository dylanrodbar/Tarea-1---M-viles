package com.example.dylanrodbar.cointransformer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Hist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hist);
        TableLayout table = (TableLayout) findViewById(R.id.tableLayoutHist);
        TableRow tRow = new TableRow(this);
        TextView t1 = new TextView(this);
        TextView t2 = new TextView(this);
        t1.setText("Fecha");
        t2.setText("Valor del dólar(₡)");

        int width = table.getWidth();

        t1.setWidth((width / 2));

        t2.setWidth((width / 2));

        t1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        t2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        tRow.addView(t1);
        tRow.addView(t2);
        table.addView(tRow);
        getDate();

    }

    public void buttonClicked(View view){

        TableLayout table = (TableLayout) findViewById(R.id.tableLayoutHist);
        TableRow tRow = new TableRow(this);
        TextView t1 = new TextView(this);
        TextView t2 = new TextView(this);
        t1.setText("Hola");
        t2.setText("Holo");

        int width = table.getWidth();

        t1.setWidth((width / 2));

        t2.setWidth((width / 2));

        t1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        t2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        tRow.addView(t1);
        tRow.addView(t2);
        table.addView(tRow);

    }

    public void getDate() {
        RequestQueue queue = Volley.newRequestQueue(this);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String today = dateFormat.format(date);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -30);  // numero de días a añadir, o restar en caso de días<0
        String pastDate = dateFormat.format(calendar.getTime());
        Toast toast1 = null;
        toast1 = Toast.makeText(getApplicationContext(),
                pastDate, Toast.LENGTH_LONG);
        toast1.show();

        String url1 = "http://indicadoreseconomicos.bccr.fi.cr/indicadoreseconomicos/WebServices/wsIndicadoresEconomicos.asmx/ObtenerIndicadoresEconomicos?tcIndicador=317&tcFechaInicio=" + today + "&tcFechaFinal=" + today + "&tcNombre=D&tnSubNiveles=N";



        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Converting xml to jsonObject
                        JSONObject jsonObj = null;
                        try {
                            jsonObj = XML.toJSONObject(response);
                        } catch (JSONException e) {
                            Log.e("JSON exception", e.getMessage());
                            e.printStackTrace();
                        }

                        JSONObject jSub = null;
                        JSONObject jSub1 = null;
                        JSONArray jSub2 = null;
                        JSONObject jSub3 = null;
                        String dValue;


                        //Browsing through the json
                        try {
                            //each variable is a deeper level inside the json
                            jSub = jsonObj.getJSONObject("DataSet");
                            jSub1 = jSub.getJSONObject("diffgr:diffgram");
                            jSub2 = jSub1.getJSONArray("Datos_de_INGC011_CAT_INDICADORECONOMIC");
                            //jSub3 = jSub2.getJSONObject("INGC011_CAT_INDICADORECONOMIC");
                            dValue = jSub3.getString("NUM_VALOR");
                            //dollarValue = Float.parseFloat(dValue);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        //Log.d("1", response.substring(0,500));




                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast1 = Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT);

                toast1.show();
            }
        });



        queue.add(stringRequest);


    }
}
