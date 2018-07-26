package concurrence.chapter5_basicmodule;

/*
 * 异步执行结果
 */
public class AsyncExecutionResult {
    
    /*
     * Interface java.util.concurrent.Future<T>
     * Future表示异步执行的结果,
     * V get() throws InterruptedException, ExecutionException表示获取异步执行的结果
     * 该方法是阻塞的,如果结果并没有完成,那么get就会阻塞直到结果完成或者中断
     * boolean cancel(boolean mayInterruptIfRunning) 试图去取消该Future,mayInterruptIfRunning
     * 表示是否要中断正在执行中的任务,一旦任务完成,就无法取消
     * boolean isDone() 查看任务是否已经完成
     */
    
    /*
     * Class java.util.concurrent.CompletableFuture<T> 
     */
    
    /*
     * Interface java.util.concurrent.ScheduledFuture<V> extends Delayed, Future<V>
     * 
     */
}
