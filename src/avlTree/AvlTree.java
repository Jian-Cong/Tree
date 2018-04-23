package avlTree;

/**
 * Created by Jian on 2018/4/15.
 */
public class AvlTree<K,V> {
    private K key;
//    private V value;
    private AvlTree<K,V> left;
    private AvlTree<K,V> right;
    private int height;
    private int count;

    AvlTree(K key,AvlTree<K,V> left, AvlTree<K,V> right){
        super();
        this.key = key;
        this.left = left;
        this.right = right;
    }

    private int compare(K k1,K k2){
        return Integer.valueOf(k1.toString()).compareTo(Integer.valueOf(k2.toString()));
    }

    public int getHeight(AvlTree<K,V> tree){
        return tree != null?tree.height:-1;
    }

    /**
     * 左左旋转
     * @param tree
     * @return
     */
    public AvlTree<K, V> LLRotate(AvlTree<K, V> tree){
        AvlTree<K, V> top = tree.left;
        tree.left = top.right;
        top.right = tree;

        tree.height = Math.max(getHeight(tree.left),getHeight(tree.right))+1;
        top.height = Math.max(getHeight(top.left),getHeight(top.right))+1;
        return top;
    }

    /**
     * 右右旋转
     * @param tree
     * @return
     */
    public AvlTree<K, V> RRRotate(AvlTree<K, V> tree){
        AvlTree<K, V> top = tree.right;
        tree.right = top.left;
        top.left = tree;

        tree.height = Math.max(getHeight(tree.left),getHeight(tree.right))+1;
        top.height = Math.max(getHeight(top.left),getHeight(top.right))+1;
        return top;
    }

    /**
     * 左右旋转,
     * 先对失衡节点的左子树进行右右旋转，再对失衡节点进行左左旋转
     * @param tree
     * @return
     */
    public AvlTree<K, V> LRRotate(AvlTree<K, V> tree){
        tree.left = RRRotate(tree.left);

        return LLRotate(tree);
    }

    /**
     * 右左旋转
     * 先对失衡节点的右子树左左旋转，再对失衡节点右右旋转
     * @param tree
     * @return
     */
    public AvlTree<K, V> RLRotate(AvlTree<K, V> tree){
        tree.right = LLRotate(tree.right);

        return RRRotate(tree);
    }


    public AvlTree<K, V> add(K key, AvlTree<K, V> tree){
        if (tree == null)
            tree = new AvlTree<K, V>(key,null, null);
        else{
            if(compare(key,tree.key) <0){
                tree.left = add(key,tree.left);
                // 判断是否失衡
                if(getHeight(tree.left)-getHeight(tree.right) == 2){
                    System.out.println("左左失衡节点是"+tree.key);
                    // 左左
                    if(compare(key,tree.left.key) < 0){
                        tree = tree.LLRotate(tree);
                    }else
                        // 左右
                        if(compare(key,tree.left.key) > 0){
                        tree = tree.LRRotate(tree);
                    }
                }
            }else if(compare(key,tree.key) >0){
                tree.right = add(key,tree.right);
                // 判断是否失衡
                if(getHeight(tree.right)-getHeight(tree.left) == 2) {
                    System.out.println("右右失衡节点是"+tree.key);
                    if (compare(key, tree.right.key) > 0) {
                        tree = tree.RRRotate(tree);
                    } else if (compare(key, tree.right.key) < 0){
                        tree = tree.RLRotate(tree);
                    }
                }
            }
        }
        if(key.equals(tree.key)){
            this.count ++;
        }
        tree.height = Math.max(getHeight(tree.left),getHeight(tree.right))+1;
        return tree;
    }

    public void pre(AvlTree<K,V> tree, AvlTree right){
        if (tree != null) {
            System.out.print(tree.key+"("+tree.height+"),");
        }
        if(tree.left != null){
            pre(tree.left,tree.left.right);
        }
        if (right != null){
            pre(right,right.right);
        }
    }
    public void mid(AvlTree<K,V> tree, AvlTree right){
        if(tree.left != null){
            mid(tree.left,tree.left.right);
        }
        if (tree != null) {
            System.out.print(tree.key+"("+tree.height+"),");
        }
        if (right != null){
            mid(right,right.right);
        }
    }
    public void back(AvlTree<K,V> tree, AvlTree right){
        if(tree.left != null){
            back(tree.left,tree.left.right);
        }
        if (right != null){
            back(right,right.right);
        }
        if (tree != null) {
            System.out.print(tree.key+"("+tree.height+"),");
        }
    }
    public void printResult(AvlTree<K,V> tree){
        System.out.println("前序遍历：");
        tree.pre(tree,tree.right);
        System.out.println("\n中序遍历：");
        tree.mid(tree,tree.right);
        System.out.println("\n后序遍历：");
        tree.back(tree,tree.right);
    }

    /**
     * 删除
     * @param key
     * @param tree
     * @return
     */
    public AvlTree<K,V> delete(K key,AvlTree<K,V> tree){
        if(tree == null){
            return null;
        }
        // 左子树
        if(compare(key,tree.key) < 0){
            tree.left = delete(key,tree.left);
            // 判断是否失衡
            if(getHeight(tree.left)-getHeight(tree.right) == 2){
                // 左左
                if(compare(key,tree.left.key) < 0){
                    tree = tree.LLRotate(tree);
                }else
                    // 左右
                    if(compare(key,tree.left.key) > 0){
                        tree = tree.LRRotate(tree);
                    }
            }
        }else if(compare(key,tree.key) > 0){
            tree.right = delete(key,tree.right);
            // 判断是否失衡
            if(getHeight(tree.right) - getHeight(tree.left) == 2){
                // 右右
                if(compare(key,tree.right.key) > 0){
                    tree = tree.RRRotate(tree);
                }else
                    // 右左
                    if(compare(key,tree.right.key) < 0){
                    tree = tree.RLRotate(tree);
                }
            }
        }
        // 相等
        if(compare(key,tree.key) == 0){
            // 有重复的节点，惰性删除
            if(tree.count > 0){
                this.count --;
            }
            else {
                if(tree.left != null && tree.right != null){
                    // 找到右子树的最小节点
                    tree.key = findMin(tree.right).key;
                    tree.right = delete(tree.key,tree);
                }else{
                    tree = tree.left != null?tree.left:tree.right;
                    if(tree == null){
                        return null;
                    }
                }
            }
        }
        tree.height = Math.max(getHeight(tree.left),getHeight(tree.right))+1;
        return tree;
    }

    /**
     * 找到tree的子树中最小的
     * @param tree
     * @return
     */
    public AvlTree<K, V> findMin(AvlTree<K,V> tree) {
        if(tree.left == null && tree.right == null)
            return tree;
        else{
            tree = (tree.left != null && tree.right != null)?tree.left:tree.left;
            tree = findMin(tree);
        }
        return tree;
    }

    public static void main(String[] args){
        AvlTree<Integer,Integer> tree = new AvlTree(10,null,null);
//        tree.add(10,tree);
        tree = tree.add(5,tree);
        tree = tree.add(15,tree);
        tree =  tree.add(2,tree);
        tree =  tree.add(3,tree);
//        tree = tree.add(3,tree);
//        tree = tree.add(6,tree);
//        tree = tree.add(1,tree);tree = tree.add(3,tree);tree = tree.add(4,tree);
//        tree = tree.add(7,tree);
//        tree = tree.add(22,tree);



        tree.printResult(tree);
//        System.out.println("\n删除节点:");
//        tree = tree.delete(6,tree);
//        tree.printResult(tree);
    }
}
