package aventura.io;

import aventura.domain.Objeto;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ObjetoAdapter implements JsonSerializer<Objeto>, JsonDeserializer<Objeto> {

    private static final String CAMPO_TIPO = "tipo";

    @Override
    public Objeto serialize(Objeto src, Type typeOfSrc, JsonSerializationContext contexto){

    }

    @Override
    public Objeto deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context){}
}
