package com.example.dylanrodbar.cointransformer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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
}
