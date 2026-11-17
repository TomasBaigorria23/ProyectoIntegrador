package modelo;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;
import excepciones.*;


/**
 * Gestor central del sistema CityLink.
 * Administra el almacenamiento estático en arreglos de clientes, repartidores y pedidos,
 * encapsula la lógica de negocio, búsquedas con sobrecarga, asignaciones y transiciones de estado.
 */
public class CityLink {

private ArrayList<Repartidor> repartidores;
private TreeMap<Integer, Cliente> clientes;
private ArrayList<Pedido> pedidos;
// Listas de turnos (para el algoritmo de intercalación de la cátedra):
private ArrayList<Pedido> turnoManana;
private ArrayList<Pedido> turnoTarde;
private LinkedList<Pedido> colaPrioridad;
    /**
     * Constructor que inicializa los arreglos con sus capacidades máximas
     * y los contadores en cero.
     */
    public CityLink() {
    this.clientes = new TreeMap<>();
    this.repartidores = new ArrayList<>();
    this.pedidos = new ArrayList<>();
    this.turnoManana = new ArrayList<>();
    this.turnoTarde = new ArrayList<>();
    this.colaPrioridad = new LinkedList<>();
}
    

    // ==========================================================
    //                   GESTIÓN DE CLIENTES
    // ==========================================================

    /**
        * Registra un cliente en el sistema verificando duplicados por código.
     */
    // 1. REGISTRAR CLIENTE
public void registrarCliente(Cliente cliente) {
    if (cliente == null) {
        throw new IllegalArgumentException("El cliente no puede ser nulo.");
    }
    // TreeMap.containsKey verifica si ya existe el código en O(log n)
    if (clientes.containsKey(cliente.getCodigo())) {
        throw new IllegalArgumentException("Ya existe un cliente con el código " + cliente.getCodigo());
    }
    // Guardamos en el árbol: la clave es el código entero, el valor es el objeto Cliente
    clientes.put(cliente.getCodigo(), cliente);
}

// 2. BUSCAR POR CÓDIGO (Árbol)
public Cliente buscarCliente(int codigo) throws ClienteNoEncontradoException {
    Cliente c = clientes.get(codigo);
    if (c == null) {
        throw new ClienteNoEncontradoException("No se encontró ningún cliente con el código: " + codigo);
    }
    return c;
}

// 3. BUSCAR POR NOMBRE (Búsqueda Lineal sobre los valores del árbol)
public Cliente buscarCliente(String nombre) throws ClienteNoEncontradoException {
    if (nombre == null || nombre.trim().isEmpty()) {
        throw new ClienteNoEncontradoException("El nombre no puede estar vacío.");
    }
    for (Cliente c : clientes.values()) {
        if (c.getNombre().equalsIgnoreCase(nombre.trim())) {
            return c;
        }
    }
    throw new ClienteNoEncontradoException("No se encontró ningún cliente con el nombre: " + nombre);
}

// 4. ELIMINAR CLIENTE (El árbol se rebalancea automáticamente)
public boolean eliminarCliente(int codigo) throws ClienteNoEncontradoException {
    if (!clientes.containsKey(codigo)) {
        throw new ClienteNoEncontradoException("No se puede eliminar: cliente no encontrado.");
    }
    clientes.remove(codigo);
    return true;
}

// 5. OBTENER CLIENTES ORDENADOS
// Como es un TreeMap, clientes.values() ya sale ordenado por código
public TreeMap<Integer, Cliente> getClientes() {
    return clientes;
}
       

    // ==========================================================
    //                 GESTIÓN DE REPARTIDORES
    // ==========================================================

    /**
     * Registra un repartidor en el sistema verificando cupo máximo y legajo duplicado.
     * 
     * @param repartidor Repartidor a registrar.
     * @throws AlmacenamientoLlenoException Si se supera el límite de 10 repartidores.
     * @throws IllegalArgumentException Si el repartidor es nulo o su legajo ya existe.
     */
   public void registrarRepartidor(Repartidor repartidor) throws AlmacenamientoLlenoException {
    if (repartidor == null) {
        throw new IllegalArgumentException("El repartidor no puede ser nulo.");
    }
    // Revisamos si ya existe ese legajo en la lista
    for (Repartidor r : repartidores) {
        if (r.getLegajo() == repartidor.getLegajo()) {
            throw new IllegalArgumentException("Ya existe un repartidor con el legajo " + repartidor.getLegajo());
        }
    }
    // Lo agregamos a la lista dinámica
    repartidores.add(repartidor);
}

    /**
     * Busca un repartidor por su legajo.
     * 
     * @param legajo Legajo numérico.
     * @return Repartidor encontrado o null si no existe.
     */
        public Repartidor buscarRepartidor(int legajo) {
    for (Repartidor r : repartidores) {
        if (r.getLegajo() == legajo) {
            return r; // Lo encontró
        }
    }
    return null; // No está en la lista
}

    // ==========================================================
    //                   GESTIÓN DE PEDIDOS
    // ==========================================================

