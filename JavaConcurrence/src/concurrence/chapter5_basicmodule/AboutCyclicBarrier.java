package concurrence.chapter5_basicmodule;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/*
 * 线程栅栏
 * CyclicBarrier会让一系列的线程等待在共同的栅栏点上,只有所有的线程到达了才能释放栅栏
 * CyclicBarrier是可以重复(循环)利用的
 * CyclicBarrier可支持一个runnable,该runnable在最后一个线程到达之后,所有的线程释放之前运行
 * 如果其中一个线程过早的离开栅栏点(或许因为timeout,或者因为中断),那么其余的线程就会抛出
 * BrokenBarrierException或者InterruptedException
 */
public class AboutCyclicBarrier {
	
	/*
	 * CyclicBarrier定义了内部类Generation,该类仅有一个boolean属性broken,
	 * Generation用于表示CyclicBarrier的代(这也体现了"栅栏"的可重复使用)
	 * 
	 * public CyclicBarrier(int parties, Runnable barrierAction)构造函数
	 * 
	 *  private final ReentrantLock lock = new ReentrantLock(); lock用于保护栅栏只能被独占的访问
	 *  
	 *  private final Condition trip = lock.newCondition();
	 *  private final int parties; 表示需要通过栅栏的总线程数量
	 *  private final Runnable barrierCommand;
	 *  private Generation generation = new Generation();
	 *  private int count; 表示尚未到达栅栏的线程数量
	 *  private void nextGeneration() 该方法会唤醒所有在trip上等待的线程,重置count,新创建Generation对象
	 *  private void breakBarrier() 该方法会释放栅栏,具体来说会将Generation中的broken设置为true,重置count,唤醒
	 *  在trip上等待的线程
	 *  
	 *  
	 *   private int dowait(boolean timed, long nanos) hrows InterruptedException, BrokenBarrierException,TimeoutException
	 *   该方法是CyclicBarrier的核心方法,主要用于将线程阻塞在栅栏点上
	 *   大致的执行过程为:
	 *   首先lock.lock,然后检查generation,如果broke属性为true,那么就会抛出BrokenBarrierException,
	 *   如果线程被中断,那么首先会设置broke状态,然后抛出InterruptedException
	 *   随后检查count,如果为0(也就说明全部的线程都到了了栅栏点),那么就会执行barrierCommand(如果有的话),再调用nextGeneration,返回0
	 *   如果count不为0(即还有没到达栅栏的线程),则不断循环,期间调用trip.await将线程挂起(挂起的时候会将lock释放掉,这样其他线程就能
	 *   获取到锁),假如最后一个线程通过栅栏点,那么线程就会唤醒并继续执行(这时候会重新获得锁),继续执行,检查generation的状态,操作同上
	 *   然后还会检查generation是否还是原来的(因为在最后一个线程调用await方法会将创建新的generation),如果不是,那么立即返回尚未到达
	 *   栅栏点的线程数
	 *   最后,释放lock
	 */
	
	public static void main(String[] args) throws Exception{
		CyclicBarrier barrier = new CyclicBarrier(2);
		new Thread(() ->  {
			try {
				barrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		}).start();
		barrier.await();
	}
	
}
