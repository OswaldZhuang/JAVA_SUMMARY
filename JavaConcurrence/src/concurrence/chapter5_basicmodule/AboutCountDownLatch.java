package concurrence.chapter5_basicmodule;

import java.util.concurrent.CountDownLatch;

/*
 * 闭锁
 */
public class AboutCountDownLatch {
    /*
     * java.util.concurrent.CountDownLatch
     * 闭锁的主要功能在于其能够使得多个线程等待一个线程在完成某个操作之后一起执行
     * 闭锁是一次性的,因为它的count参数无法被重新设置(也就是说当count变成0之后该闭锁就无法再继续使用了)
     * CountDownLatch仍然使用AQS作为同步器,采用的是共享模式下的同步策略
     */
    
    /*
     * count其实就是QAS的state
     * tryAcquireShared是判断state是否为0,为0返回1,否则返回-1,state为0表示计数器已经计完数,线程可以向下运行(即await方法不阻塞)
     * CountDownLatch的await方法实际是调用AQS的acquireSharedInterruptibly(1)方法
     * countDown方法实际是调用的AQS的releaseShared(1)
     */
    
    /*
     * 具体来说
     * 执行CountDownLatch#await()时
     * 实际上是执行AQS#acquireSharedInterruptibly(1)
     * 而上述方法在AQS#tryAcquireShared(1)<0时(也就是state不为0时),执行
     * AQS#doAcquireSharedInterruptibly(1),该方法的执行流程如下:
     * 1.将当前线程加入等待队列(mode为SHARED)
     * 2.不断循环,在此期间,获得当前线程节点的前继,如果前继是head,再次查看tryAcquireShared(1),如果大于等于0(也就是state为0),
     * 那么调用AQS#setHeadAndPropagate(当前节点,tryAcquireShared的返回值)(这一步也是能够使得锁释放得到传播的关键),返回.如果前继不是head,那么
     * 判断(shouldParkAfterFailedAcquire(前驱, 当前节点) && parkAndCheckInterrupt()),如果前面的条件为false,那么后面
     * 的条件就不会执行,然后继续循环
     * 如果前面的条件为true,那么就会将当前的线程挂起,线程阻塞在该位置,直到别的线程将其唤醒
     * shouldParkAfterFailedAcquire的判断依据如下:
     * 前驱节点的状态是SIGNAL,则返回true,如果状态是CANCELLED,那么从后往前找到非CANCELLED的节点再连起来,返回false
     * 其余情况(0或者PROPAGATE),CAS将前驱状态设置为SIGNAL,返回false
     * 也就是说只要state大于0,并且节点不是head之后的节点,它最终是会被挂起,并且前驱的状态设置为SIGNAL
     * 
     */
    
    /*
     * 执行CountDownLatch#countDown()时
     * 实际上是执行AQS#releaseShared(1)
     * 在AQS#tryReleaseShared(1)为true的情况下,执行doReleaseShared()
     * 也就是说只要state不为0,countDown仅仅是将state减1,而state变为0之后才是真正的进行释放锁的操作
     * 
     * tryReleaseShared()方法CountDownLatch中实现,具体如下:
     * 不断循环,期间:首先查看state,如果为0,返回false(说明计数器归0,不需要释放),否则state值减1,再CAS设置,如果设置成功
     * 且当前state为0,返回true,否则,返回false
     * 
     * 也就是说如果state为0后,就可以释放等待的线程了,doReleaseShared()执行流程如下:
     * 不断循环,在此期间,首先获得head,如果head的状态是SIGNAL,那么CAS设置其状态为0并且唤醒它的后继节点,如果是0,那么CAS设置为
     * PROPAGATE
     */
    
    public void useCountDownLath() throws InterruptedException {
        /*
         * 闭锁的使用
         */
        CountDownLatch latch = new CountDownLatch(3);
        
        for(int i = 0; i<3; i++) {
            new Thread(new Worker(latch)).start();
        }
        
        latch.await();//主线程等所有线程执行完成之后再执行
        doSomethingElse();
    }
    
    public void doSomethingElse() {
        System.out.println("等待之前的线程执行完毕,执行主线程业务逻辑");
    }
    
    class Worker implements Runnable{
        private CountDownLatch latch;
        
        public Worker(CountDownLatch latch) {
            super();
            this.latch = latch;
        }

        @Override
        public void run() {
            doSomething();
            latch.countDown();
        }
        
        private void doSomething() {
            //业务逻辑
            System.out.println(Thread.currentThread().getName()+" is doing something");
            try {
                Thread.currentThread().sleep(2000000000000000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
        
    }
    
    public static void main(String[] args) throws Exception{
        AboutCountDownLatch acdl = new AboutCountDownLatch();
        acdl.useCountDownLath();
    }
}
