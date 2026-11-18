package modelo;

import java.time.LocalDateTime;
import excepciones.EstadoInvalidoException;

/**
 * Clase abstracta raíz para la jerarquía de pedidos del sistema CityLink.
 * Implementa las interfaces {@link Rastreable} y {@link Facturable}.
 * Administra el ciclo de vida del pedido, sus transiciones de estado, cliente asociado
 * y delega el cálculo exacto del costo a sus subclases especializadas.
 */
public abstract class Pedido implements Rastreable, Facturable {
    
    // Atributos privados
    private int codigo;
    private Cliente cliente;
    private String destino;
    private double pesokg;
    private double distanciakm;
    private LocalDateTime fechaHora;
    private String estado;
    private Vehiculo vehiculoAsignado;

    /**
     * Constructor vacío que inicializa el estado en "PENDIENTE" y toma la fecha actual.
     */
    public Pedido() {
        this.estado = "PENDIENTE";
        this.fechaHora = LocalDateTime.now();
    }

    /**
     * Constructor completo para crear un pedido con validaciones.
     * 
     * @param codigo Código identificador único del pedido (mayor a 0).
     * @param cliente Objeto Cliente asociado al pedido (no nulo).
     * @param destino Dirección o punto de entrega en la ciudad (no vacío).
     * @param pesokg Peso de la carga en kilogramos (mayor a 0).
     * @param distanciakm Distancia del trayecto en kilómetros (mayor a 0).
     * @throws IllegalArgumentException Si algún parámetro no cumple las validaciones de negocio.
     */
    public Pedido(int codigo, Cliente cliente, String destino, double pesokg, double distanciakm) {
        setCodigo(codigo);
        setCliente(cliente);
        setDestino(destino);
        setPesokg(pesokg);
        setDistanciakm(distanciakm);
        this.estado = "PENDIENTE"; // Todo pedido ingresa en estado pendiente
        this.fechaHora = LocalDateTime.now(); // Toma la fecha y hora del sistema
    }

    // Getters y Setters con validaciones

    /**
     * Obtiene el código del pedido.
     * @return Código numérico.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Establece el código del pedido verificando que sea mayor a cero.
     * @param codigo Código positivo.
     * @throws IllegalArgumentException Si el código es &le; 0.
     */
    public void setCodigo(int codigo) {
        if (codigo <= 0) throw new IllegalArgumentException("El código debe ser mayor a cero.");
        this.codigo = codigo;
    }

    /**
     * Obtiene el cliente asociado al pedido.
     * @return Objeto Cliente.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Asocia un cliente al pedido.
     * @param cliente Cliente no nulo.
     * @throws IllegalArgumentException Si el cliente es nulo.
     */
    public void setCliente(Cliente cliente) {
        if (cliente == null) throw new IllegalArgumentException("El cliente no puede ser nulo.");
        this.cliente = cliente;
    }

    /**
     * Obtiene la dirección de destino.
     * @return Destino del pedido.
     */
    public String getDestino() {
        return destino;
    }

    /**
     * Establece el destino de entrega verificando que no esté vacío.
     * @param destino Dirección de entrega.
     * @throws IllegalArgumentException Si el destino es nulo o vacío.
     */
    public void setDestino(String destino) {
        if (destino == null || destino.trim().isEmpty()) {
            throw new IllegalArgumentException("El destino no puede estar vacío.");
        }
        this.destino = destino;
    }

    /**
     * Obtiene el peso del paquete en kg.
     * @return Peso en kg.
     */
    public double getPesokg() {
        return pesokg;
    }

    /**
     * Establece el peso verificando que sea mayor a cero.
     * @param pesokg Peso en kg.
     * @throws IllegalArgumentException Si el peso es &le; 0.
     */
    public void setPesokg(double pesokg) {
        if (pesokg <= 0) throw new IllegalArgumentException("El peso debe ser mayor a cero.");
        this.pesokg = pesokg;
    }

