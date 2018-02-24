package com.example.dylanrodbar.cointransformer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.dylanrodbar.cointransformer.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonTransformerClicked(View view){
        Intent intent = new Intent(this, Transformer.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, "");
        startActivity(intent);

    }

    public void buttonHistoricalClicked(View view){
        Intent intent = new Intent(this, Hist.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, "");
        startActivity(intent);

    }
}
