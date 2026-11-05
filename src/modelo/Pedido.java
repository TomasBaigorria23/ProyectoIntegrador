package modelo;

import java.time.LocalDateTime;
import excepciones.EstadoInvalidoException;

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
    // Constructor vacío
    public Pedido() {
        this.estado = "PENDIENTE";
        this.fechaHora = LocalDateTime.now();
    }

    // Constructor completo
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
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        if (codigo <= 0) throw new IllegalArgumentException("El código debe ser mayor a cero.");
        this.codigo = codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        if (cliente == null) throw new IllegalArgumentException("El cliente no puede ser nulo.");
        this.cliente = cliente;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        if (destino == null || destino.trim().isEmpty()) {
            throw new IllegalArgumentException("El destino no puede estar vacío.");
        }
        this.destino = destino;
    }

    public double getPesokg() {
        return pesokg;
    }

    public void setPesokg(double pesokg) {
        if (pesokg <= 0) throw new IllegalArgumentException("El peso debe ser mayor a cero.");
        this.pesokg = pesokg;
    }

    public double getDistanciakm() {
        return distanciakm;
    }

    public void setDistanciakm(double distanciakm) {
        if (distanciakm <= 0) throw new IllegalArgumentException("La distancia debe ser mayor a cero.");
        this.distanciakm = distanciakm;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getEstado() {
        return estado;
    }

    // Método de la interfaz Rastreable
    @Override
    public String obtenerEstadoActual() {
        return this.estado;
    }

    // Método abstracto: Las subclases definirán cómo se calcula el costo base + recargos
    public abstract double calcularCosto(Vehiculo vehiculo);
        public Vehiculo getVehiculoAsignado() {
        return vehiculoAsignado;
    }

    public void setVehiculoAsignado(Vehiculo vehiculoAsignado) {
        this.vehiculoAsignado = vehiculoAsignado;
    }

    @Override
    public double calcularImporte() {
        if (this.vehiculoAsignado != null) {
            return calcularCosto(this.vehiculoAsignado);
        }
        return 0.0;
    }


    // Método concreto para cambiar el estado validando reglas de negocio
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
