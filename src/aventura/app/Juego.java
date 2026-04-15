package aventura.app;

import aventura.domain.Habitacion;
import aventura.domain.Jugador;
import aventura.domain.Llave;
import aventura.domain.Objeto;
import aventura.exceptions.AventuraException;
import aventura.interfaces.Abrible;
import aventura.io.AventuraConfig;
import aventura.io.CargadorAventura;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Juego {

    private Map<String, Habitacion> habitaciones;
    private Jugador jugador;
    private AventuraConfig config;
    private CargadorAventura cargador;
    private boolean terminado;

    public Juego() {
        this.habitaciones = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
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
            this.habitaciones = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
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

        String destinoId = actual.getDestino(direccion);

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

    public static void main(String[] args) {
        new Juego().iniciar();
    }
}