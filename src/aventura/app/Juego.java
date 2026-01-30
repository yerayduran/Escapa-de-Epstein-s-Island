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
        Habitacion sala1 = new Habitacion("Zonas de Cajas Registradora", "Te encuentras en las zonas de pagos de este supermercado y por casualidad encuentras en una caja registradora algo especial.");
        Habitacion sala2 = new Habitacion("Zona de Congelados", "Estás en la zona de congelados del Mercadona y ves una nota pegada a la pared, y otra dentro del congelador.");
        Habitacion sala3 = new Habitacion("Zona de Descanso", "Una zona muy tranquila pero con algo sospechoso, además se encuentra allí una taquilla.");
        Habitacion sala4 = new Habitacion("Zona de Carga y Descarga", "Un almacén enorme donde al final ves una puerta cerrada donde se halla la salida.");

        Habitacion[] mapa = new Habitacion[]{sala1, sala2, sala3, sala4};

        SoporteLlave palo = new SoporteLlave("Palo", "Un palo robusto, útil para combinar.", true);
        CabezaLlave cabeza = new CabezaLlave("Llave", "Un trozo de llave vieja y rota.", true);
        Nota nota = new Nota("Nota", "Una nota arrugada.", true, "Para salir de aquí es esencial combinar...");
        Nota nota2 = new Nota("Ticket", "Un ticket con un texto escrito por un antiguo cliente", true, "No tengo mucho tiempo antes de que vengan a por mí, pero ve a la zona de descanso con mi tarjeta y ...");
        Llave tarjeta = new Llave("Tarjeta", "Tarjeta de empleado en mal estado con código Hacendado.", true, "Hacendado");

        Contenedor caja = new Contenedor("Caja", "Caja registradora que por desgracia no tiene dinero para mis videojuegos, pero se ve que hay algo más", true, null, null);
        Contenedor congelador = new Contenedor("Congelador", "Congelador estropeado y con comida caducada", true, null, null);
        Contenedor taquilla = new Contenedor("Taquilla", "Taquilla del empleado la cual tiene muchos arañazos", true, "Hacendado", null);

        sala4.añadirObjeto(palo);
        Puerta puertaSalida = new Puerta("Puerta", "La puerta final bloqueada. Requiere llave Hacendado completa.", true);
        sala4.añadirObjeto(puertaSalida);

        sala1.añadirObjeto(caja);
        caja.ponerObjetoDentro(tarjeta);

        sala2.añadirObjeto(nota);
        sala2.añadirObjeto(congelador);
        congelador.ponerObjetoDentro(nota2);

        sala3.añadirObjeto(taquilla);
        taquilla.ponerObjetoDentro(cabeza);

        Jugador jugador = new Jugador(7);
        Juego juego = new Juego(mapa, jugador, 0);
        juego.iniciar();
    }

    // Inicia el juego y ejecuta el bucle principal.
    public void iniciar() {
        System.out.println("=== Bienvenido a HACENDADO OUT ===\n");
        System.out.println(DESCRIPCION_JUEGO + "\n");
        System.out.println("Escribe 'ayuda' para ver los comandos disponibles.\n");

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
        System.out.println("Estás en: " + mapa[habitacionActual].getNombre());
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
        }

        if (obj instanceof Puerta) {

            llave = jugador.buscarLlavePorCodigo("5973");

            if (llave == null) {
                System.out.println("No tienes la llave necesaria para abrir esa puerta.");
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
                ║ ¡ESCAPASTE DEL MERCADONA!            ║
                ║ ¡FELICIDADES, HAS GANADO!            ║
                ║                                      ║
                ║   *** HACENDADO OUT *** COMPLETADO   ║
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
