package aventura.exceptions;

/**
 * Excepción lanzada cuando el jugador intenta añadir un objeto al inventario
 * pero este ya ha alcanzado su capacidad máxima.
 * <p>
 * Permite distinguir este caso concreto de otros errores del juego,
 * facilitando un manejo más preciso en la lógica de la aventura.
 * </p>
 *
 * @author Manuel Pérez
 * @version 1.0
 */
public class InventarioLlenoException extends AventuraException {

    /**
     * Crea una nueva excepción indicando que el inventario está lleno.
     *
     * @param mensaje Mensaje descriptivo del error.
     */
    public InventarioLlenoException(String mensaje) {
        super(mensaje);
    }
}