/**
 * @author ManuelPerez
 * @version 1.0
 */

package aventura.io;

import aventura.domain.Habitacion;
import aventura.domain.Objeto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class Migrador {

    private final Gson gson;

    public Migrador() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Objeto.class, new ObjetoAdapter())
                .create();
    }

    public AventuraConfig migrarDesdeArray(String descripcionGeneral,
                                           String habitacionInicial,
                                           Habitacion[] habitacionesAntiguas) {
        Map<String, Habitacion> mapa = new HashMap<>();

        if (habitacionesAntiguas != null) {
            for (Habitacion habitacion : habitacionesAntiguas) {
                if (habitacion == null){
                    continue;
                }

                String id = habitacion.getId();

                if (id == null ||id.isBlank()) {
                    id = "sala_" + mapa.size();
                }

                mapa.put(id, habitacion);
            }
        }

        return new AventuraConfig(descripcionGeneral, habitacionInicial, mapa);
    }
}



