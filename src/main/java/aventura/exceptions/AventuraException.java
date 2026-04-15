package aventura.exceptions;

/**
 * Excepción base para todos los errores específicos del motor de aventura.
 * <p>
 * Permite distinguir claramente entre fallos internos del juego y
 * excepciones genéricas de Java, facilitando el control de errores
 * y la depuración.
 * </p>
 *
 * @author Manuel Pérez
 * @version 1.0
 */
public class AventuraException extends Exception {

    /**
     * Crea una nueva excepción de aventura con un mensaje descriptivo.
     *
     * @param message Descripción del error ocurrido.
     */
    public AventuraException(String message) {
        super(message);
    }
}