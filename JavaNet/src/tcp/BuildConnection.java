package tcp;
/*
 * tcp建立连接
 */
public class BuildConnection {
	
	/*
	 * 首先对于服务端的socket而言,
	 * 会经历如下过程:SOCKET -> BIND -> LISTEN -> ACCEPT(此时阻塞,等待连接的客户端)
	 * 而对于客户端,
	 * SOCKET -> CONNECT
	 */
	
	/*
	 * 三阶段握手:
	 * 该阶段开始于client端调用connect与server端尝试连接
	 * 1.client发送数据段(SYN=1, seq=i),并进入SYN SENT状态
	 * 2.server在接收到上述数据后返回数据段(ACK=1,SYN=1,seq=j,ack=i+1)
	 * 并进入SYN RCVD状态
	 * 3.cilent接受到server的数据后返回数据段(ACK=1,seq=i+1,ack=j+1),并进入
	 * ESTABLIED状态
	 * 4.server接收到上述数据后进入ESTABLIHED状态
	 * 连接建立完成
	 * 实际上三阶段握手能够保证通信可靠性的前提就在于ack和seq,ack使得cilent和server端
	 * 可以知道连接请求是否失效(发送的seq是i,而接收到了的ack不为i+1那么连接肯定是失效的)
	 * 
	 * SYN攻击:
	 * client大量发送(SYN=1,seq=i)这样的消息到server,server返回ack后client不再响应ack
	 * 使得server的资源耗尽(大量占据server的半连接队列)
	 */
	
	/*
	 * 当双方端同时发起连接时就没有client和server的概念(既是client又是server)
	 * 1.双方同时发送数据段(A->B)(SYN=1,seq=i),(B->A)(SYN=1,seq=j),同时进入SYN SENT状态
	 * 2.当两端都接受到数据后,变为SYN RCVD状态,并且都发送数据包(B->A)(ACK=1,SYN=1,ack=i+1,seq=j+1),
	 * (A->B)(ACK=1,SYN=1,ack=j+1,seq=i+1),发送完成后进入到ESTABLISHED状态
	 * 3.此时连接建立,并且仅仅是建立的一个连接
	 */

}
