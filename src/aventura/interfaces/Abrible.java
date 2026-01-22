package aventura.interfaces;

import aventura.domain.Llave;
import aventura.domain.RespuestaAccion;

public interface Abrible {

    RespuestaAccion abrir(Llave llave);
    boolean estaAbierto();
}
