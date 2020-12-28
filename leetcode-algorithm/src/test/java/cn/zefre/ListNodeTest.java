package cn.zefre;

import cn.zefre.entity.ListNode;
import org.junit.Test;

/**
 * @author pujian
 * @date 2020/12/7 9:12
 */
public class ListNodeTest {

    @Test
    public void testHeadConstruct() {

        ListNode node = ListNode.rearConstruct(new int[]{1, 3, 5, 7});
        ListNode.printList(node);
    }

    @Test
    public void testOddEvenList() {
        int[] arr = new int[10];
        for(int i = 0; i < arr.length; i++) {
            arr[i] = i+1;
        }
        ListNode head = ListNode.rearConstruct(arr);
        System.out.println("my implement:");
        System.out.print("原始链表：");
        ListNode.printList(head);
        ListNode.oddEvenList(head);
        System.out.print("\n处理后的链表：");
        ListNode.printList(head);

        ListNode headE = ListNode.rearConstruct(arr);
        System.out.println("\nleetcode implement:");
        System.out.print("原始链表：");
        ListNode.printList(headE);
        ListNode.oddEvenListExample(headE);
        System.out.print("\n处理后的链表：");
        ListNode.printList(headE);

    }

    @Test
    public void testRemoveNthFromEnd() {
        int[] arr = new int[10];
        for(int i = 0; i < arr.length; i++) {
            arr[i] = i+1;
        }
        ListNode head = ListNode.rearConstruct(arr);

        ListNode listNode = ListNode.removeNthFromEnd(head, 10);
        ListNode.printList(listNode);
    }
}
