/**
 * @author ManuelPerez
 * @version 1.0
 */

package aventura.io;

import aventura.domain.*;
import aventura.exceptions.AventuraException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
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

    public void guardarJson(AventuraConfig config, Path rutaDestino) throws IOException {
        if (rutaDestino.getParent() != null) {
            Files.createDirectories(rutaDestino.getParent());
        }

        try (Writer writer = Files.newBufferedWriter(rutaDestino)) {
            gson.toJson(config, writer);
        }
    }

    public static void main(String[] args) {
        try {
            Migrador migrador = new Migrador();

            Habitacion[] habitacionesAntiguas = crearHabitacionesAntiguas();

            String descripcionJuego = "La última noche antes de llegar al pueblo, recibiste un mensaje sin remitente: 'Vuelve'. " +
                    "Pensaste que era una broma de mal gusto, pero algo dentro de ti te obligó a conducir hasta las afueras de Silent Hill.\n" +
                    "La carretera estaba vacía. Solo niebla, asfalto mojado y el sonido lejano de una sirena imposible. " +
                    "Entonces viste una silueta cruzando la calzada. Giraste el volante, el coche derrapó... y todo se volvió negro.\n" +
                    "Cuando despiertas, ya no estás del todo en el mundo real. Silent Hill ha abierto sus puertas para ti. " +
                    "Las calles del pueblo cambian a cada paso, como si alguien hubiese mezclado tus recuerdos con pesadillas ajenas.\n" +
                    "Un tramo de carretera recuerda a una vieja huida entre crimen y culpa. Un hospital oxidado esconde informes clínicos, " +
                    "cadáveres inmóviles y puertas cerradas con combinaciones imposibles. Una mansión invadida por retratos y susurros " +
                    "parece arrancada de una pesadilla infantil. Más abajo, un refugio enterrado bajo la ciudad mezcla chatarra militar, " +
                    "tecnología rota, símbolos antiguos y terminales que todavía brillan en la oscuridad.\n" +
                    "Cada lugar parece pertenecer a un mundo distinto, pero todos tienen algo en común: tú.\n" +
                    "No has venido a Silent Hill por casualidad. El pueblo te ha llamado porque sabe lo que hiciste. " +
                    "Aquí los monstruos no siempre tienen colmillos; a veces tienen recuerdos, nombres y voces que conoces demasiado bien.\n" +
                    "Si quieres escapar, tendrás que reunir fragmentos de verdad, leer lo que otros dejaron atrás y construir el artefacto " +
                    "que abre la Puerta del Juicio.\n" +
                    "Pero recuerda: en Silent Hill, algunas puertas no se abren con llaves... se abren con culpa.";

            AventuraConfig config = migrador.migrarDesdeArray(
                    descripcionJuego,
                    "Carretera de Silent Hill", // Nombre de la primera habitación
                    habitacionesAntiguas
            );

            migrador.guardarJson(config, Path.of("src/main/resources/aventura.json"));
            System.out.println("Migración completada correctamente. Revisa la carpeta src/main/resources/");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Habitacion[] crearHabitacionesAntiguas() throws AventuraException {
        Habitacion sala1 = new Habitacion(
                "Carretera de Silent Hill",
                "La niebla cubre la carretera. Hay marcas de frenazo, un coche destrozado y una señal oxidada que apunta al pueblo."
        );

        Habitacion sala2 = new Habitacion(
                "Hospital Otherworld",
                "El hospital está consumido por óxido, sangre reseca y silencio. Una radio crepita sola en la recepción."
        );

        Habitacion sala3 = new Habitacion(
                "Mansión de los Retratos",
                "Una mansión oscura donde los cuadros parecen observarte. La luz verde de los pasillos hace que todo parezca un recuerdo corrupto."
        );

        Habitacion sala4 = new Habitacion(
                "Refugio del Juicio",
                "Un complejo subterráneo mezcla restos militares, terminales de neón, símbolos rituales y ecos de una civilización rota."
        );

        Velas velas = new Velas(
                "Pilas",
                "Un paquete de pilas todavía funcional. Puede alimentar un objeto esencial para avanzar.",
                true
        );

        Nota notaCarretera = new Nota(
                "Nota",
                "Un trozo de papel húmedo encontrado junto al coche.",
                true,
                "No fue un accidente. Viniste aquí porque querías olvidar. La luz te mostrará lo que enterraste."
        );

        Nota notaHospital = new Nota(
                "Informe",
                "Un informe clínico arrugado y manchado.",
                true,
                "Paciente con amnesia disociativa. Presenta visión fragmentada de la realidad: crimen, plaga, espectros, ruina y máquinas. Repite una palabra: ASHES33."
        );

        Nota notaRefugio = new Nota(
                "Registro",
                "Un registro técnico guardado en una terminal dañada.",
                true,
                "Proyecto R.E.P.O. autorizado. El Artefacto de Apertura requiere fuente de energía portátil y activación con código 5973. La llave de acceso fue escondida tras el retrato sellado."
        );

        Llave llaveMansion = new Llave(
                "Llave",
                "Una llave ornamentada, fría al tacto, con un grabado espectral. En el reverso puede leerse: ASHES33.",
                true,
                "ASHES33"
        );

        Llave artefactoApertura = new Llave(
                "Artefacto",
                "Un artefacto improvisado, ensamblado con luz, energía y memoria. Vibra con una frecuencia extraña. Código: 5973.",
                true,
                "5973"
        );

        Contenedor coche = new Contenedor(
                "Coche",
                "El coche del accidente. La puerta del copiloto está forzada y el maletero parece medio abierto.",
                true,
                null,
                null
        );

        Contenedor taquilla = new Contenedor(
                "Taquilla",
                "Una taquilla metálica del hospital. Está oxidada, pero aún conserva un cierre funcional.",
                true,
                null,
                null
        );

        Contenedor retrato = new Contenedor(
                "Retrato",
                "Un retrato enorme de una familia sin rostro. Detrás del marco hay una cerradura oculta con teclado.",
                true,
                "ASHES33",
                null
        );

        Puerta puertaSalida = new Puerta(
                "Puerta",
                "Una puerta de acero ennegrecido, cubierta de símbolos y marcas de quemaduras. Solo reaccionará al Artefacto de Apertura con código 5973.",
                true
        );

        // Objetos en habitaciones
        sala1.agregarObjeto(coche);
        sala1.agregarObjeto(notaCarretera);

        sala2.agregarObjeto(notaHospital);
        sala2.agregarObjeto(taquilla);

        sala3.agregarObjeto(retrato);

        sala4.agregarObjeto(notaRefugio);
        sala4.agregarObjeto(puertaSalida);

        // Contenidos
        coche.setContenido(velas);
        taquilla.setContenido(artefactoApertura);
        retrato.setContenido(llaveMansion);

        return new Habitacion[]{sala1, sala2, sala3, sala4};
    }
}



