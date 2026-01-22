package aventura.interfaces;

import aventura.domain.Llave;

public interface Abrible {

    RespuestaAccion abrir(Llave llave);
    boolean estaAbierto();
}
