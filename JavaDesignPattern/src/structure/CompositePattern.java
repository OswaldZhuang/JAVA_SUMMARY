package structure;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/*
 * 组合模式:
 * 将对象组合成树形结构以表示"部分-整体"的层次结构,使得用户对单个对象和组合对象的使用具有一致性
 * e.g XML对象,文件系统
 */
public class CompositePattern {
    
    interface Component{
         void doSomething();
         void addComponent(Component component);
         void removeComponent(Component component);
         List<Component> getChild();
    }
    //叶子节点
    class Leave implements Component{

        @Override
        public void doSomething() {
            // 业务逻辑
        }

        @Override
        public void addComponent(Component component) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void removeComponent(Component component) {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<Component> getChild() {
            throw new UnsupportedOperationException();
        }
        
    }
    
    //中间节点,即分枝结点
    class Composite implements Component{
        //保证线程安全性
        List<Component> children = new CopyOnWriteArrayList<>();
        @Override
        public void doSomething() {
            // 业务逻辑
        }

        @Override
        public void addComponent(Component component) {
            children.add(component);
        }

        @Override
        public void removeComponent(Component component) {
            children.remove(component);
        }

        @Override
        public List<Component> getChild() {
            return children;
        }
        
    }
    
}
