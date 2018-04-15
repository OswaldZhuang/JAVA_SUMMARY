package security.oauth;

/*
 * OAuth是一种授权协议,当前版本2.0
 * OAut的主要作用是第三方应用只能通过认证服务器登录,这样就能区分真正的用户和第三方应用
 */
public class AboutOAuth {
    /*
     * OAuth的主要授权过程如下:
     * 第三方应用(App)首先向资源拥有者(Resource Owner)发送Authorization Request
     * Resource Owner向App返回Authorization Grant
     * App向认证服务器(Authorization Server)发送Authorization Grant
     * Authorization Server返回Access Token
     * App利用Access Token向资源服务器(Resource Server)请求资源
     * 
     * 上述过程的关键在于App如何得到用户的授权,进而获得token请求响应资源
     * 
     * OAuth 2.0 定义了4种授权的模式:
     * 
     * 授权码模式(authorization code)
     * 其特点在于通App的后台服务器与认证服务器进行互动,access_token对App是不可见的
     * 1.第三方应用服务器向认证服务器发送认证请求
     *  GET /authorize?response_type=code&client_id=xx&state=xx&redirect_uri=xxx HTTP/1.1 Host: xx.xx.com
     * 2.认证服务器向浏览器返回认证页面
     * 3.用户认证,向认证服务器发送认证信息
     * 4.认证服务器根据redirect_uri向浏览器发送授权码,浏览器会把授权码发送到第三方应用服务器
     *  HTTP/1.1 302 Found Location: https://xx.xx.com/cb?code=xx&state=xx
     * 5.第三方应用服务器收到授权码,再附上redirect_uri,向认证服务器申请令牌
     *  POST /token HTTP/1.1 Host: xx.xx.com Authorization: Basic xx Content-Type: xx grant_type=authorization_code&code=xx&redirect_uri=xx
     * 6.认证服务器核对redirect_uri和授权码,向第三方应用服务器发送token
     *  
     * 
     * 简化模式(implicit grant type)
     * 该模式不通过第三方应用的后台服务器,直接在浏览器中向认证服务器申请token,token对用户可见
     * 1.浏览器向认证服务器发送认证请求
     *  GET /authorize?response_type=token&client_id=xx&state=xx&redirect_url=xx HTTP 1.1 Host:xxx
     * 2.认证服务器返回认证界面,要求用户认证
     * 3.用户认证,向认证服务器发送认证信息
     * 4.认证服务器根据redirect_uri向浏览器返回access_token
     *  Http/1.1 302 Found Location:http://xx.xx.com/xx#access_token=xxs$state=xx&token_type=xx&expires_in=xx
     * 5.浏览器根据Location中的url发送请求,url返回一段脚本提取access_toekn中的token
     */
    

}
