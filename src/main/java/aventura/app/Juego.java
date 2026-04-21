package aventura.app;

import aventura.domain.Habitacion;
import aventura.domain.Jugador;
import aventura.domain.Llave;
import aventura.domain.Objeto;
import aventura.domain.Puerta;
import aventura.exceptions.AventuraException;
import aventura.exceptions.InventarioLlenoException;
import aventura.interfaces.Abrible;
import aventura.interfaces.Combinable;
import aventura.io.AventuraConfig;
import aventura.io.CargadorAventura;
import aventura.io.MiEntradaSalida;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

/**
 * Coordina el flujo principal de la aventura, procesa comandos
 * y administra el estado de la partida.
 *
 * @author Yeray Durán y Manuel Perez
 */
public class Juego {

    /**
     * Identificador interno usado para representar la salida final del juego.
     */
    private static final String DESTINO_FIN_JUEGO = "__FIN_JUEGO__";

    /**
     * Mapa de habitaciones cargadas, indexadas por su identificador.
     */
    private Map<String, Habitacion> habitaciones;
    /**
     * Jugador activo de la partida.
     */
    private Jugador jugador;
    /**
     * Configuración principal de la aventura cargada desde archivos.
     */
    private AventuraConfig config;
    /**
     * Componente encargado de leer la configuración del mundo.
     */
    private CargadorAventura cargador;
    /**
     * Marca que indica si la partida ha finalizado.
     */
    private boolean terminado;

    /**
     * Crea una instancia de juego con estado inicial vacío.
     */
    public Juego() {
        this.habitaciones = new HashMap<>();
        this.terminado = false;
    }

    /**
     * Inicializa el juego completo.
     *
     * Se encarga de cargar el mundo desde los ficheros de configuración,
     * crear el jugador, situarlo en la habitación inicial definida en la
     * configuración y mostrar la descripción inicial del juego.
     *
     * Además, valida que la habitación inicial exista en el mapa cargado
     * y lanza errores en caso contrario. Finalmente, inicia el bucle
     * principal de interacción con el usuario.
     */
    public void iniciar() {
        try {
            cargarMundo();

            this.jugador = new Jugador("Jugador");

            if (config.getHabitacionInicial() == null || config.getHabitacionInicial().isBlank()) {
                throw new IllegalStateException("El fichero de configuración no define una habitación inicial.");
            }

            this.jugador.setHabitacionActual(config.getHabitacionInicial());

            Habitacion actual = getHabitacionActual();
            if (actual == null) {
                throw new IllegalStateException(
                        "La habitación inicial '" + jugador.getHabitacionActual() + "' no existe en el mapa cargado."
                );
            }
            System.out.println();
            System.out.println(config.getDescripcionGeneral());
            System.out.println();
            System.out.println(actual.mirar());


            buclePrincipal();

        } catch (IOException e) {
            System.out.println("Error al cargar la aventura: " + e.getMessage());
        }
    }

    /**
     * Carga el mundo del juego desde los ficheros de configuración.
     *
     * Utiliza un {@link CargadorAventura} para leer el fichero de propiedades
     * y el JSON asociado, generando un objeto {@link AventuraConfig}.
     *
     * A partir de esta configuración, inicializa el mapa de habitaciones.
     * Si ocurre algún problema durante la carga o los datos son inválidos,
     * se lanza una excepción de tipo {@link IOException}.
     *
     * @throws IOException Si no se puede leer o interpretar la configuración.
     */
    private void cargarMundo() throws IOException {
        this.cargador = new CargadorAventura(Path.of("config.properties"));
        this.cargador.cargarConfiguracion();
        this.config = cargador.cargarMundoBase();

        if (config == null) {
            throw new IOException("No se pudo cargar el archivo aventura.json.");
        }

        this.habitaciones = config.getHabitaciones();

        if (this.habitaciones == null) {
            this.habitaciones = new HashMap<>();
        }
    }

    /**
     * Bucle principal del juego.
     *
     * Mantiene la ejecución continua leyendo comandos del usuario desde
     * consola hasta que el juego se marca como terminado. Ignora líneas
     * vacías y delega el procesamiento de cada comando en el método
     * {@link #procesarComando(String)}.
     */
    private void buclePrincipal() {
        Scanner sc = new Scanner(System.in);

        while (!terminado) {
            System.out.print("> ");
            String linea = sc.nextLine().trim();

            if (linea.isBlank()) {
                continue;
            }

            procesarComando(linea);
        }
    }

