package concurrence.init;

/*延迟初始化占位类模式*/
public class LazyInitHolderClass {
    
    /*JVM会推迟ResourceFactory的初始化操作，直到开始
     * 使用这个类时才进行初始化*/
    private static class ResourceHolder{
        public static Resource resource = new Resource();
    }
    /*任何线程在第一次调用getInstance时，ResourceHolder才会被加载和初始化，
     * 此时Resource将被初始化*/
    public static Resource getInstance() {
        return ResourceHolder.resource;
    }

}
