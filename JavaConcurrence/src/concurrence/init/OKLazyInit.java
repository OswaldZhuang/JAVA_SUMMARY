package concurrence.init;

public class OKLazyInit {
    
    private OKLazyInit okLazyInit = null;
    
    /*在初始化对象的时候采取同步的方式可以避免不一致性(竞态条件)
     * 但是对于多个线程同时调用该方法，会产生低效率的问题*/
    private synchronized OKLazyInit getInstance() {
        if(okLazyInit == null) {
            okLazyInit = new OKLazyInit();
        }
        return okLazyInit;
    }

}