    /**
     * Procesa la entrada del usuario, separando la acción (verbo) de los parámetros (argumento).
     * Deriva la ejecución al método correspondiente según el comando introducido.
     *
     * @param linea La cadena de texto completa introducida por el usuario en la consola.
     */
    private void procesarComando(String linea) {
        String[] partes = linea.split("\\s+", 2);
        String verbo = partes[0].toLowerCase();
        String argumento = partes.length > 1 ? partes[1].trim() : "";

        switch (verbo) {
            case "mirar" -> comandoMirar();
            case "ir" -> comandoIr(argumento);
            case "coger" -> comandoCoger(argumento);
            case "inventario" -> comandoInventario();
            case "abrir" -> comandoAbrir(argumento);
            case "examinar" -> comandoExaminar(argumento);
            case "combinar" -> comandoCombinar(argumento);
            case "salir", "fin", "quit" -> {
                System.out.println("Fin de la partida.");
                terminado = true;
            }
            default -> System.out.println("No entiendo ese comando.");
        }
    }

    /**
     * Devuelve la habitación actual en la que se encuentra el jugador.
     *
     * Obtiene el identificador de la habitación desde el jugador y lo
     * utiliza para buscarla en el mapa de habitaciones.
     *
     * @return La {@link Habitacion} actual o null si no se puede determinar.
     */
    private Habitacion getHabitacionActual() {
        if (jugador == null) {
            return null;
        }
        return habitaciones.get(jugador.getHabitacionActual());
    }

    /**
     * Ejecuta el comando "mirar".
     *
     * Muestra la descripción de la habitación actual, incluyendo sus
     * objetos y posibles salidas. Si no se puede determinar la habitación,
     * informa al usuario.
     */
    private void comandoMirar() {
        Habitacion actual = getHabitacionActual();
        if (actual == null) {
            System.out.println("No se pudo localizar la habitación actual.");
            return;
        }
        System.out.println(actual.mirar());
    }

    /**
     * Desplaza al jugador hacia una dirección específica si existe una salida válida.
     * Actualiza la posición del jugador a la nueva habitación y muestra la descripción
     * del nuevo entorno.
     *
     * @param direccion La dirección hacia la que el jugador desea moverse (por ejemplo: "norte", "sur").
     */
    private void comandoIr(String direccion) {
        Habitacion actual = getHabitacionActual();
        if (actual == null) {
            System.out.println("No se pudo localizar la habitación actual.");
            return;
        }

        if (direccion == null || direccion.isBlank()) {
            if (actual.getSalidas().isEmpty()) {
                System.out.println("No hay salidas disponibles desde aquí.");
                return;
            }

            System.out.println("Salidas disponibles: " + String.join(", ", actual.getSalidas().keySet()));
            direccion = MiEntradaSalida.solicitarCadena("¿Dónde quieres ir? ").trim();

            if (direccion.isBlank()) {
                System.out.println("No has indicado ninguna dirección.");
                return;
            }
        }

        String destinoId = actual.getDestino(direccion.toLowerCase());

        if (destinoId == null) {
            System.out.println("No puedes ir por ahí.");
            return;
        }

        if (DESTINO_FIN_JUEGO.equals(destinoId)) {
            System.out.println("Cruzas la Puerta del Juicio y la niebla se disipa. Fin del juego.");
            terminado = true;
            return;
        }

        Habitacion destino = habitaciones.get(destinoId);

        if (destino == null) {
            System.out.println("La habitación destino '" + destinoId + "' no existe en el mapa.");
            return;
        }

        jugador.setHabitacionActual(destinoId);
        System.out.println(destino.mirar());
    }

