package com.example.dylanrodbar.cointransformer;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transformer extends AppCompatActivity {

    public double dollarValue;
    public TransformationType transformationType;
    private static DecimalFormat decimalFormat = new DecimalFormat(".#####");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transformer);
        RadioButton radioButton = findViewById(R.id.radioButton1);
        radioButton.setChecked(true);
        transformationType = TransformationType.CR_USA;
        requestAPI();
        final EditText editTarget = findViewById(R.id.plainTextTransformer1);
        EditText edit = findViewById(R.id.plainTextTransformer);
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String secuence = String.valueOf(s);
                if ( !secuence.matches("")) {
                    double valueEditText = Double.parseDouble(secuence);




                    if (transformationType == TransformationType.CR_USA) {

                        colonToDollar(valueEditText);

                    }

                    else if(transformationType == TransformationType.USA_CR) {

                        dollarToColon(valueEditText);
                    }

                }

                else {

                    editTarget.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void dollarToColon(double value){

        final EditText editTarget = findViewById(R.id.plainTextTransformer1);
        double result = 0;
        result = value * dollarValue;
        String resultString = decimalFormat.format(result);

        editTarget.setText(resultString);




    }

    public void colonToDollar(double value) {
        final EditText editTarget = findViewById(R.id.plainTextTransformer1);
        double result = 0;
        result = value / dollarValue;
        String resultString = decimalFormat.format(result);
        editTarget.setText(resultString);


    }

    public void requestAPI() {
        RequestQueue queue = Volley.newRequestQueue(this);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String today = dateFormat.format(date);


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
                        JSONObject jSub2 = null;
                        JSONObject jSub3 = null;
                        String dValue;


                        //Browsing through the json
                        try {
                            //each variable is a deeper level inside the json
                            jSub = jsonObj.getJSONObject("DataSet");
                            jSub1 = jSub.getJSONObject("diffgr:diffgram");
                            jSub2 = jSub1.getJSONObject("Datos_de_INGC011_CAT_INDICADORECONOMIC");
                            jSub3 = jSub2.getJSONObject("INGC011_CAT_INDICADORECONOMIC");
                            dValue = jSub3.getString("NUM_VALOR");
                            dollarValue = Double.parseDouble(dValue);


                        } catch (JSONException e) {
                            e.printStackTrace();
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

    public void checkRadioButtons(View view){

        final EditText edit = findViewById(R.id.plainTextTransformer);
        final EditText editTarget = findViewById(R.id.plainTextTransformer);

        String valueEdit = edit.getText().toString();
        double valueEditFloat = 0;
        boolean checked = ((RadioButton) view).isChecked();
        Drawable imageCR=(Drawable)getResources().getDrawable(R.drawable.cr);
        Drawable imageUSA=(Drawable)getResources().getDrawable(R.drawable.usa);
        ImageView imageVCR = findViewById(R.id.imageCR);
        ImageView imageVUSA = findViewById(R.id.imageUSA);
        switch(view.getId()) {
            case R.id.radioButton1:
                if (checked)
                    imageVCR.setImageDrawable(imageCR);
                    imageVUSA.setImageDrawable(imageUSA);
                    transformationType = TransformationType.CR_USA;

                    if(!valueEdit.matches("")) {
                        valueEditFloat = Double.parseDouble(valueEdit);
                        colonToDollar(valueEditFloat);
                    }
                    else {
                        editTarget.setText("");
                    }
                    break;
            case R.id.radioButton2:
                if (checked)
                    imageVCR.setImageDrawable(imageUSA);
                    imageVUSA.setImageDrawable(imageCR);
                    transformationType = TransformationType.USA_CR;
                    if(!valueEdit.matches("")) {
                        valueEditFloat = Double.parseDouble(valueEdit);
                        dollarToColon(valueEditFloat);
                    }
                    else {
                        editTarget.setText("");
                    }
                    break;
        }


    }
}
