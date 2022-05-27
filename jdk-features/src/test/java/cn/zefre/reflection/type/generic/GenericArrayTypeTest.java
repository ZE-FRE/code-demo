package cn.zefre.reflection.type.generic;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.*;
import java.util.Comparator;
import java.util.List;

/**
 * {@link GenericArrayType}演示
 * 泛型数组  如：E[]、T[]、List<String>[]等
 * 注意：List[]、String[]不包含泛型，不属于GenericArrayType，它们是特殊的Class类型
 *
 * @author pujian
 * @date 2022/5/27 9:23
 */
public class GenericArrayTypeTest {

    interface Computer {

        <T extends Number> T[] getSomething(Comparator<? super Integer> comparator);

        List<String>[] getList();

    }

    /**
     * 返回泛型数组中元素的Type类型，即List<String>[]的List<String>、T[]中的T
     *
     * @author pujian
     * @date 2022/5/27 10:04
     */
    @Test
    public void testGetGenericComponentType() throws NoSuchMethodException {
        Method getSomething = Computer.class.getMethod("getSomething", Comparator.class);

        // TypeVariable<Method>  <T extends Number>
        TypeVariable<Method> typeVariable = getSomething.getTypeParameters()[0];
        Assert.assertSame(Number.class, typeVariable.getBounds()[0]);
        Assert.assertEquals("T", typeVariable.getName());
        Assert.assertSame(getSomething, typeVariable.getGenericDeclaration());

        // ParameterizedType Comparator<? super Integer>
        ParameterizedType genericParameterType = (ParameterizedType) getSomething.getGenericParameterTypes()[0];
        Assert.assertTrue(genericParameterType.getActualTypeArguments()[0] instanceof WildcardType);
        Assert.assertSame(Comparator.class, genericParameterType.getRawType());

        // GenericArrayType T[]
        GenericArrayType genericReturnType = (GenericArrayType) getSomething.getGenericReturnType();
        Assert.assertEquals("T[]", genericReturnType.getTypeName());
        // genericComponentType is TypeVariable<Method>  T
        Type genericComponentType = genericReturnType.getGenericComponentType();
        Assert.assertEquals("T", genericComponentType.getTypeName());



        Method getList = Computer.class.getMethod("getList");
        // GenericArrayType List<String>[]
        GenericArrayType genericReturnType1 = (GenericArrayType) getList.getGenericReturnType();

        // genericComponentType1 is ParameterizedType List<String>
        ParameterizedType genericComponentType1 = (ParameterizedType) genericReturnType1.getGenericComponentType();
        Assert.assertEquals("java.util.List<java.lang.String>", genericComponentType1.getTypeName());
        Assert.assertSame(String.class, genericComponentType1.getActualTypeArguments()[0]);
        Assert.assertSame(List.class, genericComponentType1.getRawType());

    }

}
