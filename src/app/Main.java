package app;

import java.util.Scanner;
import modelo.*;
import excepciones.*;
public class Main {
    public static void main(String[] args){
        CityLink citylink = new CityLink();
        citylink.precargarDatos();

        Scanner scanner = new Scanner (System.in);
        boolean salir = false;

        System.out.println("--------------------------------");
        System.out.println("-----Bienvenido a Citylink-----");
        System.out.println("--------------------------------");

        ///Menu principal
         while(!salir){
            mostrarMenu();
            int opcion = leerEntero(scanner, "Seleccione una opcion (1-10):");
            switch(opcion){
                case 1:
                    registrarClienteMenu(scanner, citylink);
                    break;
                case 2:
                    registrarRepartidorMenu(scanner, citylink);
                    break;
                case 3:
                    registrarPedidoMenu(scanner, citylink);
                    break;
                case 4:
                    asignarPedidoMenu(scanner, citylink);
                    break;
                case 5:
                    cambiarEstadoMenu(scanner, citylink);
                    break;
                case 6:
                    buscarClienteMenu(scanner, citylink);

                    break;
                case 7:
                    listarPedidosMenu(citylink);
                    break;
                case 8:
                    consultarRastreableMenu(scanner, citylink);
                    break;
                case 9:
                    verFacturacionMenu(citylink);
                    break;
                case 10:
                    System.out.println("\nGracias por utilizar CityLink. Hasta luego!");
                    salir = true;
                    break;
                default:
                    System.out.println("Opcion no valida. Debe ingresar un numero del 1 al 10.\n");
                    break;
            }
        }
        scanner.close();
            }
    

    
private static void mostrarMenu() {
        System.out.println ("\n--- Menu Principal ---");
        System.out.println("1.  Registrar cliente");
        System.out.println("2.  Registrar repartidor con su vehiculo");
        System.out.println("3.  Registrar pedido (normal, urgente o fragil)");
        System.out.println("4.  Asignar pedido a un repartidor");
        System.out.println("5.  Cambiar estado de un pedido");
        System.out.println("6.  Buscar cliente (por codigo o nombre)");
        System.out.println("7.  Listar pedidos con costo y tiempo estimado");
        System.out.println("8.  Consultar estado (Rastreable)");
        System.out.println("9.  Ver facturacion e importe de comisiones");
        System.out.println("10. Salir");
        
}

private static int leerEntero(Scanner scanner, String mensaje){
    while(true){
        System.out.print(mensaje);
        String entrada = scanner.nextLine().trim();
        try{
            return Integer.parseInt(entrada);
        } catch (NumberFormatException e){
            System.out.println("Error: Debe ingresar un numero valido.");

        }
    }
}
private static void registrarClienteMenu(Scanner scanner, CityLink citylink) {
    System.out.println("\n--- Registrar Cliente ---");
    int codigo = leerEntero(scanner, "Ingrese código del cliente (número positivo): ");
    
    System.out.print("Ingrese nombre: ");
    String nombre = scanner.nextLine().trim();
    
    System.out.print("Ingrese teléfono: ");
    String telefono = scanner.nextLine().trim();
    
    System.out.print("Ingrese zona: ");
    String zona = scanner.nextLine().trim();
    try {
        Cliente nuevoCliente = new Cliente(codigo, nombre, telefono, zona);
        citylink.registrarCliente(nuevoCliente);
        System.out.println(" Cliente registrado con éxito!");
    } catch (AlmacenamientoLlenoException e) {
        System.out.println(" Error: " + e.getMessage());
    } catch (IllegalArgumentException e) {
        System.out.println(" Error en los datos: " + e.getMessage());
    }
}
private static void buscarClienteMenu(Scanner scanner, CityLink citylink) {
    System.out.println("\n--- Buscar Cliente ---");
    System.out.println("1. Buscar por código");
    System.out.println("2. Buscar por nombre");
    int subOpcion = leerEntero(scanner, "Elija una opción (1 o 2): ");

    try {
        if (subOpcion == 1) {
            int codigo = leerEntero(scanner, "Ingrese el código a buscar: ");
            Cliente encontrado = citylink.buscarCliente(codigo); 
            System.out.println(" Encontrado: " + encontrado);
        } else if (subOpcion == 2) {
            System.out.print("Ingrese el nombre a buscar: ");
            String nombre = scanner.nextLine().trim();
            Cliente encontrado = citylink.buscarCliente(nombre); 
            System.out.println(" Encontrado: " + encontrado);
        } else {
            System.out.println("Opción inválida.");
        }
    } catch (ClienteNoEncontradoException e) {
        System.out.println(" Error: " + e.getMessage());
    }
}


private static void registrarRepartidorMenu(Scanner scanner, CityLink citylink) {
    System.out.println("\n--- Registrar Repartidor ---");
    int legajo = leerEntero(scanner, "Ingrese legajo del repartidor: ");
    
    System.out.print("Ingrese nombre: ");
    String nombre = scanner.nextLine().trim();

    System.out.print("Ingrese patente del vehículo: ");
    String patente = scanner.nextLine().trim();

    System.out.println("Seleccione tipo de vehículo:");
    System.out.println("1. Bicicleta (Capacidad: 10kg, No frágiles)");
    System.out.println("2. Moto (Capacidad: 30kg)");
    System.out.println("3. Camioneta (Capacidad: 500kg)");
    int tipo = leerEntero(scanner, "Opción (1-3): ");

    Vehiculo vehiculo = null;
    if (tipo == 1) {
        vehiculo = new Bicicleta(patente);
    } else if (tipo == 2) {
        vehiculo = new Moto(patente);
    } else if (tipo == 3) {
        vehiculo = new Camioneta(patente);
    } else {
        System.out.println(" Tipo de vehículo inválido. Operación cancelada.");
        return;
    }

    try {
        Repartidor nuevoRepartidor = new Repartidor(legajo, nombre, vehiculo);
        citylink.registrarRepartidor(nuevoRepartidor);
        System.out.println(" Repartidor registrado con éxito!");
    } catch (AlmacenamientoLlenoException e) {
        System.out.println(" Error: " + e.getMessage());
    } catch (IllegalArgumentException e) {
        System.out.println(" Error en los datos: " + e.getMessage());
    }
    }
    private static void registrarPedidoMenu(Scanner scanner, CityLink citylink) {
    System.out.println("\n--- REGISTRAR NUEVO PEDIDO ---");
    int codCliente = leerEntero(scanner, "Ingrese código del cliente que realiza el pedido: ");

    Cliente cliente;
    try {
        cliente = citylink.buscarCliente(codCliente);
        System.out.println("Cliente: " + cliente.getNombre() + " (Zona: " + cliente.getZona() + ")");
    } catch (ClienteNoEncontradoException e) {
        System.out.println(" Error: " + e.getMessage() + ". Primero debe registrar al cliente.");
        return;
    }

    int codigoPedido = leerEntero(scanner, "Ingrese código único para el pedido: ");
    
    System.out.print("Ingrese destino (dirección o zona): ");
    String destino = scanner.nextLine().trim();

    System.out.print("Ingrese peso en kg (ej: 4.5): ");
    double peso = Double.parseDouble(scanner.nextLine().trim());

    System.out.print("Ingrese distancia en km (ej: 12.0): ");
    double distancia = Double.parseDouble(scanner.nextLine().trim());

    System.out.println("Seleccione tipo de pedido:");
    System.out.println("1. Normal (Sin recargo)");
    System.out.println("2. Urgente (+50% recargo)");
    System.out.println("3. Frágil (+$800 adicional fijo, no viaja en bicicleta)");
    int tipo = leerEntero(scanner, "Opción (1-3): ");

    Pedido nuevoPedido;
    if (tipo == 1) {
        nuevoPedido = new PedidoNormal(codigoPedido, cliente, destino, peso, distancia);
    } else if (tipo == 2) {
        nuevoPedido = new PedidoUrgente(codigoPedido, cliente, destino, peso, distancia);
    } else if (tipo == 3) {
        nuevoPedido = new PedidoFragil(codigoPedido, cliente, destino, peso, distancia);
    } else {
        System.out.println(" Tipo de pedido inválido. Operación cancelada.");
        return;
    }

    try {
        citylink.registrarPedido(nuevoPedido);
        System.out.println(" Pedido registrado con éxito en estado PENDIENTE!");
    } catch (AlmacenamientoLlenoException | PedidoInvalidoException e) {
        System.out.println(" Error: " + e.getMessage());
    } catch (Exception e) {
        System.out.println(" Error inesperado: " + e.getMessage());
    }
}
private static void asignarPedidoMenu(Scanner scanner, CityLink citylink) {
    System.out.println("\n--- ASIGNAR PEDIDO A REPARTIDOR ---");
    int codPedido = leerEntero(scanner, "Ingrese código del pedido: ");
    int legajo = leerEntero(scanner, "Ingrese legajo del repartidor: ");

    try {
        citylink.asignarPedido(codPedido, legajo);
        System.out.println(" Pedido asignado con éxito! Pasó a estado EN_CAMINO.");
    } catch (PedidoInvalidoException e) {
        System.out.println(" Error en el pedido: " + e.getMessage());
    } catch (CapacidadExcedidaException e) {
        System.out.println(" Capacidad o restricción no permitida: " + e.getMessage());
    } catch (AlmacenamientoLlenoException e) {
        System.out.println(" Error de almacenamiento: " + e.getMessage());
    } catch (IllegalArgumentException e) {
        System.out.println(" Error: " + e.getMessage());
    } finally {
        // Requisito de la consigna: evidenciar al menos un bloque finally
        System.out.println(" Proceso de asignación finalizado.");
    }
}
private static void cambiarEstadoMenu(Scanner scanner, CityLink citylink) {
    System.out.println("\n--- CAMBIAR ESTADO DE UN PEDIDO ---");
    int codPedido = leerEntero(scanner, "Ingrese código del pedido: ");

    System.out.println("Seleccione el nuevo estado:");
    System.out.println("1. EN_CAMINO");
    System.out.println("2. ENTREGADO");
    System.out.println("3. CANCELADO");
    int opc = leerEntero(scanner, "Opción (1-3): ");

    String nuevoEstado = "";
    if (opc == 1) {
        nuevoEstado = "EN_CAMINO";
    } else if (opc == 2) {
        nuevoEstado = "ENTREGADO";
    } else if (opc == 3) {
        nuevoEstado = "CANCELADO";
    } else {
        System.out.println(" Opción inválida.");
        return;
    }

    try {
        citylink.cambiarEstadoPedido(codPedido, nuevoEstado);
        System.out.println(" Estado actualizado con éxito a: " + nuevoEstado);
    } catch (PedidoInvalidoException e) {
        System.out.println(" Error: " + e.getMessage());
    } catch (EstadoInvalidoException e) {
        System.out.println(" Transición no permitida: " + e.getMessage());
    }
}
private static void listarPedidosMenu(CityLink citylink) {
    System.out.println("\n************ LISTADO DE PEDIDOS (POLIMORFISMO) ************");
    Pedido[] pedidos = citylink.getPedidos();
    int total = citylink.getContadorPedidos();

    if (total == 0) {
        System.out.println("No hay pedidos registrados en el sistema.");
        return;
    }

    for (int i = 0; i < total; i++) {
        Pedido p = pedidos[i];
        System.out.println("------------------------------------------------------------------");
        System.out.println("Pedido #" + p.getCodigo() + " [" + p.getClass().getSimpleName() + "] - Estado: " + p.getEstado());
        System.out.println("Cliente: " + p.getCliente().getNombre() + " | Destino: " + p.getDestino());
        System.out.println("Peso: " + p.getPesokg() + " kg | Distancia: " + p.getDistanciakm() + " km");

        Vehiculo v = p.getVehiculoAsignado();
        if (v != null) {
            
            double costo = p.calcularCosto(v);
            double tiempoMinutos = v.calcularTiempoEntrega(p.getDistanciakm());

            System.out.println("Vehículo Asignado: " + v.getClass().getSimpleName() + " (Patente: " + v.getPatente() + ")");
            System.out.printf("Costo de envío: $%.2f | Tiempo estimado: %.1f minutos\n", costo, tiempoMinutos);
        } else {
            System.out.println("Vehículo: Pendiente de asignación (Cálculo exacto al asignar vehículo)");
        }
    }
    System.out.println("==================================================================");
}
    private static void consultarRastreableMenu(Scanner scanner, CityLink citylink) {
        System.out.println("\n--- CONSULTAR ESTADO (INTERFAZ RASTREABLE) ---");
        System.out.println("1. Rastrear Pedido");
        System.out.println("2. Consultar Estado de Repartidor");
        int opc = leerEntero(scanner, "Seleccione opción (1 o 2): ");

        if (opc == 1) {
            int cod = leerEntero(scanner, "Ingrese código de pedido: ");
            Pedido pedido = citylink.buscarPedido(cod);
            if (pedido != null) {
                
                Rastreable r = pedido;
                System.out.println(" Pedido #" + cod + " -> Estado actual: " + r.obtenerEstadoActual());
            } else {
                System.out.println(" No se encontró pedido con código " + cod);
            }
        } else if (opc == 2) {
            int legajo = leerEntero(scanner, "Ingrese legajo del repartidor: ");
            Repartidor repartidor = citylink.buscarRepartidor(legajo);
            if (repartidor != null) {
                
                Rastreable r = repartidor;
                System.out.println(" Repartidor " + repartidor.getNombre() + " -> " + r.obtenerEstadoActual());
            } else {
                System.out.println(" No se encontró repartidor con legajo " + legajo);
            }
        } else {
            System.out.println(" Opción inválida.");
        }
    }

