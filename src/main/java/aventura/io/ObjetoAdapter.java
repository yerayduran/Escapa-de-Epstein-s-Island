package aventura.io;

import aventura.domain.Artefacto;
import aventura.domain.Contenedor;
import aventura.domain.Fragmento;
import aventura.domain.Item;
import aventura.domain.Llave;
import aventura.domain.Mueble;
import aventura.domain.Nota;
import aventura.domain.Objeto;
import aventura.domain.Velas;
import aventura.domain.Puerta;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Adaptador personalizado de Gson para la jerarquía de {@link Objeto}.
 *
 * Permite serializar y deserializar objetos polimórficos añadiendo un campo
 * adicional "tipo" en el JSON, que indica la clase concreta del objeto.
 *
 *
 * Durante la serialización se añade el tipo según la instancia real del objeto.
 * Durante la deserialización se utiliza ese tipo para reconstruir la clase correcta.
 *
 */
public class ObjetoAdapter implements JsonSerializer<Objeto>, JsonDeserializer<Objeto> {

    /** Nombre del campo JSON que indica el tipo de objeto. */
    private static final String CAMPO_TIPO = "tipo";

    /**
     * Serializa un objeto {@link Objeto} a JSON, añadiendo el campo "tipo"
     * para identificar su clase concreta.
     *
     * @param src objeto a serializar
     * @param tipoDeSrc tipo genérico del objeto
     * @param contextoo contexto de serialización proporcionado por Gson
     * @return representación JSON del objeto con el campo "tipo"
     * @throws JsonParseException si el tipo de objeto no está soportado
     */
    @Override
    public JsonElement serialize(Objeto src, Type tipoDeSrc, JsonSerializationContext contextoo) {
        JsonObject json = contextoo.serialize(src, src.getClass()).getAsJsonObject();

        if (src instanceof Artefacto) {
            json.addProperty(CAMPO_TIPO, "artefacto");
        } else if (src instanceof Fragmento) {
            json.addProperty(CAMPO_TIPO, "fragmento");
        } else if (src instanceof Puerta) {
            json.addProperty(CAMPO_TIPO, "puerta");
        } else if (src instanceof Contenedor) {
            json.addProperty(CAMPO_TIPO, "contenedor");
        } else if (src instanceof Nota) {
            json.addProperty(CAMPO_TIPO, "nota");
        } else if (src instanceof Llave) {
            json.addProperty(CAMPO_TIPO, "llave");
        } else if (src instanceof Velas) {
            json.addProperty(CAMPO_TIPO, "velas");
        } else if (src instanceof Mueble) {
            json.addProperty(CAMPO_TIPO, "mueble");
        } else if (src instanceof Item) {
            json.addProperty(CAMPO_TIPO, "item");
        } else {
            throw new JsonParseException("Tipo de objeto no soportado: " + src.getClass().getName());
        }

        return json;
    }

    /**
     * Deserializa un JSON a un objeto {@link Objeto}, determinando la clase concreta
     * a partir del campo "tipo".
     *
     * @param json elemento JSON a deserializar
     * @param tipoDeObj tipo esperado (generalmente {@link Objeto})
     * @param contexto contexto de deserialización de Gson
     * @return instancia concreta de {@link Objeto}
     * @throws JsonParseException si falta el campo "tipo" o es desconocido
     */
    @Override
    public Objeto deserialize(JsonElement json, Type tipoDeObj, JsonDeserializationContext contexto)
            throws JsonParseException {

        JsonObject jsonObjeto = json.getAsJsonObject();

        if (!jsonObjeto.has("tipo")) {
            throw new JsonParseException("Falta el campo 'tipo' en el objeto JSON.");
        }

        String tipo = jsonObjeto.get("tipo").getAsString().trim().toLowerCase().replace("_", "").replace(" ", "");

        return switch (tipo) {
            case "artefacto" -> contexto.deserialize(jsonObjeto, Artefacto.class);
            case "fragmento" -> contexto.deserialize(jsonObjeto, Fragmento.class);
            case "puerta" -> contexto.deserialize(jsonObjeto, Puerta.class);
            case "contenedor" -> contexto.deserialize(jsonObjeto, Contenedor.class);
            case "nota" -> contexto.deserialize(jsonObjeto, Nota.class);
            case "llave" -> contexto.deserialize(jsonObjeto, Llave.class);
            case "velas" -> contexto.deserialize(jsonObjeto, Velas.class);
            case "mueble" -> contexto.deserialize(jsonObjeto, Mueble.class);
            case "item" -> contexto.deserialize(jsonObjeto, Item.class);
            default -> throw new JsonParseException("Tipo de objeto desconocido: " + tipo);
        };
    }
}