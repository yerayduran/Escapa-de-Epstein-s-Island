/**
 * @author ManuelPerez
 * @version 1.0
 */

package aventura.domain;

import aventura.interfaces.Combinable;

public class Llave extends Item implements Combinable {

    public Llave(String nombre, String descripcion) {
        super(nombre, descripcion);
    }

    @Override
    public Objeto combinar(Objeto otroObjeto) {
        return null;
    }
}
