package aventura.domain;

/**
 * Representa un mueble dentro del mundo de la aventura.
 * Un mueble es un tipo de {@link Objeto} que normalmente
 * forma parte del escenario y no suele ser portable.
 *
 * Esta clase no añade comportamiento adicional, pero
 * permite tipificar y extender en el futuro si se desea
 * añadir lógica específica para muebles.
 *
 * @author Manuel Pérez
 * @version 1.0
 */
public class Mueble extends Objeto {

    /**
     * Crea un nuevo mueble con los atributos básicos heredados de {@link Objeto}.
     *
     * @param nombre      Nombre identificativo del mueble.
     * @param descripcion Descripción detallada del mueble.
     * @param visible     Indica si el mueble es visible en la escena.
     */
    public Mueble(String nombre, String descripcion, boolean visible) {
        super(nombre, descripcion, visible);
    }
}