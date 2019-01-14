package ds.tree.binary_tree_series;

/*
 * 红黑树:
 * 0.红黑树是二叉搜索树
 * 1.根节点是黑色
 * 2.如果一个节点是红色,那么它的子节点必须是黑色
 * 3.一个节点到该节点的所有叶子节点的所有路径黑色节点数目相同
 * 4.叶子节点为黑色(叶子节点也即NIL节点,也叫做"哨兵")
 * 
 * 因此红黑树的重要特性之一就是:从根节点到叶子节点的最长路径不会比从根节点到叶子节点的最短路径大两倍
 * (首先,最长的路径是红-黑-红-黑...,而最短路径是黑-黑-...,而由于条件3的限制,最长路径与最短路径
 * 有相同的黑色节点,而在最长路径上红色节点又不可能比黑色节点多[根和叶都是黑色的],因此上述结论 成立)
 *
 * 和AVL树相比，红黑树在插入的时候有更少的旋转操作，在插入操作频繁的case中，红黑树更加适用，而如果搜索操作更频繁，
 * 那么AVL树将更使用
 */
public class RBTree { 
    
    /*
     * TreeMap,HashMap,epoll是由红黑树实现的 
     */
	
	/*
	 * 包含n个节点的红黑树高度最多为2log(n+1)
	 */
	
	class RBNode{
		//节点颜色,红色为true,黑色为false
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
	
	private RBNode root;
	
	/*
	 * 插入了节点后,会对树的平衡性造成破坏,因此需要一些措施恢复树的平衡性
	 * 采用两种办法:左旋,右旋
	 */
	
	/*
	 * 左旋的主要特点在于位于旋转过程顶部的节点k会变成左节点k',而k的右节点r会旋转到k
	 * 的位置,r的左子树r_l会成为k'的右子树
	 *                                     k                                r
	 *                                   /   \                            /   \
	 *                                k_l     r         ====>            k'    r_r
	 *                                       /  \                       /  \
	 *                                    r_l    r_r                  k_l   r_l
	 */
	private void leftRotate(RBNode node) {
		//这里node即为上述的k
		RBNode k_parent = node.parent;
		RBNode r = node.right;
		if(k_parent.left == node) {
			k_parent.left = r;
		}else {
			k_parent.right = r;
		}
		RBNode r_l = r.left;
		node.right = r_l;
		r.left = node;
	}
	
	/*
	 * 右旋的主要特点在于位于旋转过程顶部的节点k会变成右节点k',而k的左节点l会旋转到k的位置
	 * l的右子树l_r会成为k'的左子树
	 *                          k                                l
	 *                        /   \                            /  \
	 *                       l      r        ====>           l_l    k'
	 *                     /  \                                    /  \
	 *                   l_l   l_r                                l_r  r
	 */
	private void rightRotate(RBNode node) {
		//这里node即为上述的k
		RBNode k_parent = node.parent;
		RBNode l = node.left;
		if(k_parent.left == node) {
			k_parent.left = l;
		}else {
			k_parent.right = l;
		}
		RBNode l_r = l.right;
		l_r = node.left;
		l.right = node;
	}	
	
	
	//插入节点
	//插入的节点必须是红色,因为如果插入黑色节点,那么很容易违反上述规则3,而插入红色节点,违反上述规则2的可能性较小
	public void insertNode(int i_key) {
		RBNode node = new RBNode();
		node.key = i_key;
		node.color = true;
		//红黑树和二叉搜索树类似,因此插入之前需要先找到插入节点
		RBNode insert_position = null;
		RBNode current = root;
		
		while(current != null) {
			insert_position = current;
			if(current.key < i_key) {
				current = current.right;
			}else {
				current = current.left;
			}
		}
		
		node.parent = insert_position;
		//插入的树不是空树
		if(insert_position != null) {
			if(insert_position.key < i_key) {
				insert_position.right = node;
			}else {
				insert_position.left = node;
			}
		}
		//由于插入之后会破坏红黑树,因此需要一定的修正
		fixRBTree(node);
	}

	//修正的主要操作为变色和旋转
    //叔节点为红色，那么需要变色
    //叔节点为黑色，那么需要旋转。插入的节点为父节点的左孩子，那么需要进行变色+右旋转一次；
    // 插入的节点为父节点的右孩子，那么需要左旋转一次+变色+右旋转一次
	private void fixRBTree(RBNode node) {
		/*
		 * 如果刚开始没有节点,那么插入的节点为根节点,根据定义
		 * node的颜色需要变为黑色
		 */
		if(root == null) {
			node.color = false;
			root = node;
		}
		/*
		 * 如果父节点的颜色为黑色,那么没有影响任何规则,什么也不需要做
		 */
		if(node.parent.color == false) {
			//do nothing
		}
		
		/*
		 * 插入节点的父节点是红色,那么就会违反定义,因此需要做一定的
		 * 调整
		 */
		if(node.parent.color == true) {
			RBNode pare = node.parent;
			RBNode grad = pare.parent;
			RBNode uncle = null;
			if(grad.left == pare) {
				uncle = grad.right;
			}else {
				uncle = grad.left;
			}
			/*
			 * 情况1:叔节点为红色(即父节点的父节点的另外一个子节点为红色)
			 * 这个时候,需要将父节点变为黑色,祖父节点变为红色,叔节点变为黑色
			 * 即:
			 * 
			 *            b                                       r
			 *          /   \                                    /  \
			 *        r       r        ====>                    b     b
			 *       /                                         /
			 *     r                                          r
			 */
			if(uncle.color == true) {
				pare.color = false;
				grad.color = true;
				uncle.color = false;
				//此时并没有结束，因为祖父节点变为了红色，如果其父节点为红色，那么需要进一步修正
				fixRBTree(grad.parent);
			}
			/*
			 * 情况2:叔节点为黑色,且插入节点为父节点的右子节点
			 *                  k(b)
			 *                /   \
			 *         k_r(r)   k_l(b)
			 *                \   
			 *                  n(r)
			 *  这时候,以插入节点的父节点(即k_r)为支点,进行左旋操作
			 *                  k(b)
			 *                 /   \
			 *             n(r)   k_l(b)
			 *             /
			 *          k_r(r)
			 *  旋转完成后,即变为情况3,随后再进一步操作 
			 */              
			else if(uncle.color == false && node == pare.right) {
				leftRotate(pare);
				pare.color = false;
				grad.color = true;
				rightRotate(grad);
			}
			/*
			 * 情况3:叔节点为黑色,且插入节点为父节点的左子节点
			 *                    k(b)
			 *                   /   \
			 *              k_r(r)  k_l(b)
			 *              /
			 *           n(r)
			 *  这时候父节点变为黑色,祖父节点变为红色,祖父节点为支点,右旋
			 *                    k(r)                                k_r(b)
			 *                   /   \                                  /  \
			 *              k_r(b)  k_l(b)      ===>                 n(r)   k(r)
			 *              /                                                 \
			 *           n(r)                                                  k_l(b)
			 */
			else if(uncle.color == false && node == pare.left) {
				pare.color = false;
				grad.color = true;
				rightRotate(grad);
			}
			
		}
		
	}
	
	
}
