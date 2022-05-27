package cn.zefre.reflection.type.generic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;

/**
 * {@link ParameterizedType}演示
 * ParameterizedType，即参数化类型，如：List、Map、Optional等
 *
 * @author pujian
 * @date 2022/5/26 9:38
 */
public class ParameterizedTypeTest {

    static final class Company<T> {
        final static class Employee<U> { }

        List<T> list;
        List<String> aliases;
        Employee<String> employee;
    }


    private ParameterizedType listParameterized;
    private ParameterizedType aliasesParameterized;
    private ParameterizedType employeeParameterized;


    /**
     * 通过{@link Field#getGenericType()}方法获取ParameterizedType类型
     *
     * @author pujian
     * @date 2022/5/26 13:11
     */
    @Before
    public void setup() throws NoSuchFieldException {
        Type listType = Company.class.getDeclaredField("list").getGenericType();
        Type aliasesType = Company.class.getDeclaredField("aliases").getGenericType();
        Type employeeType = Company.class.getDeclaredField("employee").getGenericType();

        // 此时Field#getGenericType()方法返回的是ParameterizedType类型
        Assert.assertTrue(listType instanceof ParameterizedType);
        Assert.assertTrue(aliasesType instanceof ParameterizedType);
        Assert.assertTrue(employeeType instanceof ParameterizedType);
        Assert.assertEquals(ParameterizedTypeImpl.class, listType.getClass());
        Assert.assertEquals(ParameterizedTypeImpl.class, aliasesType.getClass());
        Assert.assertEquals(ParameterizedTypeImpl.class, employeeType.getClass());

        listParameterized = (ParameterizedType) listType;
        aliasesParameterized = (ParameterizedType) aliasesType;
        employeeParameterized = (ParameterizedType) employeeType;
    }


    /**
     * {@link Type#getTypeName()}方法
     *
     * @author pujian
     * @date 2022/5/26 13:11
     */
    @Test
    public void testGetTypeName() {
        Assert.assertEquals("java.util.List<T>", listParameterized.getTypeName());
        Assert.assertEquals("java.util.List<java.lang.String>", aliasesParameterized.getTypeName());
        Assert.assertEquals("cn.zefre.reflection.generic.ParameterizedTypeTest$Company$Employee<java.lang.String>",
                employeeParameterized.getTypeName());
    }


    /**
     * {@link ParameterizedType#getActualTypeArguments()}方法
     * 此方法返回<>中的类型
     *
     * @author pujian
     * @date 2022/5/26 13:17
     */
    @Test
    public void testGetActualTypeArguments() {
        Type listGeneric = listParameterized.getActualTypeArguments()[0];
        Type aliasesGeneric = aliasesParameterized.getActualTypeArguments()[0];
        Type employeeGeneric = employeeParameterized.getActualTypeArguments()[0];

        Assert.assertTrue(listGeneric instanceof TypeVariable);
        // aliasesGeneric is Class<java.lang.String>
        Assert.assertSame(String.class, aliasesGeneric);
        // employeeGeneric is Class<java.lang.String>
        Assert.assertSame(String.class, employeeGeneric);

        Assert.assertEquals("T", listGeneric.getTypeName());
        Assert.assertEquals("java.lang.String", aliasesGeneric.getTypeName());
        Assert.assertEquals("java.lang.String", employeeGeneric.getTypeName());
    }


    /**
     * {@link ParameterizedType#getRawType getRawType()}方法
     * 此方法返回原始类型
     *
     * @author pujian
     * @date 2022/5/26 14:05
     */
    @Test
    public void testGetRawType()  {
        Type listRawType = listParameterized.getRawType();
        Type aliasesRawType = aliasesParameterized.getRawType();
        Type employeeRawType = employeeParameterized.getRawType();

        Assert.assertSame(List.class, listRawType);
        Assert.assertSame(List.class, aliasesRawType);
        Assert.assertSame(Company.Employee.class, employeeRawType);
    }

    /**
     * {@link ParameterizedType#getOwnerType()}方法
     * 此方法返回外部类的类型
     *
     * @author pujian
     * @date 2022/5/26 14:36
     */
    @Test
    public void testGetOwnerType()  {
        Assert.assertNull(listParameterized.getOwnerType());
        Assert.assertNull(aliasesParameterized.getOwnerType());

        // ownerType is Class<cn.zefre.reflection.Company>
        Type ownerType = employeeParameterized.getOwnerType();
        Assert.assertSame(Company.class, ownerType);
        Assert.assertEquals("cn.zefre.reflection.generic.ParameterizedTypeTest$Company", ownerType.getTypeName());
    }

}
