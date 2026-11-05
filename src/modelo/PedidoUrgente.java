package modelo;

/**
 * Representa un pedido de tipo Urgente.
 * Aplica un recargo del 50% sobre el costo total (costo base + distancia * costo por km).
 */
public class PedidoUrgente extends Pedido {

    /**
     * Construye un pedido urgente con sus atributos básicos.
     * 
     * @param codigo Código identificador único.
     * @param cliente Cliente que solicita el envío.
     * @param destino Dirección de entrega.
     * @param pesokg Peso en kilogramos.
     * @param distanciakm Distancia en kilómetros.
     */
    public PedidoUrgente(int codigo, Cliente cliente, String destino, double pesokg, double distanciakm) {
        super(codigo, cliente, destino, pesokg, distanciakm);
    }

    /**
     * Calcula el costo de envío aplicando un 50% de recargo sobre el total base.
     * Fórmula: (Costo base $1000 + distancia * costoPorKm) * 1.50.
     * 
     * @param vehiculo Vehículo asignado para el transporte.
     * @return Costo total con recargo del 50%.
     */
    @Override
    public double calcularCosto(Vehiculo vehiculo) {
        double costoBase = 1000.0 + (getDistanciakm() * vehiculo.getCostoPorKm());
        return costoBase * 1.50; 
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
