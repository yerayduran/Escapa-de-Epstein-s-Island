package aventura.domain;

import aventura.exceptions.InventarioLlenoException;
import aventura.interfaces.Inventariable;

public class Jugador {

    private Objeto[] inventario;
    private int posicion;

    public Jugador(int capacidadInventario) {
        this.inventario = new Objeto[capacidadInventario];
        this.posicion = 0;
    }

    public void coger(Objeto objeto) throws InventarioLlenoException {
        if (!(objeto instanceof Inventariable)) {
            return;
        }

        for (int i = 0; i < inventario.length; i++) {
            if (inventario[i] == null) {
                inventario[i] = objeto;
                return;
            }
        }

        throw new InventarioLlenoException("El inventario está lleno.");
    }

    public Objeto[] getInventario() {
        return inventario;
    }

    public boolean eliminarObjeto(Objeto obj) {
        for (int i = 0; i < inventario.length; i++) {
            if (inventario[i].equals(obj)) {
                inventario[i] = null;
                return true;
            }
        }
        return false;
    }
}
