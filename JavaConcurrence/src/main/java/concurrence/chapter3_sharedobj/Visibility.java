package concurrence.chapter3_sharedobj;

/*线程的可见性：当一个线程修改了某个对象的状态之后，其他线程
 * 能够看到发生的变化*/

/*在缺乏同步的情况下，编译器可能会对操作的执行顺序进行调整，
称之为“指令重排”*/
public class Visibility {
    
    private static boolean ready;
    private static int number;
    
    private static class ReaderThread extends Thread{
        
        @Override
        public void run() {
            while(!ready) 
                Thread.yield();
            System.out.println(number);
        }
    }
    
    public static void main(String[] args) {
        new ReaderThread().start();
        /*读线程看到的number和ready写入的顺序可能和主线程相反*/
        number = 42;
        ready = true;
    }
    
}
