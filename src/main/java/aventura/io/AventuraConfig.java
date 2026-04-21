/**
 * @author Manuel Pérez y Yeray Durán
 * @version 1.0
 */

package aventura.io;

import aventura.domain.Habitacion;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Guarda toda la configuración inicial para arrancar la partida.
 * Básicamente es el "molde" del que sacamos la historia principal, la sala
 * donde empezamos y el mapa completo con todas las habitaciones del juego.
 *
 * @author Manuel Pérez y Yeray Durán
 * @version 1.0
 */
public class AventuraConfig {

    /**
     * El texto o "lore" de introducción que le salta al jugador nada más empezar.
     */
    private String descripcionGeneral;

    /**
     * El identificador de la sala donde soltamos al jugador al iniciar la partida.
     */
    private String habitacionInicial;

    /**
     * El mapa completo del juego. Conecta el ID de cada sala (String) con
     * su objeto Habitacion correspondiente.
     */
    private Map<String, Habitacion> habitaciones;

    /**
     * Constructor por defecto.
     * Simplemente te prepara un mapa de habitaciones vacío para que no de errores
     * al intentar meterle cosas luego.
     */
    public AventuraConfig() {
        this.habitaciones = new HashMap<>();
    }

    /**
     * Constructor por defecto.
     * Simplemente te prepara un mapa de habitaciones vacío para que no de errores
     * al intentar meterle cosas luego.
     */
    public AventuraConfig(String descripcionGeneral, String habitacionInicial, Map<String, Habitacion> habitaciones) {
        this.descripcionGeneral = descripcionGeneral;
        this.habitacionInicial = habitacionInicial;
        setHabitaciones(habitaciones);
    }

    /**
     * Te da el texto de la historia principal del juego.
     *
     * @return La descripción general.
     */
    public String getDescripcionGeneral() {
        return descripcionGeneral;
    }

    /**
     * Te dice en qué habitación arranca exactamente la aventura.
     *
     * @return El identificador de la habitación inicial.
     */
    public String getHabitacionInicial() {
        return habitacionInicial;

    }

    /**
     * Devuelve el diccionario con todas las salas que existen en la partida.
     *
     * @return Un mapa con las habitaciones.
     */
    public Map<String, Habitacion> getHabitaciones() {
        return habitaciones;
    }

    /**
     * Reemplaza el mapa de habitaciones por uno nuevo.
     * Como medida de seguridad, si le pasas un 'null', te deja un mapa vacío
     * en lugar de romper el juego más adelante.
     *
     * @param habitaciones El nuevo mapa de habitaciones que quieres guardar.
     */
    public void setHabitaciones(Map<String, Habitacion> habitaciones) {
        this.habitaciones = new HashMap<>();
        if (habitaciones != null) {
            this.habitaciones.putAll(habitaciones);
        }
    }

}
