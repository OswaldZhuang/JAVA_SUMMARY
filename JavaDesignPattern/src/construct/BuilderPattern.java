package construct;
/*
 * 建造者模式:
 * 将一个复杂对象的构建与他的表示分离,
 * 使得同样的构建过程可以创建不同的表示
 * 其思想在于建造者能够根据需求创建
 * 不同的复杂对象
 * e.g Mybatis SessionFactoryBuilder
 */
public class BuilderPattern {
    //需要建造的目标产品类
    abstract class Product{
        
    }
    
    class Prod_1 extends Product{
        
    }
    
    class Prod_2 extends Product{
        
    }
    //产品类的建造者
    class Builder{
        
        //根据需求创建某种对象
        public Product build(String demand) {
            Product product = null;
            if ("prod1".equals(demand)) {
                product = new Prod_1();
                //...复杂的创建过程
            }else if ("prod2".equals(demand)) {
                product = new Prod_2();
                //...复杂的创建过程
            }
            return product;
        }
    }
}