    /**
     * Obtiene la distancia en kilómetros hacia el destino.
     * @return Distancia en km.
     */
    public double getDistanciakm() {
        return distanciakm;
    }

    /**
     * Establece la distancia verificando que sea mayor a cero.
     * @param distanciakm Distancia en km.
     * @throws IllegalArgumentException Si la distancia es &le; 0.
     */
    public void setDistanciakm(double distanciakm) {
        if (distanciakm <= 0) throw new IllegalArgumentException("La distancia debe ser mayor a cero.");
        this.distanciakm = distanciakm;
    }

    /**
     * Obtiene la fecha y hora de registro del pedido.
     * @return LocalDateTime de creación.
     */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    /**
     * Obtiene el estado actual del pedido en texto.
     * @return Estado del pedido (PENDIENTE, EN_CAMINO, ENTREGADO, CANCELADO).
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece o restaura directamente el estado del pedido.
     * Utilizado para la reversión de transiciones de estado desde la pila de deshacer (Undo).
     * 
     * @param estado Estado a restaurar (PENDIENTE, EN_CAMINO, ENTREGADO, CANCELADO).
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Implementación de {@link Rastreable}: retorna el estado actual del envío.
     * @return Cadena que representa el estado actual.
     */
    @Override
    public String obtenerEstadoActual() {
        return this.estado;
    }

    /**
     * Método abstracto que calcula el costo total del envío aplicando recargos según el tipo de pedido.
     * 
     * @param vehiculo Vehículo con el cual se realizará el transporte.
     * @return Costo del envío en pesos.
     */
    public abstract double calcularCosto(Vehiculo vehiculo);

    /**
     * Obtiene el vehículo actualmente asignado para el transporte.
     * @return Vehiculo asignado, o null si aún no se asignó.
     */
    public Vehiculo getVehiculoAsignado() {
        return vehiculoAsignado;
    }

    /**
     * Asigna un vehículo al pedido para su transporte.
     * @param vehiculoAsignado Vehículo responsable del envío.
     */
    public void setVehiculoAsignado(Vehiculo vehiculoAsignado) {
        this.vehiculoAsignado = vehiculoAsignado;
    }

    /**
     * Implementación de {@link Facturable}: calcula el importe a facturar por el envío.
     * Si ya posee un vehículo asignado, ejecuta el cálculo polimórfico del costo.
     * 
     * @return Importe facturable del envío.
     */
    @Override
    public double calcularImporte() {
        if (this.vehiculoAsignado != null) {
            return calcularCosto(this.vehiculoAsignado);
        }
        return 0.0;
    }

    /**
     * Cambia el estado del pedido validando rigurosamente las transiciones permitidas por la empresa:
     * <ul>
     *   <li>PENDIENTE &rarr; EN_CAMINO o CANCELADO</li>
     *   <li>EN_CAMINO &rarr; ENTREGADO</li>
     * </ul>
     * 
     * @param nuevoEstado Estado al que se desea transicionar.
     * @throws EstadoInvalidoException Si la transición solicitada no está permitida.
     */
    public void cambiarEstado(String nuevoEstado) {
        nuevoEstado = nuevoEstado.toUpperCase();
        
        if (this.estado.equals("PENDIENTE")) {
            if (nuevoEstado.equals("EN_CAMINO") || nuevoEstado.equals("CANCELADO")) {
                this.estado = nuevoEstado;
            } else {
                throw new EstadoInvalidoException("Transición inválida. De PENDIENTE solo puede pasar a EN_CAMINO o CANCELADO.");
            }
        } else if (this.estado.equals("EN_CAMINO")) {
            if (nuevoEstado.equals("ENTREGADO")) {
                this.estado = nuevoEstado;
            } else {
                throw new EstadoInvalidoException("Transición inválida. De EN_CAMINO solo puede pasar a ENTREGADO.");
            }
        } else {
            throw new EstadoInvalidoException("El pedido ya está " + this.estado + " y no puede cambiar de estado.");
        }
    }
}
