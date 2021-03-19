package cn.zefre.jdk8;

import cn.zefre.jdk8.methodpointer.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *  Stream api测试
 * @author pujian
 * @date 2020/10/21 21:21
 *
 * Stream中间操作(intermediate operation)方法有：
 * filter、map、flatMap、distinct、sorted、peek、limit、skip
 *
 * 终端操作(terminal operation)方法有：
 * forEach、forEachOrdered、toArray、reduce、collect、min、max、count
 * anyMatch、allMatch、noneMatch、findFirst、findAny
 *
 * short-circuiting方法有:
 * anyMatch、allMatch、noneMatch、findFirst、findAny
 */
public class StreamTest {

    @Test
    public void testFilter() {
        List<String> names = new ArrayList<>(Arrays.asList("zhangsan", "lisi","", "wangwu", "", "qianliu"));
        // 打印空字符串的个数
        System.out.println(names.stream().filter(String::isEmpty).count());

        // 名字长度大于5的
        List<String> collect = names.stream().filter(name -> name.length() > 5).collect(Collectors.toList());
        System.out.println(collect);

        // 拼接
        String collect1 = names.stream().filter(name -> name.length() > 5).collect(Collectors.joining("===="));
        System.out.println(collect1);

        System.out.println(names.parallelStream().filter("zhangsan"::equals).count());
    }

    @Test
    public void testSorted() {
        List<Integer> nums = new ArrayList<>(Arrays.asList(3, 2, 8, 10, 15, 19));
        System.out.println(nums.stream().sorted().collect(Collectors.toList()));
    }


    @Test
    public void testPeek() {
        Stream.of("one", "two", "three", "four")
                .filter(e -> {
                    System.out.println("filter: " + e);
                    return e.length() > 3;})
                .peek(e -> System.out.println("peek1 value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("peek2 value: " + e))
                .forEach(e -> System.out.println("forEach: " + e));

        /**
         * 执行结果：
         * filter: one
         * filter: two
         * filter: three
         * peek1 value: three
         * peek2 value: THREE
         * forEach: THREE
         * filter: four
         * peek1 value: four
         * peek2 value: FOUR
         * forEach: FOUR
         */

        /**
         * peek方法与forEach方法唯一的区别就是peek方法是中间操作(intermediate operation)，而forEach是terminal operation
         */
    }

    @Test
    public void testReduce() {
        String result = Stream.of("one", "two", "three", "four")
                .reduce((s1, s2) -> {
                    System.out.println("s1 = " + s1);
                    System.out.println("s2 = " + s2);
                    return s1 + s2;
                }).get();
        System.out.println(result);

        System.out.println("===========================");


        int r = new ArrayList<>(Arrays.asList("one", "two", "three", "four", "five", "six"))
                .parallelStream()
                .reduce(0, (num, str) -> {
                    num++;
                    System.out.println(Thread.currentThread().getName() + "-" + "accumulator-" + num + "-" + str);
                    return num;
                }, (num1, num2) -> {
                    System.out.println(Thread.currentThread().getName() + "-" + "combiner-" + num1 + "-" + num2);
                    return num1+num2;
                });
        System.out.println("r = " + r);
    }

    @Test
    public void testToMap() {
        List<User> users = new ArrayList<>();
        users.add(new User(1L,"zhangsan", 10));
        users.add(new User(2L,"lisi", 11));
        users.add(new User(1L,"zhangsan1", 12));
        users.add(new User(3L,"wangwu", 13));
        users.add(new User(1L,"zhangsan2", 14));

        Map<Long, String> userMap = users.stream().collect(Collectors.toMap(User::getId, User::getName, (previous, next) -> previous));
        for (Map.Entry<Long, String> entry : userMap.entrySet()) {
            System.out.println("id: " + entry.getKey() + ",name:" + entry.getValue());
        }
    }

    @Test
    public void testSpicing() {
        final String separator = ",";
        List<String> strings = Arrays.asList("1", "2", "3");
        String reduce = strings.stream().reduce("", (prev, next) -> prev + separator + next);
        String collect = strings.stream().collect(Collectors.joining(separator));
        System.out.println(reduce);
        System.out.println(collect);
    }

    @Test
    public void testFilterMap() {
        List<Number> strings = Arrays.asList(1, 2, 3);
        List<Integer> nums = strings.stream().filter(this::validate).map(Number::intValue).collect(Collectors.toList());
        System.out.println(nums);
    }

    private boolean validate(Number number) {
        return false;
    }

}
