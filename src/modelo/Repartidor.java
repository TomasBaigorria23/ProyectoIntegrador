package modelo;

import excepciones.AlmacenamientoLlenoException;
import excepciones.CapacidadExcedidaException;

/**
 * Representa a un repartidor de la empresa CityLink.
 * Implementa las interfaces {@link Rastreable} y {@link Facturable}.
 * Administra un vehículo asignado, un arreglo interno de pedidos a su cargo
 * y calcula su comisión del 10% sobre los pedidos efectivamente entregados.
 */
public class Repartidor implements Rastreable, Facturable {

    private int legajo;
    private String nombre;
    private Vehiculo vehiculo;
    private Pedido[] pedidos;
    private int contadorPedidos;
    private static final int MAX_PEDIDOS = 20; // Capacidad máxima del arreglo por repartidor

    /**
     * Constructor por defecto que inicializa el arreglo de pedidos con capacidad 20.
     */
    public Repartidor() {
        this.pedidos = new Pedido[MAX_PEDIDOS];
        this.contadorPedidos = 0;
    }

    /**
     * Constructor completo que asigna legajo, nombre y vehículo.
     * 
     * @param legajo Identificador numérico del repartidor (mayor a 0).
     * @param nombre Nombre y apellido del repartidor.
     * @param vehiculo Vehículo con el que realizará los repartos.
     * @throws IllegalArgumentException Si algún dato es nulo o inválido.
     */
    public Repartidor(int legajo, String nombre, Vehiculo vehiculo) {
        setLegajo(legajo);
        setNombre(nombre);
        setVehiculo(vehiculo);
        this.pedidos = new Pedido[MAX_PEDIDOS];
        this.contadorPedidos = 0;
    }

    // Getters y Setters con validaciones

    /**
     * Obtiene el legajo del repartidor.
     * @return Legajo.
     */
    public int getLegajo() {
        return legajo;
    }

    /**
     * Establece el legajo del repartidor verificando que sea mayor a cero.
     * @param legajo Legajo positivo.
     * @throws IllegalArgumentException Si el legajo es &le; 0.
     */
    public void setLegajo(int legajo) {
        if (legajo <= 0) {
            throw new IllegalArgumentException("El legajo debe ser mayor a cero.");
        }
        this.legajo = legajo;
    }

    /**
     * Obtiene el nombre del repartidor.
     * @return Nombre.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del repartidor verificando que no esté vacío.
     * @param nombre Nombre del repartidor.
     * @throws IllegalArgumentException Si el nombre es nulo o vacío.
     */
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del repartidor no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    /**
     * Obtiene el vehículo actualmente asignado.
     * @return Vehiculo.
     */
    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    /**
     * Establece el vehículo asignado verificando que no sea nulo.
     * @param vehiculo Vehículo a asignar.
     * @throws IllegalArgumentException Si el vehículo es nulo.
     */
    public void setVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new IllegalArgumentException("El repartidor debe tener un vehículo asignado.");
        }
        this.vehiculo = vehiculo;
    }

    /**
     * Obtiene el arreglo con los pedidos asignados.
     * @return Arreglo de Pedido.
     */
    public Pedido[] getPedidos() {
        return pedidos;
    }

    /**
     * Obtiene la cantidad de pedidos asignados actualmente.
     * @return Cantidad de pedidos.
     */
    public int getContadorPedidos() {
        return contadorPedidos;
    }

    /**
     * Asigna un pedido verificando cupo máximo, peso y restricciones del vehículo.
     * Cambia el estado del pedido a EN_CAMINO y le asigna el vehículo.
     * 
     * @param pedido Pedido a transportar.
     * @return true si la asignación fue exitosa.
     * @throws CapacidadExcedidaException Si el vehículo no puede transportar el pedido.
     * @throws AlmacenamientoLlenoException Si el repartidor alcanzó los 20 pedidos máximos.
     * @throws IllegalArgumentException Si el pedido es nulo.
     */
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

    /**
     * Sobrecarga: Permite asignar un pedido cambiando simultáneamente el vehículo del repartidor.
     * 
     * @param pedido Pedido a transportar.
     * @param vehiculoNuevo Nuevo vehículo para el repartidor.
     * @return true si la asignación fue exitosa.
     * @throws CapacidadExcedidaException Si el nuevo vehículo no puede transportar el pedido.
     * @throws AlmacenamientoLlenoException Si el repartidor alcanzó la capacidad máxima.
     */
    public boolean asignarPedido(Pedido pedido, Vehiculo vehiculoNuevo) throws CapacidadExcedidaException, AlmacenamientoLlenoException {
        setVehiculo(vehiculoNuevo);
        return asignarPedido(pedido);
    }

    /**
     * Registra la entrega exitosa de un pedido a su cargo y actualiza su estado a ENTREGADO.
     * 
     * @param codigoPedido Código del pedido a entregar.
     * @return true si se encontró el pedido y se registró la entrega; false en caso contrario.
     */
    public boolean registrarEntrega(int codigoPedido) {
        for (int i = 0; i < contadorPedidos; i++) {
            if (pedidos[i].getCodigo() == codigoPedido && pedidos[i].getEstado().equals("EN_CAMINO")) {
                pedidos[i].cambiarEstado("ENTREGADO");
                return true;
            }
        }
        return false;
    }

    /**
     * Implementación de {@link Facturable}: Calcula la comisión del 10%
     * sobre el costo de los pedidos que fueron efectivamente entregados.
     * 
     * @return Monto de la comisión en pesos.
     */
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

    /**
     * Implementación de {@link Rastreable}: Informa si el repartidor está DISPONIBLE
     * o EN REPARTO con la cantidad de pedidos en camino.
     * 
     * @return Cadena con el estado del repartidor.
     */
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

    /**
     * Retorna una representación en texto del repartidor y su vehículo.
     * @return Cadena descriptiva.
     */
    @Override
    public String toString() {
        return "Repartidor [Legajo=" + legajo + ", Nombre=" + nombre + 
               ", Vehículo=" + vehiculo.getClass().getSimpleName() + 
               " (Patente: " + vehiculo.getPatente() + "), Pedidos asignados=" + contadorPedidos + "]";
    }
}
