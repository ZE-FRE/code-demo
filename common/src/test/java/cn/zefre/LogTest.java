package cn.zefre;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
