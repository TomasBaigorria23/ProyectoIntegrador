package modelo;

/**
 * Representa a un cliente del sistema CityLink.
 * Contiene información de identificación, contacto y ubicación dentro de la ciudad.
 */
public class Cliente {
    
    // 1. Atributos privados (Encapsulamiento)
    private int codigo;
    private String nombre;
    private String telefono;
    private String zona;

    /**
     * Constructor vacío por defecto.
     */
    public Cliente() {
    }

    /**
     * Constructor completo que valida los datos a través de los setters.
     * 
     * @param codigo Código numérico único del cliente (mayor a 0).
     * @param nombre Nombre y apellido del cliente.
     * @param telefono Teléfono de contacto.
     * @param zona Zona o sector de la ciudad donde reside.
     * @throws IllegalArgumentException Si algún dato es inválido o vacío.
     */
    public Cliente(int codigo, String nombre, String telefono, String zona) {
        setCodigo(codigo);
        setNombre(nombre);
        setTelefono(telefono);
        setZona(zona);
    }

    // 4. Getters y Setters con validación

    /**
     * Obtiene el código único del cliente.
     * @return Código del cliente.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Establece el código del cliente verificando que sea mayor a cero.
     * @param codigo Código numérico positivo.
     * @throws IllegalArgumentException Si el código es menor o igual a cero.
     */
    public void setCodigo(int codigo) {
        if (codigo <= 0) {
            throw new IllegalArgumentException("El código del cliente debe ser mayor a cero.");
        }
        this.codigo = codigo;
    }

    /**
     * Obtiene el nombre del cliente.
     * @return Nombre del cliente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cliente verificando que no sea nulo ni vacío.
     * @param nombre Nombre del cliente.
     * @throws IllegalArgumentException Si el nombre está vacío o es nulo.
     */
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    /**
     * Obtiene el teléfono del cliente.
     * @return Teléfono del cliente.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del cliente verificando que no sea nulo ni vacío.
     * @param telefono Teléfono del cliente.
     * @throws IllegalArgumentException Si el teléfono está vacío o es nulo.
     */
    public void setTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío.");
        }
        this.telefono = telefono;
    }

    /**
     * Obtiene la zona de la ciudad donde reside el cliente.
     * @return Zona de la ciudad.
     */
    public String getZona() {
        return zona;
    }

    /**
     * Establece la zona del cliente verificando que no sea nula ni vacía.
     * @param zona Zona de la ciudad.
     * @throws IllegalArgumentException Si la zona está vacía o es nula.
     */
    public void setZona(String zona) {
        if (zona == null || zona.trim().isEmpty()) {
            throw new IllegalArgumentException("La zona de la ciudad no puede estar vacía.");
        }
        this.zona = zona;
    }

    /**
     * Retorna una representación en texto del cliente.
     * @return Cadena formateada con los datos del cliente.
     */
    @Override
    public String toString() {
        return "Cliente [Código=" + codigo + ", Nombre=" + nombre + 
               ", Teléfono=" + telefono + ", Zona=" + zona + "]";
    }
}
