package mislugares.example.mislugares;

/**
 * Created by Jose on 12/10/2017.
 */

public interface Lugares {
    final static String TAG = "MisLugares";
    static GeoPunto posicionActual = new GeoPunto(0,0);
    Lugar elemento(int id);// Devuelvel el elemento dado su id
    void anyade(Lugar lugar);//Aniade un nuvevo lugar a la lista
    int  nuevo();//aniade un elemento en blanco y devuelve su id
    void borrar(int id);//Elimina un elemento de la lista dado su id
    int tamanyo();//Devuelve el numero de lugares almacenados en la lista
    void actualiza(int id, Lugar lugar);//Reemplaza un elemento
}
