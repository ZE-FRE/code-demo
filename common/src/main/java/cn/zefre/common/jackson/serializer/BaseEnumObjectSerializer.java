package cn.zefre.common.jackson.serializer;

import cn.zefre.common.jackson.enums.BaseEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * 把{@link BaseEnum}序列化成对象
 *
 * @author pujian
 * @date 2023/1/14 16:20
 */
public class BaseEnumObjectSerializer extends JsonSerializer<BaseEnum> {

    @Override
    public void serialize(BaseEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        Object code = value.getCode();
        Class<?> codeType = code.getClass();
        if (codeType == Byte.class || codeType == Integer.class) {
            gen.writeNumberField("code", new Integer(code.toString()));
        } else if (codeType == String.class) {
            gen.writeStringField("code", code.toString());
        }
        gen.writeStringField("description", value.getDescription());
    }

}
