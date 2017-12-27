package construct;
/*
 * 适配器模式:
 * 将一个类的接口转换为客户端所期待的另一种接口,
 * 从而使原本因接口不匹配而无法在一起工作的两个
 * 类能够在一起工作
 * 应用例子:SpringMVC HandlerAdaptor
 */
public class AdaptorPattern {
    /*
     * 原始类
     */
    class Adaptee{
        protected void originMethod() {
            
        }
    }
    /*
     * 目标接口
     */
    interface Target{
        void targetMethod();
    }
    /*
     * 适配器,
     * 该类用于适配原始类
     * 使得只能兼容目标接口的客户端能够
     * 调用适配器,并且实现是原始类提供的
     */
    class Adaptor extends Adaptee implements Target{

        @Override
        public void targetMethod() {
            super.originMethod();
        }
        
    }
}
