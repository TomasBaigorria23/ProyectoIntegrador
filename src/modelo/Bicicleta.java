package modelo;

/**
 * Representa un vehículo de tipo Bicicleta.
 * Posee capacidad de 10 kg, velocidad de 15 km/h, costo de $50/km y no añade tiempo extra.
 * Tiene la restricción de negocio de no poder transportar pedidos frágiles.
 */
public class Bicicleta extends Vehiculo {

    /**
     * Construye una bicicleta con su patente asignada y los parámetros predeterminados de fábrica.
     * 
     * @param patente Identificador de la bicicleta.
     */
    public Bicicleta(String patente) {
        // Capacidad: 10 kg, Velocidad: 15 km/h, Costo/km: $50
        super(patente, 10.0, 15.0, 50.0);
    }

    /**
     * Calcula el tiempo de entrega en minutos sin tiempo extra adicional.
     * 
     * @param km Distancia a recorrer en kilómetros.
     * @return Minutos estimados de viaje.
     */
    @Override
    public double calcularTiempoEntrega(double km) {
        return (km / getVelocidadKmH()) * 60.0;
    }

    /**
     * Evalúa si la bicicleta puede transportar el pedido.
     * Rechaza pedidos frágiles y verifica que no supere los 10 kg de peso.
     * 
     * @param pedido Pedido a evaluar.
     * @return true si el pedido no es frágil y su peso &le; 10 kg; false en caso contrario.
     */
    @Override
    public boolean puedeTransportar(Pedido pedido) {
        if (pedido instanceof PedidoFragil) {
            return false;
        }
        return super.puedeTransportar(pedido);
    }
}
