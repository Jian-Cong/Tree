package binaryTree;

/**
 * Created by Jian on 2018/4/10.
 */
public class Tree<K,V> {
    private K key;
    private V value;
    private Tree<K,V> left;
    private Tree<K,V> right;
    private int count;

   public Tree(){};

    Tree (K key,V value,Tree<K,V> left, Tree<K,V> right){
        this.key = key;
        this.value = value;
        this.left = left;
        this.right = right;
    }

    private int compare(K k1,K k2){
       return k1.toString().compareTo(k2.toString());
    }

    public Tree add(K key,V value,Tree<K,V> tree){
        if(tree == null){
            tree = new Tree<K,V>(key,value,null,null);
            tree.count = 0;
            return tree;
        }
        if (compare(key,tree.key) < 0) {
            tree.left = add(key,value,tree.left);
        }
        else if(compare(key,tree.key) > 0){
            tree.right = add(key,value,tree.right);
        }else{
            tree.count ++;
        }
        return tree;
    }

    /**
     * 前序遍历
     * @param tree
     * @param right
     */
    public void pre(Tree<K,V> tree,Tree right){
        if (tree != null) {
            System.out.print(tree.key+"("+tree.count+"),");
        }
        if(tree.left != null){
            pre(tree.left,tree.left.right);
        }
        if (right != null){
            pre(right,right.right);
        }
    }

    /**
     * 中序遍历
     * @param tree
     * @param right
     */
    public void mid(Tree<K,V> tree,Tree right){
        if(tree.left != null){
            mid(tree.left,tree.left.right);
        }
        if (tree != null) {
            System.out.print(tree.key+"("+tree.count+"),");
        }
        if (right != null){
            mid(right,right.right);
        }
    }

    /**
     * 后序遍历
     * @param tree
     * @param right
     */
    public void back(Tree<K,V> tree,Tree right){
        if(tree.left != null){
            back(tree.left,tree.left.right);
        }
        if (right != null){
            back(right,right.right);
        }
        if (tree != null) {
            System.out.print(tree.key+"("+tree.count+"),");
        }
    }

    /**
     * 删除节点，废除不用
     * @param tree
     * @param del
     */
    public void delete(Tree<K,V> parent,Tree<K,V> tree,Tree<K,V> del){
        if(tree != null){
            if(compare(tree.key,del.key) > 0){
                delete(tree,tree.left,del);
            }
            if(compare(tree.key,del.key) < 0){
                delete(tree,tree.right,del);
            }
            if(compare(tree.key,del.key) == 0){
                if(--tree.count == -1){
                    // tree左子树不为空右子树为空,
                    if(tree.left != null && tree.right == null){
                        // 循环找到最小的节点，删除该位置，每次遍历key值为左子树的key值
                        while(tree.left != null && tree.left.left != null){
                            tree.key = tree.left.key;
                            tree = tree.left;
                        }
                        tree.key = tree.left.key;
                        tree.left = null;
                    } else
                        // tree有右子树
                        if(tree.right != null){
                            // 同左子树
                            while(tree.right != null && tree.right.right != null){
                                tree.key = tree.right.key;
                                tree = tree.right;
                            }
                            tree.key = tree.right.key;
                            tree.right = null;
                        }else{
                            if(tree == parent.left){
                                parent.left = null;
                            }else{
                                parent.right = null;
                            }
                        }
                }
            }
        }
    }

    /**
     * 删除节点
     * @param tree
     * @param key
     * @return
     */
    public Tree<K,V> remove(Tree<K,V> tree,K key){
        if(tree == null)
            return null;
        if(compare(key,tree.key) < 0){
            tree.left = remove(tree.left,key);
        }else if(compare(key,tree.key) > 0){
            tree.right = remove(tree.right,key);
        }else if(compare(key,tree.key) == 0){
            if(--tree.count == -1) {
                if (tree.left == null && tree.right == null) {
                    return null;
                } else
                    // 左右子树都不为空，找到后继节点，赋值后继节点的key到啊当前节点的key，删除后继节点
                    if (tree.left != null && tree.right != null) {
                        K oldKey = tree.key;
                        tree.key = findNext(tree.left).key;
                        remove(tree, oldKey);
                    } else
                    // 只有一个子节点
                    {
                        tree = tree.left != null ? tree.left : tree.right;
                        return tree;
                    }
            }
        }
        return tree;
    }

    /**
     * 查找后继节点
     * @param tree
     */
    public Tree<K,V> findNext(Tree<K,V> tree){
        if(tree.left == null && tree.right == null){
            return tree;
        }else if(tree.left != null && tree.right != null){
            return findNext(tree.left);
        }else{
            return findNext(tree.left!=null?tree.left:tree.right);
        }
    }


    public void printResult(Tree<K,V> tree){
        System.out.println("前序遍历：");
        tree.pre(tree,tree.right);
        System.out.println("\n中序遍历：");
        tree.mid(tree,tree.right);
        System.out.println("\n后序遍历：");
        tree.back(tree,tree.right);
    }

    public static void main(String[] args){
        Tree<Integer,Integer> tree = new Tree<Integer,Integer>(new Integer(20),null,null,null);
        tree.add(15,null,tree);
        tree.add(25,null,tree);
        tree.add(10,null,tree);
        tree.add(12,null,tree);
        tree.add(18,null,tree);
        tree.add(30,null,tree);
//        tree.add(30,null,tree);

        tree.printResult(tree);

        System.out.println("\n开始删除节点");
//        tree.delete(null,tree,new Tree<Integer,Integer>(30,null,null,null));
        tree = tree.remove(tree,30);
        tree.printResult(tree);
    }

}
