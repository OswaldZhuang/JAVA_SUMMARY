package ds.tree.non_binary_tree_series;


/*
 * B+树
 * m阶B+树特性如下：
 * 有k个子树的分支节点包含k个关键码
 * 所有的数据都在叶子节点上，且叶子节点会链接起来，叶子节点中的元素从小到达
 * 分支节点的关键码都存在于叶子节点，并且是叶子节点的最大（小）元素
 */
public class BplusTree {

    /**
     * 比如：
     *                    8，15
     *                  /     \
     *              2,5,8     11,15
     *            /   |  \     |   \
     *         1,2->3,5->6,8->9,11->13,15
     */

    /**
     * B树与B+树区别：
     * B+树的所有数据都存储在叶子节点中，
     * B+树中有k个关键码的节点有k个子树（B树是k-1个）
     */

}
