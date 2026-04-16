package aventura.domain;

import aventura.interfaces.Abrible;

/**
 * Representa una puerta dentro del mundo de la aventura.
 * Una puerta es un tipo de {@link Mueble} que puede abrirse o permanecer cerrada,
 * normalmente mediante el uso de un objeto clave como una {@link Llave} o un artefacto.
 *
 * Implementa la interfaz {@link Abrible}, lo que garantiza que cualquier puerta
 * pueda gestionar su estado de apertura mediante los métodos definidos en dicha interfaz.
 *
 * En la nueva ambientación del juego, esta puerta representa la salida final,
 * conocida como la Puerta del Juicio.
 *
 * @author Manuel Pérez
 * @version 2.0
 */
public class Puerta extends Mueble implements Abrible {

    /**
     * Indica si la puerta está actualmente abierta.
     */
    private boolean abierta = false;

    /**
     * Crea una nueva puerta con los atributos básicos heredados de {@link Mueble}.
     *
     * @param nombre      Nombre identificativo de la puerta.
     * @param descripcion Descripción breve de la puerta.
     * @param visible     Indica si la puerta es visible en la escena.
     */
    public Puerta(String nombre, String descripcion, boolean visible) {
        super(nombre, descripcion, visible);
    }

    /**
     * Intenta abrir la puerta utilizando un objeto clave.
     * La puerta solo puede abrirse si:
     * <ul>
     *     <li>No está ya abierta.</li>
     *     <li>La llave proporcionada no es nula.</li>
     *     <li>El código de seguridad de la llave coincide con el requerido.</li>
     * </ul>
     *
     * @param llave Llave u objeto clave utilizado para intentar abrir la puerta.
     * @return Una {@link RespuestaAccion} indicando si la acción tuvo éxito
     *         y un mensaje descriptivo para el jugador.
     */
    @Override
    public RespuestaAccion abrir(Llave llave) {
        if (abierta) {
            return new RespuestaAccion(false, "La puerta ya está abierta.");
        }

        if (llave == null || !"5973".equals(llave.getCodigoSeguridad())) {
            return new RespuestaAccion(false,
                    "La puerta permanece sellada. Necesitas el Artefacto correcto para activar el mecanismo.");
        }

        abierta = true;
        return new RespuestaAccion(true,
                "El Artefacto reacciona con la cerradura. La Puerta del Juicio se abre lentamente.");
    }

    /**
     * Indica si la puerta está abierta.
     *
     * @return {@code true} si la puerta está abierta; {@code false} en caso contrario.
     */
    @Override
    public boolean estaAbierto() {
        return abierta;
    }

    @Override
    public String getCodigoNecesario() {
        return "";
    }

    @Override
    public Objeto getContenido() {
        return null;
    }

    @Override
    public void setContenido(Objeto contenido) {

    }

    @Override
    public void cerrar() {

    }
}