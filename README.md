# Proyecto Integrador - Programación I (2026)
## Sistema de Gestión de Reparto Urbano: CityLink (Entregable 2)

---

### 👤 Datos del Autor y Equipo
* **Estudiantes:** Matias Figueroa, Tomas Baigorria, Lautaro Kesler
* **Equipo de Desarrollo:** Tomas Baigorria, Lautaro Kesler, Matias Figueroa
* **Materia:** Programación I (2026)
* **Repositorio Git:** `MatiasFigueroa` (Entregable 2)

---

### 🚀 Instrucciones para Compilar y Ejecutar

El proyecto está desarrollado en Java estándar (sin dependencias externas) y organizado en cuatro paquetes: `app`, `modelo`, `algoritmos` y `excepciones`.

#### Opción 1: Desde la Terminal (Línea de Comandos)
1. Abrir la terminal y situarse en la raíz del proyecto:
   ```bash
   cd "c:\Users\figue\OneDrive\Desktop\FACULTAD\Programacion I\ProyectoIntegradorI\ProyectoIntegrador"
   ```
2. Compilar todo el proyecto hacia la carpeta `bin`:
   ```bash
   javac -d bin -sourcepath src src/app/Main.java
   ```
3. Ejecutar la aplicación:
   ```bash
   java -cp bin app.Main
   ```

#### Opción 2: Desde un IDE (VS Code / IntelliJ IDEA / Eclipse)
1. Abrir la carpeta del proyecto en el IDE.
2. Navegar al archivo `src/app/Main.java`.
3. Ejecutar mediante el botón **Run Java** o `Shift + F5`.

---

### 🏛️ Resumen de Arquitectura del Sistema (Guía para la Defensa Oral)

#### 1. Paradigma Orientado a Objetos (POO)
* **Abstracción y Herencia:**
  * `Pedido` (Clase Abstracta): Define los atributos comunes (`codigo`, `cliente`, `destino`, `pesokg`, `distanciakm`, `estado`, `fechaHora`, `vehiculoAsignado`) y el método abstracto `calcularCosto(Vehiculo v)`. Subclases: `PedidoNormal`, `PedidoUrgente` (+50% recargo), `PedidoFragil` (+$800 fijo, restricción de transporte).
  * `Vehiculo` (Clase Abstracta): Define `patente`, capacidad de carga, velocidad promedio y costo base por km. Subclases: `Bicicleta` (hasta 10 kg, no transporta frágiles), `Moto` (hasta 30 kg), `Camioneta` (hasta 500 kg).
* **Polimorfismo:**
  * Resolución dinámica del método `calcularCosto()` en cada tipo de pedido según el vehículo asignado.
  * Cálculo polimórfico del tiempo estimado de entrega `v.calcularTiempoEntrega(distancia)`.
* **Interfaces de Negocio:**
  * `Facturable`: Implementada por `Pedido` (importe de envío facturado) y por `Repartidor` (comisión del 10% sobre pedidos entregados).
  * `Rastreable`: Implementada por `Pedido` (estado actual) y `Repartidor` (disponibilidad y pedidos en camino).
* **Manejo Robusto de Excepciones:**
  * Jerarquía de excepciones personalizadas (`excepciones`): `AlmacenamientoLlenoException`, `CapacidadExcedidaException`, `ClienteNoEncontradoException`, `EstadoInvalidoException` y `PedidoInvalidoException`.
  * Validación preventiva de entradas por teclado (`NumberFormatException`) y bloque `finally` para asegurar el cierre de procesos críticos.

---

#### 2. Colecciones y Estructuras de Datos (Java Collections Framework)
Para esta etapa (Entregable 2), se migraron y seleccionaron las estructuras óptimas para cada caso de uso:

1. **`TreeMap<Integer, Cliente>` (Árbol Rojo-Negro):**
   * Almacena a los clientes ordenados automáticamente por su clave primaria (`codigo`).
   * Búsquedas, inserciones y eliminaciones en complejidad logarítmica **$O(\log n)$**.
2. **`ArrayList<Pedido>` y `ArrayList<Repartidor>`:**
   * Listas dinámicas de acceso indexado directo en **$O(1)$** para almacenar la dotación de repartidores y el historial central de pedidos.
3. **`LinkedList<Pedido>` (Cola de Prioridad):**
   * Implementa una cola de despacho con prioridad donde los pedidos `PedidoUrgente` se encolan prioritariamente al frente, mientras que los normales y frágiles se encolan al final (FIFO).
   * Despacho y atención en **$O(1)$** con `poll()`.
4. **`ArrayDeque<Operacion>` (Pila de Deshacer / Undo):**
   * Estructura **LIFO (Last-In, First-Out)** moderna y de alto rendimiento de Java para el historial de operaciones ("Ctrl + Z").
   * Registra una instantánea de cada alta de pedido, asignación a repartidor o cambio de estado.
   * La opción **Deshacer** extrae la última acción con `pop()` y revierte los cambios de manera exacta y consistente sin violar invariantes de negocio.

---

#### 3. Algoritmos Implementados (`src/algoritmos/`)

