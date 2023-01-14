package cn.zefre.common.jackson.serializer;

import cn.zefre.common.jackson.enums.BaseEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

import java.io.IOException;

/**
 * 序列化{@link BaseEnum#getCode()}的值
 *
 * @author pujian
 * @date 2023/1/13 21:16
 */
public class BaseEnumCodeSerializer extends StdScalarSerializer<BaseEnum> {

    public BaseEnumCodeSerializer() {
        super(BaseEnum.class);
    }

    @Override
    public void serialize(BaseEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        Object code = value.getCode();
        Class<?> codeType = code.getClass();
        if (codeType == Byte.class || codeType == Integer.class) {
            gen.writeNumber(new Integer(code.toString()));
        } else if (codeType == String.class) {
            gen.writeString(code.toString());
        }
    }

}
