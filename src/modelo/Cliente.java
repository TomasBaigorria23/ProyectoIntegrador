package modelo;

public class Cliente {
    
    // 1. Atributos privados (Encapsulamiento)
    private int codigo;
    private String nombre;
    private String telefono;
    private String zona;

    // 2. Constructor vacío
    public Cliente() {
    }

    // 3. Constructor completo
    public Cliente(int codigo, String nombre, String telefono, String zona) {
        // Llamamos a los setters para aprovechar las validaciones desde el momento de la creación
        setCodigo(codigo);
        setNombre(nombre);
        setTelefono(telefono);
        setZona(zona);
    }

    // 4. Getters y Setters con validación
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        if (codigo <= 0) {
            // Validamos que el código sea un número positivo
            throw new IllegalArgumentException("El código del cliente debe ser mayor a cero.");
        }
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío.");
        }
        this.telefono = telefono;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        if (zona == null || zona.trim().isEmpty()) {
            throw new IllegalArgumentException("La zona de la ciudad no puede estar vacía.");
        }
        this.zona = zona;
    }

    // 5. Método toString
    @Override
    public String toString() {
        return "Cliente [Código=" + codigo + ", Nombre=" + nombre + 
               ", Teléfono=" + telefono + ", Zona=" + zona + "]";
    }
}