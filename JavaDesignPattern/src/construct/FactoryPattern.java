package construct;

/*
 * 工厂模式:
 * 为什么使用工厂模式:
 * 1.如果创建一些对象对象非常麻烦(比如创建的步骤非常多),那么对象
 * 的创建过程就不应该在调用者内部进行,而需要一个专门的类负责创建
 * 这些对象,屏蔽掉具体对象的创建过程
 * 2.增加扩展性,增加新的类并不需要大规模更改原始的代码,只需增加工厂类
 * 的相应实现即可(甚至工厂方法模式的第二种写法(通过反射)都不需要修改工厂类,仅仅修改
 * 工厂方法的传入参数)
 */
public class FactoryPattern {
    
    /*
     * 工厂方法模式:定义一个用于创建对象的接口,让子类决定实例化哪一个类。
     * 工厂方法使一个类的实例化延迟到其子类。
     * 首先定义一个工厂的接口,
     * 然后定义具体的工厂子类,
     * 将具体的对象的生产交给具体的工厂子类去完成
     */
    static class FactoryMethod {
        /*
         * 此处声明为static是为了SimpleBMW5SeriesFactory类的
         * 静态方法可以以访问
         */
        static class BMW{
            String name;
            String series;
            double price;
        }
        
        class BMW3Series extends BMW{
            String sports;
        }
        
        static class BMW5Series extends BMW{
            String luxury;
        }
        
        /*
         * 内部接口默认是static
         * 所以FactoryMethod是static
         */
        interface BMWFactory {
            BMW createBMW();
        }
        
        class BMW3SeriesFactory implements BMWFactory{
            @Override
            public BMW createBMW() {
                return new BMW3Series();
            }
        }
        
        class BMW5SeriesFactory implements BMWFactory{
            @Override
            public BMW createBMW() {
                return new BMW5Series();
            }
        }
        
        /*
         * 工厂方法模式的另一种写法:
         * 如果我们仅仅需要一个工厂去产生
         * 多种同类型(即继承同一个类)的对象,
         * 那么我们可以在工厂方法里传入所需类型以创建正确的对象
         */
        interface BMWTotalFactory{
            <T extends BMW> T createBMW(Class<T> type);
        }
        
        class BMWTotalFactoryImpl implements BMWTotalFactory{
            @Override
            public <T extends BMW> T createBMW(Class<T> type) {
                T car = null;
                try {
                    car = (T)Class.forName(type.getName()).newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                } 
                return car;
            }
        }
        
        /*
         * 简单工厂模式
         * 也叫做静态工厂方法模式
         * 即将工厂方法改为静态方法,
         * 这样,每次调用工厂的时候无需
         * 新创建工厂对象
         */
         static class SimpleBMW5SeriesFactory {
            public static BMW createBMW() {
                return new BMW5Series();
            }
        }
    }
    
    /*
     * 抽象工厂模式:为创建一组相关或相互依赖的对象提供一个接口,而且无需指定他们的具体类
     * 抽象工厂用于创建一组相关或者相互依赖的对象
     * 抽象工厂模式和工厂方法模式的不同在于:
     * 抽象工厂模式所关注的是一族产品的生产,换句话说,一个抽象的工厂类
     * 里有多个方法用于生产多个抽象产品(每个方法对应一个产品),而
     * 具体的工厂类里的具体方法生产具体的产品,而这些产品是属于一族的(纵向)
     * 工厂方法模式所关注的是一个工厂生产一类产品(横向)
     */
    static class AbstractFactory{
        
        abstract class ProductA{
            
        }
        
        abstract class ProductB{
            
        }
        /*
         * 抽象的工厂,包含抽象方法用于生产相关联或相依赖的产品,即产品族
         */
        abstract class ProductAandProductBFactory{
            abstract ProductA createA();
            abstract ProductB createB();
        }
        
        /*
         * Type1 产品族
         */
        class Type1_ProductA extends ProductA{
            
        }
        
        class Type1_ProductB extends ProductB{
            
        }
        
        /*
         *Type2 产品族 
         */
        class Type2_ProductA extends ProductA{
            
        }
        
        class Type2_ProductB extends ProductB{
            
        }
        
        /*
         * Type1 产品族的工厂
         */
        class Type1_ProductAandProductBFactory extends ProductAandProductBFactory{

            @Override
            ProductA createA() {
                return new Type1_ProductA();
            }

            @Override
            ProductB createB() {
                return new Type1_ProductB();
            }
            
        }
        
        /*
         * Type2 产品族的工厂
         */
        class Type2_ProductAandProductBFactory extends ProductAandProductBFactory{

            @Override
            ProductA createA() {
                return new Type2_ProductA();
            }

            @Override
            ProductB createB() {
                return new Type2_ProductB();
            }
            
        }
        
    }

}
