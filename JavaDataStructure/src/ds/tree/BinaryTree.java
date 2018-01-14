package ds.tree;

import java.util.Stack;

/*
 * 二叉树
 */
public class BinaryTree {
    
    class BinaryNode{
         int key;
         BinaryNode left;
         BinaryNode right;
         BinaryNode parent;
    }
    
    /*
     * 二叉树的遍历:
     * 前序遍历:根左右,
     * 中序遍历:左根右,
     * 后序遍历:左右根,
     */
    /*
     * 以中序遍历为例
     * 递归实现
     */
    public void in_order_goThrough(BinaryNode node) {
        if(node != null) {
            in_order_goThrough(node.left);
            System.out.println(node.key);
            in_order_goThrough(node.right);
        }
    }
    /*
     * 非递归实现
     * 以中序遍历为例
     * 思路如下:
     * 1.中序遍历的规则是左根右,那么最开始的节点一定是最左边的节点(如果没有的话就是它的父节点)
     * 2.因此要找到最左边的节点,方法就是不断的调用node.left,终止条件为node.left == null
     * 3.而每次得到的节点一定是下一次得到节点的父节点,也就是中序遍历中的"中",刚才遍历节点的顺序与
     * 实际的中序遍历的节点顺序是相反的(这里指的是"中"节点),因此在遍历的过程中考虑用栈来存储,这样
     * 出栈的顺序就和实际顺序一致了
     * 4.综上所述,我们先不断的找到节点的左节点(以根节点开头,终止条件是该节点的左节点为空),在
     * 这个过程中,把节点入栈
     * 5.上述工作准备完成后,将节点出栈,先访问该节点的值("中"),然后再得到该节点的右节点,仍然依照刚才的方法
     * 不断得到左节点并入栈,入栈完毕后再出栈,访问该值,得到该节点的右节点.....
     */
    public void in_order_goThrough_normal(BinaryNode node) {
        Stack<BinaryNode> stack = new Stack<>();
        while(node != null || !stack.empty()) {
            /*
             * 不断的入栈左节点的过程
             */
            while(node != null) {
                stack.push(node);
                node = node.left;
            }
            /*
             * 出栈并访问该节点值(即"中"),得到右节点的过程
             */
            if(!stack.empty()) {
                node = stack.pop();
                System.out.println(node.key);
                node = node.right;
            }
        }
    }
}
