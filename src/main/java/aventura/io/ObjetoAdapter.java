package aventura.io;

import aventura.domain.Artefacto;
import aventura.domain.Contenedor;
import aventura.domain.FragmentoRitual;
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

public class ObjetoAdapter implements JsonSerializer<Objeto>, JsonDeserializer<Objeto> {

    private static final String CAMPO_TIPO = "tipo";

    @Override
    public JsonElement serialize(Objeto src, Type tipoDeSrc, JsonSerializationContext contextoo) {
        JsonObject json = contextoo.serialize(src, src.getClass()).getAsJsonObject();

        if (src instanceof Artefacto) {
            json.addProperty(CAMPO_TIPO, "artefacto");
        } else if (src instanceof FragmentoRitual) {
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
            case "fragmento" -> contexto.deserialize(jsonObjeto, FragmentoRitual.class);
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
