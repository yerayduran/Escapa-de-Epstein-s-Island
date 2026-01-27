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
    public RespuestaAccion coger(Objeto objeto) throws InventarioLlenoException {

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
     *         {@code false} en caso contrario.
     */
    public boolean soltarPorNombre(String nombreObjeto) {

        String n = nombreObjeto.toLowerCase();

        for (int i = 0; i < siguienteLibre; i++) {

            Objeto obj = inventario[i];

            if (obj != null && n.equals(obj.getNombre().toLowerCase())) {

                System.arraycopy(inventario, i + 1, inventario, i, siguienteLibre - i - 1);

                inventario[--siguienteLibre] = null;

                return true;
            }
        }

        return false;
    }

    /**
     * Comprueba si el jugador posee un objeto concreto.
     *
     * @param objeto Objeto a comprobar.
     * @return {@code true} si lo tiene, {@code false} en caso contrario.
     */
    public boolean tieneObjeto(Objeto objeto) {

        for (int i = 0; i < siguienteLibre; i++) {

            if (inventario[i] == objeto) {
                return true;
            }
        }

        return false;
    }

    /**
     * Busca una llave válida para abrir un contenedor.
     *
     * @param contenedor Contenedor que se desea abrir.
     * @return Llave adecuada o {@code null} si no existe.
     */
    public Llave buscarLlaveParaContenedor(Contenedor contenedor) {

        if (contenedor.getCodigoNecesario() == null) {
            return null;
        }
        for (int i = 0; i < siguienteLibre; i++) {
            if (inventario[i] instanceof Llave llave && llave.getCodigoSeguridad().equals(contenedor.getCodigoNecesario())) {
                return llave;
            }
        }
        return null;
    }

    /**
     * Busca una llave en el inventario según su código.
     *
     * @param codigo Código de seguridad.
     * @return Llave encontrada o {@code null}.
     */
    public Llave buscarLlavePorCodigo(String codigo) {
        if (codigo == null) return null;

        for (int i = 0; i < siguienteLibre; i++) {

            Objeto obj = inventario[i];
            if (obj instanceof Llave llave && codigo.equals(llave.getCodigoSeguridad())) {
                return llave;
            }
        }

        return null;
    }
}