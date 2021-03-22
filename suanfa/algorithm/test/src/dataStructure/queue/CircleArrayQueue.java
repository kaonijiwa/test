package dataStructure.queue;

public class CircleArrayQueue {
    public static void main(String[] args) {
        CircleArray circleArray = new CircleArray(3);
        circleArray.addQueue(1);
        circleArray.addQueue(2);
        circleArray.addQueue(3);
        circleArray.showQueue();
    }
}

//数组模拟队列,只能用一次，因为front位置变化了
class CircleArray {
    //数组最大容量
    private int maxSize;
    //队列头
    private int front;
    //队列尾
    private int rear;
    //模拟队列
    private int[] arr;

    //创建队列的构造器，初始化队列
    public CircleArray(int arrMaxSize) {
        maxSize = arrMaxSize;
        arr = new int[maxSize];
        front = 0;
        rear = 0;
    }

    //判断队列是否满了
    public Boolean isFull() {
        return (rear + 1) % maxSize == front;
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
        arr[rear] = num;
        rear = (rear + 1) % maxSize;
    }

    //获取队列的数据,出队列
    public int getQueue() {
        //判断队列是否为空
        if (isEmpty()) {
            throw new RuntimeException("队列为空，没有数据");
        }
        //1.先将front保存到一个变量
        //2.将front后移
        int temp = arr[front];
        front = (front + 1) % maxSize;
        return temp;
    }

    //显示队列的所有数据
    public void showQueue() {
        if (isEmpty()) {
            System.out.println("队列为空，没有数据");
            return;
        }
        //从front开始遍历,遍历多少个元素
        for (int i = front; i < front + size(); i++) {
            System.out.printf("arr[%d]=%d\n", i % maxSize, arr[i % maxSize]);
        }
    }

    //求出当前队列有效数据
    public int size() {
        return (rear + maxSize - front) % maxSize;
    }

}