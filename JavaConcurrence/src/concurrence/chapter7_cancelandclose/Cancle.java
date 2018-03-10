package concurrence.chapter7_cancelandclose;

/*
 * 取消
 */
public class Cancle {
    /*
     * 任务的取消,通常的做法是设置一个
     * 标志位,在执行任务的时候不断的轮询
     * 这个标志位以确定当前的任务是否要执行
     */
    class CancellableTask implements Runnable{
        
        private volatile boolean cancelled = false;
        
        @Override
        public void run() {
            while(!cancelled) {
                //do something
            }
        }
        
        public void cancel() {
            cancelled = true;
        }
    }
    /*
     * 然而,上述方法会有个问题:如果run方法的里的代码是阻塞的,那么
     * 任务就可能永远都没办法取消(在代码阻塞在run方法里的同时其他线程
     * 将cancelled设置为true)
     */
    
}
