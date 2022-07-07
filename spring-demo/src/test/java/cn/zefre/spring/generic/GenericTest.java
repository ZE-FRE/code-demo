package cn.zefre.spring.generic;

import cn.zefre.spring.mybatisplus.typehandler.GenericEnumType;
import cn.zefre.spring.mybatisplus.typehandler.MyInterface;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * @author pujian
 * @date 2022/7/7 17:09
 */
public class GenericTest {

    enum MyEnum implements GenericEnumType<String>, MyInterface {
        ONE("一");
        private String value;

        MyEnum(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    @Test
    public void testGeneric() {
        Type[] genericInterfaces = MyEnum.class.getGenericInterfaces();
//        for (Type genericInterface : genericInterfaces) {
//            boolean isClass = false;
//            if (genericInterface instanceof Class) {
//                isClass = true;
//            }
//            System.out.println(genericInterface + "--" + isClass);
//        }

//        ParameterizedType parameterizedType = (ParameterizedType) genericInterfaces[0];
//        System.out.println(parameterizedType.getRawType());
//
//        System.out.println(Arrays.toString(parameterizedType.getActualTypeArguments()));
//
//        Class<?> myInterface = (Class<?>) genericInterfaces[1];
//        System.out.println(Arrays.toString(myInterface.getGenericInterfaces()));


        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType) {
                ParameterizedType paramType = (ParameterizedType) genericInterface;
                if (paramType.getRawType() == GenericEnumType.class) {
                    Type actualType = paramType.getActualTypeArguments()[0];
                    System.out.println(actualType.getTypeName());
                }
            } else {
                Type[] genericInterfaces1 = ((Class<?>) genericInterface).getGenericInterfaces();
                System.out.println(Arrays.toString(genericInterfaces1));
            }
        }

        System.out.println(MyInterface.class.getGenericInterfaces()[0]);

    }

    private Class<?> getActualType(Type type) {
        if (type instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) type;
            if (paramType.getRawType() == GenericEnumType.class) {
                Type actualType = paramType.getActualTypeArguments()[0];
                return (Class<?>) actualType;
            }
        } else {
            Class<?> clazz = (Class<?>) type;
            Type[] genericInterfaces = clazz.getGenericInterfaces();
            for (Type genericInterface : genericInterfaces) {
                Class<?> actualType = getActualType(genericInterface);
                if (actualType != null) {
                    return actualType;
                }
            }
        }
        return null;
    }

    private Class<?> getActualType(Class<?> enumClass) {
        Type[] genericInterfaces = enumClass.getGenericInterfaces();

        for (Type genericInterface : genericInterfaces) {
            Class<?> actualType = getActualType(genericInterface);
            if (actualType != null) {
                return actualType;
            }
        }

        //return null;

        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType) {
                ParameterizedType paramType = (ParameterizedType) genericInterface;
                if (paramType.getRawType() == GenericEnumType.class) {
                    Type actualType = paramType.getActualTypeArguments()[0];
                    return (Class<?>) actualType;
                }
            } else {
                Type[] genericInterfaces1 = ((Class<?>) genericInterface).getGenericInterfaces();

            }
        }
        return null;
    }


    enum Enum2 implements GenericEnumType<List<String>[]> {

        ;

        private List<String>[] list;

        @Override
        public List<String>[] getValue() {
            return list;
        }
    }

    @Test
    public void testGenericArray() {
        Type[] genericInterfaces = MyEnum.class.getGenericInterfaces();
        ParameterizedType parameterizedType = (ParameterizedType) genericInterfaces[0];
        System.out.println(Arrays.toString(parameterizedType.getActualTypeArguments()));
        System.out.println(parameterizedType.getRawType());
        System.out.println(parameterizedType.getActualTypeArguments()[0]);
    }

}
