package cn.zefre.jdk8;

import lombok.extern.slf4j.Slf4j;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author pujian
 * @date 2021/11/16 21:45
 */
@Slf4j
public class CompletableFutureUtil {

    /**
     * 异步执行多个任务，并将各个任务的返回值收集到List中返回
     * 抛出异常的任务无法获取返回值，直接忽略
     *
     * @author pujian
     * @date 2021/11/16 22:15
     * @param executor 自定义线程池
     * @param tasks 任务集
     * @return 任务结果集
     */
    public static List<?> executeTasksAsync(Executor executor, Supplier<?>... tasks) {
        Objects.requireNonNull(executor, "自定义线程池不能为null");
        Objects.requireNonNull(tasks, "异步任务集不能为null");
        if (tasks.length == 0) return Collections.emptyList();
        // 提交、执行任务
        List<CompletableFuture<?>> futures = Arrays.stream(tasks).map(task -> CompletableFuture.supplyAsync(task, executor)).collect(Collectors.toList());
        // 等待所有任务完成
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[tasks.length]))
                .exceptionally(e -> null)
                .join();
        // 收集任务结果
        return futures.stream()
                .map(future -> future
                        .exceptionally(e -> { log.warn("任务调用异常：", e); return null; })
                        .getNow(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
