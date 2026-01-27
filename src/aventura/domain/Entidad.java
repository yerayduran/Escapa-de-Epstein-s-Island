package aventura.domain;

/**
 * Representa una entidad base dentro del juego.
 * <p>
 * Esta clase abstracta sirve como clase padre para todos los elementos
 * que poseen un nombre y una descripción, como objetos, muebles,
 * personajes, etc.
 * </p>
 *
 * Proporciona métodos comunes para acceder a esta información
 * y una implementación básica del método {@link #toString()}.
 *
 * @author Yeray Durán
 * @version 1.0
 */
public abstract class Entidad {

    /**
     * Nombre identificativo de la entidad.
     */
    private final String nombre;

    /**
     * Descripción detallada de la entidad.
     */
    private final String descripcion;

    /**
     * Crea una nueva entidad con los valores indicados.
     *
     * @param nombre Nombre de la entidad.
     * @param descripcion Descripción de la entidad.
     */
    public Entidad(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    /**
     * Devuelve el nombre de la entidad.
     *
     * @return Nombre de la entidad.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve la descripción de la entidad.
     *
     * @return Descripción de la entidad.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Devuelve una representación textual de la entidad.
     * <p>
     * El formato es: {@code nombre: descripcion}
     * </p>
     *
     * @return Cadena representativa de la entidad.
     */
    @Override
    public String toString() {
        return nombre + ": " + getDescripcion();
    }
}