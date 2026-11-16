package modelo;

/**
 * Representa un pedido de tipo Frágil.
 * Aplica un adicional fijo de $800 sobre el costo total (costo base + distancia * costo por km).
 * No puede ser transportado en bicicleta.
 */
public class PedidoFragil extends Pedido {

    /**
     * Construye un pedido frágil con sus atributos básicos.
     * 
     * @param codigo Código identificador único.
     * @param cliente Cliente que solicita el envío.
     * @param destino Dirección de entrega.
     * @param pesokg Peso en kilogramos.
     * @param distanciakm Distancia en kilómetros.
     */
    public PedidoFragil(int codigo, Cliente cliente, String destino, double pesokg, double distanciakm) {
        super(codigo, cliente, destino, pesokg, distanciakm);
    }

    /**
     * Calcula el costo de envío sumando un adicional fijo de $800.
     * Fórmula: Costo base $1000 + distancia * costoPorKm + $800.
     * 
     * @param vehiculo Vehículo asignado para el transporte.
     * @return Costo total en pesos.
     */
    @Override
    public double calcularCosto(Vehiculo vehiculo) {
        double costoBase = 1000.0 + (getDistanciakm() * vehiculo.getCostoPorKm());
        return costoBase + 800.0;
    }

    /**
     * Retorna el importe facturable del pedido.
     * @return Importe calculado.
     */
    @Override
    public double calcularImporte() {
        return super.calcularImporte();
    }
}
