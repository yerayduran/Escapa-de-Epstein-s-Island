package aventura.domain;

import aventura.interfaces.Combinable;

/**
 * Representa un fragmento ritual incompleto que puede combinarse
 * con una fuente de energía para formar el artefacto final.
 * <p>
 * Esta clase hereda de {@link Item} e implementa la interfaz
 * {@link Combinable}, permitiendo ensamblar una pieza clave
 * necesaria para avanzar en la historia.
 * </p>
 *
 * <p>
 * Su función principal es combinarse con unas {@link Velas}
 * para generar un {@link Artefacto}, el objeto final
 * necesario para abrir la Puerta del Juicio.
 * </p>
 *
 * @author Yeray Durán
 * @version 2.1
 */
public class FragmentoRitual extends Item implements Combinable {

    /**
     * Crea un nuevo fragmento ritual.
     *
     * @param nombre      Nombre del objeto.
     * @param descripcion Descripción del objeto.
     * @param visible     Indica si el objeto es visible para el jugador.
     */
    public FragmentoRitual(String nombre, String descripcion, boolean visible) {
        super(nombre, descripcion, visible);
    }

    /**
     * Intenta combinar este fragmento ritual con otro objeto.
     * Si el objeto proporcionado es una instancia de {@link Velas},
     * se genera un {@link Artefacto} completamente funcional.
     *
     * @param otro Objeto con el que se intenta combinar.
     * @return El artefacto final si la combinación es válida; en caso contrario, null.
     */
    @Override
    public Objeto combinar(Objeto otro) {
        if (otro instanceof Velas) {
            return new Artefacto(
                    "Artefacto",
                    "Una reliquia ensamblada a partir de un fragmento ritual y una fuente de energía portátil. Su núcleo emite una vibración inquietante. Código: 5973.",
                    true,
                    "5973"
            );
        }
        return null;
    }
}