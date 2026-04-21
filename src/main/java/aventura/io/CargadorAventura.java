package aventura.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Properties;

/**
 * Se encarga de cargar la configuración y el mundo base de la aventura
 * desde los archivos definidos en el sistema de configuración.
 *
 * Esta clase lee primero un fichero de propiedades para localizar
 * el directorio base y el archivo JSON principal del juego. Después,
 * utiliza Gson para deserializar dicho archivo y convertirlo en un
 * objeto {@link AventuraConfig}.
 */
public class CargadorAventura {

    /**
     * Instancia de Gson utilizada para convertir el archivo JSON
     * del mundo en objetos Java.
     *
     * Incluye un adaptador personalizado para la jerarquía de
     * objetos del juego.
     */
    private final Gson gson;

    /**
     * Ruta del fichero de configuración principal.
     *
     * Normalmente apunta a un archivo como {@code config.properties}.
     */
    private final Path rutaConfig;

    /**
     * Directorio base donde se encuentra el archivo principal
     * de la aventura.
     */
    private Path directorioBase;

    /**
     * Ruta completa del archivo JSON base que contiene
     * la definición del mundo.
     */
    private Path archivoBase;

    /**
     * Construye un cargador de aventura a partir de la ruta
     * del fichero de configuración.
     *
     * Además, inicializa la instancia de Gson con formato legible
     * y registra el adaptador necesario para deserializar objetos
     * del dominio del juego.
     *
     * @param rutaConfig ruta del archivo de configuración.
     */
    public CargadorAventura(Path rutaConfig) {
        this.rutaConfig = rutaConfig;
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(aventura.domain.Objeto.class, new ObjetoAdapter())
                .create();
    }

    /**
     * Carga y valida la configuración inicial del juego.
     *
     * Este método lee el fichero de propiedades indicado en
     * {@code rutaConfig}, obtiene las propiedades necesarias para
     * localizar el directorio base y el archivo JSON principal,
     * y comprueba que ambas rutas existan realmente en el sistema
     * de ficheros.
     *
     * Si falta el fichero de configuración, alguna propiedad
     * obligatoria o alguna de las rutas resultantes no existe,
     * se lanza una excepción.
     *
     * @throws IOException si no se puede leer el fichero de configuración,
     *                     si faltan propiedades obligatorias o si no existen
     *                     las rutas esperadas.
     */
    public void cargarConfiguracion() throws IOException {
        Properties properties = new Properties();

        if (!Files.exists(rutaConfig)) {
            throw new IOException("No existe el fichero de configuración: " + rutaConfig);
        }

        try (Reader reader = Files.newBufferedReader(rutaConfig)) {
            properties.load(reader);
        }

        String dirBase = properties.getProperty("juego.directorio.base");
        String archivo = properties.getProperty("juego.archivo.base");

        if (dirBase == null || dirBase.isBlank()) {
            throw new IOException("Falta la propiedad juego.directorio.base en config.properties");
        }

        if (archivo == null || archivo.isBlank()) {
            throw new IOException("Falta la propiedad juego.archivo.base en config.properties");
        }

        this.directorioBase = Path.of(dirBase).normalize();
        this.archivoBase = this.directorioBase.resolve(archivo).normalize();

        if (!Files.exists(this.directorioBase)) {
            throw new IOException("No existe el directorio base: " + this.directorioBase);
        }

        if (!Files.exists(this.archivoBase)) {
            throw new IOException("No existe el archivo de aventura: " + this.archivoBase);
        }
    }

    /**
     * Carga el mundo base de la aventura desde el archivo JSON principal.
     *
     * Antes de realizar la lectura, comprueba que la configuración
     * haya sido cargada correctamente y que la ruta del archivo base
     * esté disponible.
     *
     * Después, deserializa el contenido del JSON en un objeto
     * {@link AventuraConfig} y valida que dicho objeto no sea nulo
     * y que contenga habitaciones definidas.
     *
     * @return objeto de configuración con el mundo cargado.
     * @throws IOException si la configuración no se ha cargado antes,
     *                     si el archivo está vacío, mal formado
     *                     o no contiene habitaciones válidas.
     */
    public AventuraConfig cargarMundoBase() throws IOException {
        if (archivoBase == null) {
            throw new IOException("Primero debes cargar la configuración.");
        }

        try (Reader reader = Files.newBufferedReader(archivoBase)) {
            AventuraConfig config = gson.fromJson(reader, AventuraConfig.class);

            if (config == null) {
                throw new IOException("El archivo aventura.json está vacío o mal formado.");
            }

            if (config.getHabitaciones() == null) {
                throw new IOException("El mundo cargado no contiene habitaciones.");
            }

            return config;
        }
    }
}