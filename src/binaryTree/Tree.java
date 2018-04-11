package binaryTree;

import java.util.*;

/**
 * Created by Jian on 2018/4/10.
 */
public class Tree<K,V> {
    private K key;
    private V value;
    private Tree<K,V> left;
    private Tree<K,V> right;
    private int count;

    Tree(){};

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
        List list = new ArrayList<K>();
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
     * 后序遍历
     * @param tree
     * @param right
     */
    public void back(Tree<K,V> tree,Tree right){
        List list = new ArrayList<K>();
        if (tree != null) {
            System.out.print(tree.key+",");
        }
        if(tree.left != null){
            back(tree.left,tree.left.right);
        }
        if (right != null){
            back(right,right.right);
        }
    }

    public static void main(String[] args){
        Tree<Integer,Integer> tree = new Tree<>(new Integer(20),null,null,null);
        tree.add(15,null,tree);
        tree.add(25,null,tree);
        tree.add(10,null,tree);
        tree.add(12,null,tree);
        tree.add(18,null,tree);
        tree.add(30,null,tree);
        System.out.println("前序遍历：");
        tree.pre(tree,tree.right);
    }

}
