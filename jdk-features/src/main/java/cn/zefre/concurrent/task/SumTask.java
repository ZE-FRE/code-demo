package cn.zefre.concurrent.task;

import java.util.Optional;
import java.util.concurrent.RecursiveTask;

public class SumTask extends RecursiveTask<Integer> {
    // 阈(yu)值
    private final static int THRESHOLD = 20;
    private int[] nums;
    private int from;
    private int to;

    public SumTask(int[] nums, int from, int to) {
        this.nums = nums;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if((to - from) <= THRESHOLD)
            return calculateSum();
        SumTask leftTask = new SumTask(nums, from, (from + to) / 2);
        SumTask rightTask = new SumTask(nums, (from + to) / 2, to);
        // 将任务放入当前线程工作队列
        leftTask.fork();
        rightTask.fork();
        // 合并两个任务的结果
        return leftTask.join() + rightTask.join();
    }

    /**
     * 求和
     * @return
     */
    private Integer calculateSum() {
        return Optional
                .ofNullable(nums)
                .map(nums -> {
                    int sum = 0;
                    for(int i = from; i < to; i++)
                        sum += nums[i];
                    System.out.println(Thread.currentThread() + " calculate Σ(" + from + "-" + to + ") = " + sum);
                    return sum;
                }).orElse(0);
    }
}
