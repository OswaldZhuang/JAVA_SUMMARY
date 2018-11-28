package vm.classLoad;

public class AboutClassLoader {
    /**
     * abstract class java.lang.ClassLoader
     *
     * Class#getClassLoader()能够得到加载该类的类加载器
     * 数组类型并非由CLassLoader加载,而是在运行期间自动创建.当数组类调用getClassLoader时,其返回类型为
     * 数组的元素类型的类加载器
     * (比如String[] str = new String[4]; str.getClass().getClassLoader的返回值为String.Class.getClassLoader)
     * 但是如果数组元素类型为基本类型的话就没有类加载器
     * 继承ClassLoader的类一般是为了重写加载类的方式
     * ClassLoader采用了一种委托(delegate)的模式来搜索类和资源.每一个ClassLoader实例都有一个相关联的双亲类加载器.
     * 当需要寻找类或者资源的时候,ClassLoader会首先将搜索任务委托给他的双亲类加载器.虚拟机内置的类加载器叫做BootstrapClassLoader,
     * 它没有双亲类加载器,但它可以作为别的类加载器的双亲.
     * 支持并发加载类的类加载器称之为"可并行(parallel capable)类加载器",并且需要在类初始化的时候通过ClassLoader.registerAsParallelCapable
     * 来注册成为可并行类加载器.ClassLoader类默认注册为可并行类加载器,而子类需要显式的调用上述方法注册
     * 在委托模式不是严格的层级化的环境下,类加载器需要是可并行的,否则类加载可能会引起死锁,因为类加载器的锁在类加载过程中被持有.(详见loadClass方法)
     * 一般的,JVM从本地文件系统加载类的方式是平台相关的.UNIX系统中,类加载从CLASSPATH中加载类.
     * 由一个类加载器创建的对象的方法和构造器可能会引用其他类,为了决定引用哪个类,JVM调用原始创建那个类的类加载器的loadClass方法.
     * (比如说类A由类加载器M加载,而类A的方法在运行时引用了类B,那么加载类B的时候也是由M加载的)
     */

    /**
     * JVM中的类加载器有如下类型:
     * BootStrapClassLoader:该加载器为最顶层的类加载器,主要用来加载java的核心类,比如rt.jar,resources.jar,charsets.jar
     * sun.misc.Launcher.ExtClassLoader:该类加载器主要负责加载java的扩展类库,即JAVA_HOME/jre/lib/ext/下的所有jar包(System.getProperty("java.ext.dirs"))
     * sun.misc.Launcher.AppClassLoader/SystemClassloader:该类加载器用于加载CLASSPATH下的类
     * UserClassLoader:自定义类加载器
     * UserClassLoader -> AppClassLoader -> ExtClassLoader  BootStrapClassLoader
     */

    /**
     * 产生上述层级关系的原理如下:
     * BootStrapClassLoader会加载核心类sun.misc.Launcher,而在Launcher的构造器中,会调用Launcher.ExtClassLoader.getExtClassLoader()
     * 来获得ExtClassLoader(实际上创建该类加载器的时候传入的parent是null),之后又通过Launcher.AppClassLoader.getAppClassLoader(extClassLoader)
     * 来获得AppClassLoader,实际上该方法返回的是Launcher.AppClassLoader,传入的参数为System.getProperty("java.class.path")和ExtClassLoader实例
     */

    /**
     * 为什么使用委托模式来加载类?
     * 首先是为了安全,不同种类的类只能由特定的类加载器去加载
     * 二是避免重复加载,如果一个类父类加载器已经加载过了,那么子类加载器再去加载的时候可以避免重复加载(因为会委托给父类加载器检查并加载)
     */

    /**
     * ClassLoader的属性和方法如下:
     * private final ClassLoader parent; 类加载器的双亲
     * private static class ParallelLoaders
     * private final ConcurrentHashMap<String, Object> parallelLockMap;如果当前ClassLoader是parallel capable,该属性存储
     * 类名和锁的映射
     * private final HashMap<String, Package> packages = new HashMap<>();包信息
     * protected ClassLoader() 无参数的构造器,实际上该构造器会将SystemClassLoader作为双亲类加载器
     * protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException 核心的类加载方法
     * 首先锁住相关对象,(如果类加载器不是可并行的,那么锁住的对象就是类加载器本身,否则根据类名从parallelLockMap查找相关的类名对应
     * 的锁),第一步,检查该类是否已经被加载过了,如果没有,那么检查是否有双亲类加载器,如果有的话就交给双亲类加载器加载类(实际上这是个递归的
     * 过程),没有双亲类加载器的话,就交给BootstrapClassLoader去加载.如果上述过程加载不成功,那么调用findClass方法去加载类
     * protected Class<?> findClass(String name) throws ClassNotFoundException 该方法没有具体实现,只要目的是让子类去重写
     * 该方法来定义自己的加载方式
     * protected final Class<?> defineClass(String name, byte[] b, int off, int len, ProtectionDomain protectionDomain)
     * throws ClassFormatError 将字节数组转化为类,如果protectionDomain为空,默认的domain会分配给该class,ProtectionDomain
     * 有效的保证了当调用java.security.Policy#getPermissions(java.security.CodeSource)的时候返回一系列相同的permissions
     * 第一个定义在包中的类决定了接下来所有在这个包的类的certificates.类的certificates被包含在java.security.CodeSource中(而
     * CodeSource又被包含在ProtectionDomain中),name不能包含java.*,因为所有的在java.*包中的类只能被BootstrapClassLoader定义
     * 具体判断逻辑详见preDefineClass方法
     * protected final Class<?> defineClass(String name, java.nio.ByteBuffer b, ProtectionDomain protectionDomain)
     * throws ClassFormatError 将ByteBuffer转化为类,原理与上类似
     * private native void resolveClass0(Class<?> c);在一个类可被使用之前必须进行该操作,该操作主要用于链接类,
     * protected static boolean registerAsParallelCapable() 将调用者注册为可并行的
     * private static ClassLoader scl; 系统的类加载器
     * public static ClassLoader getSystemClassLoader() 获取系统类加载器(该类加载器用于加载CLASSPATH下的类),
     * 此方法调用时会调用initSystemClassLoader
     * private static synchronized void initSystemClassLoader() 初始化系统类加载器,实际上获得系统类加载器是通过
     * sun.misc.Launcher.getLauncher().getClassLoader()获得的
     */

    /**
     * 类加载的委托模式存在的缺点在于上层的类加载器无法得知子类加载器所加载的类的情况,
     */

    /**
     * 由于类加载的委托机制,父类加载器无法得知子类加载器的类加载情况
     * 比如说BootstrapClassLoader在加载一个java核心类的时候,需要加载外部实现的类
     * 此时类加载的机制就无法胜任这种情况(因为BootstrapClassLoader无法找到该类并且其无法得知子类加载器的加载情况)
     * 解决该问题的方法在于在Bootstrap所加载的核心类中通过Thread.currentThread().getContextClassLoader()获得
     * 一个上下文的加载器(实际上该加载器就是AppClassLoader),通过这个类加载器来加载外部实现的类
     */

    /**
     * 同名的类如果类加载器不一样,那么这些类就是不相等的
     */

    /**
     * 热替换:
     * 主要思路是通过自定义的ClassLoader来动态加载特定的class文件
     */
}
