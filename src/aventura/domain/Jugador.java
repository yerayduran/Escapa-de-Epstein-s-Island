package aventura.domain;

import aventura.exceptions.InventarioLlenoException;
import aventura.interfaces.Inventariable;

import java.util.ArrayList;
import java.util.List;

public class Jugador {
    private static final int MAX_INVENTARIO = 10;


    private String nombre;
    private final List<Objeto> inventario;
    private String habitacionActual;



    public Jugador(String nombre) {
        this.nombre = nombre;
        this.habitacionActual = "";
        this.inventario = new ArrayList<>();
    }



    public String getNombre() {
        return nombre;
    }

    public String getHabitacionActual() {
        return habitacionActual;
    }

    public void setHabitacionActual(String habitacionActual) {
        this.habitacionActual = habitacionActual;
    }

    public List<Objeto> getInventario() {
        return inventario;
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