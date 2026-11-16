package modelo;

/**
 * Interfaz que define la capacidad de un objeto de ser rastreado en tiempo real dentro del sistema.
 */
public interface Rastreable {
    
    /**
     * Retorna el estado actual del objeto rastreable.
     * @return Cadena que representa el estado actual.
     */
    String obtenerEstadoActual();
}
