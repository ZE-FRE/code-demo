package cn.zefre;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author pujian
 * @date 2021/6/24 17:30
 *
 * Slf4j等同于
 * private Logger log = LoggerFactory.getLogger(LogTest.class);
 */
@Slf4j
public class LogTest {

    @Test
    public void testLogAvailable() {
        log.debug("hello,{}", "world");
        log.warn("warning");
        log.error("error");
    }
    
    @Test
    public void split() {
        String str = "division,shed,stride,thorough,furnish,compromise,elegant,interim,disguise,subtle,beard,wage,abide,currency,dividend,constant,precede,condemn,thumb,terror,tow,backward,correlate,overall,hail,secondary";
        List<String> words = Arrays.asList(str.split(","));
        Collections.shuffle(words);
        System.out.println(words);
    }
}
