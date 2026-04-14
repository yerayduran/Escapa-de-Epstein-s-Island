package aventura.domain;

import aventura.interfaces.Combinable;

/**
 * Representa un soporte de llave que puede combinarse con una cabeza de llave
 * para formar una llave completa.
 *
 * @author Yeray Durán
 * @version 1.0
 */
public class Objeto1 extends Item implements Combinable {

    /**
     * Crea un nuevo soporte de llave.
     *
     * @param nombre      Nombre del objeto.
     * @param descripcion Descripción del objeto.
     * @param visible     Indica si el objeto es visible para el jugador.
     */
    public Objeto1(String nombre, String descripcion, boolean visible) {
        super(nombre, descripcion, visible);
    }

    /**
     * Intenta combinar este soporte con otro objeto.
     * Si el objeto proporcionado es una {@link Pilas}, se genera una
     * {@link MegaConsolador} completa.
     *
     * @param otro Objeto con el que se intenta combinar.
     * @return La llave completa si la combinación es válida; en caso contrario, null.
     */
    @Override
    public Objeto combinar(Objeto otro) {
        if (otro instanceof Pilas) {
            return new MegaConsolador("Megaconsolador Funcional de P.Diddy", "Objeto1 muy usado por un negro (5973).", true, "5973");
        }
        return null;
    }
}
