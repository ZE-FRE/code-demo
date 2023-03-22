package cn.zefre.queue;

import cn.zefre.queue.impl.PriorityQueue;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author pujian
 * @date 2023/3/21 9:38
 */
public class PriorityQueueTest {

    @Test
    public void testSort() {
        Integer[] arr = new Integer[] {22,24,48,1,38,4,8,7,81,64,14,17,38,14,4,2};
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Arrays.asList(arr));
        int size = priorityQueue.size();
        for (int i = 0; i < size; i++) {
            System.out.print(priorityQueue.poll() + " ");
        }
    }

}
