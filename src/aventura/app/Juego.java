package aventura.app;

import java.util.Locale;
import java.util.Scanner;

/**
 * Clase principal del juego "Tu Propia aventura".
 * Esqueleto para la Misión 1 (UD1-UD3).
 * VUESTRO TRABAJO es rellenar todos los TODO
 */
public class Juego {

    // --- NÚCLEO: Definición de Datos (FASE 1) ---
    // Esta parte os la damos HECHA. Es el "contrato" del núcleo.

    private static String descripcionJuego = "Estabas cagando en el baño de un Mercadona random, tenías mucho sueño porque habías estado\\n toda la noche jugando al call of duty," +
            " de repente notas cómo tus parpados empiezan a cerrarse. Al final te quedas dormido por viciar toda\\n la noche y resulta que, cuando te despiertas, en vez de" +
            " estar sentado en aquel váter estas en una especie de sillón\\n en una sala totalmente a oscuras. Cuando te levantas, se te enciende la sala en la que estás. Ya no estás " +
            "en aquel baño cutre del Mercadona,\\n ahora estabas en un lugar totalmente desconocido.";

    // El mapa de habitaciones.
    // TODO: (Skin) ¡Rellenad esto con vuestras descripciones!
    private static String[] habitaciones = {
            "Estás en una sala . Hay una puerta a la DERECHA.",  // Posición 0
            "Estás en el pasillo principal. Hay puertas a la DERECHA y a la IZQUIERDA.", // Posición 1
            "Estás en el aula 105. Hay una puerta a la IZQUIERDA y has visto una 'llave' en una mesa.", // Posición 2
            // Borra las habitaciones y escribe las tuyas
    };

    // Los objetos que hay en cada habitación.
    // TODO: (Skin) Rellenad esto con vuestros objetos
    private static String[][] objetosMapa = {
            {null, "Nota de traduccion de Codigo Morse"},           // Objetos en Habitación 0
            {null, "Carne Podrida"},           // Objetos en Habitación 1
            {"Nota", null},      // Objetos en Habitación 2
    };

    // El inventario del jugador. Tamaño fijo.
    private static String[] inventario = new String[5];

    // Variable que guarda la posición actual del jugador
    private static int habitacionActual = 0; // Empezamos en la primera habitación

    // --- FIN DE LA DEFINICIÓN DE DATOS ---


    public static void main(String[] args) {
        // Puedes utilizar la clase MiEntradaSalida, que viviría en el paquete io
        Scanner scanner = new Scanner(System.in);
        boolean jugando = true;

        System.out.println("¡Bienvenido a 'TU PROPIA AVENTURA'!");
        System.out.println("------------------------------------------");

        // TODO 1a: Muestra la descripción general del juego

        // TODO 1b: Muestra la descripción de la primera habitación
        // Pista: System.out.println(habitaciones[...]);


        // TODO 2: Iniciar el bucle principal del juego (game loop)
        while (jugando) {

            // TODO 3: Leer el comando del usuario por teclado
            System.out.print("\n> ");
            //String comando = ...;

            switch (comando) {
                case "mirar" -> {
                    mostrarInfoHabitacion();
                }
                case "inventario" -> {
                    System.out.print("Objetos en tu inventario: ");
                    for (String objeto : inventario) {
                        if (objeto != null) {
                            System.out.print(objeto + " ");
                        }
                    }
                    System.out.println();
                }
                case "ir izquierda" -> {
                    if (habitacionActual > 0) {
                        habitacionActual--;
                        System.out.println("Te has movido a la habitación de la izquierda.");
                        mostrarInfoHabitacion();
                    } else {
                        System.out.println("No puedes ir más a la izquierda.");
                    }
                }
                case "ir derecha" -> {
                    if (habitacionActual < habitaciones.length - 1) {
                        habitacionActual++;
                        System.out.println("Te has movido a la habitación de la derecha.");
                        mostrarInfoHabitacion();
                    } else {
                        System.out.println("No puedes ir más a la derecha.");
                    }
                }
                case "coger" -> {
                    if (!hayObjetosEnHabitacion()) {
                        System.out.println("No hay objetos para coger en esta habitación.");
                        break;
                    }
                    mostrarObjetosHabitacion();
                    System.out.print("¿Qué objeto quieres coger? ");
                    String objetoACoger = scanner.nextLine().toLowerCase(Locale.ROOT);

                    procesarComandoCoger(objetoACoger);
                }
                case "salir" -> {
                    jugando = false;
                    System.out.println("Saliendo del juego...");
                }

                default -> {
                    mostrarAyuda();
                }
            }

        }

        System.out.println("¡Gracias por jugar!");
        scanner.close();
    }

    /**
     * Muestra la información de la habitación actual,
     * incluyendo su descripción y los objetos que hay en ella.
     */
    private static void mostrarInfoHabitacion() {
        System.out.println(habitaciones[habitacionActual]);

        boolean hayObjetos = false;

        for (String objeto : objetosMapa[habitacionActual]) {
            if (objeto != null) {
                hayObjetos = true;
                break;
            }
        }

        if (!hayObjetos) {
            System.out.println("No hay objetos en esta habitación.");
        }
        else {
            mostrarObjetosHabitacion();
        }

    }

    /**
     * Procesa el comando de coger un objeto.
     *
     * @param objetoACoger El nombre del objeto que se quiere coger.
     */
    private static void procesarComandoCoger(String objetoACoger) {
        boolean objetoEncontrado = false;
        for (int i = 0; i < objetosMapa[habitacionActual].length; i++) {
            if (objetoACoger.equals(objetosMapa[habitacionActual][i])) {
                objetoEncontrado = true;
                // Buscar espacio en el inventario
                boolean espacioEncontrado = false;
                for (int j = 0; j < inventario.length; j++) {
                    if (inventario[j] == null) {
                        inventario[j] = objetoACoger;
                        objetosMapa[habitacionActual][i] = null; // Quitar el objeto de la habitación
                        System.out.println("Has cogido " + objetoACoger + ".");
                        espacioEncontrado = true;
                        break;
                    }
                }
                if (!espacioEncontrado) {
                    System.out.println("Tu inventario está lleno. No puedes coger más objetos.");
                }
                break;
            }
        }
        if (!objetoEncontrado) {
            System.out.println("No hay ningún objeto llamado " + objetoACoger + " en esta habitación.");
        }
    }

    /**
     * Muestra los objetos que hay en la habitación actual.
     */
    private static void mostrarObjetosHabitacion() {
        System.out.print("Objetos en la habitación: ");
        boolean hayObjetos = false;
        boolean hayMasDeUnObjeto = false;
        for (String objeto : objetosMapa[habitacionActual]) {
            if (objeto != null) {
                hayObjetos = true;
                System.out.print(hayMasDeUnObjeto ? ", " + objeto : objeto);
                hayMasDeUnObjeto = true;
            }
        }
        if (!hayObjetos) {
            System.out.print("No hay objetos.");
        }
        System.out.println();
    }
}
