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
        this.key = key;
        this.left = left;
        this.right = right;
    }

    private int compare(K k1,K k2){
        return k1.toString().compareTo(k2.toString());
    }

    public int getHeight(AvlTree<K,V> tree){
        return tree.height;
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
     * 左右旋转
     * @param tree
     * @return
     */
    public AvlTree<K, V> LRRotate(AvlTree<K, V> tree){
        AvlTree<K, V> top = tree.right;
        tree.right = top.left;
        top.left = tree;

        tree.height = Math.max(getHeight(tree.left),getHeight(tree.right))+1;
        top.height = Math.max(getHeight(top.left),getHeight(top.right))+1;
        return top;
    }


    public AvlTree<K, V> add(K key, AvlTree<K, V> tree){
        if (tree == null)
            tree = new AvlTree<K, V>(key,null, null);
        else{
            if(compare(key,tree.key) <0){
                tree.left = new AvlTree<K, V>(key,null,null);
                if(getHeight(tree.left)-getHeight(tree.right) == 2){

                }
            }else if(compare(key,tree.key) >0){
                tree.right = new AvlTree<K, V>(key,null,null);

            }
        }
        tree.height = Math.max(getHeight(tree.left),getHeight(tree.right))+1;
        return tree;
    }

    public static void main(String[] args){

    }
}
