package cn.zefre.reflection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author pujian
 * @date 2022/5/27 13:48
 */
public class MethodTest {

    static class Parent {
        public void overriddenMethod() {
            System.out.println("调用父对象的overriddenMethod方法");
        }

        public void instanceMethod() {

        }
    }

    static class Child extends Parent {
        @Override
        public void overriddenMethod() {
            System.out.println("调用子对象的overriddenMethod方法");
        }

        private void privateMethod() {

        }
    }

    /**
     * {@link Class#getDeclaredMethods()}和{@link Class#getDeclaredMethod(String, Class[])}
     * 返回本类定义的方法
     *
     * @author pujian
     * @date 2022/10/11 15:34
     */
    @Test
    public void testEquals() throws NoSuchMethodException {
        Method parentAbstractMethod = Parent.class.getDeclaredMethod("overriddenMethod");
        Method childAbstractMethod = Child.class.getDeclaredMethod("overriddenMethod");
        Assertions.assertNotSame(parentAbstractMethod, childAbstractMethod);
    }

    /**
     * {@link Method#getDeclaringClass()}返回定义这个方法的类
     *
     * @author pujian
     * @date 2022/10/11 15:35
     */
    @Test
    public void testDeclaringClass() throws NoSuchMethodException {
        Method parentAbstractMethod = Parent.class.getDeclaredMethod("overriddenMethod");
        Method childAbstractMethod = Child.class.getDeclaredMethod("overriddenMethod");

        Class<?> parentDeclaringClass = parentAbstractMethod.getDeclaringClass();
        Class<?> childDeclaringClass = childAbstractMethod.getDeclaringClass();

        Assertions.assertSame(Parent.class, parentDeclaringClass);
        Assertions.assertSame(Child.class, childDeclaringClass);
    }

    /**
     * {@link Class#getMethods()}和{@link Class#getMethod(String, Class[])}
     * 返回本类(接口)、父类、父接口定义的public方法
     *
     * @author pujian
     * @date 2022/10/11 15:34
     */
    @Test
    public void testGetMethod() throws NoSuchMethodException {
        Method instanceMethod = Child.class.getMethod("instanceMethod");
        Assertions.assertSame(Parent.class, instanceMethod.getDeclaringClass());
        // 不能获取非public的方法
        Assertions.assertThrows(NoSuchMethodException.class, () -> Child.class.getMethod("privateMethod"));
    }

    @Test
    public void testInvokeByReflection() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method methodOnChild = Child.class.getDeclaredMethod("overriddenMethod");
        // 反射调用方法，方法定义的类是子类，传入父类对象，抛异常
        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                () -> methodOnChild.invoke(new Parent()), "object is not an instance of declaring class");

        // 反射调用方法，方法定义的类是父类，传入子类对象，正常执行
        Method methodOnParent = Parent.class.getDeclaredMethod("overriddenMethod");
        methodOnParent.invoke(new Child());
    }

}
