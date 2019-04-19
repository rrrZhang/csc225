import java.util.ArrayList;
import java.util.List;

public class AVL_BST {

    public static boolean checkAVL(BST b) {
        if (b.subBalanced(b.root) >=0) {
            return true;
        } else {
            return false;
        }
    }

    public static BST createBST(int[] a) {
        BST bst = new BST();
        for (int i = 0; i < a.length; i++) {
            bst.insert(a[i]);
        }
        return bst;
    }

    public static void main(String[] args) {
        int[] A = {297,619,279,458,423,122,505,549,83,186,131,71};
        int[] B = {82,85,153,195,124,66,200,193,185,243,73,153,76};
        int[] C = {};
        int[] D = {5,3,7,1};
        int[] E = {5,1,98,100,-3,-5,55,3,56,50};
        int[] F = {78};
        BST b = createBST(F);
        System.out.println(checkAVL(b));
    }
}

class BST {

    List<Node> node = new ArrayList<Node>();
    Node root;

    public void insert(int value) {
        if (root == null) {
            root = new Node(null, value, null);
        } else {
            add(root, value);
        }
    }

    public void add(Node node, int value) {
        if (value >= node.value) {
            if (node.right == null) {
                node.right = new Node(null, value, null);
            } else {
                add(node.right, value);
            }
        } else {
            if (node.left == null) {
                node.left = new Node(null, value, null);
            } else {
                add(node.left, value);
            }
        }
    } 

    public int subBalanced(Node node) {
        if (node == null) {
        	return 0;
        } else {
            int leftHeight = subBalanced(node.left);
            if (leftHeight == -1) {
                 return -1;
            }else{
            	int rightHeight = subBalanced(node.right);
                if (rightHeight == -1) {
                     return -1;
                }else{
                    int difference1 = leftHeight - rightHeight;
                    int difference2 = rightHeight - leftHeight;
                    if (difference1 > 1 || difference2 > 1) {
                        return -1;
                    } else {
                        return 1 + Math.max(leftHeight, rightHeight);
                    }
                }
            }
        }
    }
}

class Node {

    Node left;
    Node right;
    int value;

    Node(Node left, int value, Node right) {
        this.left = left;
        this.right = right;
        this.value = value;
    }
}
