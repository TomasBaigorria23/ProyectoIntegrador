package modelo;

public class PedidoNormal extends Pedido {

    public PedidoNormal(int codigo, Cliente cliente, String destino, double pesokg, double distanciakm) {
        super(codigo, cliente, destino, pesokg, distanciakm);
    }

    @Override
    public double calcularCosto(Vehiculo vehiculo) {
        // Un pedido normal no tiene recargo
        return 1000.0 + (getDistanciakm() * vehiculo.getCostoPorKm());
    }

    @Override
    public double calcularImporte() {
        return super.calcularImporte();
    


    }
}