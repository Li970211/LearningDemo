package main.java.juc.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author 李嘉
 * @create 2020-05-30-10:01
 *
 * 1.标准访问，先打印邮件还是短信          邮件，短信
 * 2.邮件方法暂停4秒，先打印邮件还是短信    邮件，短信
 * 3.新增普通方法sayHello,是先打印邮件还是hello   hello,邮件
 * 4.两部手机，请问先打印邮件还是短信        短信，邮件
 * 5.两个静态方法，1个手机，先打印邮件还是短信    邮件，短信
 * 6.两个静态方法，2个手机，先邮件还是短信   邮件，短信
 * 7.1静态方法，1普通同步方法，一个手机  短信，邮件
 * 8.1静态方法，1普通同步方法，2个手机  短信，邮件
 *
 */
public class TestLock {

    public static void main(String[] args) {
        Phone phone1 = new Phone();
        Phone phone2 = new Phone();
        new Thread(()->{
            phone1.sendEmail();
        },"线程1").start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
//            phone1.sendSMS();
//            phone1.sayHello();
//            phone1.sendSMS();
//            phone1.sendSMS();
            phone2.sendSMS();
        },"线程2").start();

    }
}
class Phone{
    public  synchronized void sendSMS() {
        System.out.println("send SMS");
    }

//    public  synchronized void sendEmail(){
//        try {
//            TimeUnit.SECONDS.sleep(4);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("send Email");
//    }

//    public   void sendSMS() {
//        synchronized (Phone.class){
//
//            System.out.println("send SMS");
//        }
//    }

    public   void sendEmail(){
        synchronized (Phone.class){
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("send Email");
        }

    }

    public  void sayHello(){
        System.out.println("send sayHello");
    }
}