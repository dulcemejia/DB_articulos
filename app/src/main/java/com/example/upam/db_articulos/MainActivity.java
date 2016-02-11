package com.example.upam.db_articulos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText codigo, descripcion, precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        codigo=(EditText) findViewById(R.id.editText);
        descripcion=(EditText) findViewById(R.id.editText2);
        precio=(EditText) findViewById(R.id.editText3);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void add(View v){
        almacen almacen_casa = new almacen(this, "Casa", null, 1);
        SQLiteDatabase bd = almacen_casa.getWritableDatabase();
        String cod = codigo.getText().toString();
        String descri = descripcion.getText().toString();
        String pre = precio.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("codigo", cod);
        registro.put("descripcion", descri);
        registro.put("precio", pre);
        bd.insert("articulos", null, registro);
        bd.close();
        Toast.makeText(this, "Se cargaron los datos del artículo", Toast.LENGTH_SHORT).show();
        codigo.setText("");
        descripcion.setText("");
        precio.setText("");

    }

    public void delete(View v){
        almacen admin = new almacen(this, "Casa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String cod=codigo.getText().toString();
        int cant = bd.delete("articulos", "codigo=" + cod, null);
        bd.close();
        codigo.setText("");
        descripcion.setText("");
        precio.setText("");
        if (cant == 1)
            Toast.makeText(this, "Se borró el artículo con dicho código", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "No existe un artículo con dicho código", Toast.LENGTH_SHORT).show();
    }

    public void update(View v){
        almacen admin = new almacen(this,
                "Casa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String cod = codigo.getText().toString();
        String descri = descripcion.getText().toString();
        String pre = precio.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("codigo", cod);
        registro.put("descripcion", descri);
        registro.put("precio", pre);
        int cant = bd.update("articulos", registro, "codigo=" + cod, null);
        bd.close();
        if (cant == 1)
            Toast.makeText(this, "se modificaron los datos", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "no existe un artículo con el código ingresado", Toast.LENGTH_SHORT).show();
    }

    public void searchCod(View v){
        almacen admin = new almacen(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String cod = codigo.getText().toString();
        Cursor fila = bd.rawQuery(
                "select descripcion,precio from articulos where codigo=" + cod, null);
        if (fila.moveToFirst()) {
            descripcion.setText(fila.getString(0));
            precio.setText(fila.getString(1));
        } else
            Toast.makeText(this, "No existe un artículo con dicho código",
                    Toast.LENGTH_SHORT).show();
        bd.close();
    }

    public void searchDesc(View v){
        almacen admin = new almacen(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String descri = descripcion.getText().toString();
        Cursor fila = bd.rawQuery(
                "select codigo,precio from articulos where descripcion='" + descri +"'", null);
        if (fila.moveToFirst()) {
            codigo.setText(fila.getString(0));
            precio.setText(fila.getString(1));
        } else
            Toast.makeText(this, "No existe un artículo con dicha descripción",
                    Toast.LENGTH_SHORT).show();
        bd.close();
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
