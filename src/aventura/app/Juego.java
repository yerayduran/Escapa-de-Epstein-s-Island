package aventura.app;

import aventura.domain.*;
import aventura.exceptions.InventarioLlenoException;
import aventura.interfaces.Abrible;
import aventura.interfaces.Combinable;
import aventura.interfaces.Leible;

import java.util.Locale;
import java.util.Scanner;

/**
 * Clase principal del juego "Silent Hill: Broken Worlds".
 * <p>
 * Gestiona la lógica general del juego, el mapa, el jugador,
 * la entrada de comandos y la interacción con los objetos.
 * </p>
 *
 * Historia principal inspirada en Silent Hill, con referencias
 * a Resident Evil, GTA, Luigi's Mansion, Expedition 33, R.E.P.O.,
 * PEAK, It Takes Two, Elden Ring, Fallout y Cyberpunk.
 *
 * @author Yeray Durán y Manuel Pérez
 * @version 2.0
 */
public class Juego {

    // Array que representa el mapa del juego.
    private final Habitacion[] mapa;

    // Jugador principal del juego.
    private final Jugador jugador;

    // Índice de la habitación actual.
    private int habitacionActual;

    // Scanner para leer comandos.
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Texto introductorio del juego.
     */
    private static final String DESCRIPCION_JUEGO =
            "La última noche antes de llegar al pueblo, recibiste un mensaje sin remitente: 'Vuelve'. " +
                    "Pensaste que era una broma de mal gusto, pero algo dentro de ti te obligó a conducir hasta las afueras de Silent Hill.\n" +
                    "La carretera estaba vacía. Solo niebla, asfalto mojado y el sonido lejano de una sirena imposible. " +
                    "Entonces viste una silueta cruzando la calzada. Giraste el volante, el coche derrapó... y todo se volvió negro.\n" +
                    "Cuando despiertas, ya no estás del todo en el mundo real. Silent Hill ha abierto sus puertas para ti. " +
                    "Las calles del pueblo cambian a cada paso, como si alguien hubiese mezclado tus recuerdos con pesadillas ajenas.\n" +
                    "Un tramo de carretera recuerda a una vieja huida entre crimen y culpa. Un hospital oxidado esconde informes clínicos, " +
                    "cadáveres inmóviles y puertas cerradas con combinaciones imposibles. Una mansión invadida por retratos y susurros " +
                    "parece arrancada de una pesadilla infantil. Más abajo, un refugio enterrado bajo la ciudad mezcla chatarra militar, " +
                    "tecnología rota, símbolos antiguos y terminales que todavía brillan en la oscuridad.\n" +
                    "Cada lugar parece pertenecer a un mundo distinto, pero todos tienen algo en común: tú.\n" +
                    "No has venido a Silent Hill por casualidad. El pueblo te ha llamado porque sabe lo que hiciste. " +
                    "Aquí los monstruos no siempre tienen colmillos; a veces tienen recuerdos, nombres y voces que conoces demasiado bien.\n" +
                    "Si quieres escapar, tendrás que reunir fragmentos de verdad, leer lo que otros dejaron atrás y construir el artefacto " +
                    "que abre la Puerta del Juicio.\n" +
                    "Pero recuerda: en Silent Hill, algunas puertas no se abren con llaves... se abren con culpa.";

    /**
     * Crea una nueva partida.
     *
     * @param mapa Mapa de habitaciones.
     * @param jugador Jugador del juego.
     * @param habitacionInicial Habitación inicial.
     */
    public Juego(Habitacion[] mapa, Jugador jugador, int habitacionInicial) {
        this.mapa = mapa;
        this.jugador = jugador;
        this.habitacionActual = habitacionInicial;
        this.jugador.setPosicion(habitacionInicial);
    }

