package cn.zefre.stack;

/**
 * 栈
 * @Author pujian
 * @Date 2020/5/18
 * @Description
 */
public interface Stack<E> {

    /**
     * 获取栈的容量
     * @Author pujian
     * @Date 2020/5/18 14:56
     * @return
     */
    int size();
    /**
     *判断是否为空
     * @Author pujian
     * @Date 2020/5/18 14:57
     * @return
     */
    boolean isEmpty();
    /**
     *进栈
     * @Author pujian
     * @Date 2020/5/18 14:58
     * @Param element
     * @return
     */
    void push(E element);
    /**
     *出栈
     * @Author pujian
     * @Date 2020/5/18 14:59
     * @return
     */
    E pop();
}
