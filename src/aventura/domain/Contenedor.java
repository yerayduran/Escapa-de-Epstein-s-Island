package aventura.domain;

import aventura.interfaces.Abrible;

/**
 * Representa un contenedor dentro del juego que puede almacenar un objeto
 * y ser abierto mediante una llave o sin ella, dependiendo de su configuración.
 * <p>
 * Un contenedor es un tipo de mueble que puede estar cerrado o abierto y
 * opcionalmente protegido por un código de seguridad.
 * </p>
 *
 * Implementa la interfaz {@link Abrible}, lo que permite que pueda ser abierto
 * mediante el uso de una llave.
 *
 * @author Yeray Durán
 * @version 1.0
 */
public class Contenedor extends Mueble implements Abrible {

    /**
     * Código necesario para abrir el contenedor.
     * Si es {@code null}, el contenedor puede abrirse sin llave.
     */
    private final String codigoNecesario;

    /**
     * Indica si el contenedor está actualmente abierto.
     */
    private boolean abierto;

    /**
     * Objeto almacenado dentro del contenedor.
     * Puede ser {@code null} si no contiene ningún objeto.
     */
    private Objeto contenido;

    /**
     * Crea un nuevo contenedor con los datos especificados.
     *
     * @param nombre Nombre del contenedor.
     * @param descripcion Descripción del contenedor.
     * @param visible Indica si el contenedor es visible en el juego.
     * @param codigoNecesario Código requerido para abrirlo.
     *                        Si es {@code null}, no se necesita llave.
     * @param contenido Objeto inicial dentro del contenedor.
     */
    public Contenedor(String nombre, String descripcion, boolean visible, String codigoNecesario, Objeto contenido) {
        super(nombre, descripcion, visible);
        this.codigoNecesario = codigoNecesario;
        this.contenido = contenido;
        this.abierto = false;
    }

    /**
     * Devuelve el código necesario para abrir el contenedor.
     *
     * @return Código de seguridad del contenedor o {@code null}
     *         si no necesita llave.
     */
    public String getCodigoNecesario() {
        return codigoNecesario;
    }

    /**
     * Permite ver el objeto almacenado dentro del contenedor
     * sin retirarlo.
     *
     * @return Objeto contenido o {@code null} si está vacío.
     */
    public Objeto verObjetoDentro() {
        return contenido;
    }

    /**
     * Introduce un objeto dentro del contenedor.
     * Reemplaza cualquier objeto que hubiera anteriormente.
     *
     * @param objeto Objeto que se va a guardar.
     */
    public void ponerObjetoDentro(Objeto objeto) {
        this.contenido = objeto;
    }

    /**
     * Retira el objeto almacenado en el contenedor.
     * Tras esta operación, el contenedor quedará vacío.
     */
    public void retirarObjetoDentro() {
        this.contenido = null;
    }

    /**
     * Intenta abrir el contenedor usando una llave.
     * <p>
     * El resultado dependerá de si:
     * <ul>
     *   <li>Ya estaba abierto.</li>
     *   <li>No necesita llave.</li>
     *   <li>La llave tiene el código correcto.</li>
     * </ul>
     * </p>
     *
     * @param llave Llave utilizada para abrir el contenedor.
     *              Puede ser {@code null}.
     * @return Objeto {@link RespuestaAccion} con el resultado de la acción.
     */
    @Override
    public RespuestaAccion abrir(Llave llave) {

        if (abierto) {
            return new RespuestaAccion(false, "Ya está abierto.");
        }

        if (codigoNecesario == null) {
            abierto = true;
            return new RespuestaAccion(true, "Abres el contenedor con las manos.");
        }

        if (llave != null && codigoNecesario.equals(llave.getCodigoSeguridad())) {
            abierto = true;
            return new RespuestaAccion(true, "Abres el contenedor con la llave.");
        }

        return new RespuestaAccion(false, "Necesitas la llave correcta.");
    }

    /**
     * Indica si el contenedor se encuentra abierto.
     *
     * @return {@code true} si está abierto, {@code false} en caso contrario.
     */
    @Override
    public boolean estaAbierto() {
        return abierto;
    }
}