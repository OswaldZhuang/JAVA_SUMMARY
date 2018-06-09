package vm.memory;

public class Contented {
	/*
	 * CPU通常不会直接和主内存进行数据的交换,而是通过cache(即缓存),
	 * cache的基本单位为cacheline(缓存行),通常为64byte,这也是CPU读取
	 * 数据的基本单位,
	 * 由于缓存行较大,因此会存在多个变量存储在一个cacheline的情况
	 * 当CPU核心中的线程去修改cacheline中的某个变量后,该cacheline就
	 * 必须与主内存进行同步
	 * 如果多个CPU线程对同一个cacheline的不同变量进行读写操作就会
	 * 降低性能(因为cache和内存的同步次数会增加,比如线程t1去修改t1的cacheline1的
	 * 变量A,t2也要修改t2的cacheline2的变量B(cacheline1和cacheline2映射相同的内存块),
	 * 当t1修改完变量A并同步到内存之后,cacheline2需要先把刚才更新的结果同步下来
	 * 再做修改,再同步到主内存,而cacheline1又需要把t2修改的结果再同步一遍),
	 * 这也叫做"伪共享"
	 */
	/*
	 * @sun.misc.Contended
	 * 该注解是在JDK1.7加入的
	 * 其目的是为了被修饰的对象能够独享cacheline而不是和其他对象共享
	 * 提高性能
	 */

}
