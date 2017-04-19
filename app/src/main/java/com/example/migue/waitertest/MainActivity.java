package com.example.migue.waitertest;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements AdapterView.OnItemLongClickListener{

    private ListView mListView;
    final ArrayList<Waiter> Waiters = new ArrayList<Waiter>();
    List<String> resultados = new ArrayList<>();

    Button button_Añadir,  button_Calcular;
    TextView text_Dinero;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mListView = (ListView) findViewById(R.id.ListView);
        mListView.setLongClickable(true);
        button_Añadir = (Button) findViewById(R.id.Añadir);
        button_Calcular = (Button) findViewById(R.id.Calcular);
        text_Dinero = (TextView) findViewById(R.id.Dinero);

        for(int i = 0; i < 5; i++){
            Waiter Waiter = new Waiter();
            Waiter.name = "Camarera " + i;
            Waiter.hours = 50;
            Waiter.substract = 0;

            Waiters.add(Waiter);
        }

        final WaiterAdapter adapter = new WaiterAdapter(this, Waiters);
        mListView.setAdapter(adapter);

        button_Añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Waiter Waiter = new Waiter();
                Waiter.name = "Camarera";
                Waiter.hours = 0;
                Waiter.substract = 0;
                Waiters.add(Waiter);
                adapter.notifyDataSetChanged();
                mListView.setSelection(mListView.getCount() - 1);
            }
        });

        button_Calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calculate();
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapt1, View arg1,
                                           final int pos, final long id) {

                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.popup, null);

                Waiter waiterView = (Waiter) adapt1.getItemAtPosition(pos);

                final EditText edHoras = (EditText) alertLayout.findViewById(R.id.Ed_Horas);
                final EditText edResta = (EditText) alertLayout.findViewById(R.id.Ed_Resta);

                edHoras.setText(waiterView.hours);
                edResta.setText(waiterView.substract);

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Editar");
                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(false);
                alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getBaseContext(), "Operación cancelada", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Waiter waiter = Waiters.get((int) id);
                        waiter.hours = Integer.parseInt(edHoras.getText().toString());
                        waiter.substract = Integer.parseInt(edResta.getText().toString());

                        Waiters.set((int) id, waiter);
                        adapter.notifyDataSetChanged();

                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();;

                return true;
            }
        });
    }

    void Calculate(){

        int result;
        int maxHours = 0;
        int money;

        if(text_Dinero.getText().toString().trim().equals("null") || text_Dinero.getText().toString().trim().length() <= 0){
            Toast.makeText(getApplicationContext(), "Introduce una cantidad de dinero válida", Toast.LENGTH_LONG).show();
            return;
        }
        else money = Integer.parseInt(text_Dinero.getText().toString());

        for(int i=0; i < Waiters.size(); i++){
            Waiter waiter;
            waiter = Waiters.get(i);
            if (waiter.hours >= 0) maxHours += waiter.hours;
            else Toast.makeText(this, "Hay un problema en " + waiter.name, Toast.LENGTH_LONG).show();
        }

        for(int i=0; i<Waiters.size(); i++){
            Waiter waiter;
            waiter = Waiters.get(i);
            result = ((waiter.hours * money) / maxHours) - waiter.substract;
            resultados.add(waiter.name + " se lleva " + result + "€");
        }

        Intent intent = new Intent(MainActivity.this, Tabla.class);
        intent.putExtra("lista", (ArrayList<String>) resultados);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        return false;
    }
}
