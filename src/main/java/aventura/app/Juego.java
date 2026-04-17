package aventura.app;

import aventura.domain.Habitacion;
import aventura.domain.Jugador;
import aventura.domain.Llave;
import aventura.domain.Objeto;
import aventura.exceptions.AventuraException;
import aventura.exceptions.InventarioLlenoException;
import aventura.exceptions.ObjetoNoCompatibleException;
import aventura.interfaces.Abrible;
import aventura.interfaces.Combinable;
import aventura.io.AventuraConfig;
import aventura.io.CargadorAventura;
import aventura.io.MiEntradaSalida;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Juego {

    private Map<String, Habitacion> habitaciones;
    private Jugador jugador;
    private AventuraConfig config;
    private CargadorAventura cargador;
    private boolean terminado;

    public Juego() {
        this.habitaciones = new HashMap<>();
        this.terminado = false;
    }

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

    private Habitacion getHabitacionActual() {
        if (jugador == null) {
            return null;
        }
        return habitaciones.get(jugador.getHabitacionActual());
    }

    private void comandoMirar() {
        Habitacion actual = getHabitacionActual();
        if (actual == null) {
            System.out.println("No se pudo localizar la habitación actual.");
            return;
        }
        System.out.println(actual.mirar());
    }

    private void comandoIr(String direccion) {
        if (direccion == null || direccion.isBlank()) {
            System.out.println("¿A dónde quieres ir?");
            return;
        }

        Habitacion actual = getHabitacionActual();
        if (actual == null) {
            System.out.println("No se pudo localizar la habitación actual.");
            return;
        }

        String destinoId = actual.getDestino(direccion.toLowerCase());

        if (destinoId == null) {
            System.out.println("No puedes ir por ahí.");
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

    private void comandoCoger(String nombreObjeto) {
        if (nombreObjeto == null || nombreObjeto.isBlank()) {
            System.out.println("¿Qué quieres coger?");
            return;
        }

        Habitacion actual = getHabitacionActual();
        if (actual == null) {
            System.out.println("No se pudo localizar la habitación actual.");
            return;
        }

        Objeto objeto = actual.buscar(nombreObjeto);

        if (objeto == null) {
            System.out.println("No ves ese objeto aquí.");
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

    private void comandoAbrir(String nombreObjeto) {
        if (nombreObjeto == null || nombreObjeto.isBlank()) {
            System.out.println("¿Qué quieres abrir?");
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

        if (!(objeto instanceof Abrible abrible)) {
            System.out.println("Eso no se puede abrir.");
            return;
        }

        Llave llaveValida = buscarLlaveCompatible(abrible);

        var respuesta = abrible.abrir(llaveValida);
        System.out.println(respuesta.mensaje());

        if (respuesta.esExito() && abrible.getContenido() != null) {
            Objeto contenido = abrible.getContenido();
            contenido.setVisible(true);

            try {
                actual.agregarObjeto(contenido);
                abrible.setContenido(null);
                System.out.println("Dentro encuentras: " + contenido.getNombre());
            } catch (AventuraException e) {
                System.out.println("No se pudo sacar el contenido: " + e.getMessage());
            }
        }
    }

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

    private Llave buscarLlaveCompatible(Abrible abrible) {
        String codigoNecesario = abrible.getCodigoNecesario();

        if (codigoNecesario == null) {
            return null;
        }

        for (Objeto obj : jugador.getInventario()) {
            if (obj instanceof Llave llave) {
                if (codigoNecesario.equals(llave.getCodigoSeguridad())) {
                    return llave;
                }
            }
        }

        return null;
    }

    /**
     * Busca un objeto por su nombre, primero en la habitación actual y luego en el inventario del jugador.
     *
     * @param nombre El nombre del objeto a buscar.
     * @return El objeto si se encuentra, o null si no se encuentra en ninguno de los dos lugares.
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
     * Elimina un objeto del juego, ya sea que esté en la habitación o en el inventario.
     * Usado tras combinar objetos.
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