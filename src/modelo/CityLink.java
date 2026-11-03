package modelo;

import excepciones.AlmacenamientoLlenoException;
import excepciones.CapacidadExcedidaException;
import excepciones.ClienteNoEncontradoException;
import excepciones.EstadoInvalidoException;
import excepciones.PedidoInvalidoException;

/**
 * Clase gestora del sistema CityLink.
 * Administra el almacenamiento en arreglos estáticos de clientes, pedidos y repartidores,
 * encapsula la lógica de negocio y aplica polimorfismo y manejo de excepciones.
 */
public class CityLink {

    // Constantes de capacidad máxima según la consigna
    public static final int MAX_CLIENTES = 50;
    public static final int MAX_PEDIDOS = 100;
    public static final int MAX_REPARTIDORES = 10;

    // Arreglos de almacenamiento estático y contadores de elementos cargados
    private Cliente[] clientes;
    private int contadorClientes;

    private Pedido[] pedidos;
    private int contadorPedidos;

    private Repartidor[] repartidores;
    private int contadorRepartidores;

    /**
     * Constructor que inicializa los arreglos con su capacidad máxima
     * y los contadores en 0.
     */
    public CityLink() {
        this.clientes = new Cliente[MAX_CLIENTES];
        this.contadorClientes = 0;

        this.pedidos = new Pedido[MAX_PEDIDOS];
        this.contadorPedidos = 0;

        this.repartidores = new Repartidor[MAX_REPARTIDORES];
        this.contadorRepartidores = 0;
    }

    // ==========================================================
    //                   GESTIÓN DE CLIENTES
    // ==========================================================

