package aventura.domain;

import aventura.interfaces.Combinable;

/**
 * Representa la cabeza de una llave dentro del sistema de aventura.
 * <p>
 * Esta clase extiende {@link Item} e implementa la interfaz {@link Combinable},
 * permitiendo que la cabeza de la llave pueda combinarse con otros objetos
 * compatibles para formar una llave completa.
 * </p>
 *
 * <p>
 * Su función principal es combinarse con un {@link SoporteLlave} para generar
 * una instancia de {@link LlaveVerde}, representando la llave completa.
 * </p>
 *
 * @author Manuel Pérez
 * @version 1.0
 */
public class CabezaLlave extends Item implements Combinable {

    /**
     * Crea una nueva cabeza de llave.
     *
     * @param nombre      nombre identificativo del objeto.
     * @param descripcion descripción detallada del objeto.
     * @param visible     indica si el objeto es visible para el jugador.
     */
    public CabezaLlave(String nombre, String descripcion, boolean visible) {
        super(nombre, descripcion, visible);
    }

    /**
     * Intenta combinar esta cabeza de llave con otro objeto.
     * <p>
     * Si el objeto recibido es una instancia de {@link SoporteLlave}, se genera
     * una nueva {@link LlaveVerde} completamente ensamblada. En caso contrario,
     * la combinación no es válida y se devuelve {@code null}.
     * </p>
     *
     * @param otro objeto con el que se intenta combinar.
     * @return una {@link LlaveVerde} si la combinación es válida; {@code null} en caso contrario.
     */
    @Override
    public Objeto combinar(Objeto otro) {
        if (otro instanceof SoporteLlave) {
            return new LlaveVerde("Llave Verde Hacendado", "Llave verde completa Hacendado (5973).", true, "5973");
        }
        return null;
    }
}