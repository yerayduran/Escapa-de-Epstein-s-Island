package aventura.domain;

import aventura.exceptions.AventuraException;
import aventura.exceptions.InventarioLlenoException;
import aventura.interfaces.Inventariable;

import java.util.ArrayList;
import java.util.List;

public class Jugador {
    private static final int MAX_INVENTARIO = 10;


    private String nombre;
    private final List<Objeto> inventario;
    private String habitacionActual;


    /**
     * Constructor de la clase Jugador.
     * Inicializa un nuevo jugador con el nombre especificado, establece su habitación
     * actual como vacía y crea un inventario vacío.
     *
     * @param nombre El nombre del jugador.
     */
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.habitacionActual = "";
        this.inventario = new ArrayList<>();
    }

    /**
     * Obtiene el nombre del jugador.
     *
     * @return El nombre del jugador.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el identificador de la habitación en la que se encuentra actualmente el jugador.
     *
     * @return El identificador o nombre de la habitación actual.
     */
    public String getHabitacionActual() {
        return habitacionActual;
    }


    /**
     * Establece la habitación en la que se encuentra el jugador.
     *
     * @param habitacionActual El identificador o nombre de la nueva habitación.
     */
    public void setHabitacionActual(String habitacionActual) {
        this.habitacionActual = habitacionActual;
    }

    /**
     * Obtiene la lista de objetos que el jugador lleva actualmente consigo.
     *
     * @return Una lista ({@link List}) de objetos en el inventario.
     */

    public List<Objeto> getInventario() {
        return inventario;
    }

    /**
     * Añade un objeto al inventario del jugador.
     * El objeto debe implementar la interfaz {@link Inventariable} y el inventario
     * no debe superar la capacidad máxima ({@value #MAX_INVENTARIO}).
     *
     * @param objeto El {@link Objeto} que se desea añadir al inventario.
     * @throws AventuraException Si el objeto no es inventariable (no se puede coger).
     * @throws InventarioLlenoException Si el inventario ha alcanzado su capacidad máxima.
     */

   public void coger(Objeto objeto) throws AventuraException {
        if (!(objeto instanceof Inventariable)) {
            throw new AventuraException("El objeto %s no se puede coger." .formatted(objeto.getNombre()));
        }

        if (inventario.size() >= MAX_INVENTARIO) {
            throw new InventarioLlenoException("El inventario está lleno. No puedes ciger más objetos.");
        }

        inventario.add(objeto);
   }

    /**
     * Elimina un objeto específico del inventario del jugador.
     *
     * @param objeto El {@link Objeto} a eliminar.
     * @return {@code true} si el objeto estaba en el inventario y fue eliminado correctamente;
     *         {@code false} en caso contrario.
     */

    public boolean eliminarDeInventario(Objeto objeto) {
        return inventario.remove(objeto);
   }

    /**
     * Busca un objeto en el inventario del jugador por su nombre.
     * La búsqueda no distingue entre mayúsculas y minúsculas (case-insensitive).
     *
     * @param nombre El nombre exacto del objeto a buscar.
     * @return El {@link Objeto} si se encuentra en el inventario; {@code null} en caso contrario.
     */

    public Objeto buscarEnInventario(String nombre){
        for (Objeto objeto : inventario) {
            if (objeto.getNombre().equalsIgnoreCase(nombre)) {
                return objeto;
            }
        }
        return null;
   }
}


