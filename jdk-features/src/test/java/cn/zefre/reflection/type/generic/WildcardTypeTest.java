package cn.zefre.reflection.type.generic;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * {@link WildcardType}演示
 *
 * @author pujian
 * @date 2022/5/27 10:13
 */
public class WildcardTypeTest {

    final static class Robot<A extends Number> {
        private A count;
        public <U extends A> U getCount(Supplier<? extends U> supplier) {
            return supplier.get();
        }
        public void consume(Consumer<? super A> consumer) {
            consumer.accept(count);
        }
    }

    @Test
    public void testTypeVariable() throws NoSuchMethodException {
        TypeVariable<Class<Robot>> typeVariable = Robot.class.getTypeParameters()[0];
        Assert.assertEquals("A", typeVariable.getTypeName());
        Assert.assertSame(Number.class, typeVariable.getBounds()[0]);
        Assert.assertSame(Robot.class, typeVariable.getGenericDeclaration());

        // 取得Robot#getCount方法
        Method methodGetCount = Robot.class.getDeclaredMethod("getCount", Supplier.class);
        // 得到<U extends A>
        TypeVariable<Method> methodTypeVariable= methodGetCount.getTypeParameters()[0];
        Assert.assertEquals("U", methodTypeVariable.getTypeName());
        Assert.assertEquals("A", methodTypeVariable.getBounds()[0].getTypeName());
    }

    /**
     * {@link WildcardType#getUpperBounds()}方法
     * 获取上界，如：
     * ? extends Number，获取到的是Number.class
     * ? extends U，获取到的是U(TypeVariable)
     *
     * @author pujian
     * @date 2022/5/27 17:05
     */
    @Test
    public void testGetUpperBounds() throws NoSuchMethodException {
        // 取得Robot#getCount方法
        Method methodGetCount = Robot.class.getDeclaredMethod("getCount", Supplier.class);
        // 取得方法上的泛型参数
        ParameterizedType parameterizedType = (ParameterizedType) methodGetCount.getGenericParameterTypes()[0];
        // 泛型参数里有通配符?，wildcardType表示的是<? extends U>
        WildcardType wildcardType = (WildcardType) parameterizedType.getActualTypeArguments()[0];
        Assert.assertEquals("U", wildcardType.getUpperBounds()[0].getTypeName());
        // 未指定下界，为空数组
        Assert.assertArrayEquals(new Object[]{}, wildcardType.getLowerBounds());
    }

    /**
     * {@link WildcardType#getLowerBounds()}方法
     * 获取下界，如：
     * ? super Number，获取到的是Number.class
     * ?，获取到的是Object.class
     * ? super A，获取到的是A(TypeVariable)
     *
     * @author pujian
     * @date 2022/5/27 17:13
     */
    @Test
    public void testGetLowerBounds() throws NoSuchMethodException {
        // 取得Robot#getCount方法
        Method methodConsume = Robot.class.getMethod("consume", Consumer.class);
        // 取得方法上的泛型参数
        ParameterizedType parameterizedType = (ParameterizedType) methodConsume.getGenericParameterTypes()[0];
        // 泛型参数里有通配符?，wildcardType表示的是<? super A>
        WildcardType wildcardType = (WildcardType) parameterizedType.getActualTypeArguments()[0];
        Assert.assertEquals("A", wildcardType.getLowerBounds()[0].getTypeName());
        // 未指定上界时，默认未Object.class
        Assert.assertSame(Object.class, wildcardType.getUpperBounds()[0]);
    }

}
