package mislugares.example.mislugares;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by rodjose1 on 11/02/2018.
 */

public class VistaLugarActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private long id;
    private Lugar lugar;
    private ImageView imageView;
    private Uri uriFoto;
    final static int RESULTADO_EDITAR = 1;
    final static int RESULTADO_GALERIA= 2;
    final static int RESULTADO_FOTO= 3;
    private static final int SOLICITUD_PERMISO_READ_EXTERNAL_STORAGE = 0;
    private static final int SOLICITUD_PERMISO_WRITE_EXTERNAL_STORAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_lugar);
        Bundle extras = getIntent().getExtras();
        id = extras.getLong("id", -1);
        actualizarVistas();
        //actualizaLugar();
    }// Fin de OnCreate method

    //Metodo para la asignacion de una barra de menu dentro de la vista
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vista_lugar, menu);
        return true;
    }// Find fe OnCreateOptionsMenu

    //Metodo para determinar la opcion de menu seleccionada por el usuario y definir un accion a ejecutar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = false;
        switch (item.getItemId()) {
            case R.id.accion_compartir:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,
                        lugar.getNombre() + " - " + lugar.getUrl());
                startActivity(intent);
                result = true;
                break;
            case R.id.accion_llegar:
                verMapa(null);
                result = true;
                break;
            case R.id.accion_editar:
                this.lanzarEdicionLugar(null);
                result = true;
                break;
            case R.id.accion_borrar:
                int _id = MainActivity.adaptador.idPosicion((int) id);
                this.borrarLugar((int)_id);
                result= true;
                break;
            default:
                result = super.onOptionsItemSelected(item);
                break;
        }
        return result;
    }// Fin de onOptionsItemSelected method

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent
            data) {
        if (requestCode == RESULTADO_EDITAR) {
            findViewById(R.id.scrollView1).invalidate();
            actualizarVistas();}
        else if (requestCode == RESULTADO_GALERIA) {
            if (resultCode == Activity.RESULT_OK) {
                lugar.setFoto(data.getDataString());
                ponerFoto(imageView, lugar.getFoto());
                actualizaLugar();
            } else {
                Toast.makeText(this, "Foto no cargada",Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == RESULTADO_FOTO) {
            if (resultCode == Activity.RESULT_OK
                    && lugar!=null && uriFoto!=null) {
                lugar.setFoto(uriFoto.toString());
                ponerFoto(imageView, lugar.getFoto());
                actualizaLugar();
            } else {
                Toast.makeText(this, "Error en captura", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void borrarLugar(final int id){
        new AlertDialog.Builder(this)
                .setTitle("Borrado de lugar")
                .setMessage("Estas seguro que quires eliminar este lugar?")
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.lugares.borrar((int) id);
                        MainActivity.adaptador.setCursor(
                                MainActivity.lugares.extraeCursor());
                        MainActivity.adaptador.notifyDataSetChanged();
                        finish();
                    }
                })
                .setNegativeButton("Cancelar",null)
                .show();
    }// Fin de metodo borrarLugar

    //Metodo para lanzar EdicionLugarActivity
    private void lanzarEdicionLugar(View view){
        Intent i = new Intent(this, EdicionLugarActivity.class);
        i.putExtra("id",id);
        //startActivity(i);
        startActivityForResult(i, RESULTADO_EDITAR);
    }// Fin de lanzarEdicionLugar

    //Metodo para actualizar los datos de una vista
    public void actualizarVistas(){
        //lugar = MainActivity.lugares.elemento((int) id);
        lugar= MainActivity.adaptador.lugarPosicion((int) id);
        TextView nombre = (TextView) findViewById(R.id.nombre);
        nombre.setText(lugar.getNombre());
        if(lugar.getTipo().getTexto().isEmpty()){
            findViewById(R.id.barra_tipo).setVisibility(View.GONE);
        }else{
            TextView tipo = (TextView) findViewById(R.id.tipo);
            tipo.setText(lugar.getTipo().getTexto());
            ImageView logo_tipo = (ImageView) findViewById(R.id.logo_tipo);
            logo_tipo.setImageResource(lugar.getTipo().getRecurso());
        }
        if(lugar.getDireccion().isEmpty()){
            findViewById(R.id.barra_direccion).setVisibility(View.GONE);
        }else{
            TextView direccion = (TextView) findViewById(R.id.direccion);
            direccion.setText(lugar.getDireccion());
        }
        if (lugar.getTelefono() == 0) {
            findViewById(R.id.barra_telefono).setVisibility(View.GONE);
        } else {
            TextView telefono = (TextView) findViewById(R.id.telefono);
            telefono.setText(Integer.toString(lugar.getTelefono()));
        }
        if (lugar.getUrl().isEmpty()) {
            findViewById(R.id.barra_url).setVisibility(View.GONE);
        } else {
            TextView url = (TextView) findViewById(R.id.url);
            url.setText(lugar.getUrl());
        }
        if (lugar.getComentario().isEmpty()) {
            findViewById(R.id.barra_comentarios).setVisibility(View.GONE);
        } else {
            TextView comentario = (TextView) findViewById(R.id.comentario);
            comentario.setText(lugar.getComentario());
        }
        if (lugar.getFecha() == 0) {
            findViewById(R.id.barra_fecha).setVisibility(View.GONE);
        } else {
            TextView fecha = (TextView) findViewById(R.id.fecha);
            fecha.setText(DateFormat.getDateInstance().format(
                    new Date(lugar.getFecha())));
            TextView hora = (TextView) findViewById(R.id.hora);
            hora.setText(DateFormat.getTimeInstance().format(
                    new Date(lugar.getFecha())));
        }
        RatingBar valoracion = (RatingBar) findViewById(R.id.valoracion);
        valoracion.setRating(lugar.getValoracion());
        valoracion.setOnRatingBarChangeListener(

                new RatingBar.OnRatingBarChangeListener() {
                    @Override public void onRatingChanged(RatingBar ratingBar,
                                                          float valor, boolean fromUser) {
                        lugar.setValoracion(valor);
                        actualizaLugar();
                    }
                });
        ImageView iconoHora = (ImageView) findViewById(R.id.icono_hora);
        iconoHora.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                cambiarHora();
            }
        });
        ImageView iconoFecha = (ImageView) findViewById(R.id.logo_fecha);
        iconoFecha.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                cambiarFecha();
            }
        });
        imageView = (ImageView) findViewById(R.id.foto);
        ponerFoto(imageView, lugar.getFoto());
    }// Fin del metodo actualizar vista

    //Metodo para lanzar una intencion implicita que lanza un mapa para ubicar un lugar
    public void verMapa(View view) {
        Uri uri;
        double lat = lugar.getPosicion().getLatitud();
        double lon = lugar.getPosicion().getLongitud();
        if (lat != 0 || lon != 0) {
            uri = Uri.parse("geo:" + lat + "," + lon);
        } else {
            uri = Uri.parse("geo:0,0?q=" + lugar.getDireccion());
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    //Metodo para lanzar una intencion implicita que llama al dialer
    public void llamadaTelefono(View view) {
        startActivity(new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:" + lugar.getTelefono())));
    }
    // Metodo para lanzar una intencion implicita que abre una pagina web en un browser
    public void pgWeb(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse(lugar.getUrl())));
    }

    //Metodo para lanzar una intencion explicita que llama a la galeria de fotos del telefono
    public void galeria(View view) {
        /*Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULTADO_GALERIA);*/
        Intent intent;
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT < 19){
                Log.i("Build.VERSION", "< 19");
                intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, RESULTADO_GALERIA);
            } else {
                Log.i("Build.VERSION", ">= 19");
                intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, RESULTADO_GALERIA);
            }
        }else{
            solicitarPermiso(Manifest.permission.READ_EXTERNAL_STORAGE, "Sin el permiso"+
                            " leer en memoria no es posible asignar una foto al lugar seleccionado.",
                    SOLICITUD_PERMISO_READ_EXTERNAL_STORAGE, this);
        }
    }

    //Metodo para colocar una foto tomada desde la camara
    public void tomarFoto(View view) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            //Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        /*uriFoto = Uri.fromFile(new File(
                Environment.getExternalStorageDirectory() + File.separator
                        + "img_" + (System.currentTimeMillis() / 1000) + ".jpg"));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFoto);
        startActivityForResult(intent, RESULTADO_FOTO);*/
            if (intent.resolveActivity(getPackageManager()) != null) {
                ContentValues values = new ContentValues(1);
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
                uriFoto = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFoto);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(intent, RESULTADO_FOTO);
            } else {
                Toast.makeText(this, "Error en captura", Toast.LENGTH_LONG).show();
            }
        }else{
            solicitarPermiso(Manifest.permission.WRITE_EXTERNAL_STORAGE, "Sin el permiso"+
                            " escribir en memoria no es posible tomar una foto y asinarla al lugar seleccionado.",
                    SOLICITUD_PERMISO_WRITE_EXTERNAL_STORAGE, this);
        }

    }

    public static void solicitarPermiso(final String permiso, String justificacion,
                                        final int requestCode, final Activity actividad) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(actividad,
                permiso)){
            new AlertDialog.Builder(actividad)
                    .setTitle("Solicitud de permiso")
                    .setMessage(justificacion)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            ActivityCompat.requestPermissions(actividad,
                                    new String[]{permiso}, requestCode);
                        }})
                    .show();
        } else {
            ActivityCompat.requestPermissions(actividad,
                    new String[]{permiso}, requestCode);
        }
    }

    @Override public void onRequestPermissionsResult(int requestCode,
                                                     String[] permissions, int[] grantResults) {
        if (requestCode == SOLICITUD_PERMISO_READ_EXTERNAL_STORAGE) {
            if (grantResults.length== 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                galeria(null);
            } else {
                Toast.makeText(this, "Sin el permiso, no puedo realizar la " +
                        "acción", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == SOLICITUD_PERMISO_WRITE_EXTERNAL_STORAGE){
            if (grantResults.length== 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                tomarFoto(null);
            } else {
                Toast.makeText(this, "Sin el permiso, no se puede tomar la foto.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Metodo para colocar la foto regresada a la actividad y ubicarla en el ImageView
    protected void ponerFoto(ImageView imageView, String uri) {
        if (uri != null) {
            //imageView.setImageURI(Uri.parse(uri));
            imageView.setImageBitmap(reduceBitmap(this, uri, 1024,   1024));
        } else{
            imageView.setImageBitmap(null);
        }
    }


    //Metodo para eliminar un foto
    public void eliminarFoto(View view) {

        new AlertDialog.Builder(this)
                .setTitle("Borrado de foto")
                .setMessage("Estas seguro que quires eliminar esta foto?")
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        lugar.setFoto(null);
                        ponerFoto(imageView, null);
                        actualizaLugar();
                    }
                })
                .setNegativeButton("Cancelar",null)
                .show();
    }

    //Metodo para reducir la resolucion de una imagen
    public static Bitmap reduceBitmap(Context contexto, String uri,
                                      int maxAncho, int maxAlto) {
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(contexto.getContentResolver()
                    .openInputStream(Uri.parse(uri)), null, options);
            options.inSampleSize = (int) Math.max(
                    Math.ceil(options.outWidth / maxAncho),
                    Math.ceil(options.outHeight / maxAlto));
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(contexto.getContentResolver()
                    .openInputStream(Uri.parse(uri)), null, options);
        } catch (FileNotFoundException e) {
            Toast.makeText(contexto, "Fichero/recurso no encontrado",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return null;
        }
    }

    void actualizaLugar(){
        int _id = MainActivity.adaptador.idPosicion((int) id);
        MainActivity.lugares.actualiza(_id, lugar);
        MainActivity.adaptador.setCursor(MainActivity.lugares.extraeCursor());
        MainActivity.adaptador.notifyItemChanged((int) id);
    }

    @Override
    public void onTimeSet(TimePicker vista, int hora, int minuto) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTimeInMillis(lugar.getFecha());
        calendario.set(Calendar.HOUR_OF_DAY, hora);
        calendario.set(Calendar.MINUTE, minuto);
        lugar.setFecha(calendario.getTimeInMillis());
        actualizaLugar();
        TextView tHora = (TextView) findViewById(R.id.hora);
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm",
                java.util.Locale.getDefault());
        tHora.setText(formato.format(new Date(lugar.getFecha())));
    }

    public void cambiarHora() {
        DialogoSelectorHora dialogo = new DialogoSelectorHora();
        dialogo.setOnTimeSetListener(this);
        Bundle args = new Bundle();
        args.putLong("fecha", lugar.getFecha());
        dialogo.setArguments(args);
        dialogo.show(this.getSupportFragmentManager(), "selectorHora");
    }

    @Override
    public void onDateSet(DatePicker view, int anio, int mes, int dia) {
            Calendar calendario = Calendar.getInstance();
            calendario.setTimeInMillis(lugar.getFecha());
            calendario.set(Calendar.YEAR, anio);
            calendario.set(Calendar.MONTH, mes);
            calendario.set(Calendar.DAY_OF_MONTH, dia);
            lugar.setFecha(calendario.getTimeInMillis());
            actualizaLugar();
            TextView tFecha = (TextView) findViewById(R.id.fecha);
            DateFormat formato = DateFormat.getDateInstance();
            tFecha.setText(formato.format(new Date(lugar.getFecha())));
        }

    public void cambiarFecha() {
        DialogoSelectorFecha dialogo = new DialogoSelectorFecha();
        dialogo.setOnDateSetListener(this);
        Bundle args = new Bundle();
        args.putLong("fecha", lugar.getFecha());
        dialogo.setArguments(args);
        dialogo.show(this.getSupportFragmentManager(), "selectorFecha");
    }

}
