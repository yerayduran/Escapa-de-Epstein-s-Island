/**
 * @author ManuelPerez
 * @version 1.0
 */

package aventura.domain;

import aventura.interfaces.Inventariable;


public class Item extends Objeto implements Inventariable {

    public Item(String nombre, String descripcion) {
        super(nombre, descripcion);
        this.setVisible(true);
    }
}
