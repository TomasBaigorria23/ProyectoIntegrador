package modelo;

public class PedidoFragil extends Pedido {

    public PedidoFragil(int codigo, Cliente cliente, String destino, double pesokg, double distanciakm) {
        super(codigo, cliente, destino, pesokg, distanciakm);
    }

    @Override
    public double calcularCosto(Vehiculo vehiculo) {
        // Un pedido frágil suma un adicional fijo de $800
        double costoBase = 1000.0 + (getDistanciakm() * vehiculo.getCostoPorKm());
        return costoBase + 800.0;
    }

    @Override
    public double calcularImporte() {
        return 0;
    }
}
