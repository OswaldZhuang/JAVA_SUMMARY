package security.encryption;

/*
 * 数字签名
 * 主要作用:证明报文没有被篡改过
 */
public class DigitalSigning {
    /*
     * 具体过程如下:
     * 1.发送方使用hash函数对报文产生"摘要"(定长且唯一)
     * 2.发送方使用私钥对摘要加密,生成数字签名
     * 3.发送方将数字签名附在报文末然后发送(报文采用接收方的公钥加密)
     * 4.接收方收到报文后取下摘要
     * 5.接收方使用公钥对摘要解密
     * 6.接收方再利用hash函数(同一个)对报文产生摘要
     * 7.对比发送来的摘要和自己算出来的摘要是否相同,相同则是正确未被修改过的报文
     * 摘要的目的就是为了验证信息的完整性,由于摘要不可逆推,因此摘要具有安全性
     */
}
