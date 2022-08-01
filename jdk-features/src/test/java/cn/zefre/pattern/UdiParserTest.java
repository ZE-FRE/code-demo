package cn.zefre.pattern;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author pujian
 * @date 2022/8/1 13:22
 */
public class UdiParserTest {

    @Test
    public void testUnmatchedUdiCode() {
        String wholeUdi = "]C1(01)08714729719960(17)220729(10)24185778";
        Udi udi = UdiParser.parse(wholeUdi);
        assertNull(udi);
    }

    @Test
    public void testParse() {
        String wholeUdi = "(01)08714729719960(11)220721(17)230924(10)24185778(21)unique123";
        Udi udi = UdiParser.parse(wholeUdi);
        assertNotNull(udi);
        assertEquals("08714729719960", udi.getDi());
        assertEquals(UdiParser.parseDateStrToLocalDate("220721"), udi.getProduceDate());
        assertEquals(UdiParser.parseDateStrToLocalDate("230924"), udi.getExpiryDate());
        assertEquals("24185778", udi.getBatchNo());
        assertEquals("unique123", udi.getSerialNumber());
    }
}
