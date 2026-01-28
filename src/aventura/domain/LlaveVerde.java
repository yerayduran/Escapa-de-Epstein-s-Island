package aventura.domain;

/**
 * Representa una llave especial de color verde dentro del juego.
 * <p>
 * Esta clase hereda de {@link Llave} y define una llave con un
 * código de seguridad fijo ("5973"), utilizado para abrir
 * puertas o contenedores concretos del juego.
 * </p>
 *
 * Aunque el constructor recibe un parámetro {@code codigoSeguridad},
 * este se ignora, ya que el código está predefinido.
 *
 * Se utiliza principalmente para desbloquear la salida final.
 *
 * @author Yeray Durán
 * @version 1.0
 */
public class LlaveVerde extends Llave {

    /**
     * Crea una nueva llave verde con código fijo.
     *
     * @param nombre Nombre de la llave.
     * @param descripcion Descripción de la llave.
     * @param visible Indica si la llave es visible.
     * @param codigoSeguridad Parámetro ignorado (el código real es "5973").
     */
    public LlaveVerde(String nombre, String descripcion, boolean visible, String codigoSeguridad) {
        super(nombre, descripcion, visible, "5973");
    }
}