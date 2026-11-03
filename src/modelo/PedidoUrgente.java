package modelo;

public class PedidoUrgente extends Pedido {

    public PedidoUrgente(int codigo, Cliente cliente, String destino, double pesokg, double distanciakm) {
        super(codigo, cliente, destino, pesokg, distanciakm);
    }

    @Override
    public double calcularCosto(Vehiculo vehiculo) {
        // Un pedido urgente suma un 50% sobre el total
        double costoBase = 1000.0 + (getDistanciakm() * vehiculo.getCostoPorKm());
        return costoBase * 1.50; 
    }

    @Override
    public double calcularImporte() {
<<<<<<< HEAD
        return super.calcularImporte();
=======
        return 0;
>>>>>>> f182ad15953dd2cd745528d1f34316c15cb63247
    }
}
