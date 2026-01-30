package aventura.app;

import aventura.domain.*;
import aventura.exceptions.InventarioLlenoException;
import aventura.interfaces.Abrible;
import aventura.interfaces.Combinable;
import aventura.interfaces.Leible;

import java.util.Locale;
import java.util.Scanner;


public class Juego {

    // Array que representa el mapa del juego. Cada posición corresponde a una habitación.
    private final Habitacion[] mapa;

    // Jugador principal del juego.
    private final Jugador jugador;

    // Índice de la habitación en la que se encuentra el jugador.
    private int habitacionActual;

    // Scanner utilizado para leer los comandos del usuario.
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Texto introductorio que describe la historia del juego.
     */
    private static final String DESCRIPCION_JUEGO =
            "Estabas tirado en el baño de la villa de Maduro, tenías mucho sueño porque habías estado toda la noche " +
                    "mirando reels de Trump diciendo 'FAKE NEWS' en X. De repente te quedas dormido y cuando despiertas, en vez de estar sentado" +
                    " en aquel váter, estás en una sala totalmente a oscuras.\n" +
                    "Te levantas y se enciende la sala. Ya no estás en aquel baño cutre del Palacio Presidencial, ahora estás en un lugar" +
                    " desconocido. Comienzas a caminar por pasillos llenos de cuadros de Maduro y fotos edgy de Diddy. A medida " +
                    "que avanzas, todo se vuelve más absurdo y lleno de contenido viral.\n" +
                    "Finalmente, emerges a la Zona de Safes donde Diddy guardaba sus... 'juguetes' especiales.";
    /**
     * Crea una nueva partida.
     *
     * @param mapa Mapa de habitaciones.
     * @param jugador Jugador del juego.
     * @param habitacionInicial Posición inicial.
     */
    public Juego(Habitacion[] mapa, Jugador jugador, int habitacionInicial) {
        this.mapa = mapa;
        this.jugador = jugador;
        this.habitacionActual = habitacionInicial;
        this.jugador.setPosicion(habitacionInicial);
    }

    /**
     * Punto de entrada principal del programa.
     * <p>
     * Inicializa el mapa, objetos, jugador
     * y lanza el juego.
     * </p>
     *
     * @param args Argumentos de consola.
     */

    public static void main(String[] args) {
        Habitacion sala1 = new Habitacion("Zona de Safes", "Estás rodeado de safes con dinero ilícito. Uno tiene algo INSANE.");
        Habitacion sala2 = new Habitacion("Cuarto del Meme Congelado", "Congelador gigante con TODO MATERIAL COMPROMETEDOR. Nota en Comic Sans pegada.");
        Habitacion sala3 = new Habitacion("Suite VIP de Diddy", "Sala de lujo con reggaeton de fondo. Taquilla dorada con código.");
        Habitacion sala4 = new Habitacion("Bunker de Epstein", "Almacén oscuro. TODO ESTÁ CONECTADO... demasiado conectado.");

        Habitacion[] mapa = new Habitacion[]{sala1, sala2, sala3, sala4};

        Consolador consolador = new Consolador("Consolador", "Un consolador rosa SIN PILAS encontrado en el cuarto de Diddy. Necesita energía para funcionar.", true);
        Pilas pilas = new Pilas("Pilas", "Pilas Duracell edición especial. Con estas el consolador vibrará con toda su potencia.", true);
        Nota nota = new Nota("Screenshot", "Screenshot de X de Trump a las 3 AM", true, "Para escapar necesitas PODER... combina lo que Diddy dejó olvidado... el consolador con pilas vibrará la puerta...");
        Nota nota2 = new Nota("DM", "DM filtrado de Diddy", true, "BRO dejé las pilas en mi taquilla VIP... código ELITE420... combínalas con mi consolador y tendrás el poder vibratorio para abrir...");
        Llave tarjeta = new Llave("Tarjeta", "Tarjeta VIP de los after parties. Código: ELITE420", true, "ELITE420");

        Contenedor caja = new Contenedor("Safe", "Safe gigante que emite vibraciones extrañas", true, null, null);
        Contenedor congelador = new Contenedor("Congelador", "Congelador CURSED que emite ruidos raros", true, null, null);
        Contenedor taquilla = new Contenedor("Taquilla", "Taquilla dorada con teclado numérico. Código: ELITE420", true, "ELITE420", null);

        sala4.añadirObjeto(consolador);
        Puerta puertaSalida = new Puerta("Puerta", "Puerta del bunker con cerradura de vibración. Requiere un Consolador Vibratorio (código 5973) para resonar y abrirse.", true);
        sala4.añadirObjeto(puertaSalida);

        sala1.añadirObjeto(caja);
        caja.ponerObjetoDentro(tarjeta);

        sala2.añadirObjeto(nota);
        sala2.añadirObjeto(congelador);
        congelador.ponerObjetoDentro(nota2);

        sala3.añadirObjeto(taquilla);
        taquilla.ponerObjetoDentro(pilas);

        Jugador jugador = new Jugador(7);
        Juego juego = new Juego(mapa, jugador, 0);
        juego.iniciar();
    }

