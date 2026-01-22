package aventura.domain;

import aventura.exceptions.InventarioLlenoException;
import aventura.interfaces.Inventariable;

public class Jugador extends Entidad {

    private Objeto[] inventario;

    public Jugador(String nombre, String descripcion, Objeto[] inventario) {
        super(nombre, descripcion);
        this.inventario = inventario;
    }

    public Objeto[] getInventario() {
        return inventario;
    }

    public void setInventario(Objeto[] inventario) {
        System.out.println("Inventario del jugador:");
        for (int i = 0; i < inventario.length; i++) {
            System.out.println(i + ": " + (inventario[i] != null ? inventario[i].getNombre() : "vacío"));
        }
        this.inventario = inventario;
    }

    public void coger(Objeto obj) throws InventarioLlenoException {

        if (!(obj instanceof Inventariable)) {
            System.out.println("No puedes coger este objeto: no es inventariable.");
            return;
        }

        for (int i = 0; i < inventario.length; i++) {
            if (inventario[i] == null) {
                inventario[i] = obj;
                System.out.println("Has cogido: " + obj.getNombre());
                return;
            }
        }

        throw new InventarioLlenoException("El inventario está lleno. No puedes coger más objetos.");
    }

}
