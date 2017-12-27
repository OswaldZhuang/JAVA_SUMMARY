package structure;

/*
 * 装饰模式:
 * 动态的给一个对象添加一些额外的职责,
 * 对于增加功能来说,装饰模式比生成子类
 * 更加灵活
 * 装饰模式关注于对类的功能进行增强
 * e.g javaIO
 */
public class DecoratorPattern {
    //被装饰的组件
    abstract class Component{
        public abstract void operate();
    }
    //抽象装饰器,它是Component类型,通过给他传入component
    //再结合自身特有的属性与方法实现对component的装饰
    abstract class Decorator extends Component{
        private Component component;
        public Decorator(Component component) {
            this.component = component;
        }
        @Override
        public void operate() {
            component.operate();
        }
    }
    
    class Decorator1 extends Decorator{

        public Decorator1(Component component) {
            super(component);
        }
        private void dec1Method() {
            
        }
        @Override
        public void operate() {
            dec1Method();
            super.operate();
        }
    }
    
    class Decorator2 extends Decorator{

        public Decorator2(Component component) {
            super(component);
        }
        private void dec2Method() {
            
        }
        @Override
        public void operate() {
            dec2Method();
            super.operate();
        }
        
    }
}
