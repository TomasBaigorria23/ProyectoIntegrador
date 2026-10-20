package modelo;

public class Moto extends Vehiculo {

    public Moto(String patente) {
        // Capacidad: 30 kg, Velocidad: 40 km/h, Costo/km: $120
        super(patente, 30.0, 40.0, 120.0);
    }

    @Override
    public double calcularTiempoEntrega(double km) {
        // 5 minutos extra por estacionamiento
        return (km / getVelocidadKmH()) * 60.0 + 5.0;
    }
}