    // Inicia el juego y ejecuta el bucle principal.
    public void iniciar() {
        System.out.println("=== 🔞 BIENVENIDO AL MULTIVERSE DE MEMES CONTROVERSIALES 🔞 ===\n");
        System.out.println(DESCRIPCION_JUEGO + "\n");
        System.out.println("⚠️ DISCLAIMER: Este juego es SATIRA. Escribe 'ayuda' para ver los comandos.\n");

        boolean proceso = true;
        while (proceso) {
            mostrarHabitacionActual();
            System.out.println("Comandos: izquierda | derecha | mirar | cruzar <puerta> | inventario | coger <obj> | examinar <obj> | abrir <cont> | combinar <a> <b> | ayuda | salir");
            System.out.print("\n> ");
            String linea = scanner.nextLine().toLowerCase(Locale.ROOT);
            procesarComando(linea);
            System.out.println();
        }
        scanner.close();
        System.out.println("¡Gracias por jugar!");
    }

    // Muestra el nombre y descripción de la habitación actual.
    private void mostrarHabitacionActual() {
        System.out.println("--------------------------------------------------");
        System.out.println("📍 Estás en: " + mapa[habitacionActual].getNombre());
        System.out.println("--------------------------------------------------");
        mostrarInfoHabitacion();
    }

    /**
     * Analiza y ejecuta el comando introducido por el usuario.
     *
     * @param linea Texto introducido.
     */
    private void procesarComando(String linea) {
        String[] partes = linea.split("\\s+");
        String comando = partes[0];

        switch (comando) {
            case "izquierda" -> moverIzquierda();
            case "derecha" -> moverDerecha();
            case "mirar" -> mirar();
            case "inventario" -> inventario();
            case "coger" -> coger(partes);
            case "cruzar" -> cruzar(partes);
            case "examinar" -> examinar(partes);
            case "abrir" -> abrir(partes);
            case "combinar" -> combinar(partes);
            case "ayuda" -> ayuda();
            case "salir" -> {
                System.out.println("Saliendo... ¡Hasta luego!");
                System.exit(0);
            }
            default -> System.out.println("Comando no reconocido. Escribe 'ayuda'.");
        }
    }

    // Mueve al jugador a la habitacion de la derecha.
    private void moverDerecha() {
        if (habitacionActual < mapa.length - 1) {
            habitacionActual++;
            jugador.setPosicion(habitacionActual);
            System.out.println("Te has movido a la habitación de la derecha.");
        } else {
            System.out.println("No puedes ir más a la derecha.");
        }
    }

    // Mueve al jugador a la habitacion de la izquierda.
    private void moverIzquierda() {
        if (habitacionActual > 0) {
            habitacionActual--;
            jugador.setPosicion(habitacionActual);
            System.out.println("Te has movido a la habitación de la izquierda.");
        } else {
            System.out.println("No puedes ir más a la izquierda.");
        }
    }

    // Muestra la información de la habitación.
    private void mirar() {
        mostrarInfoHabitacion();
    }

