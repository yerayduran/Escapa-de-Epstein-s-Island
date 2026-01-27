package aventura.domain;

import aventura.interfaces.Inventariable;

/**
 * Representa un objeto básico que puede ser almacenado
 * en el inventario del jugador.
 * <p>
 * Un {@code Item} es un tipo de {@link Objeto} que implementa
 * la interfaz {@link Inventariable}, lo que indica que puede
 * ser recogido y guardado.
 * </p>
 *
 * Esta clase sirve como base para otros objetos más específicos
 * que puedan añadirse al inventario.
 *
 * @author Manuel Pérez
 * @version 1.0
 */
public class Item extends Objeto implements Inventariable {

    /**
     * Crea un nuevo item con los datos especificados.
     *
     * @param nombre Nombre del objeto.
     * @param descripcion Descripción del objeto.
     * @param visible Indica si el objeto es visible en la habitación.
     */
    public Item(String nombre, String descripcion, boolean visible) {
        super(nombre, descripcion, visible);
    }

}
