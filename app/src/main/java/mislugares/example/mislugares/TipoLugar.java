package mislugares.example.mislugares;

/**
 * Created by Jose on 12/10/2017.
 */

public enum TipoLugar {
    OTROS("Otros",R.drawable.otros),
    RESTAURANTE("Restaurante",R.drawable.restaurante),
    BAR("Bar",R.drawable.bar),
    COPAS("Copas",R.drawable.copas),
    ESPECTACULO("Espectaculo",R.drawable.espectaculos),
    HOTEL("Hotel",R.drawable.hotel),
    COMPRAS("Compras",R.drawable.compras),
    EDUCACION("Educacion",R.drawable.educacion),
    DEPORTE("Deporte",R.drawable.deporte),
    NATURALEZA("Naturaleza",R.drawable.naturaleza),
    GASOLINERA("Gasolinera",R.drawable.gasolinera);

    private final String texto;
    private final int recurso;

    TipoLugar(String texto,int recurso){
        this.texto= texto;
        this.recurso = recurso;
    }

    public String getTexto(){return this.texto;}
    public int getRecurso(){return this.recurso;}
    //Metodo que devuelve un array de strings con todos los valores del enum TipoLugar
    public static String[] getNombres(){
        String[] resultado = new String[TipoLugar.values().length];
        for(TipoLugar tipo: TipoLugar.values()){
            resultado[tipo.ordinal()] = tipo.texto;
        }
        return resultado;
    }// Fn de getNombres()
}
