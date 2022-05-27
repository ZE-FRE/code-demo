/**
 * java类型测试包
 * 在java中，{@link java.lang.reflect.Type}表示总的类型，是所有类型的基类。
 * 它细分为以下五种：
 * 原始类型{@link java.lang.Class}，它是一个具体的Type实现类
 * 参数化类型{@link java.lang.reflect.ParameterizedType}，主要实现是{@link sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl}
 * 泛型数组类型{@link java.lang.reflect.GenericArrayType}，主要实现是{@link sun.reflect.generics.reflectiveObjects.GenericArrayTypeImpl}
 * 类型变量{@link java.lang.reflect.TypeVariable}，主要实现是{@link sun.reflect.generics.reflectiveObjects.TypeVariableImpl}
 * 基本类型(Class)，即primitive type，也就是boolean、byte、short(2字节)、char(2字节)、int(4字节)、long(8字节)、float(4字节)、double(8字节)
 *
 * @author pujian
 * @date 2022/5/27 13:52
 */
package cn.zefre.reflection.type;
