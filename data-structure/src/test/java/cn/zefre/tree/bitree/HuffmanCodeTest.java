package cn.zefre.tree.bitree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * @author pujian
 * @date 2023/3/31 10:28
 */
public class HuffmanCodeTest {

    @Test
    public void testConstructor() {
        Map<Character, Integer> charMap = new HashMap<>();
        charMap.put('a', 5);
        charMap.put('b', 15);
        charMap.put('c', 10);
        charMap.put('d', 12);
        charMap.put('e', 4);
        /*
         *              (46)
         *          /          \
         *       (19)          (27)
         *      /    \        /     \
         *    (9)   c(10)   d(12)   b(15)
         *   /   \
         * e(4)  a(5)
         *
         * 对应的编码应该是：
         * character    code
         *     a        001
         *     b        11
         *     c        01
         *     d        10
         *     e        000
         */
        HuffmanCode huffmanCode = new HuffmanCode(charMap);
        Map<Character, Character[]> charset = huffmanCode.getCharset();
        assertArrayEquals(new Character[]{'0', '0', '1'}, charset.get('a'));
        assertArrayEquals(new Character[]{'1', '1'}, charset.get('b'));
        assertArrayEquals(new Character[]{'0', '1'}, charset.get('c'));
        assertArrayEquals(new Character[]{'1', '0'}, charset.get('d'));
        assertArrayEquals(new Character[]{'0', '0', '0'}, charset.get('e'));
    }


    @Test
    public void testEncode() {
        Map<Character, Integer> charMap = new HashMap<>();
        charMap.put('a', 5);
        charMap.put('b', 15);
        charMap.put('c', 10);
        charMap.put('d', 12);
        charMap.put('e', 4);
        HuffmanCode huffmanCode = new HuffmanCode(charMap);

        String encodedMsg = huffmanCode.encode("abcdebcdbd");
        Assertions.assertEquals("0011101100001101101110", encodedMsg);
    }

    @Test
    public void testDecode() {
        Map<Character, Integer> charMap = new HashMap<>();
        charMap.put('a', 5);
        charMap.put('b', 15);
        charMap.put('c', 10);
        charMap.put('d', 12);
        charMap.put('e', 4);
        HuffmanCode huffmanCode = new HuffmanCode(charMap);
        String msg = "abcdebcdbd";
        String encodedMsg = huffmanCode.encode(msg);

        String decodeMsg = huffmanCode.decode(encodedMsg);
        Assertions.assertEquals(msg, decodeMsg);
    }

}
