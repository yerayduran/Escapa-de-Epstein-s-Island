package aventura.domain;

/**
 * Representa una habitación dentro del juego.
 * <p>
 * Una habitación es una entidad que puede contener varios objetos
 * con los que el jugador puede interactuar.
 * </p>
 *
 * Permite añadir, retirar, buscar y describir los objetos que contiene.
 *
 * Hereda de {@link Entidad}, por lo que dispone de nombre y descripción.
 *
 * @author Manuel Pérez
 * @version 1.0
 */
public class Habitacion extends Entidad {

    /**
     * Array que almacena los objetos presentes en la habitación.
     */
    private final Objeto[] objetos;

    /**
     * Número actual de objetos almacenados.
     */
    private int tamaño;

    /**
     * Número máximo de objetos permitidos en la habitación.
     */
    private static final int MAX_OBJETOS = 7;

    /**
     * Crea una nueva habitación con el nombre y descripción indicados.
     *
     * @param nombre Nombre de la habitación.
     * @param descripcion Descripción de la habitación.
     */
    public Habitacion(String nombre, String descripcion) {
        super(nombre, descripcion);
        this.objetos = new Objeto[MAX_OBJETOS];
    }

    /**
     * Añade un objeto a la habitación si hay espacio disponible.
     *
     * @param obj Objeto que se desea añadir.
     * @return {@code true} si se añade correctamente,
     *         {@code false} si no hay espacio o el objeto es {@code null}.
     */
    public boolean añadirObjeto(Objeto obj) {
        if (obj == null || tamaño >= objetos.length) {
            return false;
        }
        objetos[tamaño++] = obj;
        return true;
    }

    /**
     * Retira un objeto de la habitación.
     * <p>
     * Si el objeto se encuentra, se reorganiza el array
     * para evitar espacios vacíos intermedios.
     * </p>
     *
     * @param obj Objeto que se desea retirar.
     * @return {@code true} si se elimina correctamente,
     *         {@code false} si no existe o es {@code null}.
     */
    public boolean retirarObjeto(Objeto obj) {
        if (obj == null) {
            return false;
        }

        for (int i = 0; i < tamaño; i++) {

            if (objetos[i] == obj) {

                System.arraycopy(objetos, i + 1, objetos, i, tamaño - i - 1);
                objetos[--tamaño] = null;
                return true;
            }
        }

        return false;
    }

    /**
     * Busca un objeto dentro de la habitación por su nombre.
     * <p>
     * La búsqueda no distingue entre mayúsculas y minúsculas.
     * </p>
     *
     * @param nombre Nombre del objeto a buscar.
     * @return El objeto encontrado o {@code null} si no existe.
     */
    public Objeto buscarPorNombre(String nombre) {

        if (nombre == null) {
            return null;
        }

        String n = nombre.toLowerCase();

        for (int i = 0; i < tamaño; i++) {

            Objeto obj = objetos[i];

            if (obj != null && n.equals(obj.getNombre().toLowerCase())) {
                return obj;
            }
        }

        return null;
    }

    /**
     * Devuelve el array completo de objetos de la habitación.
     * <p>
     * Incluye posibles posiciones {@code null}.
     * </p>
     *
     * @return Array de objetos.
     */
    public Objeto[] getObjetos() {
        return objetos;
    }

    /**
     * Genera una descripción de la habitación junto con
     * los objetos visibles que contiene.
     *
     * <p>
     * Solo se muestran los objetos marcados como visibles.
     * Si no hay ninguno, se indica al jugador.
     * </p>
     *
     * @return Descripción completa de la habitación.
     */
    public String describirConObjetosVisibles() {

        StringBuilder sb = new StringBuilder(getDescripcion());
        boolean anyVisible = false;

        for (int i = 0; i < tamaño; i++) {

            Objeto obj = objetos[i];

            if (obj != null && obj.isVisible()) {

                if (!anyVisible) {
                    sb.append("\nEn esta sala ves:");
                    anyVisible = true;
                }

                sb.append("\n - ").append(obj.getNombre());
            }
        }

        if (!anyVisible) {
            sb.append("\nNo ves nada interesante a simple vista.");
        }

        return sb.toString();
    }
}
