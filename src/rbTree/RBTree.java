package src.rbTree;

import com.sun.org.apache.regexp.internal.RE;

/**
 * 根节点单独定义，旋转的时候判断根节点有没有改变
 *
 * 添加节点进行的旋转并没有新的节点替换当前节点
 */
public class RBTree {
    public static final String RED = "red";
    public static final String BLACK = "black";
    public static RBTree root;
    public static RBTree leaf = new RBTree(BLACK);


    private Integer key;
    private RBTree parent;
    private RBTree left;
    private RBTree right;
    private String color = RED;

    public RBTree(String color) {}

    public RBTree (Integer key, RBTree left, RBTree right){
        this.key = key;
        this.left = left;
        this.right = right;
    }

    public void pre(RBTree tree){
        if(tree != null){
            System.out.print(tree.key+"("+tree.color+",parent:"+(tree.parent!=null?tree.parent.key:"")+"),");
        }
        if(tree.left != null){
            pre(tree.left);
        }
        if(tree.right != null){
            pre(tree.right);
        }
    }

    public void mid(RBTree tree){
        if(tree.left != null){
            mid(tree.left);
        }
        System.out.print(tree.key+"("+tree.color+",parent:"+(tree.parent!=null?tree.parent.key:"")+"),");
        if(tree.right != null){
            mid(tree.right);
        }
    }

    public void back(RBTree tree){
        if(tree.left != null){
            back(tree.left);
        }
        if(tree.right != null){
            back(tree.right);
        }
        System.out.print(tree.key+"("+tree.color+",parent:"+(tree.parent!=null?tree.parent.key:"")+"),");
    }

    public void print(RBTree tree){
        System.out.println("前序遍历：");
        tree.pre(tree);
        System.out.println("\n中序遍历：");
        tree.mid(tree);
        System.out.println("\n后序遍历：");
        tree.back(tree);
    }

    public void add(int key){
        RBTree temp = root;
        RBTree parent = null;
        while(temp != null){
            parent = temp;
            if(key < temp.key){
                temp = temp.left;
            }else {
                temp = temp.right;
            }
        }
        RBTree newTree = new RBTree(key,null,null);
        newTree.parent = parent;
        if(key < parent.key){
            parent.left = newTree;
        }else {
            parent.right = newTree;
        }
        addFix(newTree);
    }

    /**
     * 左旋
     * @param tree
     * @return
     */
    public RBTree leftRrotate(RBTree tree){
        // 找出旋转后的顶点
        RBTree top = tree.right;
        // 断开顶点左孩子
        tree.right = top.left;
        // 左孩子不为空，改变左孩子的父亲
        if(top.left != null)
            top.left.parent = tree;
        // 新顶点的继承旧顶点的父亲
        top.parent = tree.parent;
        // 替换旧顶点的子节点
        if(tree.parent != null){
            if(tree.key == tree.parent.left.key){
                tree.parent.left = top;
            }else if(tree.key == tree.parent.right.key){
                tree.parent.right = top;
            }
        }else{
            // tree的 父节点为空，表示tree是根节点；旋转后top节点作为根节点
            root = top;
            root.color = BLACK;
        }
        // 旧顶点成为新顶点的左孩子
        top.left = tree;
        // 给旧顶点找个爹
        tree.parent = top;
        return top;
    }

    /**
     * 右旋
     * @param tree
     * @return
     */
    public RBTree rightRotate(RBTree tree){
        RBTree top = tree.left;
        tree.left = top.right;
        if(top.right != null)
            top.right.parent = tree;
        top.parent = tree.parent;
        if(tree.parent != null){
            if(tree.key == tree.parent.left.key){
                tree.parent.left = top;
            }else if(tree.key == tree.parent.right.key){
                tree.parent.right = top;
            }
        }else{
            // tree的 父节点为空，表示tree是根节点；旋转后top节点作为根节点
            root = top;
            root.color = BLACK;
        }
        top.right = tree;
        tree.parent = top;
        return top;
    }

    /**
     * tree 是新添加的节点，不是整个树
     * @param tree
     */
    public void addFix(RBTree tree) {
        RBTree parent,grandP,uncle = null;

        while((parent= tree.parent) != null && RED.equals(parent.color)) {
            grandP = parent.parent;
            if (grandP != null) {
                // parent是祖父的左孩子
                if (parent.key < grandP.key) {
                    uncle = grandP.right;
                    if (RED.equals(parent.color)) {
                        // 叔叔节点存在且是红色
                        if (uncle != null && RED.equals(uncle.color)) {
                            parent.color = BLACK;
                            uncle.color = BLACK;
                            grandP.color = RED;
                            RBTree temp = null;
                            temp = tree;
                            tree = grandP;
                            grandP = temp;
                        } else
                            // 右孩子 叔叔黑色
                            if (tree.key > parent.key) {
                                RBTree temp = null;
                                leftRrotate(parent);
                                temp = tree;
                                tree = parent;
                                parent = temp;
                            }else {
                                // 左孩子，叔叔黑色
                                parent.color = BLACK;
                                grandP.color = RED;
                                rightRotate(grandP);
                            }
                    }
                } else if (parent.key > grandP.key) {
                    uncle = grandP.left;
                    if(RED.equals(parent.color)){
                        if(uncle != null && RED.equals(uncle.color)){
                            parent.color = BLACK;
                            uncle.color = BLACK;
                            grandP.color = RED;
                            RBTree temp = null;
                            temp = tree;
                            tree = grandP;
                            grandP = temp;
                        }else
                            // 左子树
                        if(tree.key < parent.key){
                            rightRotate(parent);
                            RBTree temp = null;
                            temp = tree;
                            tree = parent;
                            parent = temp;
                        }else {
                            parent.color = BLACK;
                            grandP.color = RED;
                            leftRrotate(grandP);
                        }
                    }
                }
            }/*else{
                tree = parent;
            }*/
        }
        if(parent == null){
            root = tree;
            root.color = BLACK;
        }

    }


