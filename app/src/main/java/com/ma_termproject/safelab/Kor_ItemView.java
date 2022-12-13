package com.ma_termproject.safelab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.ma_termproject.safelab.R;

public class Kor_ItemView extends Activity {
    // Declare Variables
    TextView txtcas;
    TextView txteng;
    TextView txtkor;
    String cas;
    String eng;
    String kor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kor_view_main);
        // Retrieve data from MainActivity on item click event
        Intent i = getIntent();
        // Get the results of rank
        cas = i.getStringExtra("rank");
        // Get the results of country
        eng = i.getStringExtra("country");
        // Get the results of population
        kor = i.getStringExtra("population");

        // Locate the TextViews in koritemview.xml
        txtcas = (TextView) findViewById(R.id.cas);
        txteng = (TextView) findViewById(R.id.country);
        txtkor = (TextView) findViewById(R.id.population);

        // Load the results into the TextViews
        txtcas.setText(cas);
        txteng.setText(eng);
        txtkor.setText(kor);
    }
}
