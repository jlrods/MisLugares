package mislugares.example.mislugares;

/**
 * Created by Jose on 11/10/2017.
 */

/**Representacion de un punto geografico
 * */
public class GeoPunto {
    private double longitud, latitud;
    /** Definicion del constructor de un punto geografico.
     * */
    public GeoPunto(double longitud, double latitud){
        this.longitud = longitud;
        this.latitud = latitud;
    }

    //Declaracion de metodos
    /** Transcribe el complejo a String.
     * @returnun string con la coordenada geografica (longitud + latitud)
     */
    public String toString(){
        return "longitud: "+longitud+"\n"+"latitud: "+latitud;
    }

    /**Calculo de la distancia en metors entre el punto actual y otro punto geografico
     *@param p El geopunto contra el cual se comparara la distancia
     */
    public double distancia(GeoPunto p){
        final double RADIO_TIERRA = 6371000;// En metros
        double dLat = Math.toRadians(latitud- p.latitud);
        double dLon = Math.toRadians(longitud - p.longitud);
        double lat1 = Math.toRadians(p.latitud);
        double lat2 = Math.toRadians(latitud);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.sin(dLon/2) * Math.sin(dLon/2) *
                        Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return  c * RADIO_TIERRA;
    }

    //Definicion de metodos Getter

    public double getLongitud() {
        return longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    //Definicion de metodos Setter

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }
}
