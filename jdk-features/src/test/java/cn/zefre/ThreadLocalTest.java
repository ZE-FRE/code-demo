package cn.zefre;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/**
 * @author pujian
 * @date 2022/9/27 13:35
 */
public class ThreadLocalTest {

    static class Product {
        private String name;

        public Product(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Product{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    /**
     * GC后会回收弱引用对象容器里的对象
     * 而弱引用对象本身是一个有强引用关联的对象，所以weakReference本身不是null
     *
     * @author pujian
     * @date 2022/9/27 13:46
     */
    @Test
    public void testWeakReference() {
        Product product = new Product("apple");
        WeakReference<Product> weakReference = new WeakReference<>(product);
        System.out.println(weakReference.get());
        product = null;
        System.gc();
        Assertions.assertNull(weakReference.get());
    }



    private static ThreadLocal<String> threadLocal = ThreadLocal.withInitial(() -> "123");

    private void setToNull() {
        threadLocal = null;
    }
    /**
     * 测试ThreadLocal内存泄露问题
     * https://blog.csdn.net/zqy_zq_zxl/article/details/124586669
     * 主要原因是：
     * Thread.threadLocals里面存储的Entry如果不手动删除的话，就会一直存在这个threadLocals里面
     * 解释：当Entry关联的ThreadLocal对象被回收以后，Reference.referent被置空
     *      但是threadLocals里面持有的这个Entry并不会被回收，所以造成了内存泄露。
     *      只要这个线程还没有结束，线程里面的这个Entry就永远都存在
     *
     * @author pujian
     * @date 2022/9/27 13:37
     */
    @Test
    public void testMemoryLeak() throws NoSuchFieldException, IllegalAccessException {
        threadLocal.get();
        Thread currentThread = Thread.currentThread();
        Field field = Thread.class.getDeclaredField("threadLocals");
        field.setAccessible(true);
        Object threadLocals = field.get(currentThread);

        setToNull();
        System.gc();

        int a = 1;
    }


}
