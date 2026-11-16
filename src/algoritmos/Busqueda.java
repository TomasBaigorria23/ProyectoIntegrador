package algoritmos;

import java.util.List;
import modelo.Cliente;
import modelo.Pedido;

public class Busqueda {

    /**
     * Búsqueda Lineal de un pedido por su código.
     * Recorre la lista de principio a fin elemento por elemento.
     * Funciona sobre listas ordenadas o desordenadas.
     * 
     * @param pedidos Lista de pedidos donde buscar.
     * @param codigo Código del pedido buscado.
     * @return El Pedido si fue encontrado, o null si no existe.
     */
    public static Pedido busquedaLinealPedido(List<Pedido> pedidos, int codigo) {
        if (pedidos == null) return null;

        for (Pedido p : pedidos) {
            if (p.getCodigo() == codigo) {
                return p; // Encontrado
            }
        }
        return null; // No existe en la lista
    }

    /**
     * Búsqueda Lineal de un cliente por su nombre.
     * Compara ignorando mayúsculas y minúsculas.
     * 
     * @param clientes Lista de clientes.
     * @param nombre Nombre a buscar.
     * @return El Cliente si fue encontrado, o null si no existe.
     */
    public static Cliente busquedaLinealCliente(List<Cliente> clientes, String nombre) {
        if (clientes == null || nombre == null) return null;

        for (Cliente c : clientes) {
            if (c.getNombre().equalsIgnoreCase(nombre.trim())) {
                return c; // Encontrado
            }
        }
        return null;
    }

    /**
     * Búsqueda Binaria de un pedido por su código.
     * REQUISITO: La lista DEBE estar previamente ordenada de menor a mayor por código.
     * Divide el espacio de búsqueda a la mitad en cada iteración (O(log n)).
     * 
     * @param pedidos Lista de pedidos ordenada por código.
     * @param codigo Código numérico a buscar.
     * @return El Pedido encontrado, o null si no existe.
     */
    public static Pedido busquedaBinariaPedido(List<Pedido> pedidos, int codigo) {
        if (pedidos == null || pedidos.isEmpty()) return null;

        int inicio = 0;
        int fin = pedidos.size() - 1;

        while (inicio <= fin) {
            // Calculamos el índice medio
            int medio = inicio + (fin - inicio) / 2;
            int codigoMedio = pedidos.get(medio).getCodigo();

            if (codigoMedio == codigo) {
                return pedidos.get(medio); // ¡Encontrado!
            } else if (codigo < codigoMedio) {
                // El elemento está en la mitad izquierda
                fin = medio - 1;
            } else {
                // El elemento está en la mitad derecha
                inicio = medio + 1;
            }
        }

        return null; // No se encontró
    }
}