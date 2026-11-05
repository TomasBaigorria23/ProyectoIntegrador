package modelo;

import excepciones.*; 

public class CityLink {


    public static final int MAX_CLIENTES = 50;
    private Cliente[] clientes;
    private int contadorClientes;
    public static final int MAX_REPARTIDORES = 10;
    private Repartidor[] repartidores;
    private int contadorRepartidores;
    public static final int MAX_PEDIDOS = 100;
    private Pedido[] pedidos;
    private int contadorPedidos;

    public CityLink() {
        this.clientes = new Cliente[MAX_CLIENTES];
        this.contadorClientes = 0;
        this.repartidores = new Repartidor[MAX_REPARTIDORES];
        this.contadorRepartidores = 0;
        this.pedidos = new Pedido[MAX_PEDIDOS];
        this.contadorPedidos = 0;
    }

    
    public void registrarCliente(Cliente cliente) throws AlmacenamientoLlenoException {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo.");
        }
        if (contadorClientes >= MAX_CLIENTES) {
            throw new AlmacenamientoLlenoException("Capacidad máxima de clientes alcanzada (" + MAX_CLIENTES + ").");
        }
        for (int i = 0; i < contadorClientes; i++) {
            if (clientes[i].getCodigo() == cliente.getCodigo()) {
                throw new IllegalArgumentException("Ya existe un cliente con el código " + cliente.getCodigo());
            }
        }
        
        clientes[contadorClientes] = cliente;
        contadorClientes++;
    }

    
    public Cliente buscarCliente(int codigo) throws ClienteNoEncontradoException {
        for (int i = 0; i < contadorClientes; i++) {
            if (clientes[i].getCodigo() == codigo) {
                return clientes[i];
            }
        }
        throw new ClienteNoEncontradoException("No se encontró ningún cliente con el código: " + codigo);
    }

    
    public Cliente buscarCliente(String nombre) throws ClienteNoEncontradoException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ClienteNoEncontradoException("El nombre no puede estar vacío.");
        }
        for (int i = 0; i < contadorClientes; i++) {
            if (clientes[i].getNombre().equalsIgnoreCase(nombre.trim())) {
                return clientes[i];
            }
        }
        throw new ClienteNoEncontradoException("No se encontró ningún cliente con el nombre: " + nombre);
    }

    public void precargarDatos() {
        // En construcción
    }
    
    public void registrarRepartidor(Repartidor repartidor) throws AlmacenamientoLlenoException {
        if (repartidor == null) {
        throw new IllegalArgumentException("El repartidor no puede ser nulo.");
    }
        if (contadorRepartidores >= MAX_REPARTIDORES) {
        throw new AlmacenamientoLlenoException("Capacidad máxima de repartidores alcanzada (" + MAX_REPARTIDORES + ").");
    }
        for (int i = 0; i < contadorRepartidores; i++) {
        if (repartidores[i].getLegajo() == repartidor.getLegajo()) {
            throw new IllegalArgumentException("Ya existe un repartidor con el legajo " + repartidor.getLegajo());
        }
    }
    repartidores[contadorRepartidores] = repartidor;
    contadorRepartidores++;
}

    public Repartidor buscarRepartidor(int legajo) {
        for (int i = 0; i < contadorRepartidores; i++) {
        if (repartidores[i].getLegajo() == legajo) {
            return repartidores[i];
        }
    }
    return null;
}
public void registrarPedido(Pedido pedido) throws AlmacenamientoLlenoException, PedidoInvalidoException {
    if (pedido == null) {
        throw new PedidoInvalidoException("El pedido no puede ser nulo.");
    }
    if (contadorPedidos >= MAX_PEDIDOS) {
        throw new AlmacenamientoLlenoException("Capacidad máxima de pedidos alcanzada (" + MAX_PEDIDOS + ").");
    }
    if (pedido.getCodigo() <= 0) {
        throw new PedidoInvalidoException("El código de pedido debe ser mayor a cero.");
    }
    if (pedido.getPesokg() <= 0) {
        throw new PedidoInvalidoException("El peso debe ser mayor a cero.");
    }
    if (pedido.getDistanciakm() <= 0) {
        throw new PedidoInvalidoException("La distancia debe ser mayor a cero.");
    }
    if (pedido.getDestino() == null || pedido.getDestino().trim().isEmpty()) {
        throw new PedidoInvalidoException("El destino del pedido no puede estar vacío.");
    }
    // Verificar código duplicado
    for (int i = 0; i < contadorPedidos; i++) {
        if (pedidos[i].getCodigo() == pedido.getCodigo()) {
            throw new PedidoInvalidoException("Ya existe un pedido con el código " + pedido.getCodigo());
        }
    }
    pedidos[contadorPedidos] = pedido;
    contadorPedidos++;
}

public Pedido buscarPedido(int codigo) {
    for (int i = 0; i < contadorPedidos; i++) {
        if (pedidos[i].getCodigo() == codigo) {
            return pedidos[i];
        }
    }
    return null;
}
    public void asignarPedido(int codigoPedido, int legajoRepartidor)
            throws PedidoInvalidoException, CapacidadExcedidaException, AlmacenamientoLlenoException {
        
        Pedido pedido = buscarPedido(codigoPedido);
        if (pedido == null) {
            throw new PedidoInvalidoException("No existe ningún pedido con el código: " + codigoPedido);
        }
        if (!pedido.getEstado().equalsIgnoreCase("PENDIENTE")) {
            throw new PedidoInvalidoException("Solo se pueden asignar pedidos en estado PENDIENTE (estado actual: " + pedido.getEstado() + ").");
        }

        Repartidor repartidor = buscarRepartidor(legajoRepartidor);
        if (repartidor == null) {
            throw new IllegalArgumentException("No existe ningún repartidor con el legajo: " + legajoRepartidor);
        }

        
        repartidor.asignarPedido(pedido);
    }
}