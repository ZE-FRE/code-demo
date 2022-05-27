package cn.zefre.reflection.type;

import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;

/**
 * {@link GenericDeclaration}测试
 * {@link GenericDeclaration}表示泛型定义，描述的是泛型是在哪里定义的
 * 它的实现类有以下三个：
 * {@link java.lang.Class}、{@link java.lang.reflect.Method}、{@link java.lang.reflect.Constructor}
 * 表示java泛型只能在类上、方法上以及构造方法上定义
 *
 * @author pujian
 * @date 2022/5/27 14:05
 */
public class GenericDeclarationTest {

    final static class GenericClass<T> {
        private T field;
        <A> GenericClass(T field, A a) {
            System.out.println("instantiating GenericClass with " + field + " and " + a);
            this.field = field;
        }
        <U extends Serializable> U convert(T field) { return null; }
    }

    @Test
    public void testGetTypeParameters() throws NoSuchMethodException {
        TypeVariable<Class<GenericClass>> classTypeVar = GenericClass.class.getTypeParameters()[0];
        TypeVariable<Constructor<GenericClass>> constructorTypeVar = GenericClass.class.getDeclaredConstructor(Object.class, Object.class).getTypeParameters()[0];
        TypeVariable<Method> methodTypeVar = GenericClass.class.getDeclaredMethod("convert", Object.class).getTypeParameters()[0];
        Assert.assertEquals("T", classTypeVar.getTypeName());
        Assert.assertEquals("A", constructorTypeVar.getTypeName());
        Assert.assertEquals("U", methodTypeVar.getTypeName());
        Assert.assertSame(Serializable.class, methodTypeVar.getBounds()[0]);


        Class<GenericClass> classGenericDeclaration = classTypeVar.getGenericDeclaration();
        Constructor<GenericClass> constructorGenericDeclaration = constructorTypeVar.getGenericDeclaration();
        Method methodGenericDeclaration = methodTypeVar.getGenericDeclaration();
        Assert.assertSame(GenericClass.class, classGenericDeclaration);
        Assert.assertEquals("cn.zefre.reflection.type.GenericDeclarationTest$GenericClass", constructorGenericDeclaration.getName());
        Assert.assertEquals("convert", methodGenericDeclaration.getName());
    }

}
