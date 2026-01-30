package aventura.interfaces;

/**
 * Indica que un objeto del juego contiene información legible por el jugador.
 * <p>
 * Los objetos que implementen esta interfaz deben proporcionar el texto que
 * se mostrará cuando el jugador intente leerlos.
 * </p>
 *
 * @author Manuel Pérez
 * @version 1.0
 */
public interface Leible {

    /**
     * Devuelve el contenido legible del objeto.
     *
     * @return Texto que el jugador verá al leer el objeto.
     */
    String leer();
}