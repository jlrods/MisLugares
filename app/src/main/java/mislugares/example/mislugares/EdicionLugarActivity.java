package mislugares.example.mislugares;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by rodjose1 on 12/02/2018.
 */

public class EdicionLugarActivity extends AppCompatActivity {
    private long id;
    private long _id;
    private Lugar lugar;
    private EditText nombre;
    private Spinner tipo;
    private EditText direccion;
    private EditText telefono;
    private EditText url;
    private EditText comentario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edicion_lugar);
        Bundle extras = getIntent().getExtras();
        id = extras.getLong("id", -1);
        _id = extras.getLong("_id", -1);
        if (_id!=-1) {
            lugar = MainActivity.lugares.elemento((int) _id);
        } else {
            lugar = MainActivity.adaptador.lugarPosicion((int) id);
        }
        //lugar = MainActivity.lugares.elemento((int) id);
        //lugar= MainActivity.adaptador.lugarPosicion((int) id);
        nombre = (EditText) findViewById(R.id.nombre);
        nombre.setText(lugar.getNombre());
        tipo = (Spinner) findViewById(R.id.tipo);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,TipoLugar.getNombres());
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipo.setAdapter(adaptador);
        tipo.setSelection(lugar.getTipo().ordinal());
        direccion = (EditText) findViewById(R.id.direccion);
        direccion.setText(lugar.getDireccion());
        telefono = (EditText) findViewById(R.id.telefono);
        telefono.setText(Integer.toString(lugar.getTelefono()));
        url = (EditText) findViewById(R.id.url);
        url.setText(lugar.getUrl());
        comentario = (EditText) findViewById(R.id.comentario);
        comentario.setText(lugar.getComentario());
    }// Fin de OnCreate method

    //Metodo para la asignacion de una barra de menu dentro de la vista
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edicion_lugar, menu);
        return true;
    }// Find fe OnCreateOptionsMenu

    //Metodo para determinar la opcion de menu seleccionada por el usuario y definir un accion a ejecutar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = false;
        switch (item.getItemId()) {
            case R.id.accion_cancelar:
                if (_id!=-1) {
                    MainActivity.lugares.borrar((int) _id);
                }
                finish();
                break;
            case R.id.accion_guardar:
                lugar.setNombre(nombre.getText().toString());
                lugar.setTipo(TipoLugar.values()[tipo.getSelectedItemPosition()]);
                lugar.setDireccion(direccion.getText().toString());
                lugar.setUrl(url.getText().toString());
                lugar.setTelefono(Integer.parseInt(telefono.getText().toString()));
                lugar.setComentario(comentario.getText().toString());
                //MainActivity.lugares.actualiza((int) id,lugar);
                //int _id = MainActivity.adaptador.idPosicion((int) id);
                if (_id==-1) {
                    _id = MainActivity.adaptador.idPosicion((int) id);
                }
                MainActivity.lugares.actualiza((int) _id, lugar);
                MainActivity.adaptador.setCursor(MainActivity.lugares.extraeCursor());
                if (id!=-1) {
                    MainActivity.adaptador.notifyItemChanged((int) id);
                } else {
                    MainActivity.adaptador.notifyDataSetChanged();
                }
                //MainActivity.lugares.actualiza(_id,lugar);
                //MainActivity.adaptador.setCursor(MainActivity.lugares.extraeCursor());
                //MainActivity.adaptador.notifyDataSetChanged();
                result = true;
                //lanzarVistaLugar(null,id);
                finish();
                break;
            default:
                result = super.onOptionsItemSelected(item);
                break;
        }
        return result;
    }// Fin de onOptionsItemSelected method

    //Lanzador de Actividad VistaLugar
    public void lanzarVistaLugar(View view,Long id){
        Intent i= new Intent(this,VistaLugarActivity.class);
        i.putExtra("id",id);
        startActivity(i);
    }
}// Fin de la clase EdicionLugarActivity