* **Algoritmos de Ordenamiento (`Ordenamiento.java`):**
  * **Método Burbuja (Bubble Sort):** Comparaciones e intercambios adyacentes con complejidad temporal **$O(n^2)$**.
  * **QuickSort (Recursivo):** Algoritmo de particionado por pivote ("divide y vencerás") con complejidad promedio **$O(n \log n)$**.
  * Ambos algoritmos soportan múltiples criterios de ordenamiento:
    1. Por Costo de Envío (Importe facturable).
    2. Por Distancia (en km).
    3. Por Fecha y Hora de Registro (`LocalDateTime`).
* **Algoritmos de Búsqueda (`Busqueda.java`):**
  * **Búsqueda Lineal:** Recorrido secuencial sobre listas con complejidad **$O(n)$**.
  * **Búsqueda Binaria:** Búsqueda logarítmica **$O(\log n)$** sobre pedidos previamente ordenados por código.
* **Algoritmo de Intercalación / Merge (`Intercalacion.java`):**
  * Toma dos listas ya ordenadas por código (Turno Mañana y Turno Tarde) y las combina en una única lista ordenada en una sola pasada con dos punteros, con complejidad lineal **$O(n + m)$** sin necesidad de reordenar.

---

### 🖥️ Menú Interactivo del Sistema (15 Opciones)

1. **Registrar cliente:** Alta en el `TreeMap`.
2. **Registrar repartidor con su vehículo:** Alta con control de duplicados y asignación de tipo de vehículo.
3. **Registrar pedido:** Creación de pedido normal, urgente o frágil, guardado en lista y encolado prioritario.
4. **Asignar pedido a un repartidor:** Asignación con control de capacidad física y registro en la Pila de Deshacer.
5. **Cambiar estado de un pedido:** Transición controlada (`PENDIENTE` $\to$ `EN_CAMINO` $\to$ `ENTREGADO` / `CANCELADO`) y registro en la Pila de Deshacer.
6. **Buscar cliente:** Por código (árbol en $O(\log n)$) o por nombre (lineal).
7. **Listar pedidos:** Listado completo con cálculo polimórfico de costos y tiempos de viaje.
8. **Consultar estado (Rastreable):** Monitoreo de pedidos y disponibilidad de repartidores.
9. **Ver facturación e importe de comisiones:** Cálculo polimórfico de importes y comisiones del 10%.
10. **Deshacer última acción (Ctrl + Z con ArrayDeque):** Revierte la última alta, asignación o cambio de estado.
11. **Ordenar pedidos (Burbuja vs QuickSort):** Ejecución y comparación temporal por Costo, Distancia o Fecha.
12. **Intercalar turnos de pedidos:** Demostración del algoritmo de intercalación $O(n + m)$ entre Turno Mañana y Tarde.
13. **Atender/Despachar pedido prioritario:** Extracción del pedido más urgente mediante la `LinkedList`.
14. **Búsqueda de pedido (Lineal vs Binaria):** Comparación de métodos de búsqueda por código identificador.
15. **Salir.**

---

### 🧪 Pruebas de Escritorio Realizadas

1. **Prueba de Undo (Pila ArrayDeque):**
   * Se asignó un pedido a un repartidor (pasó a `EN_CAMINO`). Se ejecutó la Opción 10 (Deshacer) y el pedido volvió a `PENDIENTE`, desvinculando el vehículo y liberando el cupo del repartidor.
   * Se cambió un estado a `ENTREGADO` (sumando comisión). Al presionar Deshacer, volvió a `EN_CAMINO` y la comisión se recalculó a $0.
2. **Prueba de Cola de Prioridad (`LinkedList`):**
   * Se cargaron pedidos normales y urgentes intercalados. Al despachar sucesivamente, los `PedidoUrgente` salieron en primer lugar independientemente de su orden de llegada.
3. **Prueba de Rendimiento de Algoritmos:**
   * Comparación de tiempos en nanosegundos entre Burbuja ($~2.3 \times 10^6$ ns) y QuickSort ($~3.3 \times 10^4$ ns), demostrando la eficiencia del particionado recursivo $O(n \log n)$.
4. **Prueba de Intercalación:**
   * Turno Mañana (códigos 1, 3, 5, 7) y Turno Tarde (códigos 2, 4, 6, 8) se fusionaron en un solo recorrido produciendo la secuencia `1, 2, 3, 4, 5, 6, 7, 8`.

---

### 🤖 Declaración de Uso de Inteligencia Artificial

* **Herramienta Utilizada:** Antigravity / Gemini 3.8 Flash (Google DeepMind).
* **¿Para qué se utilizó?:**
  1. Diseño e integración del patrón Command/Snapshot para la Pila de Deshacer (`ArrayDeque`) y la clase `Operacion`.
  2. Conexión de las nuevas funcionalidades al menú de consola interactivo de `Main.java` garantizando retrocompatibilidad con las opciones del Entregable 1.
  3. Redacción y estructuración de la documentación técnica en `README.md` con enfoque en la defensa oral.
* **¿Cómo se verificó el resultado?:**
  * Todo el código fue inspeccionado manualmente para asegurar que cumple estrictamente con las pautas de la materia (sin librerías externas, uso nativo del Java Collections Framework).
  * Se compilaron y ejecutaron casos de prueba automatizados en consola verificando la no ruptura de invariantes, el manejo de excepciones y la exactitud de los algoritmos de ordenamiento, búsqueda, cola e intercalación.
