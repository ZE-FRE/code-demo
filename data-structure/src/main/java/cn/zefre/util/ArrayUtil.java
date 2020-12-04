package cn.zefre.util;

/**
 * @Author pujian
 * @Date 2020/4/27
 * @Description
 */
public class ArrayUtil {

    /**
     * 复制数组
     * @Author pujian
     * @Date 2020/4/27 10:37
     * @Param src 原数组
     * @Param srcStart 原数组开始位置
     * @Param dst 目标数组
     * @Param dstStart 目标数组开始位置
     * @Param length 复制元素个数
     * @return
     */
    public static void copy(Object[] src, int srcStart, Object[] dst, int dstStart, int length){
        if(length <= 0)
            return;
        if(dstStart + length > dst.length){
            throw new RuntimeException("复制长度过长");
        }
        for (int i = 0; i < length; i++){
            dst[dstStart+i] = src[srcStart+i];
        }
    }
}