    public void remove(int key){
        RBTree parent = null;
        RBTree tree = root;
        // 实际删除的节点
        RBTree deleteNode = null;
        // deleteNode的子节点
        RBTree deleteChild = null;
        while(tree != null){
            if(key < tree.key){
                tree = tree.left;
            }else if(key > tree.key){
                tree = tree.right;
            }else {
                parent = tree.parent;
                deleteNode = tree;
                // 被删除节点tree是叶子节点
                if(tree.left == null && tree.right == null){
                    if(tree.key < parent.key){
                        parent.left = null;
                    }else{
                        parent.right = null;
                    }
                    deleteChild = leaf;
                }else
                // 被删除节点tree左子树为空
                if(tree.left == null){
                    if(tree.key < parent.key) {
                        parent.left = tree.right;
                    }else{
                        parent.right = tree.right;
                    }
                    tree.right.parent = parent;

                    deleteChild = tree.right;
                }else
                // 被删除节点tree右子树为空
                 if (tree.right == null){
                    if(tree.key < parent.key){
                        parent.left = tree.left;
                    }else {
                        parent.right = tree.left;
                    }
                    tree.left.parent = parent;

                     deleteChild = tree.left;
                }else
                    // 左右子树都不为空，找到后继节点并删除
                    {
                        RBTree nextTree = findNext(tree);
                        deleteNode = nextTree;
                        if(nextTree != null){
                            tree.key = nextTree.key;
                            // 后继节点为左子树
                            if(nextTree.key < nextTree.parent.key) {
                                nextTree.parent.left = nextTree.right;
                                deleteChild = leaf;
                            }else{
                                nextTree.parent.right = nextTree.right;
                                deleteChild = nextTree.right;
                            }
                            if(nextTree.right != null) {
                                nextTree.right.parent = nextTree.parent;
                            }
                            break;
                        }
                }
            }
        }

        // 删除的节点是黑色节点，修复红黑树
        if(deleteNode != null && BLACK.equals(deleteNode.color)){
            System.out.println("删除的节点颜色是黑色");
        }
    }


    public void remoVeFix(RBTree child,RBTree parent){
        RBTree brother = null;
        while(child == null || (RED.equals(child.color) && child.parent != null)){
            if(parent.left == child){
                brother = parent.right;
                // 兄弟节点是红色
                if(RED.equals(brother.color)){
                    brother.color = BLACK;
                    parent.color = RED;
                    leftRrotate(parent);
                    brother = parent.right;
                }else
                    // 兄弟节点的子节点都是黑色
                    if((brother.left == null || BLACK.equals(brother.left))&& (brother.right == null || BLACK.equals(brother.right))){
                        brother.color = RED;
                        child = parent;
                        parent = parent.parent;
                }else {
                        // 兄弟节点的左孩子是红色，右孩子是黑色
                        if (brother.right == null || BLACK.equals(brother.right)) {
                            brother.left.color = BLACK;
                            brother.color = RED;
                            rightRotate(brother);
                            brother = parent.right;
                        }
                        brother.color = parent.color;
                        parent.color = BLACK;
                        brother.right.color = BLACK;
                        leftRrotate(parent);

                }
            }else{

            }

        }
    }

    /**
     * 查找tree节点的后继节点
     * @param tree
     * @return
     */
    public RBTree findNext(RBTree tree){
        if(tree.key == root.key && root.right != null || tree.right != null){
            tree = tree.right;
        }
        // 左子树
        if(tree.key < tree.parent.key){
            while(tree.left  != null) {
               tree = tree.left;
            }
        }else{
            while(tree.left != null){
               tree = tree.left;
            }
        }
        return tree;
    }

    /**
     *根据key值查找节点的后继节点
     * @param key
     */
    public void next(int key){
        RBTree tree = root;
        while(tree != null && key != tree.key) {
            if (key < tree.key) {
                tree = tree.left;
            }else if(key > tree.key){
                tree = tree.right;
            }else{
                break;
            }
        }
        System.out.println("\n "+findNext(tree).key);
    }

    public static void main(String[] args){
        root = new RBTree(80,null,null);
        root.color = BLACK;
//        RBTree tree = root;
        root.add(60);
        root.add(120);
        root.add(40);
        root.add(70);
        root.add(140);
        root.add(20);
        root.add(50);
        root.add(35);
        root.add(36);
        root.add(45);
        root.add(51);
        root.print(root);

        System.out.println("\n 删除节点:");
        root.remove(40);
        root.print(root);


    }
}
