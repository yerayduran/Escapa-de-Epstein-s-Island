package aventura.domain;

import aventura.interfaces.Abrible;


/**
 * Representa un objeto contenedor que puede abrirse y, opcionalmente,
 * requerir una llave para revelar su contenido.
 *
 * @author Yeray Durán y Manuel Perez
 */
public class Contenedor extends Objeto implements Abrible {
    /**
     * Indica si el contenedor está abierto.
     */
    private boolean abierto;
    /**
     * Código necesario para abrirlo; si es {@code null}, no requiere llave.
     */
    private String codigoNecesario; // Si es null, no necesita llave
    /**
     * Objeto almacenado en el interior del contenedor.
     */
    private Objeto contenido; // Lo que hay dentro

    // CONSTRUCTOR 1: Para cosas con llave (Cofres)
    /**
     * Constructor de la clase Contenedor que requiere una llave para abrirse.
     *
     * @param nombre        El nombre del contenedor.
     * @param descripcion   La descripción del contenedor.
     * @param visible       Indica si el contenedor es visible o no.
     * @param codigo        El código necesario para abrir el contenedor.
     * @param contenido     El objeto contenido dentro del contenedor.
     */
    public Contenedor(String nombre, String descripcion, boolean visible, String codigo, Objeto contenido) {
        super(nombre, descripcion, visible);
        this.codigoNecesario = codigo;
        this.contenido = contenido;
        this.abierto = false;
    }

    // CONSTRUCTOR 2: Para cosas sin llave (Cajones, Armarios)
    /**
     * Constructor de la clase Contenedor que no requiere una llave para abrirse.
     *
     * @param nombre        El nombre del contenedor.
     * @param descripcion   La descripción del contenedor.
     * @param visible       Indica si el contenedor es visible o no.
     * @param contenido     El objeto contenido dentro del contenedor.
     */
    public Contenedor(String nombre, String descripcion, boolean visible, Objeto contenido) {
        super(nombre, descripcion, visible);
        this.codigoNecesario = null; // MARCA IMPORTANTE
        this.contenido = contenido;
        this.abierto = false;
    }

    /**
     * Intenta abrir el contenedor con la llave indicada.
     *
     * @param llave llave candidata para abrir el contenedor.
     * @return resultado de la acción con estado y mensaje.
     */
    @Override
    public RespuestaAccion abrir(Llave llave) {
        if (abierto) {
            return new RespuestaAccion(false, "Eso ya está abierto, no pierdas el tiempo.");
        }

        // CASO 1: No necesita llave (El cajón)
        // Caso: Se abre con la mano (null)
        if (this.codigoNecesario == null) {
            abierto = true;
            return new RespuestaAccion(true, "Abres " + getNombre() + " sin problemas.");
        }

        // CASO 2: Necesita llave (El cofre)
        if (llave != null) {
            if (llave.getCodigoSeguridad().equals(this.codigoNecesario)) {
                abierto = true;
                return new RespuestaAccion(true, "Clic. La llave gira y abres " + getNombre());
            }
            else {
                return new RespuestaAccion(false, "La llave no encaja.");
            }
        }

        // CASO 3: Fallo
        return new RespuestaAccion(false, "Está cerrado. Necesitas una llave.");
    }

    /**
     * Indica si el contenedor está abierto actualmente.
     *
     * @return {@code true} si está abierto.
     */
    @Override
    public boolean estaAbierto() {
        return abierto;
    }

    /**
     * Devuelve el código de seguridad necesario para abrir este contenedor.
     *
     * @return código requerido o {@code null} si no requiere llave.
     */
    @Override
    public String getCodigoNecesario() {
        return codigoNecesario;
    }

    /**
     * Obtiene el contenido guardado dentro del contenedor.
     *
     * @return objeto contenido o {@code null} si está vacío.
     */
    @Override
    public Objeto getContenido() {
        return contenido;
    }

    /**
     * Reemplaza el contenido interno del contenedor.
     *
     * @param contenido nuevo objeto a almacenar.
     */
    @Override
    public void setContenido(Objeto contenido) {
        this.contenido = contenido;
    }

    /**
     * Cierra el contenedor y deja su estado en no abierto.
     */
    @Override
    public void cerrar() {
        this.abierto = false;
    }
}