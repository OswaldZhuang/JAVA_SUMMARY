package http.protocal;

public class PostAndGetMethod {
    /*
     * http中最重要的两个方法GET,POST    
     */
    
    public void AboutGetMethod() {
        /*
         * 对于GET方法来说,请求的参数是被放在url中,而url是被放在起始行中
         * 比如起始行可以写成:
         * GET www.example.com/s/name=xx&age=xx
         * 本质上来说GET方法只发送一个TCP数据包(HEADER和数据),接着
         * 就是等待服务器的响应
         */
    }
    
    public void AboutPOSTMethod() {
        /*
         * 对于POST方法来说,提交的内容是被放在
         * 请求体(entity-body)中
         * 本质来说POST方法会发送两个TCP数据包,第一次
         * 会发送HEADER,服务器响应100 Continue,
         * 接着发送数据(data)
         */
    }

}
