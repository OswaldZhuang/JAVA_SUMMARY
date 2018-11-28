package vm.classLoad;


public class AboutClass {
    /**
     * java.lang.Class
     * Class的实例代表Java程序中运行的类或者接口
     * enum是一种类,注解是一种接口.每个数组同样属于一个类(也就是Class对象)且相同数据类型和长度
     * 的数组是同一个类.java的基本类型以及void同样也是Class的对象.
     * Class没有public的构造器,Class对象(也就是自定义的类)在类被加载的时候通过虚拟机自动构造(调用类加载器的defineClass方法)
     */

    /**
     * public static Class<?> forName(String className) throws ClassNotFoundException
     * 该方法返回className对应的Class对象,即将相应的类加载到虚拟机中(如果事先没有被加载的话)
     * 调用该方法会使得className对应的类被初始化
     * public static Class<?> forName(String name, boolean initialize, ClassLoader loader) throws ClassNotFoundException
     * 方法效果同上,如果loader为null的话就会调用Bootstrap进行加载
     * public T newInstance() throws InstantiationException, IllegalAccessException
     * 该方法返回类的实例,等同于调用无参数的构造器(实际上就是Constructor#newInstance)，该方法会传播由无参数的构造器抛出的异常
     * public native boolean isAssignableFrom
     *
     * public ClassLoader getClassLoader()
     * 该方法返回加载这个类的类加载器,如果为null,表明该类被BootstrapClassLoader加载
     * /

    public static void main(String[] args) throws Exception{
        Class string_class = Class.forName("java.lang.String", true, AboutClass.class.getClassLoader()/*null*/);
        System.out.println(string_class == String.class);
        System.out.println(AboutClass.class.getClassLoader());
    }
}
