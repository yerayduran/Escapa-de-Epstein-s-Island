package aventura.domain;

/**
 * Representa el resultado de ejecutar una acción dentro del juego.
 * <p>
 * Un resultado incluye:
 * <ul>
 *   <li>Si la acción fue exitosa o no.</li>
 *   <li>Un mensaje descriptivo para el jugador.</li>
 * </ul>
 *
 * @author Yeray Durán
 * @version 1.0
 */
public record RespuestaAccion(boolean esExito, String mensaje) {
}