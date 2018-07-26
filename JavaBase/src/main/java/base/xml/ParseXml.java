package base.xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

//JDK 中解析XML的方式
public class ParseXml {
    
    /*
     * DOM (document object model)方法解析xml
     * javax.xml.parsers.DocumentBuilderFactory 工厂类用于产生
     * javax.xml.parsers.DocumentBuilder(该类是xml真正的解析器)
     */
    public void useDOM() {
        /* DocumentBuilderFactory的实例化只能通过newInstance()方法
         * 该方法有如下方式实例化工厂类:
         * 1.查找systemProperty是否有对应的实现类(名字)
         * 1.使用jre路径下的lib/jaxp.properties文件
         * 2.采用service-provider机制,
         * 3.系统默认实现
         * 这样做的好处在于提供一个统一的接口,而具体的实现方式可以依照配置而定
         */
        DocumentBuilderFactory builderFactory = 
                DocumentBuilderFactory.newInstance();
        
        try {
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            /*
             * org.w3c.dom.Document 是DOM定义的xml模型
             */
            Document document = documentBuilder.parse(new File(""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /*
     * SAX (simple api for xml)方法解析xml
     */
    public void useSAX() {
        
    }
}
