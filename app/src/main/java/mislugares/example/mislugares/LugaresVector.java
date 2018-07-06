package mislugares.example.mislugares;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jose on 18/10/2017.
 */

public class LugaresVector implements Lugares {
    // Definicion de una lista dinamica de objetos tipo Lugar
    protected List<Lugar> vectorLugares = ejemploLugares();
    // Constructor
    public LugaresVector() {
        vectorLugares = ejemploLugares();
    }
    //Getter function para obtener un elemento de la lista dado su id
    public Lugar elemento(int id) {
        return vectorLugares.get(id);
    }
    //Funcion para incluir un nuevo lugar dentro de la lista
    public void anyade(Lugar lugar) {
        vectorLugares.add(lugar);
    }
    //Funcion para crear y aniadir un nuevo lugar a la lista
    public int nuevo() {
        Lugar lugar = new Lugar();
        vectorLugares.add(lugar);
        return vectorLugares.size()-1;
    }
    //Funcion para borrar un lugar de la lista de lugares dado su id
    public void borrar(int id) {
        vectorLugares.remove(id);
    }
    //Funcion para obtener el tamanio de la lista de lugares
    public int tamanyo() {
        return vectorLugares.size();
    }
    //Funcion para cambiar de  posicion un elemento de la lista de lugares
    public void actualiza(int id, Lugar lugar) {
        vectorLugares.set(id, lugar);
    }

    public static ArrayList<Lugar> ejemploLugares() {
        ArrayList<Lugar> lugares = new ArrayList<Lugar>();
        lugares.add(new Lugar("Escuela Politécnica Superior de Gandía",
                "C/ Paranimf, 1 46730 Gandia (SPAIN)", -0.166093, 38.995656,
                962849300, "http://www.epsg.upv.es",
                "Uno de los mejores lugares para formarse.", 3,TipoLugar.EDUCACION));
        lugares.add(new Lugar("Al de siempre",
                "P.Industrial Junto Molí Nou - 46722, Benifla (Valencia)",
                -0.190642, 38.925857, 636472405, "",
                "No te pierdas el arroz en calabaza.", 3,TipoLugar.BAR));
        lugares.add(new Lugar("androidcurso.com",
                "ciberespacio", 0.0, 0.0,
                962849300, "http://androidcurso.com",
                "Amplia tus conocimientos sobre Android.", 5,TipoLugar.EDUCACION));
        lugares.add(new Lugar("Barranco del Infierno",
                "Vía Verde del río Serpis. Villalonga (Valencia)",
                -0.295058, 38.867180, 0,
                "http://sosegaos.blogspot.com.es/2009/02/lorcha-villalonga-via-"+
                        "verde-del-rio.html","Espectacular ruta para bici o andar", 4,TipoLugar.NATURALEZA));
        lugares.add(new Lugar("La Vital",
                "Avda. de La Vital, 0 46701 Gandía (Valencia)", -0.1720092,
                38.9705949, 962881070,
                "http://www.lavital.es/", "El típico centro comercial", 2,TipoLugar.COMPRAS));
        lugares.add(new Lugar("Test",
                "", -0.1720092,
                38.9705949, 0,
                "", "", 2,TipoLugar.COMPRAS));
        return lugares;
    }
}
