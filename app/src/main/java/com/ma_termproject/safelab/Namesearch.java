package com.example.myapplication909;

import java.util.ArrayList;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Namesearch extends Activity {

    // Declare Variables
    ListView list;
    Kor_nameAdapter adapter;
    EditText editsearch;
    String[] cas;
    String[] eng;
    String[] kor;
    ArrayList<Kor_name> arraylist = new ArrayList<Kor_name>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kor_view_main);

        // Generate sample data
        cas = new String[] { "5746-86-1", "64-18-6", "7782-50-5", "24948-66-1", "643-79-8", "928-98-3", "5341-61-7", "77215-47-5", "121-44-8", "3978-81-2" };

        eng = new String[] { "(±)-Nornicotine", "Formic acid", "Japan", "1-Decen-4-yne", "Ο-Phthalaldehyde", "1,5-Pentanedithiol", "Hydrazine, hydrochloride (1:2)", "1-Imidazolidineethanol", "Triethylamine", "4-tert-Butylpyridine",};

        kor = new String[] { "(±)-노르니코틴", "개미산", "염소", "1-데센-4-인", "Ο-프탈알데하이드", "1,5-펜탄디티올", "히드라진 염산염 (1:2)", "1-이미다졸리딘에탄올", "트라이에틸아민", "4-tert-부틸피리딘" };

        // Locate the ListView in listview_main.xml
        list = (ListView) findViewById(R.id.listview);

        for (int i = 0; i < cas.length; i++)
        {
            Kor_name wp = new Kor_name(cas[i], eng[i],
                    kor[i]);
            // Binds all strings into an array
            arraylist.add(wp);
        }

        // Pass results to ListViewAdapter Class
        adapter = new Kor_nameAdapter(this, arraylist);

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

        // Locate the EditText in listview_main.xml
        editsearch = (EditText) findViewById(R.id.search);

        // Capture Text in EditText
        editsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });
    }

    // Not using options menu in this tutorial
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    public void onToggleClicked(View v) {
        boolean on = ((ToggleButton) v).isChecked();
        if (on) {
            Toast.makeText(getApplicationContext(), "대여완료", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "반납완료", Toast.LENGTH_LONG).show();
        }
    }
}
