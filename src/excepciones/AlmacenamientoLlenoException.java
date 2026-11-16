package excepciones;

/**
 * Excepción Checked que se lanza cuando se intenta agregar un nuevo elemento
 * (cliente, pedido o repartidor) a un arreglo estático que ya alcanzó su capacidad máxima.
 */
public class AlmacenamientoLlenoException extends Exception {

    /**
     * Construye la excepción con un mensaje descriptivo del desbordamiento.
     * @param mensaje Detalle del error.
     */
    public AlmacenamientoLlenoException(String mensaje) {
        super(mensaje);
    }
}
