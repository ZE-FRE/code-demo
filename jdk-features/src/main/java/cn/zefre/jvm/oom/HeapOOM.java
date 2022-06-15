package cn.zefre.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * 演示堆内存溢出
 * VM args: -Xms5M -Xmx5M -XX:+HeapDumpOnOutOfMemoryError
 *
 * @author pujian
 * @date 2022/6/2 15:47
 */
public class HeapOOM {

    final static class OOMObject {
        private long a;
        private double b;
    }

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();
        while (true) {
            list.add(new OOMObject());
        }
    }
}