    /**
     * Registra un pedido en el sistema validando todas las reglas de negocio de la consigna.
     * 
     * @param pedido Pedido a registrar.
     * @throws AlmacenamientoLlenoException Si se supera el límite de 100 pedidos.
     * @throws PedidoInvalidoException Si los datos son inválidos, negativos o el código está duplicado.
     */
    public void registrarPedido(Pedido pedido) throws AlmacenamientoLlenoException, PedidoInvalidoException {
    if (pedido == null) {
        throw new PedidoInvalidoException("El pedido no puede ser nulo.");
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
    // Revisamos si ya existe el código en la lista
    for (Pedido p : pedidos) {
        if (p.getCodigo() == pedido.getCodigo()) {
            throw new PedidoInvalidoException("Ya existe un pedido con el código " + pedido.getCodigo());
        }
    }
    // Lo guardamos en la lista dinámica
    pedidos.add(pedido);
}

    /**
     * Busca un pedido por su código identificador.
     * 
     * @param codigo Código del pedido.
     * @return Pedido encontrado o null si no existe.
     */
    public Pedido buscarPedido(int codigo) {
    for (Pedido p : pedidos) {
        if (p.getCodigo() == codigo) {
            return p; // Encontrado
        }
    }
    return null; // No existe
}
        // ==========================================================
    //            COLA DE PRIORIDAD (LINKEDLIST)
    // ==========================================================

    /**
     * Encola un pedido con prioridad: los urgentes se ubican
     * por delante de los normales y frágiles.
     */
    public void encolarPedido(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo.");
        }
        if (pedido instanceof PedidoUrgente) {
            // Buscamos la posición donde terminan los urgentes para insertarlo
            int pos = 0;
            while (pos < colaPrioridad.size() && colaPrioridad.get(pos) instanceof PedidoUrgente) {
                pos++;
            }
            colaPrioridad.add(pos, pedido);
        } else {
            // Pedidos normales y frágiles van al final de la cola
            colaPrioridad.addLast(pedido);
        }
    }

    /**
     * Despacha y retira el siguiente pedido con mayor prioridad de la cola.
     * Retorna null si la cola está vacía.
     */
    public Pedido despacharSiguientePedido() {
        if (colaPrioridad.isEmpty()) {
            return null;
        }
        return colaPrioridad.poll(); // Extrae el primer elemento en O(1)
    }

    /**
     * Obtiene la cola de prioridad actual.
     */
    public LinkedList<Pedido> getColaPrioridad() {
        return colaPrioridad;
    }

    // ==========================================================
    //             ASIGNACIÓN Y CAMBIO DE ESTADOS
    // ==========================================================

    /**
     * Asigna un pedido en estado PENDIENTE a un repartidor, verificando capacidad y restricciones.
     * Propaga {@link CapacidadExcedidaException} y {@link AlmacenamientoLlenoException} hacia el menú.
     * 
     * @param codigoPedido Código del pedido a asignar.
     * @param legajoRepartidor Legajo del repartidor que lo transportará.
     * @throws PedidoInvalidoException Si el pedido no existe o no está PENDIENTE.
     * @throws IllegalArgumentException Si el repartidor no existe.
     * @throws CapacidadExcedidaException Si el vehículo no puede transportar el pedido.
     * @throws AlmacenamientoLlenoException Si el repartidor alcanzó su tope de pedidos.
     */
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

    /**
     * Cambia el estado de un pedido validando las transiciones de negocio.
     * Si pasa a ENTREGADO, notifica al repartidor responsable para registrar su entrega y comisión.
     * 
     * @param codigoPedido Código del pedido.
     * @param nuevoEstado Nuevo estado deseado (EN_CAMINO, ENTREGADO, CANCELADO).
     * @throws PedidoInvalidoException Si el pedido no existe.
     * @throws EstadoInvalidoException Si la transición de estado no es válida.
     */
    public void cambiarEstadoPedido(int codigoPedido, String nuevoEstado)
            throws PedidoInvalidoException, EstadoInvalidoException {
        
        Pedido pedido = buscarPedido(codigoPedido);
        if (pedido == null) {
            throw new PedidoInvalidoException("No se encontró ningún pedido con código: " + codigoPedido);
        }

        nuevoEstado = nuevoEstado.toUpperCase();
            
        if (nuevoEstado.equals("ENTREGADO")) {
            boolean registrado = false;
            for (Repartidor r : repartidores) {
                if (r.registrarEntrega(codigoPedido)) {
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

    // ==========================================================
    //                      GETTERS
    // ==========================================================

    /**
     * Obtiene el arreglo con los pedidos del sistema.
     * @return Arreglo de Pedido.
     */
   
    public ArrayList<Pedido> getPedidos() {
    return pedidos;
}

public int getContadorPedidos() {
    return pedidos.size();
}

public ArrayList<Repartidor> getRepartidores() {
    return repartidores;
}

public int getContadorRepartidores() {
    return repartidores.size();
}

// Getters de los turnos para el algoritmo de intercalación:
public ArrayList<Pedido> getTurnoManana() {
    return turnoManana;
}

public ArrayList<Pedido> getTurnoTarde() {
    return turnoTarde;
}
    // ==========================================================
    //               PRECARGA DE DATOS DE PRUEBA
    // ==========================================================

    /**
     * Precarga datos iniciales requeridos por la consigna:
     * 5 clientes, 3 repartidores (uno por tipo de vehículo) y 8 pedidos de los tres tipos.
     */
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
}
