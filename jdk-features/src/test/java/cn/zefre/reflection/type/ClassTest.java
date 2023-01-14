package cn.zefre.reflection.type;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author pujian
 * @date 2022/5/27 13:49
 */
public class ClassTest {

    static class Phone {

    }

    /**
     * 测试{@link Class#getDeclaringClass()}
     *
     * @author pujian
     * @date 2022/10/11 15:54
     */
    @Test
    public void testDeclaringClass() {
        // 内部类返回定义它的类
        Class<?> declaringClass = Phone.class.getDeclaringClass();
        /*
         * 非内部类、原始类型、void、数组返回null
         */
        Assertions.assertSame(ClassTest.class, declaringClass);
        Assertions.assertNull(ClassTest.class.getDeclaringClass());
        Assertions.assertNull(int.class.getDeclaringClass());
        Assertions.assertNull(void.class.getDeclaringClass());
        Assertions.assertNull(Phone[].class.getDeclaringClass());
    }

}
