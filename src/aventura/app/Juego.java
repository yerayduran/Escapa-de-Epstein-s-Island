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
}
