package cn.zefre.jdk8;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author pujian
 * @date 2020/11/11 15:19
 */
@Slf4j
public class CompletableFutureTest {

    private HttpClient httpClient = new HttpClient();

    private List<Shop> shopList;

    @Before
    public void init() {
        shopList = new ArrayList<>(Arrays
                .asList(new Shop("芒果", "淘宝"),
                        new Shop("芒果", "天猫"),
                        new Shop("芒果", "京东"),
                        new Shop("芒果", "拼多多")));
    }

    @Test
    public void testGetShopSync() {
        long start = System.currentTimeMillis();

        shopList.stream().forEach(shop -> {
            httpClient.getPrice(shop);
            System.out.println(shop);
        });
        System.out.println("同步花费时间：" + (System.currentTimeMillis() - start) + "ms");
    }

    @Test
    public void testGetShopByFuture() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Future<Shop>> futures = shopList.stream().map(shop -> executor.submit(() -> httpClient.getPrice(shop))).collect(Collectors.toList());
        for (Future<Shop> future : futures) {
            System.out.println(future.get());
        }
        System.out.println("Future花费时间：" + (System.currentTimeMillis() - start) + "ms");
    }

    @Test
    public void testGetShopByParallelStream() {
        long start = System.currentTimeMillis();

        List<Shop> shops = shopList.parallelStream().map(shop -> httpClient.getPrice(shop)).collect(Collectors.toList());
        shops.stream().forEach(System.out::println);
        System.out.println("并行流花费时间：" + (System.currentTimeMillis() - start) + "ms");
    }


    @Test
    public void testGetShopByCompletableFuture() {
        long start = System.currentTimeMillis();

        List<CompletableFuture<Shop>> completableFutures = shopList.stream().map(shop -> CompletableFuture.supplyAsync(() -> httpClient.getPrice(shop))).collect(Collectors.toList());

        List<Shop> shops = completableFutures.stream().map(future -> future.join()).collect(Collectors.toList());
        shops.stream().forEach(System.out::println);
        System.out.println("CompletableFuture花费时间：" + (System.currentTimeMillis() - start) + "ms");

    }


    @Test
    public void testCompletableFuture() {
        /*CompletableFuture<String> thenSync = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " 执行第一步");
            return "hello";
        }).thenApply((str) -> {
            System.out.println(Thread.currentThread().getName() + " 执行第二步");
            return str + " world";
        });
        String result1 = thenSync.join();
        System.out.println("thenApply result = " + result1);*/


        CompletableFuture<String> thenAsync = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " 执行第一步");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("第一步执行完毕");
            return "hello";
        }).thenApplyAsync(str -> {
            System.out.println(Thread.currentThread().getName() + " 执行第二步");
            return str + " world";
        });
        String result2 = thenAsync.join();
        System.out.println("thenApplyAsync result = " + result2);
    }

    @Test
    public void testThenCompose() throws InterruptedException {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("执行第一个");
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("第一个执行完毕");
            return "first";
        }).thenCompose(str -> {
            System.out.println("str = " + str);
            return CompletableFuture.supplyAsync(() -> {
                System.out.println("执行第二个");
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("第二个执行完毕");
                return 10;
            });
        }).thenAccept(System.out::println);

        TimeUnit.MILLISECONDS.sleep(2000);
    }

    @Test
    public void testJoinException() {
        String result = CompletableFuture.supplyAsync(() -> {
            System.out.println("执行");
            int a = 3 / 0;
            return "success";
        }).join();
        System.out.println(result);
    }

    @Test
    public void testFutureGetException() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(() -> {
            System.out.println(Thread.currentThread().getName() + " invoke");
            int a = 3 / 0;
            return "success";
        });
        System.out.println(future.get());
        System.out.println(Thread.currentThread().getName() + " run success");
    }

    @Test
    public void testThreadException() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " start run");
        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " invoke");
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int a = 3 / 0;
        });
        thread.start();
        thread.join();
        System.out.println(Thread.currentThread().getName() + " run success");
    }

    /**
     * thenCombine方法用来合并两个future结果
     *
     * @throws InterruptedException
     */
    @Test
    public void testThenCombine() throws InterruptedException {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("执行第一个");
            try {
                TimeUnit.MILLISECONDS.sleep(1200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("第一个执行完成");
            int a = 3 / 0;
            return "haha";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            System.out.println("执行第二个");
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("第二个执行完成");
            return 10;
        }), (str, num) -> {
            System.out.println("str = " + str);
            System.out.println("num = " + num);
            // 对结果进行合并
            return str + "-" + num * 2;
        }).thenAccept((result) -> {
            System.out.println("final result = " + result);
        }).exceptionally((e) -> {
            System.out.println(e.getMessage());
            return null;
        });
        TimeUnit.MILLISECONDS.sleep(2500);
    }

    /**
     * thenAcceptBoth方法用于等待两个future完成后执行下一步操作
     *
     * @throws InterruptedException
     */
    @Test
    public void testThenAcceptBoth() throws InterruptedException {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("执行第一个");
            return "hehe";
        }).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
            System.out.println("执行第二个");
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 3 / 0;
        }), (result1, result2) -> {
            System.out.println("result1 = " + result1);
            System.out.println("result2 = " + result2);
        }).exceptionally(e -> {
            System.out.println(e.getMessage());
            return null;
        });

        TimeUnit.MILLISECONDS.sleep(1500);
    }

    @Test
    public void testApplyToEither() throws InterruptedException {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("执行第一个");
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hehe";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            System.out.println("执行第二个");
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "xixi";
        }), (result -> {
            System.out.println("result = " + result);
            return 10;
        }));

        TimeUnit.MILLISECONDS.sleep(2000);
    }

    @Test
    public void testAllOf() throws InterruptedException {

        CompletableFuture.allOf(CompletableFuture.supplyAsync(() -> {
            System.out.println("执行第一个");
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("第一个执行完成");
            return "first";
        }), CompletableFuture.supplyAsync(() -> {
            System.out.println("执行第二个");
            try {
                TimeUnit.MILLISECONDS.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("第二个执行完成");
            return "second";
        }), CompletableFuture.supplyAsync(() -> {
            System.out.println("执行第三个");
            try {
                TimeUnit.MILLISECONDS.sleep(900);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int a = 3 / 0;
            System.out.println("第三个执行完成");
            return "third";
        })).thenAccept(result -> {
            System.out.println("result = " + result);
        }).exceptionally(e -> {
            System.out.println(e.getMessage());
            return null;
        });
        TimeUnit.MILLISECONDS.sleep(2500);
    }

    @Test
    public void testAnyOf() throws InterruptedException {
        CompletableFuture.anyOf(CompletableFuture.supplyAsync(() -> {
            System.out.println("执行第一个");
            try {
                TimeUnit.MILLISECONDS.sleep(900);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int a = 3 / 0;
            System.out.println("第一个执行完成");
            return "first";
        }), CompletableFuture.supplyAsync(() -> {
            System.out.println("执行第二个");
            try {
                TimeUnit.MILLISECONDS.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("第二个执行完成");
            return "second";
        }), CompletableFuture.supplyAsync(() -> {
            System.out.println("执行第三个");
            try {
                TimeUnit.MILLISECONDS.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("第三个执行完成");
            return "third";
        })).thenAccept(result -> {
            System.out.println("检测到有一个执行完毕,result = " + result);
        }).exceptionally(e -> {
            System.out.println(e.getMessage());
            return null;
        });
        TimeUnit.MILLISECONDS.sleep(2500);
    }

    @Test
    public void testExecuteTasksAsync() {
        Supplier<String> task1 = () -> {
            System.out.println(Thread.currentThread().getName() + ":任务1执行");
            return "hello";
        };
        Supplier<Integer> task2 = () -> {
            System.out.println(Thread.currentThread().getName() + ":任务2开始执行");
            try {
                TimeUnit.MILLISECONDS.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ":任务2执行完毕");
            return 3 / 0;
        };
        Supplier<String> task3 = () -> {
            System.out.println(Thread.currentThread().getName() + ":任务3已执行");
            int[] nums = new int[2];
            nums[2] = 12;
            return "world";
        };
        long startTime = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + ":调用执行");
        List<?> results = CompletableFutureUtil.executeTasksAsync(Executors.newFixedThreadPool(3), task1, task2, task3);
        System.out.println(Thread.currentThread().getName() + ":调用花费时间：" + (System.currentTimeMillis() - startTime));
        results.forEach(System.out::println);
    }

    static class HttpClient {
        private Random random = new Random();

        public Shop getPrice(Shop shop) {
            shop.setPrice(calculatePrice());
            return shop;
        }

        private Double calculatePrice() {
            delay();
            return Double.valueOf(random.nextInt(200));
        }

        /**
         * 模拟接口调用耗费时间
         *
         * @throws InterruptedException
         */
        private void delay() {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    static class Shop {
        /**
         * 商品名称
         */
        private String name;
        /**
         * 商品价格
         */
        private Double price;
        /**
         * 供应商
         */
        private String vendor;

        public Shop(String name, String vendor) {
            this.name = name;
            this.vendor = vendor;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }

        @Override
        public String toString() {
            return "Shop{" +
                    "name='" + name + '\'' +
                    ", price=" + price +
                    ", vendor='" + vendor + '\'' +
                    '}';
        }
    }

}
