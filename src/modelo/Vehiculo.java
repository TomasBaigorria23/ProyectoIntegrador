package modelo;

public abstract class Vehiculo {
    
    // Atributos privados
    private String patente;
    private double capacidadKg;
    private double velocidadKmH;
    private double costoPorKm;

    // Constructor vacío
    public Vehiculo() {
    }

    // Constructor completo
    public Vehiculo(String patente, double capacidadKg, double velocidadKmH, double costoPorKm) {
        setPatente(patente);
        setCapacidadKg(capacidadKg);
        setVelocidadKmH(velocidadKmH);
        setCostoPorKm(costoPorKm);
    }

    // Getters y Setters con validaciones
    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        if (patente == null || patente.trim().isEmpty()) {
            throw new IllegalArgumentException("La patente no puede estar vacía.");
        }
        this.patente = patente;
    }

    public double getCapacidadKg() {
        return capacidadKg;
    }

    public void setCapacidadKg(double capacidadKg) {
        if (capacidadKg <= 0) {
            throw new IllegalArgumentException("La capacidad en kg debe ser mayor a cero.");
        }
        this.capacidadKg = capacidadKg;
    }

    public double getVelocidadKmH() {
        return velocidadKmH;
    }

    public void setVelocidadKmH(double velocidadKmH) {
        if (velocidadKmH <= 0) {
            throw new IllegalArgumentException("La velocidad debe ser mayor a cero.");
        }
        this.velocidadKmH = velocidadKmH;
    }

    public double getCostoPorKm() {
        return costoPorKm;
    }

    public void setCostoPorKm(double costoPorKm) {
        if (costoPorKm < 0) {
            throw new IllegalArgumentException("El costo por km no puede ser negativo.");
        }
        this.costoPorKm = costoPorKm;
    }

    // Método abstracto: Las subclases (Moto, Bici, Camioneta) están obligadas a programar cómo se calcula esto
    public abstract double calcularTiempoEntrega(double km);

    // Método concreto: Lógica compartida para todos los vehículos
    public boolean puedeTransportar(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo.");
        }
        return pedido.getPesokg() <= this.capacidadKg;
    }
}