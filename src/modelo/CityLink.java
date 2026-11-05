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
         try {
        // 1. Cinco clientes
        Cliente c1 = new Cliente(101, "Tomas Baigorria", "2657-1234", "Centro");
        Cliente c2 = new Cliente(102, "Lautaro Kesler", "2657-5678", "Ribera");
        Cliente c3 = new Cliente(103, "Lionel Messi", "2657-8080", "Estacion");
        Cliente c4 = new Cliente(104, "Emiliano Martinez", "2657-5656", "Jardin Del Sur");
        Cliente c5 = new Cliente(105, "Rodrigo De Paul", "2657-7886", "Microcentro");
        registrarCliente(c1);
        registrarCliente(c2);
        registrarCliente(c3);
        registrarCliente(c4);
        registrarCliente(c5);
        // 2. Tres repartidores (uno por tipo de vehículo)
        Repartidor r1 = new Repartidor(1, "Franco Colapinto", new Bicicleta("BIC-01"));
        Repartidor r2 = new Repartidor(2, "Lionel Scaloni", new Moto("MOT-02"));
        Repartidor r3 = new Repartidor(3, "Pablo Aimar", new Camioneta("CAM-03"));
        registrarRepartidor(r1);
        registrarRepartidor(r2);
        registrarRepartidor(r3);
        // 3. Ocho pedidos (Normales, Urgentes y Frágiles)
        Pedido p1 = new PedidoNormal(1, c1, "Av. Mitre 1200", 2.0, 4.0);
        Pedido p2 = new PedidoUrgente(2, c2, "Pedernera 3400", 1.5, 6.0);
        Pedido p3 = new PedidoFragil(3, c3, "Balcarce 500", 4.0, 3.5);
        Pedido p4 = new PedidoNormal(4, c4, "9 De Julio 8000", 15.0, 10.0);
        Pedido p5 = new PedidoUrgente(5, c5, "Belgrano 2000", 18.0, 8.0);
        Pedido p6 = new PedidoFragil(6, c1, "Av. 25 De Mayo 1500", 22.0, 5.0);
        Pedido p7 = new PedidoNormal(7, c2, "Av. Presidente Peron 450", 60.0, 14.0);
        Pedido p8 = new PedidoUrgente(8, c3, "Europa 7000", 120.0, 18.0);
        registrarPedido(p1);
        registrarPedido(p2);
        registrarPedido(p3);
        registrarPedido(p4);
        registrarPedido(p5);
        registrarPedido(p6);
        registrarPedido(p7);
        registrarPedido(p8);
        // Asignamos dos pedidos para demostrar cálculos con vehículo
        asignarPedido(1, 1); // p1 asignado a Bici
        asignarPedido(2, 2); // p2 asignado a Moto
    } catch (Exception e) {
        System.out.println("Error en precarga de datos: " + e.getMessage());
    }
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
        public void cambiarEstadoPedido(int codigoPedido, String nuevoEstado)
            throws PedidoInvalidoException, EstadoInvalidoException {
        
        Pedido pedido = buscarPedido(codigoPedido);
        if (pedido == null) {
            throw new PedidoInvalidoException("No se encontró ningún pedido con código: " + codigoPedido);
        }

        nuevoEstado = nuevoEstado.toUpperCase();

        if (nuevoEstado.equals("ENTREGADO")) {
            boolean registrado = false;
            for (int i = 0; i < contadorRepartidores; i++) {
                if (repartidores[i].registrarEntrega(codigoPedido)) {
                    registrado = true;
                    break;
                }
            }
            if (!registrado) {
                pedido.cambiarEstado(nuevoEstado);
            }
        } else {
            pedido.cambiarEstado(nuevoEstado);
        }
    }
    public Pedido[] getPedidos() {
    return pedidos;
}
public int getContadorPedidos() {
    return contadorPedidos;
}
    public Repartidor[] getRepartidores() {
        return repartidores;
    }

    public int getContadorRepartidores() {
        return contadorRepartidores;
    }
}