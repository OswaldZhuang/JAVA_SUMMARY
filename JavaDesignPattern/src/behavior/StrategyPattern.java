
package behavior;
/*
 * 策略模式:
 * 定义一组算法,将每个算法都封装起来,并且使他们之间
 * 可以互换
 * 其思想在于使用Context对象来管理策略,而这些
 * 策略通常实现了同一个接口
 */
public class StrategyPattern {
    interface Strategy{
        void execute();
    }
    
    class Strategy1 implements Strategy{

        @Override
        public void execute() {
            //具体策略方法
        }
        
    }
    
    class Strategy2 implements Strategy{

        @Override
        public void execute() {
            //具体策略方法
        }
        
    }
    
    class Context{
        private Strategy strategy;

        public Context(Strategy strategy) {
            this.strategy = strategy;
        }
        
        public void doExecute() {
            strategy.execute();
        }
    }
    
    //策略枚举
    enum StrategyEnum{
        
        ADD{
            @Override
            public int execute(int i, int j) {
                return i+j;
            }
            
        },
        SUB{
            @Override
            public int execute(int i, int j) {
                return i-j;
            }
        };
        public abstract int execute(int i, int j);
    }
}
