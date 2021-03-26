package cn.zefre;

import cn.zefre.template.BrotherCookNoodles;
import cn.zefre.template.CookNoodles;
import cn.zefre.template.MeCookNoodles;
import org.junit.Test;

/**
 * 模板方法模式测试
 * @author pujian
 * @date 2021/3/25 14:41
 */
public class TemplateMethodTest {

    @Test
    public void testCookNoodles() {
        CookNoodles meCookNoodles = new MeCookNoodles();
        CookNoodles brotherCookNoodles = new BrotherCookNoodles();

        meCookNoodles.cook();
        System.out.println("=======================");
        brotherCookNoodles.cook();
    }
}