    /**
     * Intenta recoger un objeto de la habitación actual y añadirlo al inventario del jugador.
     * Si el objeto existe en la habitación y el inventario no está lleno, el objeto es
     * transferido exitosamente.
     *
     * @param nombreObjeto El nombre del objeto que el jugador desea coger.
     */
    private void comandoCoger(String nombreObjeto) {
        Habitacion actual = getHabitacionActual();
        if (actual == null) {
            System.out.println("No se pudo localizar la habitación actual.");
            return;
        }

        if (nombreObjeto != null && !nombreObjeto.isBlank()) {
            System.out.println("Para coger, escribe solo 'coger'.");
        }

        List<Objeto> objetosCogibles = obtenerObjetosCogibles(actual);
        if (objetosCogibles.isEmpty()) {
            System.out.println("No hay objetos cogibles en esta habitación.");
            return;
        }

        System.out.println("Objetos cogibles: " + nombresDeObjetos(objetosCogibles));
        String nombreSeleccionado = MiEntradaSalida.solicitarCadena("¿Qué objeto quieres coger? ").trim();

        if (nombreSeleccionado.isBlank()) {
            System.out.println("No has indicado ningún objeto.");
            return;
        }

        Objeto objeto = buscarPorNombre(objetosCogibles, nombreSeleccionado);

        if (objeto == null) {
            System.out.println("Ese objeto no se puede coger aquí.");
            return;
        }

        try {
            jugador.coger(objeto);
            actual.eliminarObjeto(objeto);
            System.out.println("Has cogido: " + objeto.getNombre());
        } catch (AventuraException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Muestra por consola una lista con todos los objetos que el jugador lleva actualmente
     * en su inventario. Si el inventario está vacío, informa al usuario de ello.
     */
    private void comandoInventario() {
        if (jugador.getInventario().isEmpty()) {
            System.out.println("Tu inventario está vacío.");
            return;
        }

        System.out.println("Llevas:");
        for (Objeto obj : jugador.getInventario()) {
            System.out.println(" - " + obj.getNombre());
        }
    }

    /**
     * Ejecuta el comando "examinar" sobre un objeto.
     *
     * Busca el objeto indicado primero en la habitación actual y, si no
     * lo encuentra, en el inventario del jugador. Si existe, muestra su
     * descripción por pantalla.
     *
     * En caso de no encontrar el objeto, informa al usuario.
     *
     * @param nombreObjeto Nombre del objeto a examinar.
     */
    private void comandoExaminar(String nombreObjeto) {
        if (nombreObjeto == null || nombreObjeto.isBlank()) {
            System.out.println("¿Qué quieres examinar?");
            return;
        }

        Habitacion actual = getHabitacionActual();
        if (actual == null) {
            System.out.println("No se pudo localizar la habitación actual.");
            return;
        }

        Objeto objeto = actual.buscar(nombreObjeto);

        if (objeto == null) {
            objeto = jugador.buscarEnInventario(nombreObjeto);
        }

        if (objeto == null) {
            System.out.println("No encuentras ese objeto.");
            return;
        }

        System.out.println(objeto.getDescripcion());
    }

    /**
     * Ejecuta el comando "abrir" sobre un objeto.
     *
     * Busca el objeto indicado primero en la habitación actual y luego en
     * el inventario del jugador. Comprueba si el objeto implementa la
     * interfaz {@link Abrible} y, en caso afirmativo, intenta abrirlo
     * utilizando una llave compatible del inventario.
     *
     * Si la apertura tiene éxito y el objeto contiene otro en su interior,
     * este se hace visible y se añade a la habitación actual.
     * También gestiona errores como objetos inexistentes, no abribles
     * o problemas al añadir el contenido.
     *
     * @param nombreObjeto Nombre del objeto que se desea abrir.
     */
    private void comandoAbrir(String nombreObjeto) {
        Habitacion actual = getHabitacionActual();
        if (actual == null) {
            System.out.println("No se pudo localizar la habitación actual.");
            return;
        }

        if (nombreObjeto != null && !nombreObjeto.isBlank()) {
            System.out.println("Para abrir, escribe solo 'abrir'.");
        }

        List<Objeto> objetosAbribles = obtenerObjetosAbribles(actual);
        if (objetosAbribles.isEmpty()) {
            System.out.println("No hay muebles o contenedores abribles en esta habitación.");
            return;
        }

        System.out.println("Objetos abribles: " + nombresDeObjetos(objetosAbribles));
        String nombreSeleccionado = MiEntradaSalida.solicitarCadena("¿Qué objeto quieres abrir? ").trim();


        Objeto objeto = buscarPorNombre(objetosAbribles, nombreSeleccionado);

        if (objeto == null) {
            System.out.println("Ese objeto no se puede abrir aquí.");
            return;
        }

        Abrible abrible = (Abrible) objeto;
        Llave llaveValida = buscarLlaveCompatible(abrible);

        var respuesta = abrible.abrir(llaveValida);
        System.out.println(respuesta.mensaje());

        if (respuesta.esExito() && objeto instanceof Puerta) {
            actual.addSalida("norte", DESTINO_FIN_JUEGO);
            System.out.println("Una nueva salida se abre hacia el norte.");
        }

        if (respuesta.esExito() && abrible.getContenido() != null) {
            Objeto contenido = abrible.getContenido();
            contenido.setVisible(true);

            try {
                actual.agregarObjeto(contenido);
                abrible.setContenido(null);
                System.out.println("Dentro encuentras: " + contenido.getNombre());
                consumirObjeto(llaveValida);
            } catch (AventuraException e) {
                System.out.println("No se pudo sacar el contenido: " + e.getMessage());
            }
        }
    }

    /**
     * Obtiene los objetos visibles de una habitación que pueden recogerse.
     *
     * @param habitacion habitación a inspeccionar.
     * @return lista de objetos inventariables visibles.
     */
    private List<Objeto> obtenerObjetosCogibles(Habitacion habitacion) {
        List<Objeto> objetosCogibles = new ArrayList<>();
        for (Objeto obj : habitacion.getObjetos()) {
            if (obj != null && obj.isVisible() && obj instanceof aventura.interfaces.Inventariable) {
                objetosCogibles.add(obj);
            }
        }
        return objetosCogibles;
    }

    /**
     * Obtiene los objetos visibles de una habitación que implementan apertura.
     *
     * @param habitacion habitación a inspeccionar.
     * @return lista de objetos abribles visibles.
     */
    private List<Objeto> obtenerObjetosAbribles(Habitacion habitacion) {
        return habitacion.getObjetos().stream()
                .filter(Objects::nonNull)
                .filter(Objeto::isVisible)
                .filter(obj -> obj instanceof Abrible)
                .toList();
    }

    /**
     * Busca un objeto por nombre dentro de una lista dada.
     *
     * @param objetos colección de objetos candidata.
     * @param nombre nombre a localizar (sin distinguir mayúsculas/minúsculas).
     * @return objeto encontrado o {@code null} si no existe coincidencia.
     */
    private Objeto buscarPorNombre(List<Objeto> objetos, String nombre) {
        for (Objeto obj : objetos) {
            if (obj.getNombre().equalsIgnoreCase(nombre)) {
                return obj;
            }
        }
        return null;
    }

    /**
     * Convierte una lista de objetos en una cadena con sus nombres separados por comas.
     *
     * @param objetos lista de objetos a representar.
     * @return texto con los nombres listados.
     */
    private String nombresDeObjetos(List<Objeto> objetos) {
        List<String> nombres = new ArrayList<>();
        for (Objeto obj : objetos) {
            nombres.add(obj.getNombre());
        }
        return String.join(", ", nombres);
    }

    /**
     * Intenta combinar dos objetos presentes en el inventario o en la habitación actual.
     * Si los objetos son compatibles y la combinación tiene éxito, los objetos originales
     * son consumidos y el objeto resultante se añade al inventario del jugador. Si el
     * inventario está lleno, el nuevo objeto caerá en la habitación actual.
     *
     * @param argumento Cadena de texto introducida por el usuario (actualmente no se usa directamente,
     *                  ya que el método solicita interactivamente los nombres de los objetos).
     */
    private void comandoCombinar(String argumento) {
        System.out.println("¿Qué objetos quieres combinar?");
        mostrarTodosLosObjetos();

        String objeto1Nombre = MiEntradaSalida.solicitarCadena("Primer objeto: ").trim();
        Objeto objeto1 = buscarObjeto(objeto1Nombre);

        if (objeto1 == null){
            System.out.printf("No se encontró %s%n", objeto1Nombre);
            return;
        }

        String objeto2Nombre = MiEntradaSalida.solicitarCadena("Segundo objeto: ").trim();
        Objeto objeto2 = buscarObjeto(objeto2Nombre);

        if (objeto2 == null) {
            System.out.printf("No se encontró %s%n", objeto2Nombre);
            return;
        }

        if (objeto1 instanceof Combinable combinable1) {
            try {
                Objeto resultado = combinable1.combinar(objeto2);
                if (resultado != null) {
                    System.out.printf("Has combinado %s y %s para crear %s.%n",
                            objeto1.getNombre(), objeto2.getNombre(), resultado.getNombre());

                    // 1. Eliminar los objetos originales del inventario o de la habitación
                    consumirObjeto(objeto1);
                    consumirObjeto(objeto2);

                    // 2. Añadir el nuevo objeto al inventario
                    try {
                        jugador.coger(resultado);
                        System.out.println("El nuevo objeto está en tu inventario.");
                    } catch (InventarioLlenoException e) {
                        // El inventario está lleno, dejamos el objeto en la habitación
                        System.out.println("¡Cuidado! Tu inventario estaba lleno y el objeto cayó al suelo.");
                        try {
                            getHabitacionActual().agregarObjeto(resultado);
                            System.out.println("El nuevo objeto está en la habitación actual.");
                        } catch (AventuraException ex) {
                            System.out.println("La habitación también está llena... el objeto se ha perdido en el limbo (Bug).");
                        }
                    }
                } else {
                    System.out.println("La combinación no produjo ningún objeto.");
                }

            } catch (AventuraException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println(objeto1.getNombre() + " no se puede combinar con otros objetos.");
        }
    }


    /**
     * Busca en el inventario del jugador una llave que coincida con el código de seguridad
     * necesario para abrir un objeto específico.
     *
     * @param abrible El objeto que implementa la interfaz {@link Abrible} y que se desea abrir.
     * @return El objeto {@link Llave} compatible si el jugador lo posee en su inventario;
     *         {@code null} en caso contrario.
     */
    private Llave buscarLlaveCompatible(Abrible abrible) {
        return jugador.getInventario().stream()
                .filter(obj -> obj instanceof Llave)
                .map(obj -> (Llave) obj)
                .filter(llave -> Objects.equals(llave.getCodigoSeguridad(), abrible.getCodigoNecesario()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Busca un objeto por su nombre en el entorno del jugador.
     * Prioriza la búsqueda en la habitación actual (lo que el jugador ve) y, si no lo encuentra,
     * busca en el inventario del jugador (lo que el jugador tiene).
     *
     * @param nombre El nombre exacto del objeto a buscar.
     * @return El {@link Objeto} encontrado, o {@code null} si no existe en la habitación ni en el inventario.
     */
    private Objeto buscarObjeto(String nombre) {
        // 1. Buscamos en la habitación (Prioridad 1: Lo que veo)
        Objeto encontrado = getHabitacionActual().buscar(nombre);

        if (encontrado != null) {
            return encontrado;
        }

        // 2. Si no está en la sala, buscamos en el bolsillo (Prioridad 2: Lo que tengo)
        return jugador.buscarEnInventario(nombre);
    }

    /**
     * Muestra todos los objetos accesibles al jugador.
     *
     * Actualmente, delega en la visualización del inventario, pero puede
     * ampliarse para incluir también los objetos de la habitación u otras
     * fuentes.
     */
    private void mostrarTodosLosObjetos() {
        mostrarObjetosInventario();
    }


    /**
     * Muestra los objetos presentes en el inventario del jugador.
     */
    private void mostrarObjetosInventario() {
        System.out.print("Objetos en el inventario: ");
        boolean hayObjetos = false;
        boolean hayMasDeUnObjeto = false;
        for (Objeto objeto : jugador.getInventario()) {
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

    /**
     * Elimina de forma definitiva un objeto del juego. Intenta borrarlo tanto del
     * inventario del jugador como de la habitación actual para asegurar que desaparezca
     * por completo (utilizado típicamente tras combinar objetos con éxito).
     *
     * @param obj El {@link Objeto} que se desea eliminar de la partida.
     */
    private void consumirObjeto(Objeto obj) {
        // Intentamos borrar del inventario
        jugador.eliminarDeInventario(obj);
        // Intentamos borrar de la habitación
        getHabitacionActual().eliminarObjeto(obj);
    }


    public static void main(String[] args) {
        new Juego().iniciar();
    }
}