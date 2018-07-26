public class AboutCollection {
	
	/*
	 * java.lang.Iterable<E>
	 * 实现了该接口的类可以使用java提供的for each的语法糖
	 * for(E element : elements){...}
	 * 原因是因为该接口定义了
	 * Iterator<T> iterator()
	 * 方法,在jvm执行foreach的时候,会调用该类的iterator()方法,进而调用next()完成遍历
	 */
	
	/*
	 * java.util.Iterator<E>
	 * 集合的迭代器,定义了一些迭代(遍历)的方法
	 * boolean hasNext()
	 * E next()
	 */
	
	/*
	 * Interface java.util.Collection<E> extends Iterable<E>
	 * 该接口声明了一些基本的有关集合的方法
	 */
	
	/*
	 * java.util.AbstractCollection<E> implements Collection<E>
	 * 实现了一些基本的Collection的方法(大部分方法都是通过Iterator实现的)
	 * 该类保留了一些抽象的方法:
	 * 比如:Iterator<T> iterator(), int size()
	 */

}
