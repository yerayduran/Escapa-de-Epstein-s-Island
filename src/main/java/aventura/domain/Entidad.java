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
 * @author Yeray Durán y Manuel Pérez
 * @version 1.0
 */
public abstract class Entidad {


    private String nombre;
    private String descripcion;

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


    public String getNombre() {
        return nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }


    @Override
    public String toString() {
        return nombre + ": " + getDescripcion();
    }
}