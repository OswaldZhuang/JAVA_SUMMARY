package concurrence.chapter4_objcomposition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

/*
 * 若没有则添加问题
 */
public class AboutAddIfAbsent {
    
    class NewVector<E> extends Vector<E>{
        public synchronized boolean putIfAbsent(E e) {
            if(!contains(e)) {
                add(e);
                return true;
            }
            return false;
        }
    }
    
    
    class ListHelper<E>{
        List<E> list = Collections.synchronizedList(new ArrayList<>());
        /*
         * 实际上该方法并不是线程安全的
         * synchronized关键字只是对ListHelper进行了加锁
         * 也就是说同一时间内仅仅只有一个线程能够访问ListHelper的
         * putIfAbsent方法(实际上是整个ListHelper对象),然而该锁并没有限制对list对象的
         * 访问,具体来讲就是在putIfAbsent执行的过程中其他线程也
         * 可以对list对象进行修改,从而线程不安全
         */
        public synchronized boolean putIfAbsent(E e) {
            if(!list.contains(e)) {
                list.add(e);
                return true;
            }
            return false;
        }
        
        /*
         *因此,比较好的做法就是在复合的操作前,对被操作的对象直接加锁 
         */
        public boolean betterPutIfAbsent(E e) {
            synchronized(list) {
                if(!list.contains(e)) {
                    list.add(e);
                    return true;
                }
                return false;
            }
        }
    }
    
    /*
     * 通过组合的方式
     */
    class ImprovedList<E> implements List<E>{
        
        private final List<E> list;
        
        public ImprovedList(List<E> list) {
            this.list = list;
        }
        
        /*
         * 这样实现putIfAbsent是可以接受的
         * 因为list被封装进了ImprovedList内部
         * 使得访问的时候只能访问ImprovedList对象,
         * 而ImprovedList对象的操作实际上是委托给了
         * 内部的list对象,这样的话synchronized就
         * 能够起到相应的作用,因为对ImprovedList
         * 对象的访问是原子性的,而list对象又不能被直接访问
         */
        public synchronized boolean putIfAbsent(E e) {
            if(!list.contains(e)) {
                list.add(e);
                return true;
            }
            return false;
        }
        
        /*
         * 以下方法均实现同步,不再改写
         */

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<E> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return null;
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public boolean add(E e) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {
        }

        @Override
        public E get(int index) {
            return null;
        }

        @Override
        public E set(int index, E element) {
            return null;
        }

        @Override
        public void add(int index, E element) {
        }

        @Override
        public E remove(int index) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator<E> listIterator() {
            return null;
        }

        @Override
        public ListIterator<E> listIterator(int index) {
            return null;
        }

        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            return null;
        }
        
    }

}
