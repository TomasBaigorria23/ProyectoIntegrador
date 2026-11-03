package modelo;

import excepciones.AlmacenamientoLlenoException;
import excepciones.CapacidadExcedidaException;

public class Repartidor implements Rastreable, Facturable {

    private int legajo;
    private String nombre;
    private Vehiculo vehiculo;
    private Pedido[] pedidos;
    private int contadorPedidos;
    private static final int MAX_PEDIDOS = 20; // Capacidad máxima del arreglo por repartidor

    public Repartidor() {
        this.pedidos = new Pedido[MAX_PEDIDOS];
        this.contadorPedidos = 0;
    }

    public Repartidor(int legajo, String nombre, Vehiculo vehiculo) {
        setLegajo(legajo);
        setNombre(nombre);
        setVehiculo(vehiculo);
        this.pedidos = new Pedido[MAX_PEDIDOS];
        this.contadorPedidos = 0;
    }

    // Getters y Setters con validaciones
    public int getLegajo() {
        return legajo;
    }

    public void setLegajo(int legajo) {
        if (legajo <= 0) {
            throw new IllegalArgumentException("El legajo debe ser mayor a cero.");
        }
        this.legajo = legajo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del repartidor no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new IllegalArgumentException("El repartidor debe tener un vehículo asignado.");
        }
        this.vehiculo = vehiculo;
    }

    public Pedido[] getPedidos() {
        return pedidos;
    }

    public int getContadorPedidos() {
        return contadorPedidos;
    }

    // 1. Asignar pedido verificando capacidad y restricciones
    public boolean asignarPedido(Pedido pedido) throws CapacidadExcedidaException, AlmacenamientoLlenoException {
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo.");
        }
        if (contadorPedidos >= MAX_PEDIDOS) {
            throw new AlmacenamientoLlenoException("El repartidor alcanzó la capacidad máxima de pedidos.");
        }
        if (!vehiculo.puedeTransportar(pedido)) {
            throw new CapacidadExcedidaException("El vehículo no puede transportar este pedido (supera capacidad o restricción de tipo).");
        }

        pedidos[contadorPedidos] = pedido;
        contadorPedidos++;
        pedido.setVehiculoAsignado(this.vehiculo);
        pedido.cambiarEstado("EN_CAMINO");
        return true;
    }

    // 2. Sobrecarga del método asignarPedido (pauta del proyecto)
    public boolean asignarPedido(Pedido pedido, Vehiculo vehiculoNuevo) throws CapacidadExcedidaException, AlmacenamientoLlenoException {
        setVehiculo(vehiculoNuevo);
        return asignarPedido(pedido);
    }

    // 3. Registrar la entrega de un pedido a su cargo
    public boolean registrarEntrega(int codigoPedido) {
        for (int i = 0; i < contadorPedidos; i++) {
            if (pedidos[i].getCodigo() == codigoPedido && pedidos[i].getEstado().equals("EN_CAMINO")) {
                pedidos[i].cambiarEstado("ENTREGADO");
                return true;
            }
        }
        return false;
    }

    // Método de la interfaz Facturable: Comisión del 10% sobre los pedidos entregados
    @Override
    public double calcularImporte() {
        double totalEntregado = 0;
        for (int i = 0; i < contadorPedidos; i++) {
            if (pedidos[i].getEstado().equals("ENTREGADO")) {
                totalEntregado += pedidos[i].calcularCosto(vehiculo);
            }
        }
        return totalEntregado * 0.10;
    }

    // Método de la interfaz Rastreable
    @Override
    public String obtenerEstadoActual() {
        int enCamino = 0;
        for (int i = 0; i < contadorPedidos; i++) {
            if (pedidos[i].getEstado().equals("EN_CAMINO")) {
                enCamino++;
            }
        }
        if (enCamino > 0) {
            return "EN REPARTO (" + enCamino + " pedido/s en camino)";
        }
        return "DISPONIBLE";
    }

    @Override
    public String toString() {
        return "Repartidor [Legajo=" + legajo + ", Nombre=" + nombre + 
               ", Vehículo=" + vehiculo.getClass().getSimpleName() + 
               " (Patente: " + vehiculo.getPatente() + "), Pedidos asignados=" + contadorPedidos + "]";
    }
}