    // Muestra el inventario del jugador.
    private void inventario() {
        System.out.print("💼 Inventario: ");
        Objeto[] inv = jugador.getInventario();
        boolean hayObjetos = false;
        for (Objeto obj : inv) {
            if (obj != null) {
                System.out.print(obj.getNombre() + " ");
                hayObjetos = true;
            }
        }
        if (!hayObjetos) {
            System.out.print("vacío (como las promesas políticas)");
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

    /**
     * Permite abrir un contenedor o una puerta.
     * <p>
     * Busca automáticamente la llave adecuada
     * si el jugador la tiene en el inventario.
     * </p>
     *
     * @param partes Comando introducido por el usuario.
     */
    private void abrir(String[] partes) {

        if (partes.length < 2) {
            System.out.println("Uso: abrir <contenedor>");
            return;
        }

        String nombreContenedor = partes[1];

        Objeto obj = buscarObjeto(nombreContenedor);

        if (!(obj instanceof Abrible abrible)) {
            System.out.println("No hay '" + nombreContenedor + "' que se pueda abrir.");
            return;
        }

        Llave llave = null;

        if (obj instanceof Contenedor contenedor) {
            llave = jugador.buscarLlaveParaContenedor(contenedor);

            // Mensaje especial para la taquilla
            if (contenedor.getNombre().equalsIgnoreCase("Taquilla") && llave != null) {
                System.out.println("🔓 Usando la " + llave.getNombre() + " que conseguiste del Safe de la primera sala...");
            }
        }

        if (obj instanceof Puerta) {

            llave = jugador.buscarLlavePorCodigo("5973");

            if (llave == null) {
                System.out.println("⛔ No tienes el Consolador Vibratorio (consolador + pilas) para hacer resonar la puerta del bunker");
                return;
            }
        }

        RespuestaAccion res = abrible.abrir(llave);

        System.out.println(res.mensaje());

        if (!res.esExito()) return;

        if (obj instanceof Puerta && llave != null) {

            boolean quitada = jugador.soltarPorNombre(llave.getNombre());

            if (quitada) {
                System.out.println("La llave se ha usado y eliminado de tu inventario.");
            }
        }

        if (obj instanceof Contenedor contenedor) {

            Objeto contenido = contenedor.verObjetoDentro();

            if (contenido != null) {

                System.out.println("Encuentras: " + contenido.getNombre());

                try {

                    RespuestaAccion r = jugador.coger(contenido);

                    if (r.esExito()) {

                        contenedor.retirarObjetoDentro();
                        System.out.println("Guardado en inventario.");

                    } else {

                        System.out.println(r.mensaje());
                    }

                } catch (InventarioLlenoException e) {

                    System.out.println("No hay espacio en inventario.");
                }
            }
        }
    }

    /**
     * Permite combinar dos objetos del inventario.
     *
     * @param partes Comando introducido por el usuario.
     */
    private void combinar(String[] partes) {

        if (partes.length < 3) {
            System.out.println("Uso: combinar <obj1> <obj2>");
            return;
        }

        String nombreA = partes[1];
        String nombreB = partes[2];

        Objeto a = buscarObjeto(nombreA);
        Objeto b = buscarObjeto(nombreB);

        if (a == null || b == null) {
            System.out.println("No encuentro uno de los objetos.");
            return;
        }

        if (!jugador.tieneObjeto(a) || !jugador.tieneObjeto(b)) {
            System.out.println("Ambos objetos deben estar en tu inventario.");
            return;
        }

        Objeto resultado = null;

        if (a instanceof Combinable ca) {
            resultado = ca.combinar(b);
        } else if (b instanceof Combinable cb) {
            resultado = cb.combinar(a);
        }

        if (resultado == null) {
            System.out.println("Estos objetos no se pueden combinar.");
            return;
        }

        System.out.println("¡Combinado exitosamente! Creado: " + resultado.getNombre());

        jugador.soltarPorNombre(nombreA);
        jugador.soltarPorNombre(nombreB);

        try {

            jugador.coger(resultado);
            System.out.println(resultado.getNombre() + " añadido al inventario.");

        } catch (InventarioLlenoException e) {

            mapa[habitacionActual].añadirObjeto(resultado);
            System.out.println(resultado.getNombre() + " dejado en la habitación.");
        }
    }

    /**
     * Permite al jugador cruzar una puerta abierta y finalizar el juego.
     *
     * @param partes Comando introducido por el usuario.
     */
    private void cruzar(String[] partes) {

        if (partes.length < 2) {
            System.out.println("Uso: cruzar <puerta>");
            return;
        }

        String nombre = partes[1];

        Objeto obj = buscarEnHabitacion(nombre);

        if (!(obj instanceof Puerta puerta)) {
            System.out.println("No hay '" + nombre + "' aquí para cruzar.");
            return;
        }

        if (!puerta.estaAbierto()) {
            System.out.println("Primero abre la puerta con la llave correcta.");
            return;
        }

        System.out.println("""
                ╔══════════════════════════════════════╗
                ║ ¡CONSOLADOR VIBRATORIO ACTIVADO!     ║
                ║ LA PUERTA RESONÓ CON LAS VIBRACIONES ║
                ║ Y SE ABRIÓ EL BUNKER                 ║
                ║ ¡VIRALIZASTE EL ESCÁNDALO!          ║
                ║ 500M DE VIEWS EN 1 HORA              ║
                ║                                      ║
                ║  🔥 DIDDY'S FREAK ESCAPE 🔥          ║
                ╚══════════════════════════════════════╝
                """);

        System.exit(0);
    }

    /**
     * Muestra el menú de ayuda con los comandos disponibles.
     */
    private void ayuda() {

        System.out.println("""
                Comandos disponibles:
                - izquierda/derecha
                - mirar
                - inventario
                - coger <objeto>
                - examinar <objeto>
                - abrir <contenedor>
                - combinar <obj1> <obj2>
                - cruzar <puerta>
                - salir
                """);
    }

    /**
     * Busca un objeto en la habitación actual.
     *
     * @param nombre Nombre del objeto.
     * @return Objeto encontrado o {@code null}.
     */
    private Objeto buscarEnHabitacion(String nombre) {

        Objeto[] objetos = mapa[habitacionActual].getObjetos();
        String n = nombre.toLowerCase(Locale.ROOT);

        for (Objeto obj : objetos) {

            if (obj != null &&
                    obj.getNombre().toLowerCase(Locale.ROOT).equals(n)) {
                return obj;
            }
        }
        return null;
    }

    /**
     * Busca un objeto en la habitación o en el inventario.
     *
     * @param nombre Nombre del objeto.
     * @return Objeto encontrado o {@code null}.
     */
    private Objeto buscarObjeto(String nombre) {

        Objeto encontrado = buscarEnHabitacion(nombre);

        if (encontrado != null) {
            return encontrado;
        }

        String n = nombre.toLowerCase(Locale.ROOT);
        for (Objeto obj : jugador.getInventario()) {

            if (obj != null &&
                    obj.getNombre().toLowerCase(Locale.ROOT).equals(n)) {
                return obj;
            }
        }
        return null;
    }
}
