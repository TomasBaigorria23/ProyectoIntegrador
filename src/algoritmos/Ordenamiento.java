package algoritmos;

import java.util.List;
import modelo.Pedido;

public class Ordenamiento {

    // Constantes para elegir el criterio de ordenamiento
    public static final int POR_COSTO = 1;
    public static final int POR_DISTANCIA = 2;
    public static final int POR_FECHA = 3;

    /**
     * Compara dos pedidos según el criterio seleccionado.
     * Retorna:
     *  < 0 si p1 va antes que p2
     *    0 si son iguales
     *  > 0 si p1 va después que p2
     */
    private static int comparar(Pedido p1, Pedido p2, int criterio) {
        switch (criterio) {
            case POR_COSTO:
                // Comparamos por costo facturable
                return Double.compare(p1.calcularImporte(), p2.calcularImporte());
            case POR_DISTANCIA:
                // Comparamos por distancia en km
                return Double.compare(p1.getDistanciakm(), p2.getDistanciakm());
            case POR_FECHA:
                // Comparamos por fecha y hora de registro
                return p1.getFechaHora().compareTo(p2.getFechaHora());
            default:
                return Integer.compare(p1.getCodigo(), p2.getCodigo());
        }
    }

    // ==========================================================
    //            ALGORITMO 1: BURBUJA (BUBBLE SORT)
    // ==========================================================
    /**
     * Ordena la lista de pedidos usando el método Burbuja.
     * Complejidad: O(n^2).
     * 
     * @param pedidos Lista a ordenar.
     * @param criterio Criterio de orden (POR_COSTO, POR_DISTANCIA, POR_FECHA).
     */
    public static void ordenarBurbuja(List<Pedido> pedidos, int criterio) {
        if (pedidos == null || pedidos.size() < 2) return;

        int n = pedidos.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (comparar(pedidos.get(j), pedidos.get(j + 1), criterio) > 0) {
                    // Intercambio (swap)
                    Pedido temp = pedidos.get(j);
                    pedidos.set(j, pedidos.get(j + 1));
                    pedidos.set(j + 1, temp);
                }
            }
        }
    }

    // ==========================================================
    //            ALGORITMO 2: QUICKSORT (RECURSIVO)
    // ==========================================================
    /**
     * Método público para iniciar el ordenamiento Quicksort.
     * Complejidad promedio: O(n log n).
     * 
     * @param pedidos Lista a ordenar.
     * @param criterio Criterio de orden (POR_COSTO, POR_DISTANCIA, POR_FECHA).
     */
    public static void ordenarQuicksort(List<Pedido> pedidos, int criterio) {
        if (pedidos == null || pedidos.size() < 2) return;
        quicksortRecursivo(pedidos, 0, pedidos.size() - 1, criterio);
    }

    private static void quicksortRecursivo(List<Pedido> pedidos, int inicio, int fin, int criterio) {
        if (inicio < fin) {
            int indicePivote = particionar(pedidos, inicio, fin, criterio);
            quicksortRecursivo(pedidos, inicio, indicePivote - 1, criterio);
            quicksortRecursivo(pedidos, indicePivote + 1, fin, criterio);
        }
    }

    private static int particionar(List<Pedido> pedidos, int inicio, int fin, int criterio) {
        Pedido pivote = pedidos.get(fin);
        int i = inicio - 1;

        for (int j = inicio; j < fin; j++) {
            if (comparar(pedidos.get(j), pivote, criterio) <= 0) {
                i++;
                // Swap pedidos[i] con pedidos[j]
                Pedido temp = pedidos.get(i);
                pedidos.set(i, pedidos.get(j));
                pedidos.set(j, temp);
            }
        }

        // Colocar el pivote en su posición final correcta
        Pedido temp = pedidos.get(i + 1);
        pedidos.set(i + 1, pedidos.get(fin));
        pedidos.set(fin, temp);

        return i + 1;
    }
}