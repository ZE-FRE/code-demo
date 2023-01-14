package cn.zefre.common.jackson.enums;

/**
 * 枚举接口
 *
 * @author pujian
 * @date 2023/1/13 21:13
 */
public interface BaseEnum<T> {

    /**
     * 获取枚举代码
     *
     * @return T
     * @author pujian
     * @date 2023/1/14 18:58
     */
    T getCode();
    /**
     * 获取枚举描述
     *
     * @return java.lang.String
     * @author pujian
     * @date 2023/1/14 18:58
     */
    String getDescription();

}