    /**
     * Punto de entrada principal del programa.
     *
     * @param args Argumentos de consola.
     */
    public static void main(String[] args) {

        Habitacion sala1 = new Habitacion(
                "Carretera de Silent Hill",
                "La niebla cubre la carretera. Hay marcas de frenazo, un coche destrozado y una señal oxidada que apunta al pueblo."
        );

        Habitacion sala2 = new Habitacion(
                "Hospital Otherworld",
                "El hospital está consumido por óxido, sangre reseca y silencio. Una radio crepita sola en la recepción."
        );

        Habitacion sala3 = new Habitacion(
                "Mansión de los Retratos",
                "Una mansión oscura donde los cuadros parecen observarte. La luz verde de los pasillos hace que todo parezca un recuerdo corrupto."
        );

        Habitacion sala4 = new Habitacion(
                "Refugio del Juicio",
                "Un complejo subterráneo mezcla restos militares, terminales de neón, símbolos rituales y ecos de una civilización rota."
        );

        Habitacion[] mapa = new Habitacion[]{sala1, sala2, sala3, sala4};

        Pilas pilas = new Pilas(
                "Pilas",
                "Un paquete de pilas todavía funcional. Puede alimentar un objeto esencial para avanzar.",
                true
        );

        Nota notaCarretera = new Nota(
                "Nota",
                "Un trozo de papel húmedo encontrado junto al coche.",
                true,
                "No fue un accidente. Viniste aquí porque querías olvidar. La luz te mostrará lo que enterraste."
        );

        Nota notaHospital = new Nota(
                "Informe",
                "Un informe clínico arrugado y manchado.",
                true,
                "Paciente con amnesia disociativa. Presenta visión fragmentada de la realidad: crimen, plaga, espectros, ruina y máquinas. Repite una palabra: ASHES33."
        );

        Nota notaRefugio = new Nota(
                "Registro",
                "Un registro técnico guardado en una terminal dañada.",
                true,
                "Proyecto R.E.P.O. autorizado. El Artefacto de Apertura requiere fuente de energía portátil y activación con código 5973. La llave de acceso fue escondida tras el retrato sellado."
        );

        Llave llaveMansion = new Llave(
                "Llave",
                "Una llave ornamentada, fría al tacto, con un grabado espectral. En el reverso puede leerse: ASHES33.",
                true,
                "ASHES33"
        );

        Llave artefactoApertura = new Llave(
                "Artefacto",
                "Un artefacto improvisado, ensamblado con luz, energía y memoria. Vibra con una frecuencia extraña. Código: 5973.",
                true,
                "5973"
        );

        Contenedor coche = new Contenedor(
                "Coche",
                "El coche del accidente. La puerta del copiloto está forzada y el maletero parece medio abierto.",
                true,
                null,
                null
        );

        Contenedor taquilla = new Contenedor(
                "Taquilla",
                "Una taquilla metálica del hospital. Está oxidada, pero aún conserva un cierre funcional.",
                true,
                null,
                null
        );

        Contenedor retrato = new Contenedor(
                "Retrato",
                "Un retrato enorme de una familia sin rostro. Detrás del marco hay una cerradura oculta con teclado.",
                true,
                "ASHES33",
                null
        );

        Puerta puertaSalida = new Puerta(
                "Puerta",
                "Una puerta de acero ennegrecido, cubierta de símbolos y marcas de quemaduras. Solo reaccionará al Artefacto de Apertura con código 5973.",
                true
        );

        // Objetos en habitaciones
        sala1.añadirObjeto(coche);
        sala1.añadirObjeto(notaCarretera);

        sala2.añadirObjeto(notaHospital);
        sala2.añadirObjeto(taquilla);

        sala3.añadirObjeto(retrato);

        sala4.añadirObjeto(notaRefugio);
        sala4.añadirObjeto(puertaSalida);

        // Contenidos
        coche.ponerObjetoDentro(pilas);
        taquilla.ponerObjetoDentro(artefactoApertura);
        retrato.ponerObjetoDentro(llaveMansion);

        Jugador jugador = new Jugador(7);
        Juego juego = new Juego(mapa, jugador, 0);
        juego.iniciar();
    }

    /**
     * Inicia el juego.
     */
    public void iniciar() {
        System.out.println("=== SILENT HILL: BROKEN WORLDS ===\n");
        System.out.println(DESCRIPCION_JUEGO + "\n");
        System.out.println("Escribe 'ayuda' para ver los comandos.\n");

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

    /**
     * Muestra la habitación actual.
     */
    private void mostrarHabitacionActual() {
        System.out.println("--------------------------------------------------");
        System.out.println("📍 Estás en: " + mapa[habitacionActual].getNombre());
        System.out.println("--------------------------------------------------");
        System.out.println(mapa[habitacionActual].getDescripcion());
        mostrarInfoHabitacion();
    }

    /**
     * Procesa el comando introducido.
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
                System.out.println("Abandonas la niebla... por ahora.");
                System.exit(0);
            }
            default -> System.out.println("Comando no reconocido. Escribe 'ayuda'.");
        }
    }

    /**
     * Mueve al jugador a la derecha.
     */
    private void moverDerecha() {
        if (habitacionActual < mapa.length - 1) {
            habitacionActual++;
            jugador.setPosicion(habitacionActual);
            System.out.println("Avanzas entre la niebla hacia una nueva zona del pueblo.");
        } else {
            System.out.println("No puedes ir más a la derecha.");
        }
    }

    /**
     * Mueve al jugador a la izquierda.
     */
    private void moverIzquierda() {
        if (habitacionActual > 0) {
            habitacionActual--;
            jugador.setPosicion(habitacionActual);
            System.out.println("Retrocedes con cautela, como si el pueblo intentara retenerte.");
        } else {
            System.out.println("No puedes ir más a la izquierda.");
        }
    }

    /**
     * Muestra la información de la habitación.
     */
    private void mirar() {
        System.out.println(mapa[habitacionActual].getDescripcion());
        mostrarInfoHabitacion();
    }

    /**
     * Muestra el inventario del jugador.
     */
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
            System.out.print("vacío");
        }

