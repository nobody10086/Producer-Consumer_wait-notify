import java.util.LinkedList;
import java.util.List;

public class Storage {
    int max_Storage_Size = 10; // 仓库容量
    private LinkedList<Object> list = new LinkedList<>(); //仓库存储的载体

    //生产者
    public void producer() {
        synchronized (list) {

            //判断仓库是否已满
            while (list.size() >= max_Storage_Size) {
                System.out.println(
                        "[ The Storage is full.\t" + "The Produce " + Thread.currentThread().getName() + "has worked done temporarily. ]"
                );
                try {
                    list.wait(); //当缓冲区已满时，生产者线程停止执行，放弃锁，使自己处于等状态，让其他线程执行；
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }

            //仓库未满时，生产者生产一个产品导入仓库
            list.add(new Object());
            System.out.println(
                    "The producer " + Thread.currentThread().getName() + " has added one production.And now the Storage has " + list.size()
            );
            list.notifyAll(); //当生产者向缓冲区放入一个产品时，向其他等待的线程发出可执行的通知，同时放弃锁，使自己处于等待状态；
        }
    }

    //消费者
    public void consumer() {
        synchronized (list) {

            //判断仓库是否为空
            while (list.size() == 0) {
                System.out.println(
                        "[ The Storage is empty.\t" + "The consumer " + Thread.currentThread().getName() + " has worked done temporarily. ]"
                );
                try {
                    list.wait(); //当缓冲区已空时，消费者线程停止执行，放弃锁，使自己处于等状态，让其他线程执行
                } catch (InterruptedException interruptedException) {interruptedException.printStackTrace();
                }
            }

            //当消费者从缓冲区取出一个产品时，向其他等待的线程发出可执行的通知，同时放弃锁，使自己处于等待状态
            list.remove();
            System.out.println(
                    "The consumer " + Thread.currentThread().getName() + " has removed one production.And now the Storage has " + list.size()
            );
            list.notifyAll(); //当消费者从缓冲区取出一个产品时，向其他等待的线程发出可执行的通知，同时放弃锁，使自己处于等待状态。
        }
    }
}