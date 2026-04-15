/**
 * @author ManuelPerez
 * @version 1.0
 */

package aventura.io;

import aventura.domain.Habitacion;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class AventuraConfig {

    private String descripcionGeneral;
    private String habitacionInicial;
    private Map<String, Habitacion> habitaciones;

    public AventuraConfig() {
        this.habitaciones = new HashMap<>();
    }

    public AventuraConfig(String descripcionGeneral, String habitacionInicial, Map<String, Habitacion> habitaciones) {
        this.descripcionGeneral = descripcionGeneral;
        this.habitacionInicial = habitacionInicial;
        this.habitaciones = habitaciones;
    }

    public String getDescripcionGeneral() {
        return descripcionGeneral;
    }


    public String getHabitacionInicial() {
        return habitacionInicial;

    }

    public Map<String, Habitacion> getHabitaciones() {
        return habitaciones;
    }


}
