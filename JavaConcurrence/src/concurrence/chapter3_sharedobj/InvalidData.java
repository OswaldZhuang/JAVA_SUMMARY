package concurrence.chapter3_sharedobj;

public class InvalidData {
    
    /*当一个线程调用了set更新num后，另一个线程调用get去访问这个num，
                那么，这个线程可能会看到更新的num，也可能看不到，因此，需要对这两个方法
                进行同步.(之所以也要对get进行同步，是因为必须保证读写操作不能同时进行)*/
    private static class SycInteger{
        private int num;
        
        /*另一种简单的实现方案就是对num加关键字volatile*/
        
        public synchronized void set(int i) {
            num = i;
        }
        
        public synchronized  int get() {
            return num;
        }
    }

}
