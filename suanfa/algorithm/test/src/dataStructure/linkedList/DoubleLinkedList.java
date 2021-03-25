package dataStructure.linkedList;

public class DoubleLinkedList {

    public static void main(String[] args) {
        HeroNode2 node01 = new HeroNode2(1, "潘凤", "无双");
        HeroNode2 node02 = new HeroNode2(2, "邢道荣", "上将");
        HeroNode2 node03 = new HeroNode2(3, "刘备", "玄德");
        DoubleLinkedDemo demo = new DoubleLinkedDemo();
        demo.add(node01);
        demo.add(node02);
        demo.add(node03);
        demo.list();
        demo.delete(3);
        demo.list();

    }

}

//创建一个双向链表的类
class DoubleLinkedDemo {

    private HeroNode2 head = new HeroNode2(0, "", "");

    //遍历双向链表
    public void list() {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        HeroNode2 temp = head.next;
        while (true) {
            if (temp == null) {
                return;
            }
            System.out.println(temp);
            temp = temp.next;
        }
    }

    //添加到双链表
    public void add(HeroNode2 node) {
        HeroNode2 temp = head;
        while (true) {
            if (temp.next == null) {
                break;
            }
            temp = temp.next;
        }
        temp.next = node;
        node.pre = temp;
    }

    //从双向链表中删除一个节点
    public void delete(int no) {

        //判断当前链表是否为空
        if (head.next == null) {
            System.out.println("链表为空，不能删除");
            return;
        }
        HeroNode2 temp = head.next;
        boolean flag = false;
        while (true) {
            //已经到链表的最后
            if (temp == null) {
                break;
            }
            if (temp.no == no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        //找到该节点
        if (flag) {
            //如果temp是最后一个节点
            if (temp.next != null) {
                temp.next.pre = temp.pre;
            }
            temp.pre.next = temp.next;
        } else {
            System.out.printf("要删除的节点不存在：%s", temp);
        }

    }

}

//定义节点
class HeroNode2 {
    public int no;
    public String name;
    public String nickName;
    //指向下一个节点
    public HeroNode2 next;
    //指向前一个节点
    public HeroNode2 pre;

    public HeroNode2(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "HeroNode2{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }

}