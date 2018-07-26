package base.system;


public class UseSystem {
    /*
     * java.lang.System
     * 该类无法被实例化(因为构造器是私有的)
     * 该类提供了如下功能:
     * 标准输入输出,错误输出;
     * 访问额外定义的属性和环境变量;
     * 加载文件和库的方法;
     * 拷贝部分数组的工具方法
     *
     */

    /*
     * 其方法与属性定义如下:
     * private static native void registerNatives(); 该方法会通过static块调用,
     * 虚拟机会通过initializeSystemClass方法完成该类的初始化
     * public final static InputStream in = null; 标准输入流,该输入流是早已打开的,并且用于接收
     * 键盘输入或者其他输入
     * public final static PrintStream out = null; 标准输出流,该输出流是早已打开的,并且用于屏幕
     * 或者其他输出
     * public final static PrintStream err = null; 标准错误流
     * 实际上述流会在static块中被初始化
     * System类还提供了一些set方法用于重新分配这些流
     * public static Console console() 该方法返回和JVM唯一相关联的java.io.Console对象
     * public static Channel inheritedChannel() throws IOException 返回一个Channel
     * 该对象由electorProvider.provider()提供
     * public static void setSecurityManager(final SecurityManager s) 重置SecurityManager
     * public static SecurityManager getSecurityManager()
     * public static native long currentTimeMillis();返回当前的毫秒时间,实际上该时间可能会比正确的
     * 时间更大
     * public static native long nanoTime();以纳秒返回正在运行的JVM的高精度的当前时间,该方法只能用做
     * 计算流逝的时间(即两个时间相减),其和系统时间没有任何关系,并且也是不保证精准性的
     * public static native void arraycopy(Object src,  int  srcPos, Object dest, int destPos, int length);
     * 该方法用于从指定源数组拷贝数组到目标数组,src表示源数组,srcPos表示源数组的开始位置,dest表示目标数组,destPos表示目标数组的开始位置,
     * length表示要拷贝的长度
     * 该方法可能会抛出NullPointerException,ArrayStoreException(src或者dest不是数组;src和dest的数据类型不一致),IndexOutOfBoundsException
     * public static native int identityHashCode(Object x);返回对象的hash值(该值和Object#hashCode()相同)
     * private static Properties props;
     * private static native Properties initProperties(Properties props);
     * public static Properties getProperties()
     * public static void setProperties(Properties props)
     * public static String getProperty(String key)
     * public static String getProperty(String key, String def)
     * public static String setProperty(String key, String value)
     * public static String clearProperty(String key)
     * 上述方法用于系统属性(system properties)
     * public static String getenv(String name) 获取Environment中key对应的value
     * public static java.util.Map<String,String> getenv() 获取Environment对应的key-value集合,该集合是不可更改的
     * Environment和SystemProperties都是key-value值,都可以用于传递用户定义的信息到java进程中,不同的是Environment更具有全局性(父进程和子进程共享),
     * 但是Environment的潜在副作用更大(比如有些系统大小写不敏感),因此最好使用SystemProperties,在有全局性需求的时候才使用Environment
     * public static void exit(int status) 终结掉当前运行的java虚拟机,传入参数为状态码,非零状态码表示非正常终结。该方法实际上调用的是
     * Runtime.getRuntime().exit(status)
     * public static void gc() 运行垃圾收集器(实际上是"建议"),该方法实际运行Runtime.getRuntime().gc();
     * public static void runFinalization() 运行finalization(实际上是"建议"),即那些被遗弃的但是其finalize方法还没有调用的对象会被调用
     * finalize方法,该方法实际调用Runtime.getRuntime().runFinalization()
     * public static void load(String filename) 加载本地库,filename必须是绝对路径
     * public static void loadLibrary(String libname)
     * private static void initializeSystemClass()初始化System类,该方法在线程初始化之后调用,主要工作是初始化system property和各种流
     * private static void setJavaLangAccess() 允许特权类在java.lang之外
     */
}
