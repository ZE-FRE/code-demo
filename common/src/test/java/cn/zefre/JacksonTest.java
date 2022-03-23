package cn.zefre;

import cn.zefre.common.jackson.entity.Car;
import cn.zefre.common.jackson.entity.DateWrap;
import cn.zefre.common.jackson.entity.Empty;
import cn.zefre.common.jackson.entity.Lotus;
import cn.zefre.common.jackson.util.ObjectMapperUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import java.util.*;

/**
 * @author pujian
 * @date 2022/3/22 14:39
 */
public class JacksonTest {

    private ObjectMapper objectMapper = ObjectMapperUtil.getObjectMapper();

    @Test
    public void testSerialization() throws JsonProcessingException {
        Car car = new Car();
        System.out.println();

        System.out.print("序列化POJO：");
        System.out.println(objectMapper.writeValueAsString(car));

        System.out.print("序列化Map：");
        Map<String, Object> map = new HashMap<>();
        map.put("name", "map");
        map.put("weight", 2.0);
        System.out.println(objectMapper.writeValueAsString(map));

        System.out.print("序列化List：");
        List<Lotus> lotuses = Collections.singletonList(new Lotus("雪莲花", 0.1));
        System.out.println(objectMapper.writeValueAsString(lotuses));

        System.out.print("序列化Set：");
        Set<Lotus> lotusSet = new HashSet<>(Collections.singletonList(new Lotus("雪莲花", 0.1)));
        System.out.println(objectMapper.writeValueAsString(lotusSet));

        System.out.print("序列化数组：");
        Lotus[] lotusArr = new Lotus[] { new Lotus("雪莲花", 0.1) };
        System.out.println(objectMapper.writeValueAsString(lotusArr));
    }

    @Test
    public void testDeserialization() throws JsonProcessingException {
        String json = "{\"name\":\"john\",\"weight\":1.5}";

        System.out.println("反序列化为Car：");
        System.out.println(objectMapper.readValue(json, Car.class));
        System.out.println();

        System.out.print("反序列化为POJO：");
        System.out.println(objectMapper.readValue(json, Lotus.class));

        System.out.print("反序列化为Map：");
        Map<String, Object> map = objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        System.out.println(map);

        String jsonArr = "[{\"name\":\"john\",\"weight\":1.5}]";
        System.out.print("反序列化为List：");
        List<Lotus> lotuses = objectMapper.readValue(jsonArr, new TypeReference<List<Lotus>>() {});
        System.out.println(lotuses);

        System.out.print("反序列化为Set：");
        Set<Lotus> lotusSet = objectMapper.readValue(jsonArr, new TypeReference<Set<Lotus>>() {});
        System.out.println(lotusSet);

        System.out.print("反序列化为数组：");
        // 两种都可以
        // Lotus[] lotusArr = objectMapper.readValue(jsonArr, Lotus[].class);
        Lotus[] lotusArr = objectMapper.readValue(jsonArr, new TypeReference<Lotus[]>() {});
        System.out.println(Arrays.toString(lotusArr));
    }


    @Test
    public void testEmptyObject() throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(new Empty()));
        System.out.println(objectMapper.readValue("{}", Empty.class));
        System.out.println(objectMapper.readValue("{\"empty\":\"\"}", Lotus.class));
    }

    @Test
    public void testDate() throws JsonProcessingException {
        Date now = new Date();
        String dateStr = objectMapper.writeValueAsString(now);
        System.out.println(dateStr);
        System.out.println(objectMapper.readValue(dateStr, Date.class));

        DateWrap dateWrap = new DateWrap(now);
        String dateStr2 = objectMapper.writeValueAsString(dateWrap);
        System.out.println(dateStr2);
        System.out.println(objectMapper.readValue("{\"now\": \"2022_03_23 15:48:00\"}", DateWrap.class));
    }
}
