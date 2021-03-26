package cn.zefre;

import cn.zefre.builder.Computer;
import cn.zefre.builder.Director;
import cn.zefre.builder.Human;
import cn.zefre.builder.MacComputerBuilder;
import org.junit.Test;

/**
 * @author pujian
 * @date 2021/3/25 10:16
 */
public class BuilderTest {

    @Test
    public void testTraditionalBuilder() {
        Director director = new Director(new MacComputerBuilder());
        Computer computer = director.build();
        System.out.println(computer);
    }

    @Test
    public void testJavaCommonBuilder() {
        Human human = Human.builder()
                .setHead("this is head")
                .setBody("this is body")
                .setFooter("this is footer")
                .build();
        System.out.println(human);
    }
}
