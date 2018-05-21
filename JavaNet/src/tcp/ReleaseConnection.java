package tcp;
/*
 * tcp释放连接
 */
public class ReleaseConnection {
	/*
	 * tcp通过FIN来作为关闭传输连接的命令
	 * 从而关闭本端的数据流(也就是说本端不会发送任何数据,但还可以接收数据)
	 * 完全关闭tcp需要双方都关闭了各自的数据流
	 * 这种特征源于tcp的半关闭特性(half-close)
	 * A --------> B
	 * A <-------- B
	 * FIN发送是socket调用CLOSE原语的结果
	 */
	/*
	 * tcp四阶段挥手:
	 * 1.client此时为ESTABLISHED状态,现在客户端需要结束本次传输,其应用层调用CLOSE方法,向server端发送
	 * 数据段(FIN=1,seq=m),client进入FIN WAIT 1状态
	 * 2.server端(ESTABLISHED状态)收到数据段后,向client端发送(ACK=1,ack=m+1,seq=n),进入CLOSE WAIT状态,
	 * 与此同时,server端应用层释放client端到server端的传输连接(也就是说client端不再发送数据,server端不再接收数据),但是server端
	 * 仍然可以向client端发送数据
	 * 3.client端接受到上述数据段后,进入到FIN WAIT 2状态,等待server释放其连接
	 * 4.如果server端需要关闭其连接,那么它将发送数据段(FIN=1,ACK=1,seq=w,ack=m+1)(此时的ack=m+1用于回应上一次ack)
	 * 然后进入到LAST ACK状态
	 * 5.client端接收到上述数据后,向server端发送数据段(ACK=1,seq=m+1,ack=w+1),进入TIME WAIT状态,此时tcp连接还未完全
	 * 释放,在等待2MSL后,client端进入CLOSED状态,tcp彻底释放
	 * 6.server端收到数据段后,即进入CLOSED状态
	 */
	
	/*
	 * 双方同时释放连接:
	 * 1.双方同时发送数据段(FIN=1,seq=i),(FIN=1,seq=j),并进入FIN WAIT 1状态
	 * 2.双方接受到数据段后变为CLOSING状态并发送数据(ACK=1,ack=j+1,seq=i+1),(ACK=1,ack=i+1,seq=j+1),
	 * 3.当接收到ack后,状态变为TIME WAIT,等待2MSL后进入CLOSED状态
	 */
	
}
