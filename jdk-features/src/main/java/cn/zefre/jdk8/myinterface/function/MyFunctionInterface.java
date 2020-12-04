package cn.zefre.jdk8.myinterface.function;

/**
 * 函数接口
 * @author pujian
 * @date 2020/10/21 14:26
 * @description 函数式接口就是一个有且仅有一个抽象方法，但是可以有多个非抽象方法的接口
 * 接口的非抽象方法是1.8新增的，1.8之前接口中都是抽象方法
 * 接口的非抽象方法有：静态方法和默认方法(default关键字修饰)
 */
@FunctionalInterface
public interface MyFunctionInterface {

    int op(int a, int b);
}
