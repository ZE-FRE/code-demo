package cn.zefre.spring.jackson;

import cn.zefre.spring.common.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;

/**
 * @author pujian
 * @date 2022/3/22 13:55
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class JacksonUsageTest {

    @Resource
    private ObjectMapper objectMapper;

    @Test
    public void testSerialize() throws JsonProcessingException {
        String response = objectMapper.writeValueAsString(Response.ok());
        System.out.println("response = " + response);
    }

}
