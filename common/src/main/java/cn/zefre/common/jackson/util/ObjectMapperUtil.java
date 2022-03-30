package cn.zefre.common.jackson.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.text.DateFormat;

/**
 * @author pujian
 * @date 2022/3/22 14:25
 */
public class ObjectMapperUtil {

    private static volatile ObjectMapper objectMapper;

    public static ObjectMapper getObjectMapper() {
        if (null == objectMapper) {
            synchronized (ObjectMapperUtil.class) {
                if (null == objectMapper)
                    create();
            }
        }
        return objectMapper;
    }

    private static void create() {
        objectMapper = new ObjectMapper();
        // 美化输出
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        // 反序列化时需要一个对象，但是给的空字符串(不是空字符串的情况则报错)，强制将空字符串""转换为null
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        // 忽略一个字段也没有的POJO的序列化
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 在反序列化时忽略在 json 中存在但 Java 对象不存在的属性
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 日期不输出为时间戳
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // 在序列化时忽略值为 null 的属性
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 忽略值为默认值的属性
        objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_EMPTY);
        // 设置date format为"yyyy-MM-dd HH:mm::ss"
        objectMapper.setDateFormat(DateFormat.getDateTimeInstance());
    }

}
