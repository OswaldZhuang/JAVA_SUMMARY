package concurrence.chapter5_basicmodule;

/*
 * 条件队列
 * 指的是一组线程通过某种方式等待某些条件为真
 * 既然涉及到线程的等待(或者说阻塞),那么一定是需要获得相关
 * 的对象的锁
 */
public class ConditionalQueue {
    /*
     * 在java.lang.Object类中预定义了一些条件队列的方法
     *  1.public final void wait() throws InterruptedException,
     *    该方法的底层实现是本地方法,调用该方法时,会把当前对象的锁释放掉(这也是为什么需要对象的锁),
     *    并挂起该线程(实际上将等待该对象的线程放入到条件队列中),
     *    也就是说会阻塞在wait()方法
     *  2.public final void wait(long timeout, int nanos) throws InterruptedException
     *  3.public final native void wait(long timeout) throws InterruptedException
     *  4.public final native void notify()
     *    该方法用于通知条件队列上的线程,调用该方法前必须持有对象的锁,调用该方法之后会将锁释放掉以便等待的线程能够重新获得锁
     *    该方法会选择一个线程进行通知,然而该方法一般是不推介使用的,因为具有一定的随机性
     *  5.public final native void notifyAll()
     *    该方法作用同上,不过是通知所有在(持有的锁对应的对象的)条件队列中等待的线程
     */
    
    class BankAcocunt{
        private double money;
        private static final double money_over_head = 10000000;
        public synchronized void withdraw(double withdraw_money) throws InterruptedException {
            /*
             * 为了防止假唤醒(过早唤醒)的情况,必须使用循环来不断的检查条件
             * 原因是线程被唤醒(即wait方法继续不再阻塞向下继续执行)的时候条件不一定成立
             * 1.如果notifyAll方法的调用并不是针对当前的条件,那么当前条件下的wait就不能被唤醒
             * 2.在针对当前条件的notifyAll后,wait方法开始唤醒,但在wait方法获得锁之前当前条件又
             * 被其他线程改变了,wait方法再拿到锁之后条件变得不成立,此时wait也不能被唤醒
             * 综上所述,在使用条件队列的时候,判断条件是否成立应该用while而不是if
             */
            while(isAccountEmpty(withdraw_money)) {
                this.wait();
            }
            money -= withdraw_money;
            this.notifyAll();
            
        }
        public synchronized void saveMoney(double saved_money) throws InterruptedException {
            while(isAccountFull(saved_money)) {
                this.wait();
            }
            money += saved_money;
            this.notifyAll();
        }
        public boolean isAccountEmpty(double withdraw_money) {
            return money - withdraw_money <= 0;
        }
        public boolean isAccountFull(double saved_money) {
            return money + saved_money >= money_over_head;
        }
    }
}
