package cn.zefre.reflection.type.generic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.TypeVariableImpl;

import java.io.Serializable;
import java.lang.reflect.*;

/**
 * {@link TypeVariable}演示
 * 类型变量，即T、U、E这种
 *
 * @author pujian
 * @date 2022/5/26 14:45
 */
public class TypeVariableTest {

    @FunctionalInterface
    interface ICustom {
        void pay();
    }

    /**
     * 类型变量的上界可以为多个，必须使用&符号相连接
     * 第一个上界可以不为接口，之后的必须都为接口
     * 也就是说，&符号后必须为接口
     * @author pujian
     * @date 2022/5/26 16:46
     */
    final static class User<T extends Serializable & Comparable<T> & ICustom> {
        T username;
    }

    private TypeVariable<Class<User<?>>> typeVariable;


    /**
     * 通过{@link Field#getGenericType()}方法获取TypeVariable类型
     *
     * @author pujian
     * @date 2022/5/26 15:58
     */
    @SuppressWarnings("unchecked")
    @Before
    public void setup() throws NoSuchFieldException {
        Type usernameType = User.class.getDeclaredField("username").getGenericType();
        // 此时Field#getGenericType()方法返回的是TypeVariable类型
        Assert.assertTrue(usernameType instanceof TypeVariable);
        Assert.assertSame(TypeVariableImpl.class, usernameType.getClass());
        typeVariable = (TypeVariable<Class<User<?>>>) usernameType;
    }

    /**
     * {@link TypeVariable#getBounds()}方法
     * 获取上界
     *
     * @author pujian
     * @date 2022/5/26 15:58
     */
    @Test
    public void testGetBounds() {
        Type[] bounds = typeVariable.getBounds();
        // bounds[0] is Class<java.io.Serializable>
        Assert.assertSame(Serializable.class, bounds[0]);
        // bounds[1] is ParameterizedType, namely java.lang.Comparable<T>
        ParameterizedType bound1 = (ParameterizedType) bounds[1];
        Assert.assertSame(Comparable.class, bound1.getRawType());
        Assert.assertEquals("T", bound1.getActualTypeArguments()[0].getTypeName());
        Assert.assertNull(bound1.getOwnerType());
        // bounds[2] is Class<ICustom>
        Assert.assertSame(ICustom.class, bounds[2]);
    }

    /**
     * {@link TypeVariable#getGenericDeclaration()}方法
     * 获取这个TypeVariable是定义在哪里的(定义在类上还是方法上还是构造函数上)
     * 可能的返回值：Class、Method、Constructor
     *
     * @author pujian
     * @date 2022/5/26 16:14
     */
    @Test
    public void testGetGenericDeclaration() {
        Class<User<?>> genericDeclaration = typeVariable.getGenericDeclaration();
        Assert.assertSame(User.class, genericDeclaration);
    }

    /**
     * {@link TypeVariable#getName()}方法
     * 获取源代码中TypeVariable的name
     *
     * @author pujian
     * @date 2022/5/26 16:14
     */
    @Test
    public void testGetName() {
        Assert.assertEquals("T", typeVariable.getName());
        Assert.assertEquals("T", User.class.getTypeParameters()[0].getName());
    }

    /**
     * {@link TypeVariable#getAnnotatedBounds()}方法
     *
     * @author pujian
     * @date 2022/5/26 16:15
     */
    @Test
    public void testGetAnnotatedBounds() {
        AnnotatedType[] annotatedBounds = typeVariable.getAnnotatedBounds();
        Assert.assertSame(Serializable.class, annotatedBounds[0].getType());
        Assert.assertEquals("java.lang.Comparable<T>", annotatedBounds[1].getType().getTypeName());
        Assert.assertSame(ICustom.class, annotatedBounds[2].getType());
    }




    final static class Account<U> { }

    @Test
    public void testOverall() {
        TypeVariable<Class<Account>>[] typeVariables = Account.class.getTypeParameters();
        TypeVariable<Class<Account>> typeVariable = typeVariables[0];

        // 未指定上界，则隐式为Object
        Assert.assertSame(Object.class, typeVariable.getBounds()[0]);
        // U是在Account类上定义的
        Assert.assertSame(Account.class, typeVariable.getGenericDeclaration());
        Assert.assertEquals("U", typeVariable.getName());
        // 上界为Object
        Assert.assertSame(Object.class, typeVariable.getAnnotatedBounds()[0].getType());
    }

}
