package com.example.migue.waitertest;

import android.content.DialogInterface;
import android.os.Bundle;
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
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements AdapterView.OnItemLongClickListener{

    private ListView mListView;
    Button button_Añadir;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ArrayList<Waiter> Waiters = new ArrayList<Waiter>();
        mListView = (ListView) findViewById(R.id.ListView);
        mListView.setLongClickable(true);
        button_Añadir = (Button) findViewById(R.id.Añadir);

        for(int i = 0; i < 5; i++){
            Waiter Waiter = new Waiter();
            Waiter.name = "Camarera " + i;
            Waiter.hours = "50";
            Waiter.substract = "0";

            Waiters.add(Waiter);
        }

        final WaiterAdapter adapter = new WaiterAdapter(this, Waiters);
        mListView.setAdapter(adapter);

        /*mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>adapter,View v, int position){
                ItemClicked item = adapter.getItemAtPosition(position);

                Intent intent = new Intent(Activity.this,destinationActivity.class);
                //based on item add info to intent
                startActivity(intent);
            }
        });*/

        button_Añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Waiter Waiter = new Waiter();
                Waiter.name = "Camarera";
                Waiter.hours = "0";
                Waiter.substract = "0";
                Waiters.add(Waiter);
                adapter.notifyDataSetChanged();
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
                        waiter.hours = edHoras.getText().toString();
                        waiter.substract = edResta.getText().toString();

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

    @Override
    public boolean onItemLongClick(AdapterView<?> l, View v,
                                   final int position, long id) {

        Toast.makeText(this, "long clicked pos: " + position, Toast.LENGTH_LONG).show();

        return true;
    }

    public void buttonClicked(View view) {

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
}
