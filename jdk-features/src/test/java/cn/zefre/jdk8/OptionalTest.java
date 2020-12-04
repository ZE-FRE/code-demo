package cn.zefre.jdk8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author pujian
 * @date 2020/10/29 16:34
 */
public class OptionalTest {


    /*
     * Optional:
     * Optional就是一个容器，可以存放一个值，这个值可以是null也可以不是
     * 它的构造方法是私有的，在外部可以通过of、ofNullable、empty这三个静态方法来实例化
     */


    /**
     * 测试ifPresent方法
     * ifPresent：如果Optional的值存在，则执行自定义的代码块，不存在则什么也不做
     * @author pujian
     * @date 2020/10/29 17:11
     */
    @Test
    public void testIfPresent() {
        List<String> names = new ArrayList<>(Arrays.asList("zhangsan", "lisi"));
        Optional.of(names).ifPresent(list -> {
            System.out.println(list);
        });
    }

    @Test
    public void testMapAndFlatMap() {
        System.out.println(Optional.of("zhangsan").map(name ->  null).isPresent()); // false
        System.out.println(Optional.of("zhangsan").flatMap(name ->  Optional.ofNullable(null)).isPresent()); //false
        /*
         * map与flatMap方法的区别：
         * map方法参数mapper可以返回任何类型，而flatMap参数的mapper必须返回Optional
         */
    }

    @Test
    public void testOrElse() {
        System.out.println(Optional.of("zhangsan").orElse("存在"));
        System.out.println(Optional.empty().orElse("不存在"));

        System.out.println("====================");
        String name = null;
        System.out.println(Optional.ofNullable(name).orElseGet(() -> "zhangsan"));

        System.out.println("====================");
        Optional.empty().orElseThrow(() -> new RuntimeException("Optional里的值为null"));

    }
}
