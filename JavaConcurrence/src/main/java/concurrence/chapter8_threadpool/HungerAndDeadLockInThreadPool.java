package concurrence.chapter8_threadpool;

/*
 * 线程池中的饥饿与死锁
 */
public class HungerAndDeadLockInThreadPool {
	
	/*
	 * 在单线程的Executor中,如果在一个任务中提交了另一个任务,那么
	 * 就会发生死锁
	 * 原因在于Executor为一个任务t1分配了一个执行线程,当任务执行时,又
	 * 向该Executor提交了另一个任务t2,由于Executor无法再分配更多的线程,
	 * 因此提交的任务无法得到执行(加入到队列中等待t1占用的线程释放),而t1又需要t2执行的结果,
	 * 这时候死锁发生
	 */

}
