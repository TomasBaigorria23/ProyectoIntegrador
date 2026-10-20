package modelo;

public class Bicicleta extends Vehiculo {

    public Bicicleta(String patente) {
        // Capacidad: 10 kg, Velocidad: 15 km/h, Costo/km: $50
        super(patente, 10.0, 15.0, 50.0);
    }

    @Override
    public double calcularTiempoEntrega(double km) {
        // Sin tiempo extra
        return (km / getVelocidadKmH()) * 60.0;
    }

    @Override
    public boolean puedeTransportar(Pedido pedido) {
        // La bicicleta NO puede transportar pedidos frágiles
        if (pedido instanceof PedidoFragil) {
            return false;
        }
        // Si no es frágil, verifica la capacidad de peso heredada
        return super.puedeTransportar(pedido);
    }
}