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
        // Requisito de la interfaz Facturable. 
        // Por ahora retorna 0 hasta que la clase Gestora le asigne definitivamente un vehículo.
<<<<<<< HEAD
        return super.calcularImporte();
=======
        return 0;
>>>>>>> f182ad15953dd2cd745528d1f34316c15cb63247
    }
}