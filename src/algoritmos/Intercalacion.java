package algoritmos;

import java.util.ArrayList;
import java.util.List;
import modelo.Pedido;

public class Intercalacion {

    /**
     * Combina dos listas de pedidos que YA están ordenadas por código
     * en una única lista combinada ordenada, en una sola pasada O(n + m).
     * 
     * @param turnoManana Lista de pedidos ordenada por código.
     * @param turnoTarde Lista de pedidos ordenada por código.
     * @return Nueva lista combinada y ordenada sin reordenar.
     */
    public static List<Pedido> intercalarPorCodigo(List<Pedido> turnoManana, List<Pedido> turnoTarde) {
        List<Pedido> resultado = new ArrayList<>();

        if (turnoManana == null && turnoTarde == null) return resultado;
        if (turnoManana == null || turnoManana.isEmpty()) return new ArrayList<>(turnoTarde);
        if (turnoTarde == null || turnoTarde.isEmpty()) return new ArrayList<>(turnoManana);

        int i = 0; // Puntero para turno mañana
        int j = 0; // Puntero para turno tarde

        // Recorrido simultáneo comparando elementos de ambos turnos
        while (i < turnoManana.size() && j < turnoTarde.size()) {
            Pedido p1 = turnoManana.get(i);
            Pedido p2 = turnoTarde.get(j);

            if (p1.getCodigo() <= p2.getCodigo()) {
                resultado.add(p1);
                i++;
            } else {
                resultado.add(p2);
                j++;
            }
        }

        // Si quedaron elementos en el turno mañana, los agregamos al final
        while (i < turnoManana.size()) {
            resultado.add(turnoManana.get(i));
            i++;
        }

        // Si quedaron elementos en el turno tarde, los agregamos al final
        while (j < turnoTarde.size()) {
            resultado.add(turnoTarde.get(j));
            j++;
        }

        return resultado;
    }
}