package cn.zefre.jdk8.methodpointer;

/**
 * @author pujian
 * @date 2020/10/21 16:49
 */
@FunctionalInterface
public interface UserConstructor {

    User construct(Long id, String name, Integer age);
}
