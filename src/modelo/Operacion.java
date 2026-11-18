package modelo;

/**
 * Representa una operación realizada por el operador en el sistema.
 * Se almacena en la Pila de Deshacer (ArrayDeque) para poder revertir
 * altas de pedidos, asignaciones o cambios de estado (LIFO).
 */
public class Operacion {

    // Constantes para identificar el tipo de operación
    public static final String ALTA_PEDIDO = "ALTA_PEDIDO";
    public static final String ASIGNACION = "ASIGNACION";
    public static final String CAMBIO_ESTADO = "CAMBIO_ESTADO";

    private String tipo;
    private Pedido pedido;
    private Repartidor repartidor; // Guardado en caso de asignación
    private String estadoAnterior;  // Guardado en caso de cambio de estado

    /**
     * Constructor para operaciones de Alta de Pedido.
     */
    public Operacion(String tipo, Pedido pedido) {
        this.tipo = tipo;
        this.pedido = pedido;
    }

    /**
     * Constructor para operaciones de Asignación de Pedido a un Repartidor.
     */
    public Operacion(String tipo, Pedido pedido, Repartidor repartidor) {
        this.tipo = tipo;
        this.pedido = pedido;
        this.repartidor = repartidor;
    }

    /**
     * Constructor para operaciones de Cambio de Estado de un Pedido.
     */
    public Operacion(String tipo, Pedido pedido, String estadoAnterior) {
        this.tipo = tipo;
        this.pedido = pedido;
        this.estadoAnterior = estadoAnterior;
    }

    // Getters

    public String getTipo() {
        return tipo;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public Repartidor getRepartidor() {
        return repartidor;
    }

    public String getEstadoAnterior() {
        return estadoAnterior;
    }

    @Override
    public String toString() {
        switch (tipo) {
            case ALTA_PEDIDO:
                return "Alta del pedido #" + pedido.getCodigo();
            case ASIGNACION:
                return "Asignación del pedido #" + pedido.getCodigo() + " al repartidor " + repartidor.getNombre();
            case CAMBIO_ESTADO:
                return "Cambio de estado del pedido #" + pedido.getCodigo() + " (estado previo: " + estadoAnterior + ")";
            default:
                return "Operación desconocida";
        }
    }
}