    private static void verFacturacionMenu(CityLink citylink) {
        System.out.println("\n================ REPORTE DE FACTURACION Y COMISIONES ================");
        
        double totalFacturado = 0.0;
        Pedido[] pedidos = citylink.getPedidos();
        int cantPedidos = citylink.getContadorPedidos();

        System.out.println("--- Importe Facturado por Pedidos con Vehículo ---");
        for (int i = 0; i < cantPedidos; i++) {
            Facturable f = pedidos[i];
            double importe = f.calcularImporte();
            if (importe > 0) {
                System.out.printf("Pedido #%d (%s): $%.2f\n", 
                    pedidos[i].getCodigo(), pedidos[i].getClass().getSimpleName(), importe);
                totalFacturado += importe;
            }
        }
        System.out.printf("TOTAL FACTURADO POR ENVIOS: $%.2f\n\n", totalFacturado);

        double totalComisiones = 0.0;
        Repartidor[] repartidores = citylink.getRepartidores();
        int cantRepartidores = citylink.getContadorRepartidores();

        System.out.println("--- Comisiones Ganadas por Repartidores (10% sobre entregados) ---");
        for (int i = 0; i < cantRepartidores; i++) {
            Facturable f = repartidores[i];
            double comision = f.calcularImporte();
            System.out.printf("Repartidor %s (Legajo %d): $%.2f\n", 
                repartidores[i].getNombre(), repartidores[i].getLegajo(), comision);
            totalComisiones += comision;
        }
        System.out.printf("TOTAL DE COMISIONES A PAGAR: $%.2f\n", totalComisiones);
        System.out.println("====================================================================");
    }
}
