package cn.zefre;

import cn.zefre.concurrent.task.FileSizeTask;
import cn.zefre.concurrent.task.SumTask;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinTest {

    /**
     * 求和
     */
    @Test
    public void testSum() {

        int[] nums = new int[100];
        for (int i = 0; i < nums.length; i++)
            nums[i] = i + 1;

        ForkJoinPool pool = ForkJoinPool.commonPool();
        ForkJoinTask<Integer> result = pool.submit(new SumTask(nums, 0, nums.length));
        System.out.println("最终结算结果：" + result.invoke());
        pool.shutdown();

    }

    /**
     * 测试单线程计算目录/文件大小
     */
    @Test
    public void testCalculateFileSize() {

        long start = System.currentTimeMillis();
        final String filePath = "D:\\test";
        File file = new File(filePath);
        long fileSize = calculateFileSize(file);
        System.out.println(filePath + " size = " + fileSize + " byte");

        System.out.println("花费时间：" + (System.currentTimeMillis() - start));
    }

    long calculateFileSize(File file) {
        if(null == file || !file.exists()) {
            throw new RuntimeException("文件不存在！");
        }
        long size = 0;
        if(file.isDirectory()) {
            File[] children = file.listFiles();
            for(File childFile : children)
                size += calculateFileSize(childFile);
        } else {
            System.out.println(file.getName() + " size = " + file.length() + " byte");
            return file.length();
        }
        return size;
    }

    /**
     * 测试使用fork/join框架来计算目录/文件大小
     */
    @Test
    public void testForkJoinFileSize() {

        long start = System.currentTimeMillis();
        final String filePath = "D:\\test";
        ForkJoinPool pool = ForkJoinPool.commonPool();
        ForkJoinTask<Long> result = pool.submit(new FileSizeTask(new File(filePath)));
        System.out.println(filePath + " size = " + result.invoke() + " byte");
        pool.shutdown();

        System.out.println("花费时间：" + (System.currentTimeMillis() - start));
    }

}
