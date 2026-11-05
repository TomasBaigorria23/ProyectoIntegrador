package modelo;

/**
 * Clase abstracta que define la estructura y comportamiento base de los vehículos de reparto.
 * Modela atributos comunes como patente, capacidad de carga, velocidad promedio y costo por kilómetro.
 */
public abstract class Vehiculo {
    
    // Atributos privados
    private String patente;
    private double capacidadKg;
    private double velocidadKmH;
    private double costoPorKm;

    /**
     * Constructor vacío por defecto.
     */
    public Vehiculo() {
    }

    /**
     * Constructor completo que inicializa los atributos del vehículo validando sus valores.
     * 
     * @param patente Identificador o patente del vehículo.
     * @param capacidadKg Capacidad máxima de carga en kilogramos (debe ser mayor a 0).
     * @param velocidadKmH Velocidad promedio de circulación en km/h (debe ser mayor a 0).
     * @param costoPorKm Costo operativo por kilómetro recorrido (debe ser mayor o igual a 0).
     * @throws IllegalArgumentException Si algún parámetro no cumple las validaciones.
     */
    public Vehiculo(String patente, double capacidadKg, double velocidadKmH, double costoPorKm) {
        setPatente(patente);
        setCapacidadKg(capacidadKg);
        setVelocidadKmH(velocidadKmH);
        setCostoPorKm(costoPorKm);
    }

    // Getters y Setters con validaciones

    /**
     * Obtiene la patente del vehículo.
     * @return Patente del vehículo.
     */
    public String getPatente() {
        return patente;
    }

    /**
     * Establece la patente del vehículo verificando que no sea nula ni vacía.
     * @param patente Patente del vehículo.
     * @throws IllegalArgumentException Si la patente es nula o vacía.
     */
    public void setPatente(String patente) {
        if (patente == null || patente.trim().isEmpty()) {
            throw new IllegalArgumentException("La patente no puede estar vacía.");
        }
        this.patente = patente;
    }

    /**
     * Obtiene la capacidad de carga máxima en kilogramos.
     * @return Capacidad en kg.
     */
    public double getCapacidadKg() {
        return capacidadKg;
    }

    /**
     * Establece la capacidad de carga verificando que sea mayor a cero.
     * @param capacidadKg Capacidad en kg.
     * @throws IllegalArgumentException Si la capacidad es menor o igual a cero.
     */
    public void setCapacidadKg(double capacidadKg) {
        if (capacidadKg <= 0) {
            throw new IllegalArgumentException("La capacidad en kg debe ser mayor a cero.");
        }
        this.capacidadKg = capacidadKg;
    }

    /**
     * Obtiene la velocidad promedio del vehículo en km/h.
     * @return Velocidad promedio en km/h.
     */
    public double getVelocidadKmH() {
        return velocidadKmH;
    }

    /**
     * Establece la velocidad promedio verificando que sea mayor a cero.
     * @param velocidadKmH Velocidad en km/h.
     * @throws IllegalArgumentException Si la velocidad es menor o igual a cero.
     */
    public void setVelocidadKmH(double velocidadKmH) {
        if (velocidadKmH <= 0) {
            throw new IllegalArgumentException("La velocidad debe ser mayor a cero.");
        }
        this.velocidadKmH = velocidadKmH;
    }

    /**
     * Obtiene el costo operativo por kilómetro recorrido.
     * @return Costo por km.
     */
    public double getCostoPorKm() {
        return costoPorKm;
    }

    /**
     * Establece el costo operativo por kilómetro verificando que no sea negativo.
     * @param costoPorKm Costo por km.
     * @throws IllegalArgumentException Si el costo es negativo.
     */
    public void setCostoPorKm(double costoPorKm) {
        if (costoPorKm < 0) {
            throw new IllegalArgumentException("El costo por km no puede ser negativo.");
        }
        this.costoPorKm = costoPorKm;
    }

    /**
     * Método abstracto que calcula el tiempo estimado de entrega en minutos para una distancia dada.
     * Cada tipo de vehículo adiciona sus tiempos extras correspondientes.
     * 
     * @param km Distancia del trayecto en kilómetros.
     * @return Tiempo estimado de entrega en minutos.
     */
    public abstract double calcularTiempoEntrega(double km);

    /**
     * Determina si el vehículo tiene la capacidad de peso suficiente para transportar un pedido.
     * 
     * @param pedido Pedido a evaluar.
     * @return true si el peso del pedido no excede la capacidad del vehículo; false en caso contrario.
     * @throws IllegalArgumentException Si el pedido es nulo.
     */
    public boolean puedeTransportar(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo.");
        }
        return pedido.getPesokg() <= this.capacidadKg;
    }
}
