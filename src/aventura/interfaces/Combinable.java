package aventura.interfaces;

import aventura.domain.Objeto;

/**
 * Representa un objeto capaz de combinarse con otro para generar un nuevo objeto.
 * <p>
 * Las clases que implementen esta interfaz deben definir la lógica necesaria
 * para determinar si la combinación es válida y qué objeto resultante se genera.
 * </p>
 *
 * @author Yeray Durán
 * @version 1.0
 */
public interface Combinable {

    /**
     * Intenta combinar este objeto con otro.
     *
     * @param otro Objeto con el que se intenta realizar la combinación.
     * @return El objeto resultante si la combinación es válida; en caso contrario, null.
     */
    Objeto combinar(Objeto otro);
}
