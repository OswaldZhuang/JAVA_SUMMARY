/**
 * CAP 和 BASE理论
 */
public class CAPandBASETheory {
    /**
     * CAP：
     * C:一致性,Consistency
     * 在replicate模式下,数据在主从之间能够保持一致
     * A:可用性,Availability
     * 系统的服务必须一直处在可用的状态,对于用户的每一个操作请求总是有限时间内能够返回结果
     * P:分区容错性,Partition tolerance
     * 在遇到网络分区故障的时候(子网内部正常,子网之间网络不通,即俗称的"脑裂"),系统仍然能够对外提供一致性和可用性服务
     * 在分布式系统中,上述三个条件不可能同时成立,最多只有其中两个能同时成立
     *
     * 而分布式系统中P是必须成立的(当网络出现问题的时候整个服务必须可用),因此主要考虑C和A之间的平衡问题
     */

    /**
     * BASE：
     * basically availability：基本可用
     *  响应时间的损失，系统功能的损失
     * soft state：软状态
     *  系统中允许存在中间状态
     * eventually consistent：最终一致
     *  数据的一致性不需要强一致
     */
}
