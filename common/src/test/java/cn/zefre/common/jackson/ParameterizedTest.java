package cn.zefre.common.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.junit.jupiter.api.Test;

/**
 * jackson反序列化参数化类型
 *
 * @author pujian
 * @date 2023/3/4 10:18
 */
public class ParameterizedTest {

    @Data
    static class Response<T> {
        private int code;
        private T data;
    }

    @Data
    static class Order {
        private String orderNo;
    }

    public <DATA> DATA deserialize(Class<DATA> dataType) throws JsonProcessingException {
        String response = "{\"code\":1,\"data\":{\"orderNo\":\"123456\"}}";
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Response.class, dataType);
        Response<DATA> dataResponse = objectMapper.readValue(response, javaType);
        return dataResponse.getData();
    }

    @Test
    public void testParameterizedDeserialization() throws JsonProcessingException {
        Order order = deserialize(Order.class);
        System.out.println(order);
    }

}
