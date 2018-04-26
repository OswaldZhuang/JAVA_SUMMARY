package security.oauth;

public class AboutJWT {
	//JSON WEB TOKEN
	
	/*
	 * JWT由三部分组成:
	 * header,payload,signature
	 * 
	 * header中声明:
	 * {
	 * 		"typ":"JWT",
	 * 		"alg":"HS256"
	 * }
	 * 然后将header进行base64加密,变为header_base64
	 * payload同样进行base64加密,变为payload_base64
	 * signature = HS256(header_base64 + "." + payload_base64, secret)
	 * 即signature为上述加密后的值连接起来再通过header中定义的加密算法与
	 * secret一起加盐加密(secret是保存在server端的)
	 * 
	 * 最后jwt = header_base64 + "." + payload_base64 + "." + signature
	 * 
	 * client向server发送请求的时候会附带jwt(实际上是cilent通过了验证server产生了jwt先
	 * 发送给client,然后client保存起来),server解析请求得到其中的jwt,逆向base64得到加密算法
	 * 然后根据自己保存的secret再次构造signature,如果自己构造的和发来的一致,那么验证通过
	 */

}
