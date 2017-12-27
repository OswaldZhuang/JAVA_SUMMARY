package behavior;
/*
 * 访问者模式:
 * 代表一个对象结构的元素上的操作,
 * 访问者使你在不改变操作的元素的类的前提下定义一个新的操作
 * 其思想在于数据结构与操作分离,被访问的类是固定的,而访问者
 * 是可变的
 */
public class VisitorPattern {
    
    //访问者
    interface Visitor{
        //访问被访问者
        void visit(Visited visited);
    }
    //被访问者
    abstract class Visited{
        //被访问者中的元素
        private String element;
        public String getElement() {
            return element;
        }
        public void setElement(String element) {
            this.element = element;
        }
        //接受访问者访问
        public abstract void accept(Visitor visitor);
    }
    
    class ConcreteVisited extends Visited{
        
        @Override
        public void accept(Visitor visitor) {
           visitor.visit(this); 
        }
    }
    
    class ConcreteVisitor implements Visitor{
        @Override
        public void visit(Visited visited) {
            ConcreteVisited concreteVisited = (ConcreteVisited)visited;
            concreteVisited.getElement();
            //实现对元素新的操作
            //....
        }
    }
}
