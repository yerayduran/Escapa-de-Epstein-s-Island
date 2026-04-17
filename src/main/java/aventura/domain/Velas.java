package aventura.domain;

import aventura.interfaces.Combinable;

/**
 * Representa una fuente de energía portátil dentro del sistema de aventura.
 * <p>
 * Esta clase extiende {@link Item} e implementa la interfaz {@link Combinable},
 * permitiendo que las pilas puedan combinarse con otros objetos compatibles
 * para crear un artefacto clave necesario para avanzar en la historia.
 * </p>
 *
 * <p>
 * Su función principal es combinarse con un {@link Fragmento} para generar
 * una instancia de {@link Artefacto}, representando el objeto final
 * necesario para abrir la Puerta del Juicio.
 * </p>
 *
 * @author Manuel Pérez y Yeray Durán
 * @version 2.1
 */
public class Velas extends Item implements Combinable {

    /**
     * Crea unas nuevas pilas.
     *
     * @param nombre      nombre identificativo del objeto.
     * @param descripcion descripción detallada del objeto.
     * @param visible     indica si el objeto es visible para el jugador.
     */
    public Velas(String nombre, String descripcion, boolean visible) {
        super(nombre, descripcion, visible);
    }

    /**
     * Intenta combinar estas pilas con otro objeto.
     * <p>
     * Si el objeto recibido es una instancia de {@link Fragmento},
     * se genera un nuevo {@link Artefacto} completamente funcional.
     * En caso contrario, la combinación no es válida y se devuelve {@code null}.
     * </p>
     *
     * @param otro objeto con el que se intenta combinar.
     * @return un {@link Artefacto} si la combinación es válida; {@code null} en caso contrario.
     */
    @Override
    public Objeto combinar(Objeto otro) {
        if (otro instanceof Fragmento) {
            return new Artefacto(
                    "Artefacto de Apertura",
                    "Una reliquia activada mediante una fuente de energía portátil. Su núcleo late con una vibración inquietante. Código: 5973.",
                    true,
                    "5973"
            );
        }
        return null;
    }
}