        System.out.println();
    }

    /**
     * Muestra los objetos de la habitación.
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
     * Muestra la información general de la habitación.
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
     * Permite coger un objeto.
     *
     * @param partes Comando separado por partes.
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
     * Examina un objeto.
     *
     * @param partes Comando separado por partes.
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
     * Abre un contenedor o una puerta.
     *
     * @param partes Comando introducido.
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

            if (contenedor.getNombre().equalsIgnoreCase("Retrato") && llave != null) {
                System.out.println("🔓 Introduces el código de la llave y el retrato se desplaza con un chirrido insoportable.");

                boolean quitada = jugador.soltarPorNombre(llave.getNombre());
                if (quitada) {
                    System.out.println("La llave se ha usado y eliminado de tu inventario.");
                }
            }
        }

        if (obj instanceof Puerta) {
            llave = jugador.buscarLlavePorCodigo("5973");

            if (llave == null) {
                System.out.println("⛔ No tienes el Artefacto de Apertura necesario para activar la Puerta del Juicio.");
                return;
            }
        }

        RespuestaAccion res = abrible.abrir(llave);
        System.out.println(res.mensaje());

        if (!res.esExito()) return;

        if (obj instanceof Puerta && llave != null) {
            boolean quitada = jugador.soltarPorNombre(llave.getNombre());
            if (quitada) {
                System.out.println("El artefacto ha sido absorbido por el mecanismo de la puerta.");
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
     * Combina dos objetos del inventario.
     *
     * @param partes Comando introducido.
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

        System.out.println("¡Combinación completada! Creado: " + resultado.getNombre());

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
     * Cruza la puerta final.
     *
     * @param partes Comando introducido.
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
            System.out.println("Primero abre la puerta con el artefacto correcto.");
            return;
        }

        System.out.println("""
                ╔════════════════════════════════════════════════════╗
                ║ La sirena deja de sonar.                           ║
                ║ La niebla se aparta lentamente de tu camino.       ║
                ║ Por primera vez, Silent Hill guarda silencio.      ║
                ║                                                    ║
                ║ Has reunido los fragmentos de tu memoria.          ║
                ║ Ya sabes la verdad: no llegaste aquí por azar.     ║
                ║ El pueblo te llamó para obligarte a recordar.      ║
                ║                                                    ║
                ║ Frente a ti, la puerta final se abre.              ║
                ║ No sabes si al cruzarla serás libre...             ║
                ║ o si simplemente despertarás en otra pesadilla.    ║
                ║                                                    ║
                ║         SILENT HILL: BROKEN WORLDS                 ║
                ╚════════════════════════════════════════════════════╝
                """);

        System.exit(0);
    }

    /**
     * Muestra los comandos disponibles.
     */
    private void ayuda() {
        System.out.println("""
                Comandos disponibles:
                - izquierda / derecha
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
     * @return Objeto encontrado o null.
     */
    private Objeto buscarEnHabitacion(String nombre) {
        Objeto[] objetos = mapa[habitacionActual].getObjetos();
        String n = nombre.toLowerCase(Locale.ROOT);

        for (Objeto obj : objetos) {
            if (obj != null && obj.getNombre().toLowerCase(Locale.ROOT).equals(n)) {
                return obj;
            }
        }
        return null;
    }

    /**
     * Busca un objeto en la habitación o en el inventario.
     *
     * @param nombre Nombre del objeto.
     * @return Objeto encontrado o null.
     */
    private Objeto buscarObjeto(String nombre) {
        Objeto encontrado = buscarEnHabitacion(nombre);

        if (encontrado != null) {
            return encontrado;
        }

        String n = nombre.toLowerCase(Locale.ROOT);
        for (Objeto obj : jugador.getInventario()) {
            if (obj != null && obj.getNombre().toLowerCase(Locale.ROOT).equals(n)) {
                return obj;
            }
        }
        return null;
    }
}