//Author: Sophie Pallanck
//Date: 5/9/20
/*Purpose: This program implements methods to insert words into standard
BST and AVL trees, using helper methods to maintain AVL balance of the tree */
package avl;

public class AVL {

  public Node root;

  private int size;

  public int getSize() {
    return size;
  }

  /** find w in the tree. return the node containing w or
  * null if not found */
  public Node search(String w) {
    return search(root, w);
  }
  private Node search(Node n, String w) {
    if (n == null) {
      return null;
    }
    if (w.equals(n.word)) {
      return n;
    } else if (w.compareTo(n.word) < 0) {
      return search(n.left, w);
    } else {
      return search(n.right, w);
    }
  }

  /** insert w into the tree as a standard BST, ignoring balance */
  public void bstInsert(String w) {
    if (root == null) {
      root = new Node(w);
      size = 1;
      return;
    }
    bstInsert(root, w);
  }

  /* insert w into the tree rooted at n, ignoring balance
   * pre: n is not null */
  private void bstInsert(Node n, String w) {
    if (w.equals(n.word)) { //already contains this word
      return;
    }
    if (w.compareTo(n.word) < 0) {
      if (n.left != null) {
        bstInsert(n.left, w);
      } else {
        n.left = new Node(w, n);
        size++;
      }
    } else {
      if (n.right != null) {
        bstInsert(n.right, w);
      } else {
        n.right = new Node(w, n);
        size++;
      }
    }
  }

  /** insert w into the tree, maintaining AVL balance
  *  precondition: the tree is AVL balanced */
  public void avlInsert(String w) {
    if (root == null) { //tree is empty
      root = new Node(w);
      size = 1;
      root.height = 0;
      return;
    }
    avlInsert(root, w);
  }

  /* insert w into the tree, maintaining AVL balance
   *  precondition: the tree is AVL balanced and n is not null */
  private void avlInsert(Node n, String w) {
    if (w.equals(n.word)) { //this word is already in tree
      return;
    }
    if (w.compareTo(n.word) < 0) { //do normal BST insert
      if (n.left != null) {
        avlInsert(n.left, w);
      } else {
        n.left = new Node(w, n);
        size++;
      }
    } else {
      if (n.right != null) {
        avlInsert(n.right, w);
      } else {
        n.right = new Node(w, n);
        size++;
      }
    }
    n.height = 1 + Math.max(height(n.left), height(n.right)); //update height
    rebalance(n); //fix any violations of AVL property
  }

  /** helper function that returns the 
   * height of Node n, or -1 if n is null */
  private int height(Node n) {
    if (n == null) { //avoids null pointer exceptions
      return -1;
    } 
    return n.height;
  }

  /** do a left rotation: rotate on the edge from x to its right child.
  *  precondition: x has a non-null right child */
  public void leftRotate(Node x) {
    Node y = x.right;
    x.right = y.left; //set x's right subtree to be y's left subtree
    if (y.left != null) {
      y.left.parent = x;
    }
    y.parent = x.parent;
    if (x.parent == null) { //check if we're working w/root
      root = y;
    } else if (x == x.parent.left) {
      x.parent.left = y;
    } else {
      x.parent.right = y;
    }
    y.left = x; //set y's left subtree to be x
    x.parent = y;
    x.height = 1 + Math.max(height(x.left), height(x.right)); //find new heights
    y.height = 1 + Math.max(height(y.left), height(y.right));
  }

  /** do a right rotation: rotate on the edge from x to its left child.
  *  precondition: y has a non-null left child */
  public void rightRotate(Node y) {
    Node x = y.left;
    y.left = x.right; //sets y's left subtree to be x's right subtree
    if (x.right != null) {
      x.right.parent = y; 
    }
    x.parent = y.parent;
    if (y.parent == null) { //check if we're working w/root
      root = x;
    } else if (y == y.parent.right) {
      y.parent.right = x;
    } else {
      y.parent.left = x;
    }
    x.right = y; //set x's right subtree to be y
    y.parent = x;
    y.height = 1 + Math.max(height(y.left), height(y.right)); //find new heights
    x.height = 1 + Math.max(height(x.left), height(x.right));
  }
  
  /** returns the balance factor of the given Node n
   * or -1 if Node is null */
  private int getBalance(Node n) {
    if (n == null) {
      return -1;
    } 
    return height(n.right) - height(n.left);
  }

  /** rebalance a node N after a potentially AVL-violoting insertion.
  *  precondition: none of n's descendants violates the AVL property */
  public void rebalance(Node n) {
    int bal = getBalance(n); //check balance factor
    if (bal < -1) {
      if ((bal = getBalance(n.left)) < 0) { 
        rightRotate(n);
      } else { 
        leftRotate(n.left);
        rightRotate(n);
      }
    } else if ((bal = getBalance(n)) > 1) { 
      if ((bal = getBalance(n.right)) < 0) { 
        rightRotate(n.right);
        leftRotate(n);
      } else { 
        leftRotate(n);
      }
    }
  }

  /** remove the word w from the tree */
  public void remove(String w) {
    remove(root, w);
  }

  /* remove v from the tree rooted at n */
  private void remove(Node n, String w) {
    return; // (enhancement TODO - do the base assignment first)
  }

  /** print a sideways representation of the tree - root at left,
  * right is up, left is down. */
  public void printTree() {
    printSubtree(root, 0);
  }
  private void printSubtree(Node n, int level) {
    if (n == null) {
      return;
    }
    printSubtree(n.right, level + 1);
    for (int i = 0; i < level; i++) {
      System.out.print("        ");
    }
    System.out.println(n);
    printSubtree(n.left, level + 1);
  }

  /** inner class representing a node in the tree. */
  public class Node {
    public String word;
    public Node parent;
    public Node left;
    public Node right;
    public int height;

    public String toString() {
      return word + "(" + height + ")";
    }

    /** constructor: gives default values to all fields */
    public Node() { }

    /** constructor: sets only word */
    public Node(String w) {
      word = w;
    }

    /** constructor: sets word and parent fields */
    public Node(String w, Node p) {
      word = w;
      parent = p;
    }

    /** constructor: sets all fields */
    public Node(String w, Node p, Node l, Node r) {
      word = w;
      parent = p;
      left = l;
      right = r;
    }
  }
}
