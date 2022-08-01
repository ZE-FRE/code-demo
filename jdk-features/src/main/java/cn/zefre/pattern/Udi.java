package cn.zefre.pattern;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author pujian
 * @date 2022/8/1 13:21
 */
@Data
public class Udi {

    /** 耗材DI码标识 */
    public static final String DI = "01";

    /** 耗材批号标识 */
    public static final String BATCH_NO = "10";

    /** 耗材生产日期标识 */
    public static final String PRODUCE_DATE = "11";

    /** 耗材有效期标识 */
    public static final String EXPIRY_DATE = "17";

    /** 耗材生产序列号标识 */
    public static final String SERIAL_NUMBER = "21";

    /**
     * 完整UDI编码
     */
    private String wholeUdi;

    /**
     * 耗材DI码
     */
    private String di;

    /**
     * 耗材批号
     */
    private String batchNo;

    /**
     * 耗材有效期
     */
    private LocalDate expiryDate;

    /**
     * 耗材生产日期
     */
    private LocalDate produceDate;

    /**
     * 耗材序列号
     */
    private String serialNumber;

}
