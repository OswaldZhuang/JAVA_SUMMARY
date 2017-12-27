package behavior;
/*
 * 备忘录模式:
 * 在不破坏封装性的前提下,捕获一个对象的内部状态,
 * 并在该对象之外保存这个状态,这样以后就可以将该对象恢复到原先
 * 保存的状态
 * e.g 数据库连接的事务管理
 */
public class MementoPattern {
    /*
     * 通用的备忘录模式代码
     */
    //发起人角色,记录当前的内部状态,定义哪些属于备份的范围,负责创建和恢复备忘录数据
    class Originator{
        private String state;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
        //创建一个备忘录
        public Memento createMemento() {
            return new Memento(this.state);
        }
        //从给定的备忘录中恢复
        public void restoreMemento(Memento memento) {
            setState(memento.getState());
            //this.state = memento.getState();
        }
    }
    //备忘录,负责存储和提供Originator中的内部状态
    class Memento{
        //发起人的内部状态
        private String state;

        public Memento(String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
        
        
    }
    //备忘录的管理角色,
    class Caretaker{
        private Memento memento;

        public Memento getMemento() {
            return memento;
        }

        public void setMemento(Memento memento) {
            this.memento = memento;
        }
        
    }
    
    /*
     * 多状态的备忘录模式
     * 思路:将Originator中的多个状态存入
     * Memmento所维护的HashMap中(当然也可以是其他数据结构,只要能从中恢复)
     * (可以用Introspector或反射获取Originator对象的属性)
     */
    
    /*
     * 多份备忘录的备忘录模式
     * 思路:在备忘录管理角色Caretaker中维护一个HashMap(HashMap的key是检查点)
     * 以存储多个Memento
     */
}
