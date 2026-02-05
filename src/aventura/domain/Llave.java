package aventura.domain;

/**
 * Representa una llave dentro del juego.
 * <p>
 * Una llave es un tipo especial de {@link Item} que permite
 * abrir contenedores o puertas protegidas mediante un código
 * de seguridad.
 * </p>
 *
 * Cada llave está asociada a un código único que debe coincidir
 * con el del contenedor correspondiente.
 *
 * Hereda de {@link Item}.
 *
 * @author Manuel Pérez
 * @version 1.0
 */
public class Llave extends Item {

    /**
     * Código de seguridad asociado a la llave.
     */
    private final String codigoSeguridad;

    /**
     * Crea una nueva llave con los datos indicados.
     *
     * @param nombre Nombre de la llave.
     * @param descripcion Descripción de la llave.
     * @param visible Indica si la llave es visible en la habitación.
     * @param codigoSeguridad Código necesario para abrir el contenedor.
     */
    public Llave(String nombre, String descripcion, boolean visible, String codigoSeguridad) {
        super(nombre, descripcion, visible);
        this.codigoSeguridad = codigoSeguridad;
    }

    /**
     * Devuelve el código de seguridad de la llave.
     *
     * @return Código de seguridad.
     */
    public String getCodigoSeguridad() {
        return codigoSeguridad;
    }
}