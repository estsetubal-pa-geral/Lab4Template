/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.pa.adts;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author brunomnsilva
 */
public class IntegerBST implements IntegerBinarySearchTree {

    private TreeNode root;

    public IntegerBST() {
        this.root = null;
    }

    public IntegerBST(int rootValue) {
        this.root = new TreeNode(rootValue, null, null, null);
    }

    @Override
    public int sum() throws EmptyContainerException {  
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    @Override
    public int sumInternals() throws EmptyContainerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
    @Override
    public int countGreaterThan(Integer value) throws EmptyContainerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
      
    @Override
    public Set<Integer> greaterThan(Integer value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
    @Override
    public boolean isEmpty() {
        return (this.root == null);
    }

    @Override
    public int size() {
        return size(this.root);
    }

    private int size(TreeNode treeRoot) {
        if (treeRoot == null) {
            return 0;
        }

        return 1 + size(treeRoot.left) + size(treeRoot.right);
    }

    @Override
    public int height() {
        return height(this.root);
    }

    private int height(TreeNode treeRoot) {
        if (treeRoot == null) {
            return -1;
        }

        return 1 + Math.max(height(treeRoot.left), height(treeRoot.right));
    }

    @Override
    public boolean exists(Integer elem) {
        if (isEmpty()) {
            return false;
        }

        return exists(elem, this.root);
    }

    private boolean exists(Integer elem, TreeNode treeRoot) {
        if (treeRoot == null) {
            return false;
        }
        if (elem == treeRoot.element) {
            return true;
        }

        if (elem < treeRoot.element) {
            return exists(elem, treeRoot.left);
        } else {
            return exists(elem, treeRoot.right);
        }
    }

    @Override
    public void insert(Integer elem) {
        if (isEmpty()) {
            this.root = new TreeNode(elem, null, null, null);
        } else {
            insert(elem, this.root);
        }
    }

    private void insert(Integer elem, TreeNode treeRoot) {
        if (treeRoot == null) {
            throw new IllegalArgumentException("Invalid usage.");
        }

        if (elem == treeRoot.element) {
            return;
        }

        if (elem < treeRoot.element) {
            if (treeRoot.hasLeft()) {
                insert(elem, treeRoot.left);
            } else {
                treeRoot.setLeft(new TreeNode(elem, treeRoot, null, null));
            }
        } else {
            if (treeRoot.hasRight()) {
                insert(elem, treeRoot.right);
            } else {
                treeRoot.setRight(new TreeNode(elem, treeRoot, null, null));
            }
        }
    }

    @Override
    public void remove(Integer elem) throws EmptyContainerException {
        if(isEmpty()) {
            throw new EmptyContainerException();
        }
        remove(elem, this.root);
    }
    
    private void remove(Integer elem, TreeNode treeRoot)  {
        if( treeRoot == null ) return;
        
        if( elem == treeRoot.element ) 
            removeNode(treeRoot);
        else if( elem < treeRoot.element ) //recursividade na sub-arvore esquerda
            remove(elem, treeRoot.left);
        else //recursividade na sub-arvore direita
            remove(elem, treeRoot.right);
    }
    
    private void removeNode(TreeNode treeRoot) {
        if( treeRoot == null ) return; //programacao defensiva
        
        TreeNode parent = treeRoot.parent;
        
        //caso 1: é um nó externo, basta removê-lo
        if( treeRoot.left == null && treeRoot.right == null ) {
            
            if(parent == null) {
                //é a raiz da àrvore geral, porque não tem pai (antecessor)
                this.root = null;
            } else if( parent.left == treeRoot ) {
                parent.left = null;
            } else { //parent.right == treeRoot                
                parent.right = null;
            }
        }
        //caso 3: possui ambas as sub-àrvores. Temos duas opçoes:
        //1 - substituir o elemento pelo maior elemento da sub-àrvore esquerda, ou;
        //2 - substituir o elemento pela menor elemento da sub-àrvore direita.
        //vamos utilizar a abordagem 2
        else if( treeRoot.left != null && treeRoot.right != null ) {
            int minimumRight = minimum(treeRoot.right);
            //a ordem das proximas duas instrucoes é crítica; se forem trocadas
            //a remocao irá ocorrer depois da subsituição, logo o valor deixa
            //de existir na àrvore
            remove(minimumRight);
            treeRoot.element = minimumRight;
            
        }
        //caso 2: só tem uma sub-àrvore; substituir o nó por essa sub-àrvore
        else {
            //obter referencia da raiz dessa sub-àrvore
            TreeNode subTree = (treeRoot.left != null) ? treeRoot.left : treeRoot.right;
            
            //update: 16 Out 2019 Caso de treeRoot ser raiz (nao possui parent)
            if(treeRoot == this.root) {
                this.root = subTree;  
                subTree.parent = null;
            } else {
                if( parent.left == treeRoot ) {
                    parent.left = subTree;                    
                } else {
                    parent.right = subTree;
                }
                //atualizar pai da sub-arvore
                subTree.parent = parent;
            }
            
        }
    }

    @Override
    public Integer minimum() throws EmptyContainerException {
        if (isEmpty()) {
            throw new EmptyContainerException();
        }

        return minimum(this.root);
    }
    
    private Integer minimum(TreeNode treeRoot) {
        TreeNode cur = treeRoot;
        while (cur.hasLeft()) {
            cur = cur.left;
        }
        return cur.element;
    }

    @Override
    public Integer maximum() throws EmptyContainerException {
        if (isEmpty()) {
            throw new EmptyContainerException("The BST is empty.");
        }

        TreeNode cur = this.root;
        while (cur.hasRight()) {
            cur = cur.right;
        }
        return cur.element;
    }

    @Override
    public Iterable<Integer> inOrder() {
        List<Integer> list = new ArrayList<>();
        inOrder(this.root, list);
        return list;
    }
    
    private void inOrder(TreeNode treeRoot, List<Integer> elements) {
        if( treeRoot == null) return;
        
        inOrder(treeRoot.left, elements);
        elements.add( treeRoot.element );
        inOrder(treeRoot.right, elements);
    }

    @Override
    public Iterable<Integer> preOrder() {
    	//TODO: completar trabalho autónomo, nao faz parte do lab
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterable<Integer> posOrder() {
    	//TODO: completar tabalho autonomo, nao faz parte do lab
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterable<Integer> breadthOrder() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString() {
        if (isEmpty()) {
            return "Empty tree.";
        }

        StringBuilder sb = new StringBuilder();
        inOrderPrettyString(root, new StringBuilder(), true, sb);
        return sb.toString();
    }

    private void inOrderPrettyString(TreeNode treeRoot,
            StringBuilder prefix, boolean isTail, StringBuilder sb) {

        if (treeRoot.right != null) {
            inOrderPrettyString(treeRoot.right, new StringBuilder().append(prefix).append(isTail ? "│   " : "    "), false, sb);
        }

        sb.append(prefix).append(isTail ? "└── " : "┌── ").append(treeRoot.element).append("\n");

        if (treeRoot.left != null) {
            inOrderPrettyString(treeRoot.left, new StringBuilder().append(prefix).append(isTail ? "    " : "│   "), true, sb);
        }

    }


    private class TreeNode {

        private int element;
        private TreeNode left;
        private TreeNode right;
        private TreeNode parent;

        public TreeNode(int element, TreeNode parent, TreeNode left, TreeNode right) {
            this.element = element;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        public TreeNode(int element) {
            this(element, null, null, null);
        }

        public boolean hasLeft() {
            return left != null;
        }

        public boolean hasRight() {
            return right != null;
        }
       

        public void setLeft(TreeNode node) {
            left = node;
        }

        public void setRight(TreeNode node) {
            right = node;
        }
        
    }

}
