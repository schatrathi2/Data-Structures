import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Your implementation of a BST.
 *
 * @author Sruthi Chatrathi
 * @version 1.0
 * @userid schatrathi6
 * @GTID 903558326
 *
 * Collaborators:
 *
 * Resources:
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        for (T d : data) {
            if (d == null) {
                throw new IllegalArgumentException("There is null data in the collection.");
            }
            this.add(d);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }

        root = addRecursive(root, data);
        size++;
    }

    /**
     * Recursive Method to help with add method.
     * @param curr the current node during traversal
     * @param data the data to add
     * @return the node that will be added
     */
    private BSTNode addRecursive(BSTNode curr, T data) {
        boolean duplicate = false;
        if (curr == null) {
            return new BSTNode<T>(data);
        }

        if (data.compareTo((T) (curr.getData())) < 0) {
            curr.setLeft(addRecursive(curr.getLeft(), data));
        } else if (data.compareTo((T) (curr.getData())) > 0) {
            curr.setRight(addRecursive(curr.getRight(), data));
        } else if (data.compareTo((T) (curr.getData())) == 0) {
            size--;
            return curr;
        }

        return curr;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }
        root = removeRecursive(root, data);
        size--;
        return data;
    }
    /**
     * Recursive Method to help with remove method.
     * @param curr the current node during traversal
     * @param data the data to remove
     * @return the node to replace the removed node
     * @throws java.util.NoSuchElementException if the data is not in the tree
     */
    private BSTNode removeRecursive(BSTNode curr, T data) {
        if (curr == null) {
            return null;
        }

        if (data.compareTo((T) (curr.getData())) < 0) {
            curr.setLeft(removeRecursive(curr.getLeft(), data));
        } else if (data.compareTo((T) (curr.getData())) > 0) {
            curr.setRight(removeRecursive(curr.getRight(), data));
        } else if (data.compareTo((T) (curr.getData())) == 0) {
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            }

            if (curr.getLeft() == null) {
                return curr.getRight();
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            }

            curr.setData(forTwoChildren(curr.getRight()));
            curr.setRight(removeRecursive(curr.getRight(), (T) curr.getData()));
        } else {
            throw new NoSuchElementException("The data is not in the BST.");
        }
        return curr;
    }

    /**
     * Recursive Method for when there are 2 children.
     * @param curr the current node during traversal
     * @return the data that will replace the removed node's data
     */
    private T forTwoChildren(BSTNode curr) {
        T replace = (T) (curr).getData();
        while (curr.getLeft() != null) {
            replace = (T) curr.getLeft().getData();
            curr = curr.getLeft();
        }
        return replace;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }

        BSTNode g = getRecursive(root, data);
        return (T) g.getData();
    }

    /**
     * Recursive Method to help with get method.
     * @param curr the current node during traversal
     * @param data the data to get
     * @return the node with the data to get
     * @throws java.util.NoSuchElementException if the data is not in the tree
     */
    private BSTNode getRecursive(BSTNode curr, T data) {
        boolean found = false;
        while (curr != null && !found) {
            if (data.compareTo((T) (curr.getData())) < 0) {
                curr = curr.getLeft();
            } else if (data.compareTo((T) (curr.getData())) > 0) {
                curr = curr.getRight();
            } else {
                found = true;
                break;
            }
            curr = getRecursive(curr, data);
        }
        if (!found) {
            throw new NoSuchElementException("The data is not in the BST.");
        }
        return curr;
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }

        return containsRecursive(root, data);
    }

    /**
     * Recursive Method to help with contains method.
     * @param curr the current node during traversal
     * @param data the data to find
     * @return whether the data is in the BST
     */
    private boolean containsRecursive(BSTNode curr, T data) {
        boolean found = false;
        while (curr != null && !found) {
            if (data.compareTo((T) (curr.getData())) < 0) {
                curr = curr.getLeft();
            } else if (data.compareTo((T) (curr.getData())) > 0) {
                curr = curr.getRight();
            } else {
                found = true;
                break;
            }
            found = containsRecursive(curr, data);
        }
        return found;
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<>();
        preorderRecursive(root, list);
        return list;
    }

    /**
     * Recursive Method to help with preorder method.
     * @param curr the current node during traversal
     * @param list the list which will have the preordered elements
     */
    private void preorderRecursive(BSTNode curr, List list) {

        if (curr != null) {
            list.add((T) curr.getData());
            preorderRecursive(curr.getLeft(), list);
            preorderRecursive(curr.getRight(), list);
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new ArrayList<>();
        inorderRecursive(root, list);
        return list;
    }

    /**
     * Recursive Method to help with inorder method.
     * @param curr the current node during traversal
     * @param list the list which will have the inordered elements
     */
    private void inorderRecursive(BSTNode curr, List list) {

        if (curr != null) {
            inorderRecursive(curr.getLeft(), list);
            list.add((T) curr.getData());
            inorderRecursive(curr.getRight(), list);
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new ArrayList<>();
        postorderRecursive(root, list);
        return list;
    }

    /**
     * Recursive Method to help with postorder method.
     * @param curr the current node during traversal
     * @param list the list which will have the postordered elements
     */
    private void postorderRecursive(BSTNode curr, List list) {

        if (curr != null) {
            postorderRecursive(curr.getLeft(), list);
            postorderRecursive(curr.getRight(), list);
            list.add((T) curr.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> list = new ArrayList<>();
        Queue<BSTNode> lo = new LinkedList<>();
        lo.add(root);

        while (!lo.isEmpty()) {
            BSTNode n = lo.remove();
            list.add((T) n.getData());
            if (n.getLeft() != null) {
                lo.add(n.getLeft());
            }
            if (n.getRight() != null) {
                lo.add(n.getRight());
            }
        }
        return list;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightRecursive(root);
    }

    /**
     * Recursive Method to help with height method.
     * @param curr the current node during traversal
     * @return the height of the root node
     */
    private int heightRecursive(BSTNode curr) {
        if (curr == null) {
            return -1;
        } else {
            int lheight = heightRecursive(curr.getLeft());
            int rheight = heightRecursive(curr.getRight());

            if (lheight > rheight) {
                return lheight + 1;
            } else {
                return rheight + 1;
            }
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Determines if a binary tree is a valid BST.
     *
     * This must be done recursively. Do NOT modify the tree passed in.
     *
     * If the order property is violated, this method may not need to traverse
     * the entire tree to function properly, so you should only traverse the
     * branches of the tree necessary to find order violations and only do so once.
     * Failure to do so will result in an efficiency penalty.
     *
     * EXAMPLES: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * isBST(50) should return true, since for every node, the node's left
     * subtree is less than the node's data, and the node's right subtree is
     * greater than the node's data.
     *
     *             20
     *           /    \
     *         21      38
     *        /          \
     *       6          50
     *        \
     *         12
     *
     * isBST(20) should return false, since 21 is in 20's left subtree.
     *
     *
     * Should have a worst-case running time of O(n).
     *
     * @param node the root of the binary tree
     * @return true if the tree with node as the root is a valid BST,
     *         false otherwise
     */
    public boolean isBST(BSTNode<T> node) {
        return isBSTRecursive(root, root.getLeft().getData(), root.getRight().getData());
    }

    /**
     * Recursive Method to help with isBST method.
     * @param curr the current node during traversal
     * @param min the min constraint
     * @param max the max constraint
     * @return whether it is a BST
     */
    public boolean isBSTRecursive(BSTNode curr, T min, T max) {
        if (curr == null) {
            return true;
        }

        if (min == null && max == null) {
            return true;
        }
        if (min == null && max != null) {
            if (curr.getData().compareTo(max) > 0) {
                return false;
            }
        } else if (min != null && max == null) {
            if (curr.getData().compareTo(min) < 0) {
                return false;
            }
        } else if (min != null && max != null) {
            if (curr.getData().compareTo(min) < 0 || curr.getData().compareTo(max) > 0) {
                return false;
            }
        }

        return (isBSTRecursive(curr.getLeft(), (T) curr.getLeft().getLeft().getData(),
            (T) curr.getLeft().getRight().getData()) && isBSTRecursive(curr.getRight(),
            (T) curr.getRight().getLeft().getData(), (T) curr.getRight().getRight().getData()));
    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
