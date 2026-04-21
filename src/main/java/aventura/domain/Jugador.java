package aventura.domain;

import aventura.exceptions.AventuraException;
import aventura.exceptions.InventarioLlenoException;
import aventura.interfaces.Inventariable;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa al jugador que maneja el usuario durante la partida.
 * Lleva el control de su nombre, dónde está y qué lleva encima.
 *
 * @author Yeray Durán y Manuel Perez
 */
public class Jugador {

    /**
     * Número máximo de objetos que caben en el inventario. Si lo superas, se lanza excepción.
     */
    private static final int MAX_INVENTARIO = 10;

    /**
     * El nombre del jugador, que se asigna al crearlo.
     */
    private String nombre;

    /**
     * Lista de objetos que el jugador lleva encima en este momento.
     */
    private final List<Objeto> inventario;

    /**
     * El identificador de la habitación donde está el jugador ahora mismo.
     */
    private String habitacionActual;

    /**
     * Crea un jugador nuevo con el nombre dado, sin objetos y sin habitación asignada.
     *
     * @param nombre El nombre que le quieres poner al jugador.
     */
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.habitacionActual = "";
        this.inventario = new ArrayList<>();
    }


    /**
     * Devuelve el nombre del jugador.
     *
     * @return El nombre del jugador.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve el nombre del jugador.
     *
     * @return El nombre del jugador.
     */
    public String getHabitacionActual() {
        return habitacionActual;
    }

    /**
     * Mueve al jugador a otra habitación, actualizando su posición.
     *
     * @param habitacionActual El id de la habitación a la que se mueve el jugador.
     */
    public void setHabitacionActual(String habitacionActual) {
        this.habitacionActual = habitacionActual;
    }

    /**
     * Devuelve la lista de objetos que lleva el jugador en el inventario.
     *
     * @return La lista con todos los objetos del inventario.
     */
    public List<Objeto> getInventario() {
        return inventario;
    }

    /**
     * Intenta meter un objeto en el inventario del jugador.
     * Si el objeto no se puede coger (no es {@link Inventariable}) o el inventario
     * está petado hasta arriba, lanza una excepción para avisarnos.
     *
     * @param objeto El objeto que el jugador quiere recoger.
     * @throws AventuraException Si el objeto no es cogible.
     * @throws InventarioLlenoException Si el inventario ya tiene los {@value MAX_INVENTARIO} objetos que caben.
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
     * Saca un objeto del inventario del jugador y lo elimina.
     * Devuelve true si lo encontró y lo borró, o false si ese objeto no estaba.
     *
     * @param objeto El objeto que quieres quitar del inventario.
     * @return {@code true} si se eliminó correctamente, {@code false} si no estaba.
     */
   public boolean eliminarDeInventario(Objeto objeto) {
        return inventario.remove(objeto);
   }

    /**
     * Busca un objeto en el inventario por su nombre, sin importar mayúsculas o minúsculas.
     * Si no lo encuentra, devuelve null.
     *
     * @param nombre El nombre del objeto que buscas.
     * @return El objeto si está en el inventario, o {@code null} si no está.
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


