package modelo;

/**
 * Interfaz que define la capacidad de un objeto de calcular un importe económico (facturación o comisión).
 */
public interface Facturable {

    /**
     * Calcula y retorna el importe económico correspondiente al objeto.
     * @return Importe en pesos.
     */
    double calcularImporte();
}
