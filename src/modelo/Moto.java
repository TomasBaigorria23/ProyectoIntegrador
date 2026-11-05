package modelo;

/**
 * Representa un vehículo de tipo Moto.
 * Posee capacidad de 30 kg, velocidad de 40 km/h, costo de $120/km
 * y añade 5 minutos extra por maniobras de estacionamiento.
 */
public class Moto extends Vehiculo {

    /**
     * Construye una moto con su patente asignada y los parámetros predeterminados de fábrica.
     * 
     * @param patente Patente de la moto.
     */
    public Moto(String patente) {
        // Capacidad: 30 kg, Velocidad: 40 km/h, Costo/km: $120
        super(patente, 30.0, 40.0, 120.0);
    }

    /**
     * Calcula el tiempo estimado de entrega sumando 5 minutos extra por estacionamiento.
     * 
     * @param km Distancia del trayecto en kilómetros.
     * @return Tiempo en minutos.
     */
    @Override
    public double calcularTiempoEntrega(double km) {
        return (km / getVelocidadKmH()) * 60.0 + 5.0;
    }
}
