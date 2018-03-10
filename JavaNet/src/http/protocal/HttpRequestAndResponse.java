package http.protocal;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestAndResponse {
    
    /*
     * Http 请求格式
     */
    /*
     * 起始行:<method> <request-url> <version>
     * <method>:GET,HEAD,PUT,POST,TRACE,OPTIONS,DELETE,UPDATE
     * <request-url>:http(s)://www.xxx.com/nn/inedex.html<;/?/#>(;表示参数 ?表示查询 #表示片段)
     * <version>:HTTP/<major>.<minor>
     */
    /*
     * 首部/请求头(HEADER)
     * Accept:e.g text/*
     * Host:www.xxx.com:<port>
     * cookie
     * ...
     * 在头部还可能有一些认证相关的字段,比如:
     * 对于JWT,有
     * Authorization:'Bearer '+<token>
     */
    /*
     * 请求体(entity-body)
     */
    
/*
 * -------------------------------------------------------------------------------------------------
 */
   
    /*
     * Http 响应格式
     */
    /*
     * 起始行:<version> <status> <reason-phrase>
     * 状态码(status)含义如下:
     * 100-199 信息性状态码
     * 200-299 成功状态码
     *  200 OK
     *  202 Accepted
     * 300-399 重定向状态码
     *  301 Moved Permanemtly
     * 400-499 客户端错误状态码
     *  400 Bad Request 错误请求
     *  401 Unauthorized 未认证
     *  403 Forbidden 
     *  404 Not Found
     * 500-599 服务器错误状态码
     *  500 Internal Server Error
     *  502 Bad Geteway 
     */
    /*
     * 首部(HEADER)
     * Content-Type:e.g text/plain
     * Content-lenght:e.g 19
     */
    
    
    public void HttpUsageInJDK() throws Exception{
        /*
         * URI:Uniform Resource Identifier(统一资源标识符),
         * 用于唯一的标识一个资源
         */
        /*
         * URL表示:
         * Uniform Resource Locator(统一资源定位符)
         * 这是URI的一种实现方式
         */
        URL httpUrl = new URL("http://www.xxx.com//xx/xxx.html");
        /*
         * URL#openConnection返回的是URLConnection,这是个抽象类
         * URLConnection有一个非常重要的抽象方法connect(),该方法用于
         * 连接指定的URL
         * 对于Http的连接,则采用HttpURLConnection(实际上这个类也是个抽象类,真正的实现(比如连接)需要委托给一些更底层的类去做)
         * HttpURLConnection则只是实现一些与http协议相关的方法(比如返回码,)
         */
        HttpURLConnection httpURLConnection = (HttpURLConnection)httpUrl.openConnection();
        /*
         * 设置http请求的方法
         */
        httpURLConnection.setRequestMethod("GET");
        /*
         * 通过连接获取输入流用于读取http的响应
         */
        InputStream in = httpURLConnection.getInputStream();
    }
}
