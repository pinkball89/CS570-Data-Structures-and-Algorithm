/**
 * 2021F CS 570-B. Homework Assignment 5.
 * A treap is a binary search tree (BST) which additionally maintains heap priorities. An
 * example is given in Figure 1. A node consists of:
 * •  A key k (given by the letter in the example),
 * •  A random heap priority p (given by the number in the example). The heap priority p
 * is assigned at random upon insertion of a node. It should be unique in the treap.
 * •  A pointer to the left child and to the right child node.
 * The Node class:: Create a private static inner class Node<E> of the Treap class (described in the next subsection) with
 * the following attributes and constructors:
 * The Treap class:
 * •  Data fields:
 * private Random  p r i o r i t y G e n e r a t o r ;
 * private Node <E > root ;
 * E must be Comparable.
 * •  Constructors:
 * public Treap ()
 * public Treap ( long  seed )
 * Treap() creates an empty treap. Initialize priorityGenerator using new Random(). See
 * http://docs.oracle.com/javase/8/docs/api/java/util/Random.html for more in-formation
 * regarding Java’s pseudo-random number generator.  Treap(long seed) creates an empty treap and initializes priorityGenerator using new Random(seed).
 * •  Methods:
 * boolean add ( E  key )
 * boolean  add ( E  key ,  int  priority )   b o o l e a n delete ( E  key )
 * private boolean find ( Node <E >  root ,  E  key )
 * public boolean find ( E key )
 * public String  toString ()
 *
 * CWID: 20007427
 *
 * @Truong
 * @Date 11/19/2021
 */

import java.util.Random;
import java.util.Stack;


public class Treap<E extends Comparable<E>> {

    ////Create a private static inner class Node<E> of the Treap class
    private static class Node<E> {
        //Data fields
        public E data; //key for the search
        public int priority; //random heap priority
        public Node<E> left;
        public Node<E> right;

        //Constructors
        public Node(E data, int priority) {
            if (data == null) {
                throw new IllegalArgumentException();
            } else {
                this.left = null;
                this.right = null;
                this.data = data;
                this.priority = priority;
            }
        }

        //Methods

        //*rotateLeft()  performs a left rotation according to Figure 2 returning a reference to the root of the result.
        // The root node in the figure corresponds to this node. Update the attributes of the nodes accordingly.
        public Node<E> rotateLeft() {
            Node<E> temp = this.right;
            Node<E> right = temp.left;
            temp.left = this;
            this.right = right;
            return temp;
        }

        //* rotateRight()  performs  a  right  rotation  according  to  Figure  2,  returning  a  reference  to  the
        //root of the result. The root node in the figure corresponds to this node. Update the data priority  attributes
        // as  well  as  the  left  and  right  pointers  of  the  involved  nodes accordingly.
        public Node<E> rotateRight() {
            Node<E> temp = this.left;
            Node<E> left = temp.right;
            temp.right = this;
            this.left = left;
            return temp;
        }

        public String toString() {
            return this.data.toString();
        }
    }

    ////The TREAP class

    //Data fields
    private Random priorityGenerator;
    private Node<E> root;

    //Constructors
    //*Treap() creates an empty treap. Initialize priorityGenerator using new Random(). See
    //http://docs.oracle.com/javase/8/docs/api/java/util/Random.html for more in-formation
    //regarding Java’s pseudo-random number generator.
    public Treap() {
        priorityGenerator = new Random();
        root = null;
    }

    //*Treap(long seed) creates an empty treap and initializes priorityGenerator using new Random(seed).
    public Treap(long seed) {
        priorityGenerator = new Random(seed);
        root = null;
    }

    //Methods
    public void reheap(Node<E> child, Stack<Node<E>> stack) {
        while (!stack.isEmpty()) {
            Node<E> parent = stack.pop();
            if (parent.priority < child.priority) {
                if (parent.data.compareTo(child.data) > 0) {
                    child = parent.rotateRight();
                } else {
                    child = parent.rotateLeft();
                }
                if (!stack.isEmpty()) {
                    if (stack.peek().left == parent) {
                        stack.peek().left = child;
                    } else {
                        stack.peek().right = child;
                    }
                } else {
                    this.root = child;
                }
            } else {
                break;
            }
        }
    }


    //*Add operation
    //To insert the given element into the tree, create a new node containing key as its data and a random priority generated
    // by  priorityGenerator. The method returns  true, if a node with the key  was successfully added to  the treap.
    // If there is  already  a  node containing  the given key, the method returns false and does not modify the treap.
    boolean add(E key) {
        return add(key, priorityGenerator.nextInt());
    }

