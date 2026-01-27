package aventura.app;

import aventura.domain.Objeto;

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
}
