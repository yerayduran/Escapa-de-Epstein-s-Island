package aventura.app;

import aventura.domain.Objeto;
import aventura.domain.RespuestaAccion;
import aventura.exceptions.InventarioLlenoException;
import aventura.interfaces.Leible;

import java.util.Locale;
import java.util.Scanner;


public class Juego {

    static void main(String[] args) {

    }

    // Muestra la informacion de la habitación.
    private void mirar() {
        mostrarInfoHabitacion();
    }

    // Muestra el inventario del jugador.
    private void inventario() {
        System.out.print("Inventario: ");
        Objeto[] inv = jugador.getInventario();
        boolean hayObjetos = false;
        for (Objeto obj : inv) {
            if (obj != null) {
                System.out.print(obj.getNombre() + " ");
                hayObjetos = true;
            }
        }
        if (!hayObjetos) {
            System.out.print("vacío");
        }
        System.out.println();
    }

    /**
     * Muestra por pantalla los objetos que hay en la habitación actual.
     * <p>
     * Si no hay objetos, indica que no hay ninguno.
     * </p>
     */
    private void mostrarObjetosHabitacion() {

        Objeto[] objetos = mapa[habitacionActual].getObjetos();
        System.out.print("Objetos: ");

        boolean hayObjetos = false;

        for (Objeto obj : objetos) {

            if (obj != null) {
                System.out.print(obj.getNombre() + " ");
                hayObjetos = true;
            }
        }

        if (!hayObjetos) {
            System.out.print("ninguno");
        }

        System.out.println();
    }

    /**
     * Muestra información general de la habitación actual.
     * <p>
     * Si existen objetos, los muestra.
     * Si no hay ninguno, informa al jugador.
     * </p>
     */
    private void mostrarInfoHabitacion() {

        Objeto[] objetos = mapa[habitacionActual].getObjetos();
        boolean hayObjetos = false;

        for (Objeto obj : objetos) {

            if (obj != null) {
                hayObjetos = true;
                break;
            }
        }

        if (hayObjetos) {
            mostrarObjetosHabitacion();
        } else {
            System.out.println("No hay objetos en esta habitación.");
        }
    }

    /**
     * Permite al jugador recoger un objeto de la habitación.
     *
     * @param partes Comando introducido por el usuario separado por palabras.
     */
    private void coger(String[] partes) {

        if (partes.length < 2) {
            System.out.println("Uso: coger <objeto>");
            return;
        }

        String nombreObjeto = partes[1];

        Objeto objeto = buscarEnHabitacion(nombreObjeto);

        if (objeto == null) {
            System.out.println("No hay '" + nombreObjeto + "' aquí.");
            return;
        }

        try {

            RespuestaAccion r = jugador.coger(objeto);

            if (r.esExito()) {

                mapa[habitacionActual].retirarObjeto(objeto);
                System.out.println("Cogiste " + objeto.getNombre() + ".");

            } else {

                System.out.println(r.mensaje());
            }

        } catch (InventarioLlenoException e) {

            System.out.println("Inventario lleno.");
        }
    }

    /**
     * Permite examinar un objeto de la habitación o del inventario.
     * <p>
     * Muestra su descripción y, si es legible, su contenido.
     * </p>
     *
     * @param partes Comando introducido por el usuario.
     */
    private void examinar(String[] partes) {

        if (partes.length < 2) {
            System.out.println("Uso: examinar <objeto>");
            return;
        }

        String nombreObjeto = partes[1];

        Objeto objeto = buscarObjeto(nombreObjeto);

        if (objeto == null) {
            System.out.println("No encuentro '" + nombreObjeto + "'.");
            return;
        }

        System.out.println(objeto.getDescripcion());

        if (objeto instanceof Leible legible) {
            System.out.println("Texto: " + legible.leer());
        }
    }
}
