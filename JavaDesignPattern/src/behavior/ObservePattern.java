package behavior;

import java.util.Vector;

/*
 * 观察者模式:对象间一对多的依赖关系,使得每当一个对象改变状态,则所有依赖于它的对象都会得到通知并被自动更新
 * 发布订阅模式(publish/subscribe)
 * JDK实现可参考java.util.Observer和java.util.Observable
 */
public class ObservePattern {
    
    /*
     * 观察者
     */
    class Observer{
        /*
         * 观察者的更新事件
         */
        public void update() {
            
        }
    }
    
    /*
     * 被观察者
     * 也可以是一个主题(topic)
     */
    class Observable{
        Vector<Observer> observers;
        
        /*
         * 将观察者注册到被观察者
         */
        public void register(Observer o) {
            observers.add(o);
        }
        /*
         * 通知观察者进行更新
         * 此处的通知是个同步的操作,如果其中一个observer的update()
         * 操作时间过长,那么就会阻塞其他通知
         */
        public void notifyObservers() {
            for (Observer observer : observers) {
                observer.update();
            }
        }
    }
}