    boolean add(E key, int priority) {
        if (root == null) {
            root = new Node<E>(key, priority);
            return true;
        } else {
            Node<E> n = new Node<E>(key, priority);
            Stack<Node<E>> stack = new Stack<Node<E>>();
            Node<E> localroot = root;
            while (localroot != null) {
                localroot.data.compareTo(key);
                if (localroot.data.compareTo(key) == 0) {
                    return false;
                } else {
                    if (localroot.data.compareTo(key) < 0) {
                        stack.push(localroot);
                        if (localroot.right == null) {
                            localroot.right = n;
                            reheap(n, stack);
                            return true;
                        } else {
                            localroot = localroot.right;
                        }
                    } else {
                        stack.push(localroot);
                        if (localroot.left == null) {
                            localroot.left = n;
                            reheap(n, stack);
                            return true;
                        } else {
                            localroot = localroot.left;
                        }
                    }
                }
            }
            return false;
        }
    }

    //*boolean delete(E key)
    // deletes the node with the given key from the treap and returns true.
    // If the key was not found, the method does not modify the treap and returns false. In order to remove a node
    // trickle it down using rotation until it becomes a leaf and then remove it. When trickling down,
    // sometimes you will have to rotate left and sometimes right. That will depend on whether there is no left subtree of the node to delete,
    // or there is no right subtree of the node to erase; if the node to erase has both then you have to look at
    // the priorities of the children and consider the highest one to determine whether you have to rotate to the left or the right.
    public boolean delete(E key) {
        if (find(key) == false || root == null) {
            return false;
        } else {
            root = delete(key, root);
            return true;
        }
    }

    private Node<E> delete(E key, Node<E> localroot) {
        if (localroot == null) {
            return localroot;
        } else {
            if (localroot.data.compareTo(key) < 0) {
                localroot.right = delete(key, localroot.right);
            } else {
                if (localroot.data.compareTo(key) > 0) {
                    localroot.left = delete(key, localroot.left);
                } else {
                    if (localroot.right == null) {
                        localroot = localroot.left;
                    } else if (localroot.left == null) {
                        localroot = localroot.right;
                    } else {
                        if (localroot.right.priority < localroot.left.priority) {
                            localroot = localroot.rotateRight();
                            localroot.right = delete(key, localroot.right);
                        } else {
                            localroot = localroot.rotateLeft();
                            localroot.left = delete(key, localroot.left);
                        }
                    }
                }
            }
        }
        return localroot;
    }

    //*private boolean find(Node<E> root, E key): Finds a node with the given key in the treap rooted at root and
    // returns true if it finds it and false otherwise.
    private boolean find(Node<E> root, E key) {
        if (root == null) {
            return false;
        }
        if (key.compareTo(root.data) == 0) {
            return true;
        } else if (key.compareTo(root.data) < 0) {
            return find(root.left, key);
        } else {
            return find(root.right, key);
        }
    }

    //*boolean find(E key): Finds a node with the given key in the treap and returns true if it finds it and false otherwise.
    public boolean find(E key) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }
        return find(root, key);
    }

    //*public String toString(): Carries out a preorder traversal of the tree and returns a represen-tation of the  nodes  as  a  string.
    // Each  node  with  key  k  and  priority  p,  left  child  l,  and  right  child  r  is represented as the string [k, p] (l) (r).
    // If the left child does not exist, the string representation is [k, p] null (r).
    // Analogously, if there is no right child, the string representa-tion of the tree is [k, p] (l)  null.
    // Variables  l,  k,  and  p  must  be  replaced  by  its  corresponding  string  representation,  as
    //defined by the toString() method of the corresponding object.
    public String toString() {
        StringBuilder strb = new StringBuilder();
        preOrderTraverse(root, 1, strb);
        return strb.toString();

    }

    private void preOrderTraverse(Node<E> node, int depth, StringBuilder strbuilder) {
        for (int i = 1; i < depth; i++) {
            strbuilder.append("  ");
        }
        if (node == null) {
            strbuilder.append("null\n");
        } else {
            strbuilder.append("(key = " + node.toString() + ", ");
            strbuilder.append("priority = ");
            strbuilder.append(node.priority);
            strbuilder.append(")");
            strbuilder.append("\n");
            preOrderTraverse(node.left, depth + 1, strbuilder);
            preOrderTraverse(node.right, depth + 1, strbuilder);
        }
    }

    //An example test
    //For testing purposes you might consider creating a Treap by inserting this list of pairs
    //(key,priority) using the method boolean add(E key, int priority): (4,19);(2,31);(6,70);(1,84);(3,12);(5,83);(7,26)
    public static void main(String[] args) {
        Treap<Integer> testTree = new Treap<Integer>();
        //test add((E key, int priority) method
        testTree.add(4,19);
        testTree.add(2,31);
        testTree.add(6,70);
        testTree.add(1,84);
        testTree.add(3,12);
        testTree.add(5,83);
        testTree.add(7,26);

        System.out.println("This Treap is:");
        System.out.println(testTree.toString());
        System.out.println("Deleting a Node with a key 1: "+ testTree.delete(1)); //test delete(E key) method
        System.out.println("Find a Node  with a key 6?: "+ testTree.find(6)); // test find(E key) method
        System.out.println("After deleting key 6:");
        System.out.println(testTree.toString());

    }
}

