package aventura.io;

import aventura.domain.Artefacto;
import aventura.domain.Contenedor;
import aventura.domain.FragmentoRitual;
import aventura.domain.Item;
import aventura.domain.Llave;
import aventura.domain.Mueble;
import aventura.domain.Nota;
import aventura.domain.Objeto;
import aventura.domain.Pilas;
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
    public JsonElement serialize(Objeto src, Type tipoDeSrc, JsonSerializationContext contexto) {
        JsonObject json = contexto.serialize(src, src.getClass()).getAsJsonObject();

        if (src instanceof Artefacto) {
            json.addProperty(CAMPO_TIPO, "artefacto");
        } else if (src instanceof FragmentoRitual) {
            json.addProperty(CAMPO_TIPO, "fragmentoRitual");
        } else if (src instanceof Pilas) {
            json.addProperty(CAMPO_TIPO, "pilas");
        } else if (src instanceof Puerta) {
            json.addProperty(CAMPO_TIPO, "puerta");
        } else if (src instanceof Contenedor) {
            json.addProperty(CAMPO_TIPO, "contenedor");
        } else if (src instanceof Nota) {
            json.addProperty(CAMPO_TIPO, "nota");
        } else if (src instanceof Llave) {
            json.addProperty(CAMPO_TIPO, "llave");
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
    public Objeto deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext contexto){}
}
