package ds.tree.binary_tree_series;

/*
 * 红黑树:
 * 0.红黑树是二叉搜索树
 * 1.根节点是黑色
 * 2.如果一个节点是红色,那么它的子节点必须是黑色
 * 3.一个节点到该节点的叶子节点的所有路径黑色节点数目相同
 * 4.叶子节点为黑色(叶子节点也即NIL节点,也叫做"哨兵")
 * 
 * 因此红黑树的重要特性之一就是:从根节点到叶子节点的最长路径不会比从根节点到叶子节点的最短路径大两倍
 * (首先,最长的路径是红-黑-红-黑...,而最短路径是黑-黑-...,而由于条件3的限制,最长路径与最短路径
 * 有相同的黑色节点,而在最长路径上红色节点又不可能比黑色节点多[根和叶都是黑色的],因此上述结论 成立)
 */
public class RBTree { 
    
    /*
     * TreeMap,HashMap,epoll是由红黑树实现的 
     */
	
	/*
	 * 包含n个节点的红黑树高度最多为2log(n+1)
	 */
	
	class RBNode{
		//节点颜色,红色为1,黑色为0
		boolean color;
		//左节点
		RBNode left;
		//右节点
		RBNode right;
		//父节点
		RBNode parent;
		//键值
		int key;
	}
	
	/*
	 * 插入了节点后,会对树的平衡性造成破坏,因此需要一些措施恢复树的平衡性
	 * 采用两种办法:左旋,右旋
	 */
	
	/*
	 * 左旋的主要特点在于位于旋转过程顶部的节点k会变成左节点k',而k的右节点r会旋转到k
	 * 的位置,r的左子树r_l会成为k'的右子树
	 */
	public void leftRotate(RBNode node) {
		//这里node即为上述的k
		//该过程均为考虑节点为空的情况
		RBNode k_parent = node.parent;
		RBNode r = node.right;
		//假设k处在左节点上
		k_parent.left = r;
		RBNode r_l = r.left;
		node.right = r_l;
		r.left = node;
	}
	
	/*
	 * 右旋的主要特点在于位于旋转过程顶部的节点k会变成右节点k',而k的左节点l会旋转到k的位置
	 * l的右子树l_r会成为k'的左子树
	 */
	public void rightRotate(RBNode node) {
		//这里node即为上述的k
	    //该过程均为考虑节点为空的情况
		RBNode k_parent = node.parent;
		RBNode l = node.left;
		//假设k处在左节点上
		k_parent.left = l;
		RBNode l_r = l.right;
		l_r = node.left;
		l.right = node;
	}	
	
	
	//插入节点
	//首先,插入的节点必须是红色,因为如果插入黑色节点,那么很容易违反上述规则3,而插入红色节点,违反上述规则2的可能性较小
	public void insertNode() {
		
	}
	
}