    /**
     * Registra un nuevo cliente en el sistema.
     * @param cliente Cliente a registrar.
     * @throws AlmacenamientoLlenoException Si el arreglo de clientes está completo.
     * @throws IllegalArgumentException Si el cliente es nulo o su código ya existe.
     */
    public void registrarCliente(Cliente cliente) throws AlmacenamientoLlenoException {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo.");
        }
        if (contadorClientes >= MAX_CLIENTES) {
            throw new AlmacenamientoLlenoException("Capacidad máxima de clientes alcanzada (" + MAX_CLIENTES + ").");
        }
        for (int i = 0; i < contadorClientes; i++) {
            if (clientes[i].getCodigo() == cliente.getCodigo()) {
                throw new IllegalArgumentException("Ya existe un cliente registrado con el código " + cliente.getCodigo() + ".");
            }
        }
        clientes[contadorClientes] = cliente;
        contadorClientes++;
    }

    /**
     * Busca un cliente por su código único.
     * @param codigo Código del cliente a buscar.
     * @return El cliente encontrado.
     * @throws ClienteNoEncontradoException Si no existe un cliente con ese código.
     */
    public Cliente buscarCliente(int codigo) throws ClienteNoEncontradoException {
        for (int i = 0; i < contadorClientes; i++) {
            if (clientes[i].getCodigo() == codigo) {
                return clientes[i];
            }
        }
        throw new ClienteNoEncontradoException("No se encontró ningún cliente con el código: " + codigo);
    }

    /**
     * Sobrecarga: Busca un cliente por su nombre (búsqueda que no distingue mayúsculas).
     * @param nombre Nombre del cliente a buscar.
     * @return El cliente encontrado.
     * @throws ClienteNoEncontradoException Si no existe un cliente con ese nombre.
     */
    public Cliente buscarCliente(String nombre) throws ClienteNoEncontradoException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ClienteNoEncontradoException("El nombre de búsqueda no puede estar vacío.");
        }
        for (int i = 0; i < contadorClientes; i++) {
            if (clientes[i].getNombre().equalsIgnoreCase(nombre.trim())) {
                return clientes[i];
            }
        }
        throw new ClienteNoEncontradoException("No se encontró ningún cliente con el nombre: " + nombre);
    }

    /**
     * Elimina un cliente desplazando los elementos siguientes para no dejar huecos.
     * @param codigo Código del cliente a eliminar.
     * @return true si se eliminó con éxito.
     * @throws ClienteNoEncontradoException Si el cliente no existe.
     */
    public boolean eliminarCliente(int codigo) throws ClienteNoEncontradoException {
        int indice = -1;
        for (int i = 0; i < contadorClientes; i++) {
            if (clientes[i].getCodigo() == codigo) {
                indice = i;
                break;
            }
        }
        if (indice == -1) {
            throw new ClienteNoEncontradoException("No se puede eliminar: cliente con código " + codigo + " no encontrado.");
        }
        // Desplazamiento a la izquierda
        for (int i = indice; i < contadorClientes - 1; i++) {
            clientes[i] = clientes[i + 1];
        }
        clientes[contadorClientes - 1] = null;
        contadorClientes--;
        return true;
    }

    // ==========================================================
    //                 GESTIÓN DE REPARTIDORES
    // ==========================================================

    /**
     * Registra un repartidor en el sistema.
     * @param repartidor Repartidor a registrar.
     * @throws AlmacenamientoLlenoException Si el arreglo de repartidores está lleno.
     */
    public void registrarRepartidor(Repartidor repartidor) throws AlmacenamientoLlenoException {
        if (repartidor == null) {
            throw new IllegalArgumentException("El repartidor no puede ser nulo.");
        }
        if (contadorRepartidores >= MAX_REPARTIDORES) {
            throw new AlmacenamientoLlenoException("Capacidad máxima de repartidores alcanzada (" + MAX_REPARTIDORES + ").");
        }
        for (int i = 0; i < contadorRepartidores; i++) {
            if (repartidores[i].getLegajo() == repartidor.getLegajo()) {
                throw new IllegalArgumentException("Ya existe un repartidor con el legajo " + repartidor.getLegajo() + ".");
            }
        }
        repartidores[contadorRepartidores] = repartidor;
        contadorRepartidores++;
    }

    /**
     * Busca un repartidor por su legajo.
     * @param legajo Legajo del repartidor.
     * @return Repartidor encontrado o null si no existe.
     */
    public Repartidor buscarRepartidor(int legajo) {
        for (int i = 0; i < contadorRepartidores; i++) {
            if (repartidores[i].getLegajo() == legajo) {
                return repartidores[i];
            }
        }
        return null;
    }

    // ==========================================================
    //                   GESTIÓN DE PEDIDOS
    // ==========================================================

    /**
     * Registra un pedido en el sistema validando todas las reglas de negocio.
     * @param pedido Pedido a registrar.
     * @throws AlmacenamientoLlenoException Si se supera la capacidad máxima de pedidos.
     * @throws PedidoInvalidoException Si los datos son inconsistentes o el código está duplicado.
     */
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
        if (pedido.getCliente() == null) {
            throw new PedidoInvalidoException("El pedido debe tener un cliente existente asociado.");
        }
        for (int i = 0; i < contadorPedidos; i++) {
            if (pedidos[i].getCodigo() == pedido.getCodigo()) {
                throw new PedidoInvalidoException("Ya existe un pedido con el código " + pedido.getCodigo() + ".");
            }
        }
        pedidos[contadorPedidos] = pedido;
        contadorPedidos++;
    }

    /**
     * Busca un pedido por su código.
     * @param codigo Código del pedido.
     * @return Pedido encontrado o null si no existe.
     */
    public Pedido buscarPedido(int codigo) {
        for (int i = 0; i < contadorPedidos; i++) {
            if (pedidos[i].getCodigo() == codigo) {
                return pedidos[i];
            }
        }
        return null;
    }

    // ==========================================================
    //             ASIGNACIÓN Y CAMBIO DE ESTADOS
    // ==========================================================

    /**
     * Asigna un pedido a un repartidor a partir de sus identificadores.
     * Propaga CapacidadExcedidaException y AlmacenamientoLlenoException lanzadas por Repartidor.
     * @param codigoPedido Código del pedido a asignar.
     * @param legajoRepartidor Legajo del repartidor que lo transportará.
     * @throws PedidoInvalidoException Si el pedido no existe o no está PENDIENTE.
     * @throws IllegalArgumentException Si el repartidor no existe.
     * @throws CapacidadExcedidaException Si el vehículo no puede transportar el pedido.
     * @throws AlmacenamientoLlenoException Si el repartidor tiene su cupo completo.
     */
    public void asignarPedido(int codigoPedido, int legajoRepartidor)
            throws PedidoInvalidoException, CapacidadExcedidaException, AlmacenamientoLlenoException {
        Pedido pedido = buscarPedido(codigoPedido);
        if (pedido == null) {
            throw new PedidoInvalidoException("No se encontró el pedido con código: " + codigoPedido);
        }
        if (!pedido.getEstado().equalsIgnoreCase("PENDIENTE")) {
            throw new PedidoInvalidoException("Solo se pueden asignar pedidos en estado PENDIENTE (estado actual: " + pedido.getEstado() + ").");
        }

        Repartidor repartidor = buscarRepartidor(legajoRepartidor);
        if (repartidor == null) {
            throw new IllegalArgumentException("No se encontró ningún repartidor con legajo: " + legajoRepartidor);
        }

        // Llama a asignarPedido de repartidor (que propaga CapacidadExcedidaException y AlmacenamientoLlenoException)
        repartidor.asignarPedido(pedido);
    }

    /**
     * Sobrecarga: Asigna un objeto Pedido directamente a un objeto Repartidor.
     * @param pedido Pedido a asignar.
     * @param repartidor Repartidor asignado.
     * @throws CapacidadExcedidaException Si el vehículo no puede transportar el pedido.
     * @throws AlmacenamientoLlenoException Si el repartidor superó su capacidad máxima.
     */
    public void asignarPedido(Pedido pedido, Repartidor repartidor)
            throws CapacidadExcedidaException, AlmacenamientoLlenoException {
        if (pedido == null || repartidor == null) {
            throw new IllegalArgumentException("Pedido y repartidor deben ser no nulos.");
        }
        repartidor.asignarPedido(pedido);
    }

    /**
     * Cambia el estado de un pedido validando las transiciones de negocio.
     * @param codigoPedido Código del pedido.
     * @param nuevoEstado Nuevo estado (EN_CAMINO, ENTREGADO, CANCELADO).
     * @throws PedidoInvalidoException Si el pedido no existe.
     * @throws EstadoInvalidoException Si la transición de estado no está permitida.
     */
    public void cambiarEstadoPedido(int codigoPedido, String nuevoEstado)
            throws PedidoInvalidoException, EstadoInvalidoException {
        Pedido pedido = buscarPedido(codigoPedido);
        if (pedido == null) {
            throw new PedidoInvalidoException("No se encontró el pedido con código: " + codigoPedido);
        }

        if (nuevoEstado.equalsIgnoreCase("ENTREGADO")) {
            // Si pasa a ENTREGADO, registramos la entrega en el repartidor que lo tiene a cargo
            for (int i = 0; i < contadorRepartidores; i++) {
                if (repartidores[i].registrarEntrega(codigoPedido)) {
                    return;
                }
            }
        }
        // Si no fue atendido por registrarEntrega o es otra transición, se ejecuta directamente:
        pedido.cambiarEstado(nuevoEstado);
    }

    // ==========================================================
    //            FACTURACIÓN Y CONSULTA DE ESTADOS
    // ==========================================================

    /**
     * Calcula la suma de los costos de envío de todos los pedidos facturables.
     * @return Importe total facturado por pedidos.
     */
    public double calcularTotalFacturadoPedidos() {
        double total = 0.0;
        for (int i = 0; i < contadorPedidos; i++) {
            total += pedidos[i].calcularImporte();
        }
        return total;
    }

    /**
     * Calcula la suma de las comisiones de todos los repartidores.
     * @return Importe total de comisiones.
     */
    public double calcularTotalComisionesRepartidores() {
        double total = 0.0;
        for (int i = 0; i < contadorRepartidores; i++) {
            total += repartidores[i].calcularImporte();
        }
        return total;
    }

    // ==========================================================
    //                   GETTERS Y PRECARGA
    // ==========================================================

    public Cliente[] getClientes() {
        return clientes;
    }

    public int getContadorClientes() {
        return contadorClientes;
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

    /**
     * Precarga datos de prueba requeridos por la consigna:
     * Al menos 5 clientes, 3 repartidores (uno por tipo de vehículo)
     * y 8 pedidos que incluyan los tres tipos (normal, urgente y frágil).
     */
    public void precargarDatos() {
        try {
            // 1. Cinco Clientes
            Cliente c1 = new Cliente(101, "Juan Perez", "11-4567-8901", "Centro");
            Cliente c2 = new Cliente(102, "Maria Gomez", "11-2345-6789", "Norte");
            Cliente c3 = new Cliente(103, "Carlos Lopez", "11-8765-4321", "Sur");
            Cliente c4 = new Cliente(104, "Lucia Fernandez", "11-3456-7890", "Oeste");
            Cliente c5 = new Cliente(105, "Marcos Diaz", "11-9876-5432", "Centro");

            registrarCliente(c1);
            registrarCliente(c2);
            registrarCliente(c3);
            registrarCliente(c4);
            registrarCliente(c5);

            // 2. Tres Repartidores (uno por vehículo)
            Repartidor r1 = new Repartidor(1, "Lucas Silva", new Bicicleta("BIC-01"));
            Repartidor r2 = new Repartidor(2, "Romina Ramos", new Moto("MOT-02"));
            Repartidor r3 = new Repartidor(3, "Esteban Quito", new Camioneta("CAM-03"));

            registrarRepartidor(r1);
            registrarRepartidor(r2);
            registrarRepartidor(r3);

            // 3. Ocho Pedidos (Normales, Urgentes y Frágiles)
            Pedido p1 = new PedidoNormal(1, c1, "Av. Corrientes 1200", 2.0, 4.0);
            Pedido p2 = new PedidoUrgente(2, c2, "Av. Santa Fe 3400", 1.5, 6.0);
            Pedido p3 = new PedidoFragil(3, c3, "Calle Florida 500", 4.0, 3.5);
            Pedido p4 = new PedidoNormal(4, c4, "Av. Rivadavia 8000", 15.0, 10.0);
            Pedido p5 = new PedidoUrgente(5, c5, "Av. Cabildo 2000", 18.0, 8.0);
            Pedido p6 = new PedidoFragil(6, c1, "Av. Belgrano 1500", 22.0, 5.0);
            Pedido p7 = new PedidoNormal(7, c2, "Av. San Martin 450", 60.0, 14.0);
            Pedido p8 = new PedidoUrgente(8, c3, "Av. Libertador 7000", 120.0, 18.0);

            registrarPedido(p1);
            registrarPedido(p2);
            registrarPedido(p3);
            registrarPedido(p4);
            registrarPedido(p5);
            registrarPedido(p6);
            registrarPedido(p7);
            registrarPedido(p8);

            // Asignamos un par de pedidos inicialmente para demostrar funcionamiento
            asignarPedido(p1, r1); // p1 (normal 2kg) asignado a Bicicleta
            asignarPedido(p2, r2); // p2 (urgente 1.5kg) asignado a Moto
            asignarPedido(p7, r3); // p7 (normal 60kg) asignado a Camioneta

        } catch (Exception e) {
            System.err.println("Error durante la precarga de datos: " + e.getMessage());
        }
    }
}
