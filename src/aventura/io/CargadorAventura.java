package aventura.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Properties;

public class CargadorAventura {

    private final Gson gson;
    private final Path rutaConfig;
    private Path directorioBase;
    private Path archivoBase;

    public CargadorAventura(Path rutaConfig) {
        this.rutaConfig = rutaConfig;
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(aventura.domain.Objeto.class, new ObjetoAdapter())
                .create();
    }

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