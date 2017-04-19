package com.example.migue.waitertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class Tabla extends AppCompatActivity {

    List<String> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabla);

        lista = getIntent().getStringArrayListExtra("lista");

        ArrayAdapter<String> Adapter =
                new ArrayAdapter<String>(this,
                        R.layout.tabla_item,
                        R.id.Result_Item,
                        lista
                );

        ListView resultList = new ListView(this);
        setContentView(resultList);
        resultList.setAdapter(Adapter);

    }
}
