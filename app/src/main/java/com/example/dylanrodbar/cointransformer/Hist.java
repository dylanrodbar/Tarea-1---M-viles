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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Hist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hist);
        addFirstRow();
        getWeeklyData();

    }



    public void addFirstRow() {


        TableLayout table = (TableLayout) findViewById(R.id.tableLayoutHist);
        TableRow tRow = new TableRow(this);
        TextView t1 = new TextView(this);
        TextView t2 = new TextView(this);
        t1.setText("Fecha");
        t2.setText("Valor en colones");

        int width = table.getWidth();

        t1.setWidth((width / 2));

        t2.setWidth((width / 2));

        t1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        t2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        tRow.addView(t1);
        tRow.addView(t2);
        table.addView(tRow);

    }

    public void addRow(String date, String dollarV) {

        TableLayout table = (TableLayout) findViewById(R.id.tableLayoutHist);
        TableRow tRow = new TableRow(this);
        TextView t1 = new TextView(this);
        TextView t2 = new TextView(this);




        t1.setText(date);
        t2.setText(dollarV);

        int width = table.getWidth();

        t1.setWidth((width / 2));

        t2.setWidth((width / 2));

        t1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        t2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        tRow.addView(t1);
        tRow.addView(t2);
        table.addView(tRow);

    }

    public void getWeeklyData() {
        RequestQueue queue = Volley.newRequestQueue(this);

        final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        final String today = dateFormat.format(date);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);  // numero de días a añadir, o restar en caso de días<0
        String pastDate = dateFormat.format(calendar.getTime());


        String url1 = "http://indicadoreseconomicos.bccr.fi.cr/indicadoreseconomicos/WebServices/wsIndicadoresEconomicos.asmx/ObtenerIndicadoresEconomicos?tcIndicador=317&tcFechaInicio=" + pastDate + "&tcFechaFinal=" + today + "&tcNombre=D&tnSubNiveles=N";



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
                        JSONObject jSub2 = null;
                        JSONArray jSub3 = null;
                        String dValue;
                        String nn = "";
                        int n = 0;

                        //Browsing through the json
                        try {
                            //each variable is a deeper level inside the json
                            jSub = jsonObj.getJSONObject("DataSet");
                            jSub1 = jSub.getJSONObject("diffgr:diffgram");
                            jSub2 = jSub1.getJSONObject("Datos_de_INGC011_CAT_INDICADORECONOMIC");

                            jSub3 = jSub2.getJSONArray("INGC011_CAT_INDICADORECONOMIC");
                            n = jSub3.length();
                            nn = String.valueOf(n);

                            //dValue = jSub3.getString("NUM_VALOR");
                            //dollarValue = Float.parseFloat(dValue);


                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                        String date = "";
                        String dollarValue = "";
                        for(int i = 0; i < n; i++) {

                            try {

                                date = jSub3.getJSONObject(i).getString("DES_FECHA");
                                dollarValue = jSub3.getJSONObject(i).getString("NUM_VALOR");
                                addRow(date, dollarValue);


                            } catch (JSONException e) {

                                e.printStackTrace();
                            }


                        }

                        //Log.d("1", response.substring(0,500));




                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });



        queue.add(stringRequest);


    }
}
