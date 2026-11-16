package modelo;

/**
 * Representa un pedido de tipo Normal.
 * No aplica recargos adicionales: el costo es la base fija de $1000 más el costo por km del vehículo.
 */
public class PedidoNormal extends Pedido {

    /**
     * Construye un pedido normal con sus atributos básicos.
     * 
     * @param codigo Código identificador único.
     * @param cliente Cliente que solicita el envío.
     * @param destino Dirección de entrega.
     * @param pesokg Peso en kilogramos.
     * @param distanciakm Distancia en kilómetros.
     */
    public PedidoNormal(int codigo, Cliente cliente, String destino, double pesokg, double distanciakm) {
        super(codigo, cliente, destino, pesokg, distanciakm);
    }

    /**
     * Calcula el costo de envío sin recargos adicionales.
     * Fórmula: Costo base $1000 + (distancia * costoPorKm del vehículo).
     * 
     * @param vehiculo Vehículo asignado para el transporte.
     * @return Costo total en pesos.
     */
    @Override
    public double calcularCosto(Vehiculo vehiculo) {
        return 1000.0 + (getDistanciakm() * vehiculo.getCostoPorKm());
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
