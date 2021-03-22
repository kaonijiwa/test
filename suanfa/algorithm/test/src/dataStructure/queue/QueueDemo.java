package dataStructure.queue;

public class QueueDemo {

    // 1.队列是一个有序列表,可以用数组或者链表实现
    // 2.先进先出
    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue(3);
        queue.addQueue(2);
        queue.addQueue(3);
        queue.addQueue(4);
        queue.addQueue(5);
        queue.showQueue();
        System.out.println(queue.getQueue());
        System.out.println(queue.getQueue());
        System.out.println(queue.getQueue());
        System.out.println(queue.getQueue());

    }
}

//数组模拟队列,只能用一次，因为front位置变化了
class ArrayQueue {
    //数组最大容量
    private int maxSize;
    //队列头
    private int front;
    //队列尾
    private int rear;
    //模拟队列
    private int[] arr;

    //创建队列的构造器，初始化队列
    public ArrayQueue(int arrMaxSize) {
        maxSize = arrMaxSize;
        arr = new int[maxSize];
        //指向队列的头部,队列头前一个位置
        front = -1;
        //指向队列尾的数据(就是队列最后一个数据)
        rear = -1;
    }

    //判断队列是否满了
    public Boolean isFull() {
        return rear == maxSize - 1;
    }

    //判断队列是否为空
    public Boolean isEmpty() {
        return front == rear;
    }

    //添加数据到队列
    public void addQueue(int num) {
        //判断队列是否已经满了
        if (isFull()) {
            System.out.println("队列已经满了，不能加入数据");
            return;
        }
        rear++;
        arr[rear] = num;
    }

    //获取队列的数据,出队列
    public int getQueue() {
        //判断队列是否为空
        if (isEmpty()) {
            throw new RuntimeException("队列为空，没有数据");
        }
        front++;
        return arr[front];
    }

    //显示队列的所有数据
    public void showQueue() {
        if (isEmpty()) {
            System.out.println("队列为空，没有数据");
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("arr[%d]=%d\n", i, arr[i]);
        }
    }
}
