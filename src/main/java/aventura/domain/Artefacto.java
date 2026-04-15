package aventura.domain;

/**
 * Representa el artefacto final necesario para desbloquear la salida del juego.
 * <p>
 * Esta clase hereda de {@link Llave} y actúa como un objeto clave especial
 * con un código de seguridad fijo, utilizado para activar la Puerta del Juicio
 * y permitir el acceso a la salida final.
 * </p>
 *
 * <p>
 * El artefacto es una reliquia ensamblada a partir de piezas encontradas
 * en los distintos escenarios del pueblo. Su función es servir como llave
 * final en el último tramo de la aventura.
 * </p>
 *
 * @author Yeray Durán
 * @version 2.1
 */
public class Artefacto extends Llave {

    /**
     * Crea un nuevo artefacto con los datos indicados.
     *
     * @param nombre          Nombre del artefacto.
     * @param descripcion     Descripción del artefacto.
     * @param visible         Indica si el objeto es visible en la habitación.
     * @param codigoSeguridad Código necesario para abrir la puerta o contenedor.
     */
    public Artefacto(String nombre, String descripcion, boolean visible, String codigoSeguridad) {
        super(nombre, descripcion, visible, codigoSeguridad);
    }
}