package collection.list;

public class HowListWorks {
    
    /*
     * Interface java.util.List<E> extends Collection<E>
     */
	
	/*
	 * abstract class java.util.AbstractList<E> extends AbstractCollection<E> implements List<E>
	 */

    /**
     * java.util.ArrayList<E>
     * capacity是ArrayList里面的数组的长度,当有元素加入到ArrayList中的时候,需要扩容时capacity自动增加.ensureCapacity方法能够改变
     * capacity大小
     * ArrayList不是线程安全的.ArrayList返回的迭代器是fail-fast(快速失败)的.
     * private static final int DEFAULT_CAPACITY = 10;
     * private static final Object[] EMPTY_ELEMENTDATA = {};如果调用有参数的构造器且初始化长度为0,那么elementData指向该值
     * private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};如果调用无参数的构造器,那么elementData指向该值
     * transient Object[] elementData;
     * private int size;ArrayList大小(即其包含的元素的数量,该变量和capacity的区别在于capacity是数组的长度,而该长度并不
     * 一定是真实存在的元素的个数,因为中间可能会有空隙)
     * public void trimToSize() 将capacity缩小为实际元素个数(即size),本质上是调用Arrays#copyOf方法
     * public int indexOf(Object o) 该方法返回第一次出现的元素的位置,返回-1表示没有该元素,实际上也就是遍历数组
     * public int lastIndexOf(Object o) 该方法返回最后一次出现的元素的位置,遍历方式是相反遍历
     * public Object[] toArray() 返回一个相对应的数组(实际上该数组是新创建的)
     * public <T> T[] toArray(T[] a)
     * public E get(int index) 通过位置获得数组元素值
     * public E set(int index, E element) 根据位置替换元素值,返回原来的元素的值
     * private void grow(int minCapacity) 扩容操作,传入参数为所需要的最小容量,首先会将数组的长度扩大为原来的1.5倍
     * (具体操作是oldCapacity + (oldCapacity >> 1),之所以用这种操作而不是直接用乘法是因为加法比乘法更快),其最大值也只能
     * 扩展到Integer.MAX_VALUE,最后使用Arrays.copyOf将数组复制到新数组中
     * public boolean add(E e) 向ArrayList添加新的元素,只有当前数组的长度小于size+1的时候会进行扩容操作,扩容完成后会在size+1
     * 处添加新增元素(即尾部添加)
     * public void add(int index, E element) 在指定的位置插入元素
     * public E remove(int index) 在指定位置移除元素
     * public boolean remove(Object o) 移除一个出现的指定元素,判断是否相同是采用equals方法
     * public void clear() 清空ArrayList
     * public boolean addAll(Collection<? extends E> c) 将c中的元素追加到ArrayList
     * public boolean addAll(int index, Collection<? extends E> c) 在指定位置插入c的所有元素
     * protected void removeRange(int fromIndex, int toIndex) 去掉从fromIndex到toIndex的元素
     * public boolean removeAll(Collection<?> c) 去除ArrayList中c包含的元素
     * public boolean retainAll(Collection<?> c) 保留ArrayList中c包含的元素
     */
	
	

}
