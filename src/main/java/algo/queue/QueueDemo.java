package main.java.algo.queue;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 队列是一个有序列表，可以用数组或者是链表来实现；
 * 队列遵循先入先出的原则，即先存入队列的数据，要先
 * 取出，后存入的要后取出。
 */
public class QueueDemo {
    public static void main(String[] args) throws Exception {

        //使用数组来模拟队列
        ArrayQueue arrayQueue = new ArrayQueue(5);
        new Thread(()->{

            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName()+"\t 将元素"+ i+ "放入队列");
                arrayQueue.add(i);
                System.out.println(Thread.currentThread().getName()+"\t"+arrayQueue.toString());
                arrayQueue.showQueue();
            }
        },"线程1").start();

        Thread.sleep(10);

        new Thread(()->{
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName()+"\t 移除队列元素");
                arrayQueue.remove();
                System.out.println(Thread.currentThread().getName()+"\t"+arrayQueue.toString());
                arrayQueue.showQueue();
            }
        },"线程2").start();
    }
}
class ArrayQueue{
   private volatile int size;
    //队列队首指针
   private volatile int front;
    //队列队尾指针
   private volatile int rear;
   private volatile Object[] array;

    public ArrayQueue(int size) {
        this.size = size + 1;
        this.array = new Object[this.size];
    }

    public  void add(Object object){
        if (isFull()) {
            System.out.println("队列已满");
            return;
        }
         this.array[this.rear] = object;
        //real需要后移动一位，当rear为5即队列第一次已满，需要+1（预留位）%size
        this.rear = (this.rear + 1) % (this.size);
    }

    public  Object remove( ){
        if (isEmpty()) {
            System.out.println("队列为空");
            return null;
        }
        Object object = this.array[this.front];
        //front为数组下标，每删除一个元素，下标后移一个，由于真实数组的长度为size-1,则取模时需要size-1,使用真实的数组大小
        this.front = (this.front +1) %  (this.size -1);
        return object;
    }

    public  boolean  isFull(){
        return this.front == this.rear + 1 || this.rear + 1 == this.front + this.size;
    }

    public boolean  isEmpty(){
        return this.front == this.rear;
    }

    public void showQueue(){
        for (int i = 0; i < getQueueSize(); i++) {
            System.out.println();
            System.out.printf(Thread.currentThread().getName() + "---array[%d] = %d",i , this.array[(i+this.front)%(this.size)]);
            System.out.println();
        }
    }

    public int getQueueSize(){
        return (this.rear + this.size - this.front) % (this.size);
    }

    @Override
    public String toString() {
        return "ArrayQueue{" +
                "front=" + front +
                ", rear=" + rear +
                ", array=" + Arrays.toString(array) +
                '}';
    }
}

//class ArrayQueue{
//    //队列大小
//    private Integer size;
//    //队列输入指针位置
//    private AtomicInteger rear = new AtomicInteger(0);
//    //数组
//    private volatile Object[] array;
//
//    public ArrayQueue(int size) {
//        this.size = size;
//        this.array = new Object[this.size];
//    }
//
//    public ArrayQueue(){
//        this.size = 0;
//        this.array = new Object[this.size];
//    }
//
//    public boolean addItemToQueue(Object object){
//        if (isFull()){
//            System.out.println("队列已满");
//            return false;
//        } else if (this.rear.get() <= this.array.length){
//            this.array[this.rear.get()] = object;
//            this.rear.getAndIncrement();
//            return true;
//        }
//        return false;
//    }
//
//    public Object pollItemToQueue() {
//        if (isEmpty()) {
//            System.out.println("队列为空");
//            return null;
//        }
//        Object object = this.array[0];
//        updateQueueContent();
//        return object;
//    }
//
//
//    public void removeItemToQueue() {
//        if (isEmpty()) {
//            System.out.println("队列为空");
//            return;
//        }
//        updateQueueContent();
//    }
//
//    private void updateQueueContent() {
//        for (int i = 0; i < this.array.length; i++) {
//            if (i < this.rear.get() - 1){
//                this.array[i] = this.array[i+1];
//            } else {
//                this.array[i] = null;
//            }
//        }
//        this.rear.getAndDecrement();
//    }
//
//
//    public boolean isEmpty(){
//        if (0 == this.rear.get()){
//            return true;
//        }
//        return false;
//    }
//
//    public boolean isFull(){
//        if (this.rear.get() == this.array.length){
//            return true;
//        }
//        return false;
//    }
//
//
//    @Override
//    public String toString() {
//        return "ArrayQueue{" +
//                "obj=" + Arrays.toString(array) +
//                "real=" + this.rear +
//                '}';
//    }
//}