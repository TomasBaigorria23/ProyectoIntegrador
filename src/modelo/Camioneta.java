package modelo;

public class Camioneta extends Vehiculo {

    public Camioneta(String patente) {
        // Capacidad: 500 kg, Velocidad: 30 km/h, Costo/km: $300
        super(patente, 500.0, 30.0, 300.0);
    }

    @Override
    public double calcularTiempoEntrega(double km) {
        // 10 minutos extra por carga y descarga
        return (km / getVelocidadKmH()) * 60.0 + 10.0;
    }
}
