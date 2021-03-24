package dataStructure.linkedList;

public class SingleLinkedList {
    public static void main(String[] args) {
        HeroNode node01 = new HeroNode(4, "宋江", "及时雨");
        HeroNode node02 = new HeroNode(1, "卢俊义", "玉麒麟");
        HeroNode node03 = new HeroNode(3, "吴用", "智多星");
        HeroNode node04 = new HeroNode(2, "吴用", "智多星");
        HeroNode node05 = new HeroNode(2, "吕布", "奉先");
        HeroNode node06 = new HeroNode(1, "张飞", "翼德");
        HeroNode node07 = new HeroNode(5, "张飞", "翼德");
        LinkedListDemo demo = new LinkedListDemo();
        demo.addByOrder(node01);
        demo.addByOrder(node02);
        demo.addByOrder(node03);
        demo.addByOrder(node04);
        demo.addByOrder(node05);
        System.out.println(demo.head);
        demo.update(node06);
        demo.update(node07);
        System.out.println(demo.head);
        demo.delete(node01);
        System.out.println(demo.head);
        //求单链表的有效节点的个数
        System.out.println(demo.getLength(demo.head));
        //测试获取倒数第k个节点
        HeroNode lastIndexNode = demo.findLastIndexNode(demo.head, 2);
        System.out.println(lastIndexNode);
        //将单链表反转
        demo.reverse(demo.head);
        System.out.println(demo.head);

    }
}

class LinkedListDemo {

    //头节点，头节点不能动,头节点不存放数据
    public HeroNode head = new HeroNode(0, "", "");

    //添加节点到单向链表
    public void add(HeroNode heroNode) {
        //因为head节点不能动，因此我们需要一个辅助遍历temp
        HeroNode temp = head;
        //当退出while循环时，temp就指向了链表最后的节点
        while (true) {
            if (temp.next == null) {
                break;
            }
            temp = temp.next;
        }
        temp.next = heroNode;
    }

    //有序的添加
    public void addByOrder(HeroNode heroNode) {
        HeroNode temp = head;
        boolean flag = false;
        while (true) {
            if (temp.next == null) {
                break;
            }
            //temp的下一个节点的no大于要插入节点的no
            if (temp.next.no > heroNode.no) {
                break;
            } else if (temp.next.no == heroNode.no) {
                //temp的下一个节点的no等于要插入节点的no
                flag = true;
                break;
            }
            temp = temp.next;
        }
        //不能添加，编号已经存在
        if (flag) {
            System.out.println("此英雄的编号已经存在了：" + heroNode);
            return;
        }
        heroNode.next = temp.next;
        temp.next = heroNode;
    }

    //修改节点的信息，根据no修改
    public void update(HeroNode heroNode) {
        //判断是否为空
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        HeroNode temp = head;
        //表示是否找到了该节点
        boolean flag = false;
        while (true) {
            if (temp == null) {
                //链表已经遍历完毕
                break;
            }
            if (temp.no == heroNode.no) {
                System.out.println("找到了");
                flag = true;
                break;
            }
            temp = temp.next;
        }
        //根据flag判断是否找到要修改的节点
        if (flag) {
            temp.name = heroNode.name;
            temp.nickName = heroNode.nickName;
            return;
        }
        System.out.println("没有找到这个编号的节点，不能修改：" + heroNode);
    }

    //删除节点
    public void delete(HeroNode heroNode) {
        //判断是否为空
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        HeroNode temp = head;
        //表示是否找到了该节点
        boolean flag = false;
        while (true) {
            if (temp.next == null) {
                //链表已经遍历完毕
                break;
            }
            if (temp.next.no == heroNode.no) {
                System.out.println("找到了");
                flag = true;
                break;
            }
            temp = temp.next;
        }
        //根据flag判断是否找到要修改的节点
        if (flag) {
            temp.next = temp.next.next;
            return;
        }
        System.out.println("没有找到这个编号的节点，不能删除：" + heroNode);
    }


    //显示链表
    public void show() {
        //如果链表为空
        if (head.next == null) {
            return;
        }
        HeroNode temp = head.next;
        while (true) {
            if (temp == null) {
                break;
            }
            System.out.println(temp);
            temp = temp.next;
        }
    }

    //获取单链表的节点的个数，如果带头节点，则不需要统计头节点
    public int getLength(HeroNode node) {
        //空链表
        if (head.next == null) {
            return 0;
        }
        int length = 0;
        HeroNode cur = head.next;
        while (cur != null) {
            length++;
            cur = cur.next;
        }
        return length;
    }

    //查找单链表中的倒数第k个节点
    public HeroNode findLastIndexNode(HeroNode node, int index) {
        if (head.next == null) {
            return null;
        }
        //获取链表长度
        int length = getLength(node);
        //校验
        if (index > length || index <= 0) {
            return null;
        }
        HeroNode cur = head.next;
        for (int i = 0; i < length - index; i++) {
            cur = cur.next;
        }
        return cur;
    }

    //翻转单链表
    public void reverse(HeroNode node) {
        //链表为空或者只有一个节点
        if (node.next == null || head.next.next == null) {
            return;
        }
        HeroNode cur = head.next;
        HeroNode temp = null;
        HeroNode reverseHead = new HeroNode(0, "", "");
        while (cur != null) {
            temp = cur.next;//先暂时保存当前节点的下一个节点
            cur.next = reverseHead.next;//将cur节点的下一个节点指向新链表的最前端
            reverseHead.next = cur;//将cur连接到新的链表上
            cur = temp;
        }
        head.next = reverseHead.next;

    }

}

//定义节点
class HeroNode {
    public int no;
    public String name;
    public String nickName;
    //指向下一个节点
    public HeroNode next;

    public HeroNode(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

//    @Override
//    public String toString() {
//        return "HeroNode{" +
//                "no=" + no +
//                ", name='" + name + '\'' +
//                ", nickName='" + nickName +
//                '}';
//    }


    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", next=" + next +
                '}';
    }
}