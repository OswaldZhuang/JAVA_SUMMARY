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
	}
	
	private AVLNode root;
    
    /*
     * AVL添加节点的时候,为了保持平衡,要进行左旋或者右旋
     * val为需要插入的值,t为查找的起始节点
     * 返回插入的子节点(子树)
     *  插入过后需要检查插入的节点是否破坏了平衡性
	 *	一般来说,检查插入节点的祖父节点和其兄弟的子树的高度差是否大于1即可
	 *	旋转的条件为:LL->右旋 RR->左旋 LR->左右旋 RL->右左旋
     */
	public AVLNode insert(int val, AVLNode t) {
		AVLNode node = new AVLNode();
		node.val = val;
		if(root == null) {
			root = node;
			return root;
		}
		//插入到左子树
		if(val < t.val) {
			t.left = insert(val, t.left);
			//因为是插入到左子树,因此只需要比较左右
			//子树的高度差即可
			if(height(t.left) - height(t.right) == 2) {
				//未平衡,需要调整
				if (t.val < t.left.val) {
					//LL,右旋
					t = rightRotate(t);
				}else if(t.val > t.left.val) {
					//LR,左右旋
					t = leftRightRotate(t);
				}
			}
		}else if(val > t.val) {//插入到右子树
			t.right = insert(val, t.right);
			//同上
			if(height(t.right) - height(t.left) == 2) {
				//未平衡,需要调整
				if (t.val < t.left.val) {
					//RL,右左旋
					t = rightLeftRotate(t);
				}else if(t.val > t.left.val) {
					//RR,左旋
					t = leftRotate(t);
				}
			}
		}else {
			
		}
		t.height = Math.max(height(t.left), height(t.right))+1;
		return t;
	}
	
	private int height(AVLNode node) {
		return node == null ? -1 : node.height;
	}
	
	
	/*
	 * 左旋类似,参考下图
	 */
	private AVLNode leftRotate(AVLNode node) {
		return null;
	}
	
	/*
	 * 右旋
	 *          m(10)                                      m(10)
	 *         /     \                                        /      \
	 *       k(7)    s(13)                             k_l(5)   s(13)
	 *      /              \                              /     \         \
	 *   k_l(5)          y(21)      ==>       l(3)     k(7)     y(21)
	 *    /                                                           
	 *   l(3)                                                       
	 */
	private AVLNode rightRotate(AVLNode node) {
		return null;
	}
	//左右旋
	/*     
	 *            m(10)                                                       m(10)                                                                    m(10)
	 *           /     \                                                          /     \                                                                     /      \
	 *         k(7)    s(13)                                               k(7)  s(13)                                                             l(6)      s(13)
	 *        /            \                ===>首先左旋            /          \                      ===>右旋                        /   \           \
	 *    k_l(5)           y(21)                (k_l为支点)        l(6)        y(21)                         (k为支点)             k_l(5) k(7)      y(21)
	 *         \                                                             /
	 *         l(6)                                                      k_l(5)
	 */
	private AVLNode leftRightRotate(AVLNode node) {
		node.left = leftRotate(node.left);
		return rightRotate(node);
	}
	//左右旋
	/*
	 * 类似于上
	 */
	private AVLNode rightLeftRotate(AVLNode node) {
		node.right = rightRotate(node.right);
		return leftRotate(node);
	}
}
