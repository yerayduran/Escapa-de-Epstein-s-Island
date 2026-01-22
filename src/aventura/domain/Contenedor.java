package aventura.domain;

import aventura.interfaces.Abrible;
import aventura.domain.RespuestaAccion;

public class Contenedor extends Mueble implements Abrible {

    private String codigoNecesario;
    private boolean abierto;
    private Objeto contenido;

    public Contenedor(String nombre, String descripcion, String codigoNecesario, Objeto contenido) {
        super(nombre, descripcion);
        this.codigoNecesario = codigoNecesario;
        this.contenido = contenido;
        this.abierto = false;
    }

    @Override
    public RespuestaAccion abrir(Llave llave) {

        if (abierto) {
            return new RespuestaAccion(false, "Ya está abierto.");
        }

        if (codigoNecesario == null) {
            abierto = true;
            return new RespuestaAccion(true, "Lo abres sin dificultad.");
        }

        if (llave != null && codigoNecesario.equals(llave.getCodigoSeguridad())) {
            abierto = true;
            return new RespuestaAccion(true, "La llave encaja perfectamente.");
        }

        return new RespuestaAccion(false, "Está cerrado y necesita una llave.");
    }

    @Override
    public boolean estaAbierto() {
        return abierto;
    }

    public Objeto getContenido() {
        if (abierto) {
            Objeto temp = contenido;
            contenido = null;
            return temp;
        }
        return null;
    }
}
