package modelo;

/**
 * Representa un vehículo de tipo Camioneta.
 * Posee capacidad de 500 kg, velocidad de 30 km/h, costo de $300/km
 * y añade 10 minutos extra por operaciones de carga y descarga.
 */
public class Camioneta extends Vehiculo {

    /**
     * Construye una camioneta con su patente asignada y los parámetros predeterminados de fábrica.
     * 
     * @param patente Patente de la camioneta.
     */
    public Camioneta(String patente) {
        // Capacidad: 500 kg, Velocidad: 30 km/h, Costo/km: $300
        super(patente, 500.0, 30.0, 300.0);
    }

    /**
     * Calcula el tiempo estimado de entrega sumando 10 minutos extra por carga y descarga.
     * 
     * @param km Distancia del trayecto en kilómetros.
     * @return Tiempo total estimado en minutos.
     */
    @Override
    public double calcularTiempoEntrega(double km) {
        return (km / getVelocidadKmH()) * 60.0 + 10.0;
    }
}
