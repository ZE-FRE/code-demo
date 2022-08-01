package cn.zefre.pattern;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * udi编码解析工具
 * udi编码分为ai部分(application identifier)与数据部分(data)
 * 例如(01)08714729719960，ai部分为：01，data部分为08714729719960
 *
 * @author pujian
 * @date 2022/8/1 13:22
 */
public final class UdiParser {

    private UdiParser() { }

    /**
     * udi编码正则表达式，分为三组，这三组下标以及对应表示的部分如下：
     * index    content
     *   1      (01)08714729719960
     *   2      (01)
     *   3      08714729719960
     * 另外，0固定表示输入的整个字符串
     * 如：Pattern.compile(UDI_REGEX).matcher("(01)08714729719960(17)220729");
     * 则：0-->(01)08714729719960(17)220729
     */
    private static final String UDI_REGEX = "((\\(\\d{2,4}\\))([^()]+))+";

    private static final Pattern pattern = Pattern.compile(UDI_REGEX);

    /**
     * 解析udi编码，得到{@link Udi}
     *
     * @param wholeUdi udi编码
     * @author pujian
     * @date 2022/7/21 15:22
     * @return cn.rivamed.common.udi.Udi
     */
    public static Udi parse(String wholeUdi) {
        if (wholeUdi == null || wholeUdi.isEmpty()) {
            return null;
        }
        Matcher matcher = pattern.matcher(wholeUdi);
        // 不是udi编码
        if (!matcher.matches()) {
            return null;
        }
        Udi udi = new Udi();
        udi.setWholeUdi(wholeUdi);
        matcher.reset();
        while (matcher.find()) {
            String matchedStr = matcher.group(1);
            // 得到ai部分
            String ai = matcher.group(2).replace("(","").replace(")","");
            // 得到数据部分
            String data = matcher.group(3);
            switch (ai) {
                case Udi.DI:
                    udi.setDi(data);
                    break;
                case Udi.BATCH_NO:
                    udi.setBatchNo(data);
                    break;
                case Udi.PRODUCE_DATE:
                    udi.setProduceDate(parseDateStrToLocalDate(data));
                    break;
                case Udi.EXPIRY_DATE:
                    udi.setExpiryDate(parseDateStrToLocalDate(data));
                    break;
                case Udi.SERIAL_NUMBER:
                    udi.setSerialNumber(data);
                    break;
                default:
                    break;
            }
            wholeUdi = wholeUdi.replace(matchedStr, "");
            if (wholeUdi.isEmpty()) {
                return udi;
            }
            matcher.reset(wholeUdi);
        }
        return udi;
    }


    /**
     * udi格式日期前缀
     * 解析udi编码得到的日期字符串，只有6位：yyMMdd，缺省了年份前缀20
     */
    private static final String UDI_FORMAT_DATE_PREFIX = "20";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * 将udi格式的日期字符串转为Date对象
     *
     * @param udiDateStr 日期字符串
     * @author pujian
     * @date 2022/3/14 14:00
     * @return java.util.Date
     */
    public static LocalDate parseDateStrToLocalDate(String udiDateStr) {
        if (udiDateStr == null || udiDateStr.isEmpty()) {
            return null;
        }
        udiDateStr = UDI_FORMAT_DATE_PREFIX + udiDateStr;
        return LocalDate.parse(udiDateStr, formatter);
    }

}
