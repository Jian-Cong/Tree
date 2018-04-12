package binaryTree;

/**
 * Created by Jian on 2018/4/10.
 */
public class Tree<K,V> {
    private K key;
    private V value;
    private Tree<K,V> left;
    private Tree<K,V> right;
    private int count = -1;

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
            System.out.print(tree.key+",");
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
            System.out.print(tree.key+",");
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
            System.out.print(tree.key+",");
        }
    }

    /**
     * 删除节点
     * @param tree
     * @param del
     */
    public void delete(Tree<K,V> tree,Tree<K,V> del){
        if(tree != null){
            if(compare(tree.key,del.key) > 0){
                delete(tree.left,del);
            }
            if(compare(tree.key,del.key) < 0){
                delete(tree.right,del);
            }
            if(compare(tree.key,del.key) == 0){
                if(--tree.count == -1){
                    // tree没有子节点
                    if(tree.right == null){
//                        tree
                    }
                    // ，tree有右子树，且没有tree.right.left为空
                    if(tree.right == null || (tree.right != null && tree.right.left == null)){
                        tree.key = tree.right.key;
                        if(tree.right.left !=null){
//                            tree.right = null;
//                            tree.right.left
                        }else{
                            tree.right = tree.right.right;
                        }
                    }
                    // tree有右子树，且没有tree.right.left不为空
                    if(tree.right != null && tree.right.left != null){
                        tree = tree.right.left;
                    }
                }
            }
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
        Tree<Integer,Integer> tree = new Tree<>(new Integer(20),null,null,null);
        tree.add(15,null,tree);
        tree.add(25,null,tree);
        tree.add(10,null,tree);
        tree.add(12,null,tree);
        tree.add(18,null,tree);
        tree.add(30,null,tree);

        tree.printResult(tree);

        System.out.println("\n开始删除节点");
        tree.delete(tree,new Tree<>(20,null,null,null));
        tree.printResult(tree);
    }

}
