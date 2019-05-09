public class PAXOS {
    /**
     * paxos算法
     * 主要解决的是非可靠性的进程下，网络中的一致性问题
     *
     * paxos的前提是网络中传输的数据不会被篡改（即不存在拜占庭将军问题）
     */

    /**
     * paxos中的角色可以分为：
     * Proposer：提议的发起者
     * Leader：proposer中的用于发起请求的
     * Acceptor：决定提议是否生效的投票方
     * Learner：执行被选举出的请求
     * Client：发起请求的进程
     */

    /**
     * Quorums：法定人数
     * 指的是proposer发起提议给acceptor的人数
     * 这个数字应该是acceptor数量的一半以上
     */

    /**
     * paxos是一种二阶段提交的协议
     * paxos的具体过程如下：
     * 第一阶段：
     *      1.prepare
     *          proposer向所有的acceptor发送数字n，该数字一定是大于该proposer之前发送的数字
     *      2.promise
     *          acceptor接收到数字后，比较其之前接受到的数字，
     *          如果这是最大的数字，那么发送promise消息
     *          给proposer表示认可该消息。如果在此之前已经有了accepted的请求，那么promise消息中会包含
     *          accepted的数字和具体请求w
     *          否则不返回消息或者返回否定的应答
     * 第二阶段：
     *      1.accept
     *          proposer在接受到法定人数的应答后，
     *          如果应答是否定的，那么进行下一轮提议
     *          如果应答肯定，那么向法定人数发送accept消息，如果promise消息中不包含任何accepted的内容，那么accept消息
     *          中封装刚才的数字n以及自己原来希望发送的请求v，否则，封装n和accepted中的w
     *      2.accepted
     *          acceptor接收到accept消息，如果其中的n大于其之前已经promise的消息，那么返回accepted
     */

    public class Proposer{
        private int promised_;

        private int req_num;
        private Object req_val;
        private Acceptor[] acceptors;

        public void prepare(){
            for (Acceptor a : acceptors) {
                //send req_num
                //greater than promised
            }
        }

        private int getPromise(){
            return 0;
        }

        private void sendAccept(int req_num, Object req_val){
            //send to quorums
        }

        public void accept(int promised, Object promised_val){
            if(getPromise() > acceptors.length/2){
                if(promised_val != null){
                    sendAccept(req_num, promised_val);
                    promised_ = promised;
                }else {
                    sendAccept(req_num, req_val);
                }
            }
        }

    }

    public class Acceptor{
        private int promised;

        private int accepted;
        private Object accepted_val;

        private void sendPromise(boolean reject, int accepted, Object accepted_val){
        }

        private void sendAccepted(boolean reject){
        }

        public void promise(int req_num){
            if(req_num > promised){
                promised = req_num;
                if(accepted != Integer.MIN_VALUE){
                    sendPromise(false, accepted, accepted_val);
                }else{
                    sendPromise(false, 0, null);
                }
            }else{
                sendPromise(true, 0, null);
            }
        }

        public void accepted(int req_num, Object req_val){
            if(req_num <= promised) sendAccepted(true);
            else sendAccepted(false);
        }
    }


}
