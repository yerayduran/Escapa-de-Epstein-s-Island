package aventura.domain;

import aventura.exceptions.AventuraException;
import aventura.exceptions.InventarioLlenoException;
import aventura.interfaces.Inventariable;

import java.util.ArrayList;
import java.util.List;

public class Jugador {
    private static final int MAX_INVENTARIO = 10;


    private String nombre;
    private final List<Objeto> inventario;
    private String habitacionActual;



    public Jugador(String nombre) {
        this.nombre = nombre;
        this.habitacionActual = "";
        this.inventario = new ArrayList<>();
    }



    public String getNombre() {
        return nombre;
    }

    public String getHabitacionActual() {
        return habitacionActual;
    }

    public void setHabitacionActual(String habitacionActual) {
        this.habitacionActual = habitacionActual;
    }

    public List<Objeto> getInventario() {
        return inventario;
    }

   public void coger(Objeto objeto) throws AventuraException {
        if (!(objeto instanceof Inventariable)) {
            throw new AventuraException("El objeto %s no se puede coger." .formatted(objeto.getNombre()))
        }

        if (inventario.size() >= MAX_INVENTARIO) {
            throw new InventarioLlenoException("El inventario está lleno. No puedes ciger más objetos.")
        }

        inventario.add(objeto);
   }

   public boolean eliminarDeInventario(Objeto objeto) {
        return inventario.remove(objeto);
   }

   public Objeto buscarEnInventario(String nombre){
        for (Objeto objeto : inventario) {
            if (objeto.getNombre().equalsIgnoreCase(nombre)) {
                return objeto;
            }
        }
        return null;
   }
}


