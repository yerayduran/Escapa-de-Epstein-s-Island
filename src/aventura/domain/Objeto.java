package aventura.domain;

/**
 * Clase base abstracta para todos los objetos del mundo de la aventura.
 * Un objeto es una {@link Entidad} que puede o no ser visible para el jugador.
 *
 * La visibilidad determina si el objeto aparece en la descripción de la escena
 * o si debe permanecer oculto hasta que algún evento o acción lo revele.
 *
 * @author Yeray Durán
 * @version 1.0
 */
public abstract class Objeto extends Entidad {

    /**
     * Indica si el objeto es visible para el jugador.
     * Puede cambiar dinámicamente durante la partida.
     */
    private boolean visible;

    /**
     * Construye un nuevo objeto con los atributos básicos heredados de {@link Entidad}
     * y un estado inicial de visibilidad.
     *
     * @param nombre      Nombre identificativo del objeto.
     * @param descripcion Descripción breve del objeto.
     * @param visible     Estado inicial de visibilidad del objeto.
     */
    public Objeto(String nombre, String descripcion, boolean visible) {
        super(nombre, descripcion);
        this.visible = visible;
    }

    /**
     * Indica si el objeto es visible actualmente.
     *
     * @return {@code true} si el objeto es visible; {@code false} en caso contrario.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Modifica el estado de visibilidad del objeto.
     *
     * @param visible Nuevo estado de visibilidad.
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
