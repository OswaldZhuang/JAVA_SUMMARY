package behavior;
/*
 * 模板方法模式
 * 定义一个操作中的算法框架,而将一些步骤延迟到子类中,使得
 * 子类可以不改变一个算法的结构即可重定义该算法的某些特定步骤
 * e.g servlet
 */
public class TemplateMethodPattern {
    
    abstract class TemplateClass{
        
        protected abstract void part1();
        protected abstract void part2();
        
        //钩子方法,hook method
        protected boolean executePart1() {
            return true;
        }
        
        /*
         * 模板方法,一般定义为final,不允许子类改写
         */
        public final void templateMethod() {
            if(executePart1()) {
                part1();
            }
            part2();
        }
    }
    
    class ConcreteClass extends TemplateClass{
        
        @Override
        protected boolean executePart1() {
            return false;
        }

        @Override
        protected void part1() {
            //算法步骤中的具体逻辑
        }
        @Override
        protected void part2() {
            //算法步骤中的具体逻辑
        }
    }
}
