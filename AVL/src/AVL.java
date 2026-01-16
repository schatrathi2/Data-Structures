import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;

/**
 * Your implementation of an AVL.
 *
 * @author Sruthi Chatrathi
 * @version 1.0
 * @userid schatrathi6
 * @GTID 903558326
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class AVL<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        for (T d : data) {
            if (d == null) {
                throw new IllegalArgumentException("There is null data in the collection.");
            }
            this.add(d);
        }
    }

    /**
     * Returns the max between two ints.
     * @param a first int
     * @param b second int
     * @return the larger int
     */
    private int max(int a, int b) {
        if (a >= b) {
            return a;
        }
        return b;
    }

    /**
     * Calculates the new height and balance factor for a node.
     * @param curr the node that needs its height and balance factor calculated
     */
    private void calculate(AVLNode curr) {
        if (curr.getRight() == null && curr.getLeft() == null) {
            curr.setHeight(0);
            curr.setBalanceFactor(0);
        } else if (curr.getRight() == null) {
            curr.setHeight(curr.getLeft().getHeight() + 1);
            curr.setBalanceFactor(curr.getLeft().getHeight() - (-1));
        } else if (curr.getLeft() == null) {
            curr.setHeight(curr.getRight().getHeight() + 1);
            curr.setBalanceFactor(-1 - curr.getRight().getHeight());
        } else {
            curr.setHeight((max(curr.getLeft().getHeight(), curr.getRight().getHeight())) + 1);
            curr.setBalanceFactor(curr.getLeft().getHeight() - curr.getRight().getHeight());
        }
    }

    /**
     * Rotates a tree so the balance factor is fixed.
     * @param y the node around which the tree will e rotated
     * @return the new root of the tree
     */
    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.getLeft();
        AVLNode t2 = x.getRight();

        x.setRight(y);
        y.setLeft(t2);

        this.calculate(y);
        this.calculate(x);

        return x;
    }

    /**
     * Rotates a tree so the balance factor is fixed.
     * @param x the node around which the tree will e rotated
     * @return the new root of the tree
     */
    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.getRight();
        AVLNode t2 = y.getLeft();

        y.setLeft(x);
        x.setRight(t2);

        this.calculate(x);
        this.calculate(y);

        return y;
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
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
    private AVLNode addRecursive(AVLNode curr, T data) {
        if (curr == null) {
            return new AVLNode<T>(data);
        }

        if (data.compareTo((T) (curr.getData())) < 0) {
            curr.setLeft(addRecursive(curr.getLeft(), data));
        } else if (data.compareTo((T) (curr.getData())) > 0) {
            curr.setRight(addRecursive(curr.getRight(), data));
        } else {
            return curr;
        }

        this.calculate(curr);

        if (curr.getBalanceFactor() > 1 && data.compareTo((T) (curr.getLeft().getData())) < 0) {
            return rightRotate(curr);
        }
        if (curr.getBalanceFactor() < -1 && data.compareTo((T) (curr.getRight().getData())) > 0) {
            return leftRotate(curr);
        }
        if (curr.getBalanceFactor() > 1 && data.compareTo((T) (curr.getLeft().getData())) > 0) {
            curr.setLeft(leftRotate(curr.getLeft()));
            return rightRotate(curr);
        }
        if (curr.getBalanceFactor() < -1 && data.compareTo((T) (curr.getRight().getData())) < 0) {
            curr.setRight(rightRotate(curr.getRight()));
            return leftRotate(curr);
        }
        return curr;
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     *    simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     *    replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     *    replace the data, NOT successor. As a reminder, rotations can occur
     *    after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not found
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
     * Returns a node's predecessor.
     * @param curr the node that will have its predecessor returned
     * @return the predecessor of the node
     */
    private AVLNode pred(AVLNode curr) {
        AVLNode node = curr.getLeft();

        while (node.getRight() != null) {
            node = node.getRight();
        }

        return node;
    }

    /**
     * Recursive Method to help with remove method.
     * @param curr the current node during traversal
     * @param data the data to remove
     * @return the node to replace the removed node
     * @throws java.util.NoSuchElementException if the data is not in the tree
     */
    private AVLNode removeRecursive(AVLNode curr, T data) {
        if (curr == null) {
            return curr;
        }

        if (data.compareTo((T) (curr.getData())) < 0) {
            curr.setLeft(removeRecursive(curr.getLeft(), data));
        } else if (data.compareTo((T) (curr.getData())) > 0) {
            curr.setRight(removeRecursive(curr.getRight(), data));
        } else if (data.compareTo((T) (curr.getData())) == 0) {
            if (curr.getLeft() == null || curr.getRight() == null) {
                AVLNode temp = null;

                if (temp == curr.getLeft()) {
                    temp = curr.getRight();
                } else {
                    temp = curr.getLeft();
                }

                if (temp == null) {
                    temp = curr;
                    curr = null;
                } else {
                    curr = temp;
                }
            } else {
                AVLNode temp = pred(curr);
                curr.setData(temp.getData());
                curr.setLeft(removeRecursive(curr.getLeft(), (T) temp.getData()));
            }
        } else {
            throw new NoSuchElementException("The data is not in the AVL.");
        }

        if (curr == null) {
            return curr;
        }

        this.calculate(curr);

        if (curr.getBalanceFactor() > 1 && curr.getLeft().getBalanceFactor() >= 0) {
            return rightRotate(curr);
        }
        if (curr.getBalanceFactor() < -1 && curr.getRight().getBalanceFactor() <= 0) {
            return leftRotate(curr);
        }
        if (curr.getBalanceFactor() > 1 && curr.getLeft().getBalanceFactor() < 0) {
            curr.setLeft(leftRotate(curr.getLeft()));
            return rightRotate(curr);
        }
        if (curr.getBalanceFactor() < -1 && curr.getRight().getBalanceFactor() > 0) {
            curr.setRight(rightRotate(curr.getRight()));
            return leftRotate(curr);
        }
        return curr;
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }

        AVLNode a = getRecursive(root, data);
        return (T) a.getData();
    }

    /**
     * Recursive Method to help with get method.
     * @param curr the current node during traversal
     * @param data the data to get
     * @return the node with the data to get
     * @throws java.util.NoSuchElementException if the data is not in the tree
     */
    private AVLNode getRecursive(AVLNode curr, T data) {
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
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree
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
     * @return whether the data is in the AVL
     */
    private boolean containsRecursive(AVLNode curr, T data) {
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
     * Returns the height of the root of the tree. Do NOT compute the height
     * recursively. This method should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     *
     * Your list should not contain duplicate data, and the data of a branch
     * should be listed in order going from the root to the leaf of that branch.
     *
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     *
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {
        List<T> list = new ArrayList<>();
        deepestRecursive(root, list);
        return list;
    }

    /**
     * Recursive Method to help with deepest branches method.
     * @param curr the current node during traversal
     * @param list the list which will have the preordered deepest elements
     */
    private void deepestRecursive(AVLNode curr, List list) {

        if (curr != null) {
            list.add((T) curr.getData());
            if (curr.getBalanceFactor() > 0) {
                deepestRecursive(curr.getLeft(), list);
            } else if (curr.getBalanceFactor() == 0) {
                deepestRecursive(curr.getLeft(), list);
                deepestRecursive(curr.getRight(), list);
            } else if (curr.getBalanceFactor() < 0) {
                deepestRecursive(curr.getRight(), list);
            }
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
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