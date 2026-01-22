/**
 * @author ManuelPerez
 * @version 1.0
 */

package aventura.domain;

import aventura.interfaces.Abrible;
import aventura.interfaces.Leible;

public class Nota extends Item implements Leible {

    public Nota(String nombre, String descripcion) {
        super(nombre, descripcion);
    }

    @Override
    public String leer() {
        return "";
    }
}
