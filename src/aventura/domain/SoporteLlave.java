package aventura.domain;

import aventura.interfaces.Combinable;

/**
 * Representa un soporte de llave que puede combinarse con una cabeza de llave
 * para formar una llave completa.
 *
 * @author Yeray Durán
 * @version 1.0
 */
public class SoporteLlave extends Item implements Combinable {

    /**
     * Crea un nuevo soporte de llave.
     *
     * @param nombre      Nombre del objeto.
     * @param descripcion Descripción del objeto.
     * @param visible     Indica si el objeto es visible para el jugador.
     */
    public SoporteLlave(String nombre, String descripcion, boolean visible) {
        super(nombre, descripcion, visible);
    }

    /**
     * Intenta combinar este soporte con otro objeto.
     * Si el objeto proporcionado es una {@link ConsoladorVibratorio}, se genera una
     * {@link LlaveVerde} completa.
     *
     * @param otro Objeto con el que se intenta combinar.
     * @return La llave completa si la combinación es válida; en caso contrario, null.
     */
    @Override
    public Objeto combinar(Objeto otro) {
        if (otro instanceof ConsoladorVibratorio) {
            return new LlaveVerde("Llave Verde Hacendado", "Llave verde completa Hacendado (5973).", true, "5973");
        }
        return null;
    }
}
