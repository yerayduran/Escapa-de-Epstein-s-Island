package aventura.domain;

import aventura.exceptions.AventuraException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Representa una habitación del juego de aventura.
 *
 * Cada habitación dispone de un identificador único, una descripción,
 * una colección de objetos presentes en ella y un conjunto de salidas
 * que conectan con otras habitaciones.
 *
 * También ofrece operaciones para añadir, eliminar y buscar objetos,
 * así como para consultar o modificar las salidas disponibles.
 */
public class Habitacion {

    /**
     * Número máximo de objetos que puede contener la habitación.
     */
    private static final int MAX_OBJETOS = 10;

    /**
     * Identificador único de la habitación.
     */
    private String id;

    /**
     * Descripción textual de la habitación.
     */
    private String descripcion;

    /**
     * Lista de objetos presentes actualmente en la habitación.
     */
    private List<Objeto> objetos;

    /**
     * Mapa de salidas de la habitación.
     *
     * La clave representa la dirección (por ejemplo, "norte" o "sur")
     * y el valor representa el identificador de la habitación destino.
     */
    private Map<String, String> salidas;

    /**
     * Construye una habitación vacía.
     *
     * Inicializa la lista de objetos y el mapa de salidas.
     * El mapa de salidas utiliza un comparador insensible a mayúsculas
     * y minúsculas para facilitar las búsquedas por dirección.
     */
    public Habitacion() {
        this.objetos = new ArrayList<>();
        this.salidas = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    /**
     * Construye una habitación con identificador y descripción.
     *
     * Además, inicializa la lista de objetos y el mapa de salidas.
     *
     * @param id identificador único de la habitación.
     * @param descripcion descripción textual de la habitación.
     */
    public Habitacion(String id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
        this.objetos = new ArrayList<>();
        this.salidas = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    /**
     * Añade un objeto a la habitación.
     *
     * Antes de insertar el objeto, comprueba que no se haya alcanzado
     * la capacidad máxima de objetos permitida en la sala.
     *
     * @param obj objeto que se desea añadir a la habitación.
     * @throws AventuraException si la habitación ya contiene el número máximo de objetos.
     */
    public void agregarObjeto(Objeto obj) throws AventuraException {
        if (objetos.size() >= MAX_OBJETOS) {
            throw new AventuraException("No se puede agregar un objeto");
        }
        objetos.add(obj);
    }

    /**
     * Elimina un objeto de la habitación.
     *
     * @param obj objeto que se desea eliminar.
     * @return {@code true} si el objeto estaba en la habitación y se eliminó correctamente;
     *         {@code false} en caso contrario.
     */
    public boolean eliminarObjeto(Objeto obj) {
        return objetos.remove(obj);
    }

    /**
     * Genera una descripción completa de la habitación.
     *
     * El texto devuelto incluye la descripción base de la habitación,
     * la lista de objetos visibles presentes en ella y las salidas
     * disponibles hacia otras habitaciones.
     *
     * @return una cadena con la información visible de la habitación.
     */
    public String mirar() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.descripcion).append("\n");

        for (Objeto obj : objetos) {
            if (obj != null && obj.isVisible()) {
                sb.append(" - ").append(obj.getNombre()).append("\n");
            }
        }

        if (!salidas.isEmpty()) {
            sb.append("Salidas: ").append(String.join(", ", salidas.keySet())).append("\n");
        }

        return sb.toString();
    }

    /**
     * Devuelve la lista de objetos de la habitación.
     *
     * @return lista de objetos presentes en la habitación.
     */
    public List<Objeto> getObjetos() {
        return objetos;
    }

    /**
     * Sustituye la lista de objetos de la habitación.
     *
     * Si la lista recibida es {@code null}, se asigna una lista vacía
     * para evitar referencias nulas.
     *
     * @param objetos nueva lista de objetos de la habitación.
     */
    public void setObjetos(List<Objeto> objetos) {
        this.objetos = (objetos != null) ? objetos : new ArrayList<>();
    }

    /**
     * Devuelve el identificador de la habitación.
     *
     * @return identificador de la habitación.
     */
    public String getId() {
        return id;
    }

    /**
     * Modifica el identificador de la habitación.
     *
     * @param id nuevo identificador de la habitación.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Devuelve la descripción de la habitación.
     *
     * @return descripción textual de la habitación.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Devuelve el mapa de salidas de la habitación.
     *
     * Cada entrada del mapa asocia una dirección con el identificador
     * de la habitación de destino.
     *
     * @return mapa de salidas disponibles.
     */
    public Map<String, String> getSalidas() {
        return salidas;
    }

    /**
     * Sustituye el conjunto de salidas de la habitación.
     *
     * Crea internamente un nuevo {@link TreeMap} con comparación
     * insensible a mayúsculas y minúsculas, y copia en él las
     * salidas proporcionadas.
     *
     * @param salidas nuevo mapa de salidas.
     */
    public void setSalidas(Map<String, String> salidas) {
        this.salidas = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        if (salidas != null) {
            this.salidas.putAll(salidas);
        }
    }

    /**
     * Añade una salida a la habitación.
     *
     * La salida solo se registra si tanto la dirección como el
     * identificador de destino son distintos de {@code null}.
     *
     * @param direccion dirección de salida, por ejemplo "norte".
     * @param destinoId identificador de la habitación destino.
     */
    public void addSalida(String direccion, String destinoId) {
        if (direccion != null && destinoId != null) {
            salidas.put(direccion, destinoId);
        }
    }

    /**
     * Obtiene el identificador de la habitación destino asociado
     * a una dirección concreta.
     *
     * @param direccion dirección cuya salida se desea consultar.
     * @return el identificador de la habitación destino, o {@code null}
     *         si la dirección no existe o no es válida.
     */
    public String getDestino(String direccion) {
        if (salidas == null || direccion == null) {
            return null;
        }
        return salidas.get(direccion);
    }

    /**
     * Busca un objeto en la habitación por su nombre.
     *
     * La búsqueda no distingue entre mayúsculas y minúsculas.
     *
     * @param nombre nombre del objeto que se desea localizar.
     * @return el objeto encontrado, o {@code null} si no existe
     *         ningún objeto con ese nombre en la habitación.
     */
    public Objeto buscar(String nombre) {
        for (Objeto obj : objetos) {
            if (obj != null && obj.getNombre().equalsIgnoreCase(nombre)) {
                return obj;
            }
        }
        return null;
    }
}