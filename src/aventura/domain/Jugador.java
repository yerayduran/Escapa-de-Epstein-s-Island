package aventura.domain;

import aventura.exceptions.InventarioLlenoException;
import aventura.interfaces.Inventariable;

/**
 * Representa al jugador dentro del juego.
 * <p>
 * El jugador es una entidad capaz de moverse entre habitaciones
 * y gestionar un inventario de objetos.
 * </p>
 *
 * Permite recoger, soltar y consultar objetos,
 * así como buscar llaves para abrir contenedores y puertas.
 *
 * Hereda de {@link Entidad}.
 *
 * @author Yeray Durán
 * @version 1.0
 */
public class Jugador extends Entidad {

    /**
     * Array que representa el inventario del jugador.
     */
    private Objeto[] inventario;

    /**
     * Índice de la siguiente posición libre del inventario.
     */
    private int siguienteLibre;

    /**
     * Posición actual del jugador en el mapa.
     */
    private int posicion;

    /**
     * Crea un nuevo jugador con un inventario del tamaño indicado.
     *
     * @param tamInventario Tamaño máximo del inventario.
     */
    public Jugador(int tamInventario) {
        super("Jugador", "Tú mismo");
        this.inventario = new Objeto[tamInventario];
        this.siguienteLibre = 0;
        this.posicion = 0;
    }

    /**
     * Devuelve el inventario del jugador.
     *
     * @return Array de objetos del inventario.
     */
    public Objeto[] getInventario() {
        return inventario;
    }

    /**
     * Devuelve la posición actual del jugador.
     *
     * @return Índice de la habitación actual.
     */
    public int getPosicion() {
        return posicion;
    }

    /**
     * Establece la posición actual del jugador.
     *
     * @param posicion Nueva posición en el mapa.
     */
    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    /**
     * Permite al jugador recoger un objeto y guardarlo
     * en su inventario.
     *
     * @param objeto Objeto que se desea recoger.
     * @return Resultado de la acción.
     * @throws InventarioLlenoException Si el inventario está lleno.
     */
    public RespuestaAccion coger(Objeto objeto)
            throws InventarioLlenoException {

        if (!(objeto instanceof Inventariable)) {
            return new RespuestaAccion(false, "Este objeto no se puede coger.");
        }

        if (siguienteLibre >= inventario.length) {
            throw new InventarioLlenoException("Inventario lleno");
        }

        inventario[siguienteLibre++] = objeto;

        return new RespuestaAccion(true, "Cogido correctamente");
    }

    /**
     * Elimina un objeto del inventario según su nombre.
     * <p>
     * Reorganiza el inventario tras eliminarlo.
     * </p>
     *
     * @param nombreObjeto Nombre del objeto a soltar.
     * @return {@code true} si se elimina correctamente,
     * {@code false} en caso contrario.
     */
    public boolean soltarPorNombre(String nombreObjeto) {

        String n = nombreObjeto.toLowerCase();

        for (int i = 0; i < siguienteLibre; i++) {

            Objeto obj = inventario[i];

            if (obj != null &&
                    n.equals(obj.getNombre().toLowerCase())) {

                System.arraycopy(
                        inventario,
                        i + 1,
                        inventario,
                        i,
                        siguienteLibre - i - 1
                );

                inventario[--siguienteLibre] = null;

                return true;
            }
        }

        return false;
    }

}