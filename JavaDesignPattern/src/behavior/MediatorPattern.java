package behavior;
/*
 * 中介者模式:
 * 用中介对象封装一系列的对象交互,中介者使各对象
 * 不需要显示的相互作用,从而使其松散耦合,而且可以独立
 * 的改变他们之间的交互
 * 其思想在于原本多个对象之间存在交互使得对象之间的耦合呈现网状,
 * 加入中介者后对象的交互放在了中介者中,对象之间的耦合呈现星状
 * e.g springMVC的Controller
 */
public class MediatorPattern {
    //抽象交互类
    abstract class Component{
        //每个交互类与中介者耦合,交互方法委托给中介者完成
        private Mediator mediator;
        public void setMediator(Mediator mediator) {
            this.mediator = mediator;
        }
        public Mediator getMediator() {
            return mediator;
        }
    }
    //具体交互类
    class Component1 extends Component{
        //Component1自己的逻辑
        public void Comp1_selfMethod() {
            
        }
        //与Component2的交互委托给中介者
        public void interactWithComponent2() {
            getMediator().interact1();
        }
    }
    class Component2 extends Component{
        //Component2自己的逻辑
        public void Comp2_selfMethod() {
            
        }
        //与Component1的交互委托给中介者
        public void ineractWithComponent1() {
            getMediator().interact2();
        }
    }
    //定义抽象中介者
    abstract class Mediator{
        protected Component1 component1;
        protected Component2 component2;
        public void setComponent1(Component1 component1) {
            this.component1 = component1;
        }
        public void setComponent2(Component2 component2) {
            this.component2 = component2;
        }
        public abstract void interact1();
        public abstract void interact2();
    }
    
    class ConcreteMediator extends Mediator{
        /*
         * 本来component1和component2的交互应该是
         * component1持有component2的引用,在交互方法
         * 中调用component2的方法.
         * 而加入中介者之后,交互全部委托给中介者完成,component1和
         * component2之间没有了任何耦合
         */
        @Override
        public void interact1() {
            component1.Comp1_selfMethod();
            component2.Comp2_selfMethod();
        }

        @Override
        public void interact2() {
            component2.Comp2_selfMethod();
            component1.Comp1_selfMethod();
        }
        
    }
}
