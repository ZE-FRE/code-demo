package cn.zefre.entity;

/**
 * @author pujian
 * @date 2020/11/14 10:39
 */
public class ListNode {

    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }

    public static ListNode rearConstruct(int[] arr) {
        if(null == arr)
            throw new IllegalArgumentException("the arr is null");
        ListNode head = new ListNode();
        ListNode worker = head;
        for (int num : arr) {
            ListNode newNode = new ListNode(num);
            worker.next = newNode;
            worker = newNode;
        }
        return head.next;
    }

    public static void printList(ListNode head) {
        ListNode p = head;
        while (null != p) {
            System.out.print(p.val + " ");
            p = p.next;
        }
    }

    public static ListNode oddEvenList(ListNode head) {
        if(null == head)
            return null;
        // 链表长度
        int size = 1;
        // 尾指针
        ListNode rear = head;
        while(null != rear.next) {
            size++;
            rear = rear.next;
        }
        if(size <= 2)
            return head;
        int moveTimes = size >> 1;
        // 指向奇数节点编号的工作指针
        ListNode oddPointer = head;
        // 指向偶数节点编号的工作指针
        ListNode evenPointer = head.next;
        for(int i = 0; i < moveTimes; i++) {
            oddPointer.next = evenPointer.next;
            // 移动奇数工作指针
            oddPointer = evenPointer.next;
            // 断开原本偶数编号节点与奇数编号节点的连接
            evenPointer.next = null;
            // 将偶数编号节点移动到末尾
            rear.next = evenPointer;
            rear = evenPointer;
            // 移动偶数工作指针
            evenPointer = oddPointer.next;
        }
        return head;
    }

    public static ListNode oddEvenListExample(ListNode head) {
        if (null == head)
            return null;

        ListNode odd = head, oddHead = head, even = head.next, evenHead = head.next;
        while (null != even && null != even.next) {
            odd.next = even.next;
            odd = even.next;
            even.next = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return oddHead;
    }

    /*
     * 给定一个链表: 1->2->3->4->5, 和 n = 2.
     * 当删除了倒数第二个节点后，链表变为 1->2->3->5.
     * 1.n保证有效
     */
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode p = head;
        int size = 1;
        while (null != p.next) {
            size++;
            p = p.next;
        }
        p = head;
        int index = size - n;
        if(index <= 0) {
            head = head.next;
            p.next = null;
            return head;
        }

        while (--index > 0) {
            p = p.next;
        }
        ListNode removeNode = p.next;
        p.next = p.next.next;
        removeNode.next = null;
        return head;
    }
}
