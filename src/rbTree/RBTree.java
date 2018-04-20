package src.rbTree;

import com.sun.org.apache.regexp.internal.RE;

public class RBTree {
    public static final String RED = "red";
    public static final String BLACK = "black";

    private int key;
    private RBTree parent;
    private RBTree left;
    private RBTree right;
    private String color = RED;

    public RBTree() {}

    public RBTree (int key, RBTree left, RBTree right){
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

    public void add(int key,RBTree tree){
        RBTree parent = null;
        while(tree != null){
            parent = tree;
            if(key < tree.key){
                tree = tree.left;
            }else {
                tree = tree.right;
            }
        }
        tree = new RBTree(key,null,null);
        tree.parent = parent;
        if(key < parent.key){
            parent.left = tree;
        }else {
            parent.right = tree;
        }

        /*
        if(tree == null){
            tree = new RBTree(key,null,null);
        }else{
            if(key < tree.key){
                tree.left = add(key,tree.left);
                tree.left.parent = tree;
                tree = addFix(tree);
            }else{
                tree.right = add(key,tree.right);
                tree.right.parent = tree;
                tree = addFix(tree);
            }
        }
        return tree;*/
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
        }
        top.right = tree;
        tree.parent = top;
        return top;
    }

    public RBTree addFix(RBTree tree){
        if(tree.parent == null)
            tree.color = BLACK;
        else {
            RBTree parent = tree.parent;
            RBTree grandP = parent.parent;
            RBTree uncle = null;
            if(grandP != null){
                // parent是祖父的左孩子
                if(parent.key == grandP.left.key){
                    uncle = grandP.right;
                    if(RED.equals(parent.color)){
                        if(RED.equals(uncle.color)){
                            parent.color = BLACK;
                            uncle.color = BLACK;
                            grandP.color = RED;
                            addFix(grandP);
                        }else
                            // 左孩子，叔叔黑色
                            if(tree.key < parent.key){
                            parent.color = BLACK;
                            grandP.color = RED;
                            grandP = rightRotate(grandP);
                        }else
                            // 右孩子 叔叔黑色
                            {
                                addFix(parent);
                                parent = leftRrotate(parent);
                            }
                    }
                }else if(parent.key == grandP.right.key){
                    uncle = grandP.left;
                }

                uncle = (grandP.left != null && grandP.left.key == tree.key)?grandP.right:tree.left;
                if(uncle != null){
                    // 叔叔节点是红的
                    if(RED.equals(uncle.color)){
                        tree.parent.color = BLACK;
                        uncle.color = BLACK;
                        grandP.color = RED;
                        addFix(grandP);
                    }else if(tree.key< tree.parent.key){
                        tree.parent.color = BLACK;
                        grandP.color = RED;
                        // 右旋
                       tree = rightRotate(grandP);
                    }else if(tree.key > tree.parent.key){
                        tree = leftRrotate(tree.parent);
                    }
                }
            }
        }
        return tree;
    }

    public static void main(String[] args){
        RBTree tree = new RBTree(3,null,null);
        tree.add(2,tree);
        tree.add(4,tree);
        tree.add(1,tree);
        tree.print(tree);

    }
}
