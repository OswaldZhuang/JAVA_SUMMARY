package ds.tree.binary_tree_series;

/*
 * 平衡二叉树(AVL树)
 * 或者是空树,或者左子树与右子树的深度之差(平衡因子)不超过1,且左右子树都是AVL树
 * AVL是二叉查找树
 */
public class BalanceBinaryTree {
	
	class AVLNode{
		AVLNode right;
		AVLNode left;
		AVLNode parent;
		int val;
		//当前节点所在树的高度
		int height;
		AVLNode(int val){
		    this.val = val;
        }
	}
	
	private AVLNode root;

	//获取该节点的左右子树的高度差
	private int balance(AVLNode node){
        return node == null ? -1 : (node.left.height - node.right.height);
    }
    
    /*
     * AVL添加节点的时候,为了保持平衡,要进行左旋或者右旋
     * val为需要插入的值,t为查找的起始节点（更通俗的说是不平衡的那个点）
     * 返回插入的子节点(子树)
     *  插入过后需要检查插入的节点是否破坏了平衡性
     *  平衡性的判断依据为检查该节点的左子树和右子树的高度差
     *  如果左子树比右子树的高度大2（也就是插入在t的左子树），并且插入的值比t的值小，那么属于LL
     *  如果左子树比右子树的高度大2，并且插入的值比t的值大，那么属于LR
     *  如果右子树比左子树的高度大2（也就是插入在t的右子树），并且插入的值比t的值大，那么属于RR
     *  如果右子树比左子树的高度大2，并且插入的值比t的值小，那么属于RL
	 *	旋转的条件为:
	 *	LL->t右旋
	 *	RR->t左旋
	 *	LR->t的左子树左t右旋
	 *	RL->t的右子树右t左旋
     */
	public AVLNode insert(int val, AVLNode t) {

        /**
         * 如果是插入的空树的话那么直接返回该节点
         * 这也是递归的终止条件
         */
        if (t == null) {
            return new AVLNode(val);
        }

        //根据值的大小插入到t的不同子树中
        if (val < t.val) {
            t.left = insert(val, t.left);
        } else if (val > t.val) {
            t.right = insert(val, t.right);
        } else {
            //不能插入值相等的节点
            return t;
        }

        //更新节点的高度
        t.height = 1 + Math.max(t.left.height, t.right.height);

        //插入到左子树中，并且不平衡
        if (balance(t) > 1) {
            if (val < t.left.val) return rightRotate(t); //LL 类型
            if (val > t.left.val) return leftRightRotate(t); //LR 类型
        } else if (balance(t) < -1) {//插入到右子树中，并且不平衡
            if (val > t.right.val) return leftRotate(t); //RR 类型
            if (val < t.right.val) return rightLeftRotate(t); //LR 类型
        }
        return t;

    }
	
	private int height(AVLNode node) {
		return node == null ? -1 : (node.left.height - node.right.height);
	}


	/*
	 * 左旋类似,参考下图
	 */
	private AVLNode leftRotate(AVLNode node) {
	    AVLNode r = node.right;
	    AVLNode r_l = r.left;
	    r.left = node;
	    node.right = r_l;

	    r.height = Math.max(r.left.height, r.right.height) + 1;
        node.height = Math.max(node.left.height, node.left.height) + 1;
        return null;
	}
	
	/*
	 * 右旋：l为插入的节点，l既可以是k_l的左节点也可以是其右节点（都比k小，k即为参数node）
	 *          m(10)                                           m(10)
	 *         /     \                                        /      \
	 *       k(7)    s(13)                                  k_l(5)   s(13)
	 *      /              \                              /     \         \
	 *   k_l(5)          y(21)      ==>                l(3)     k(7)     y(21)
	 *    /                                                           
	 *   l(3)                                                       
	 */
	private AVLNode rightRotate(AVLNode node) {
        //旋转
        AVLNode l = node.left;
        AVLNode l_r = l.right;
        l.right = node;
        node.left = l_r;

        //更新高度
        l.height = Math.max(l.left.height, l.right.height) + 1;
        node.height = Math.max(node.left.height, node.left.height) + 1;

        return l;
	}
	//左右旋
	/*     
	 *            m(10)                                                            m(10)                                                                        m(10)
	 *           /     \                                                          /     \                                                                     /      \
	 *         k(7)    s(13)                                                    k(7)  s(13)                                                                l(6)      s(13)
	 *        /            \                ===>首先左旋                         /          \                      ===>右旋                                 /   \           \
	 *    k_l(5)           y(21)                (k_l为支点)                   l(6)        y(21)                         (k为支点)                        k_l(5) k(7)      y(21)
	 *         \                                                             /
	 *         l(6)                                                      k_l(5)
	 */
	private AVLNode leftRightRotate(AVLNode node) {
		node.left = leftRotate(node.left);
		return rightRotate(node);
	}
	//右左旋
	/*
	 * 类似于上
	 */
	private AVLNode rightLeftRotate(AVLNode node) {
		node.right = rightRotate(node.right);
		return leftRotate(node);
	}
}
