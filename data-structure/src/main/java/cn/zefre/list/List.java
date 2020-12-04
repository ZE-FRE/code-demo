package cn.zefre.list;

/**
 * 线性表
 * @Author pujian
 * @Date 2020/4/27
 * @Description
 */
public interface List<E> {

    /**
     *获取线性表大小
     * @Author pujian
     * @Date 2020/4/27 10:03
     * @return
     */
    int size();

    /**
     *判断是否为空，是返回true，否返回false
     * @Author pujian
     * @Date 2020/4/27 10:03
     * @return
     */
    boolean isEmpty();

    /**
     *获取元素第一次出现的下标
     * @Author pujian
     * @Date 2020/4/27 10:08
     * @Param elem
     * @return
     */
    int indexOf(E elem);

    /**
     *获取元素最后出现的下标
     * @Author pujian
     * @Date 2020/4/27 10:09
     * @Param elem
     * @return
     */
    int lastIndexOf(E elem);

    /**
     *根据下标获取元素
     * @Author pujian
     * @Date 2020/4/27 10:03
     * @Param index 下标，取值从0开始
     * @return
     */
    E get(int index);

    /**
     *在指定位置插入元素
     * @Author pujian
     * @Date 2020/4/27 10:04
     * @Param index 下标，取值从0开始
     * @Param elem 元素
     * @return
     */
    void add(int index, E elem);

    /**
     *在末尾插入元素
     * @Author pujian
     * @Date 2020/4/27 10:05
     * @Param elem
     * @return
     */
    void add(E elem);

    /**
     *从指定下标开始插入多个元素
     * @Author pujian
     * @Date 2020/4/27 10:14
     * @Param index
     * @Param list
     * @return
     */
    void add(int index, List<? extends E> list);

    /**
     *在末尾插入多个元素
     * @Author pujian
     * @Date 2020/4/27 10:15
     * @Param list
     * @return
     */
    void add(List<? extends E> list);

    /**
     * 设置指定下标的元素值
     * @Author pujian
     * @Date 2020/4/27 10:59
     * @Param index
     * @Param elem
     * @return
     */
    void set(int index, E elem);

    /**
     *删除指定下标元素并用改元素返回
     * @Author pujian
     * @Date 2020/4/27 10:05
     * @Param index
     * @return
     */
    E remove(int index);

    /**
     *删除指定区间的元素
     * 下标区间取值范围为：[start, end)
     * @Author pujian
     * @Date 2020/4/27 10:10
     * @Param start 开始下标
     * @Param end 结束下标
     * @return
     */
    void removeRange(int start, int end);

    /**
     *清空线性表
     * @Author pujian
     * @Date 2020/4/27 10:07
     * @return
     */
    void clear();
}
