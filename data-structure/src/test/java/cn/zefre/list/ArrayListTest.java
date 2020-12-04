package cn.zefre.list;

import cn.zefre.list.impl.ArrayList;
import org.junit.Test;


/**
 * @Author pujian
 * @Date 2020/4/27
 * @Description
 */
public class ArrayListTest {

    private List<String> list;

    private void printSize(){
        System.out.println("size = " + list.size());
    }

    @Test
    public void testInit(){
        list = new ArrayList<>(5);
        printSize();
        list.add("1");
    }
}
