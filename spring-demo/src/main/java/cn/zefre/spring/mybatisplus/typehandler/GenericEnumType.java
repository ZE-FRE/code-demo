package cn.zefre.spring.mybatisplus.typehandler;

/**
 * 映射String类型的枚举接口
 * @see cn.zefre.spring.mybatisplus.typehandler.StringEnumTypeHandler
 *
 * @author pujian
 * @date 2022/7/5 14:06
 */
public interface GenericEnumType<T> {

    /**
     * 获取枚举要映射的值
     *
     * @author pujian
     * @date 2022/7/5 16:20
     * @return java.lang.String
     */
    T getValue();

}
