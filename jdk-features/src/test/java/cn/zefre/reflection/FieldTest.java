package cn.zefre.reflection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

/**
 * @author pujian
 * @date 2022/5/27 13:48
 */
public class FieldTest {

    static class Parent {
        public String name;

        protected int age;
    }

    static class Child extends Parent {

    }

    @Test
    public void testEquals() throws NoSuchFieldException {
        Field fieldInParent = Parent.class.getField("name");
        Field fieldInChild = Child.class.getField("name");
        Assertions.assertNotSame(fieldInParent, fieldInChild);
    }

    @Test
    public void testGetField() throws NoSuchFieldException {
        // 不能获取非public的字段
        Assertions.assertThrows(NoSuchFieldException.class, () -> Child.class.getField("age"));
    }

}
