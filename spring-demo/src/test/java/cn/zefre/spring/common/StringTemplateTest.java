package cn.zefre.spring.common;

import cn.zefre.spring.common.util.StringTemplateUtil;
import cn.zefre.spring.mybatisplus.crud.mapper.GenericMapper;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pujian
 * @date 2022/6/28 13:25
 */
public class StringTemplateTest {

    @Test
    public void testReplace() {
        String template = "$(field)=#{$(map).$(field)}";
        Map<String, Object> param = new HashMap<>();
        param.put("map", GenericMapper.SET_PARAM_MAP);
        param.put("field", "hello");
        System.out.println(StringTemplateUtil.replace(template, param));

        param.put("field", "hi");
        System.out.println(StringTemplateUtil.replace(template, param));
    }

}
