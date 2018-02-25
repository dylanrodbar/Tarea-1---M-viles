package com.example.dylanrodbar.cointransformer;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

public class Transformer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transformer);
        RadioButton radioButton = findViewById(R.id.radioButton1);
        radioButton.setChecked(true);
    }

    public void checkRadioButtons(View view){

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
                    break;
            case R.id.radioButton2:
                if (checked)
                    imageVCR.setImageDrawable(imageUSA);
                    imageVUSA.setImageDrawable(imageCR);
                    break;
        }

    }
}
