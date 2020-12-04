package cn.zefre.queque;

/**
 * 队列
 * @author pujian
 * @date 2020/5/26 17:14
 */
public interface Queue<E> {
    /**
     *队列为空，返回true，反之返回false
     * @author pujian
     * @date 2020/5/26 17:30
     * @return
     */
    boolean isEmpty();
    /**
     *入队
     * @author pujian
     * @date 2020/5/26 17:16
     * @Param element
     * @return
     */
    void offer(E element);
    /**
     *出队
     * @author pujian
     * @date 2020/5/26 17:16
     * @return
     */
    E poll();
    /**
     *获取队首元素
     * @author pujian
     * @date 2020/5/26 17:17
     * @return
     */
    E peek();
    /**
     * 获取队列长度
     * @author pujian
     * @date 2020/5/27 15:34
     * @return
     */
    int size();
}
