import java.util.NoSuchElementException;

/**
 * Your implementation of a CircularSinglyLinkedList without a tail pointer.
 *
 * @author YOUR NAME HERE
 * @version 1.0
 * @userid YOUR USER ID HERE (i.e. gburdell3)
 * @GTID YOUR GT ID HERE (i.e. 900000000)
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class CircularSinglyLinkedList<T> {


    // Do not add new instance variables or modify existing ones.
    private CircularSinglyLinkedListNode<T> head;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the data to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index > size) {
            throw new IndexOutOfBoundsException("The index is too large.");
        } else if (index < 0) {
            throw new IndexOutOfBoundsException("The index cannot be less than 0.");
        }

        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }

        if (index == 0) {
            this.addToFront(data);
        } else if (index == size) {
            this.addToBack(data);
        } else {
            CircularSinglyLinkedListNode newNode = new CircularSinglyLinkedListNode<T>(data);
            CircularSinglyLinkedListNode current = head;
            CircularSinglyLinkedListNode temp = null;
            for (int i = 0; i <= index; i++) {
                if (i == index - 1) {
                    temp = current.getNext();
                    current.setNext(newNode);
                }
                if (i == index) {
                    newNode.setNext(temp);
                }
                current = current.getNext();
            }
            size++;
        }
    }

    /**
     * Adds the data to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }

        CircularSinglyLinkedListNode newNode = new CircularSinglyLinkedListNode<T>(data);
        if (head == null) {
            head = newNode;
            size++;
        } else {
            CircularSinglyLinkedListNode temp = head;
            newNode.setNext(temp);
            head = newNode;
            size++;
            CircularSinglyLinkedListNode curr = head;
            int count = 0;
            do {
                if (count == size - 1) {
                    curr.setNext(head);
                }
                curr = curr.getNext();
                count++;
            } while (curr != head);
        }
    }

    /**
     * Adds the data to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }

        if (head == null) {
            this.addToFront(data);
        } else {
            CircularSinglyLinkedListNode newNode = new CircularSinglyLinkedListNode<T>(data);
            CircularSinglyLinkedListNode curr = head;
            int count = 0;
            do {
                if (count == size - 1) {
                    curr.setNext(newNode);
                    newNode.setNext(head);
                }
                curr = curr.getNext();
                count++;
            } while (curr != head);
            size++;
        }
    }

    /**
     * Removes and returns the data at the specified index.
     *
     * Must be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (this.isEmpty()) {
            throw new NoSuchElementException("The list is empty.");
        }

        if (index == 0) {
            this.removeFromFront();
        } else if (index == size - 1) {
            this.removeFromBack();
        } else {
            CircularSinglyLinkedListNode temp = head;
            CircularSinglyLinkedListNode curr = head;
            int count = 0;
            do {
                if (count == index - 1) {
                    temp = curr.getNext();
                    curr.setNext(curr.getNext().getNext());
                }
                curr = curr.getNext();
                count++;
            } while (curr != head);
            size--;
            return (T) temp.getData();
        }
        return null;
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("The list is empty.");
        }

        CircularSinglyLinkedListNode temp = head;
        head = head.getNext();
        size--;
        CircularSinglyLinkedListNode curr = head;
        int count = 0;
        do {
            if (count == size - 1) {
                curr.setNext(head);
            }
            curr = curr.getNext();
            count++;
        } while (curr != head);
        return (T) temp.getData();
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("The list is empty.");
        }

        CircularSinglyLinkedListNode temp = head;
        CircularSinglyLinkedListNode curr = head;
        int count = 0;
        do {
            if (count == size - 2) {
                temp = curr.getNext();
                curr.setNext(head);
            }
            curr = curr.getNext();
            count++;
        } while (curr != head);
        size--;
        return (T) temp.getData();
    }

    /**
     * Returns the data at the specified index.
     *
     * Should be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index > size) {
            throw new IndexOutOfBoundsException("The index is too large.");
        } else if (index < 0) {
            throw new IndexOutOfBoundsException("The index cannot be less than 0.");
        }

        CircularSinglyLinkedListNode temp = head;
        CircularSinglyLinkedListNode curr = head;
        int count = 0;
        do {
            if (count == index) {
                temp = curr;
            }
            curr = curr.getNext();
            count++;
        } while (curr != head);
        return (T) temp.getData();
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        if (head == null) {
            return true;
        }
        return false;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }

        if (this.isEmpty()) {
            throw new NoSuchElementException("The list is empty.");
        }

        CircularSinglyLinkedListNode current = head;
        boolean isThere = false;
        int index = 0;
        for (int i = 0; i < size; i++) {
            if (current.getData().equals(data)) {
                isThere = true;
                index = i;
            }
            current = current.getNext();
        }

        if (!isThere) {
            throw new NoSuchElementException("The data is not in the list.");
        }

        if (index == 0) {
            return removeFromFront();
        } else if (index == size - 1) {
            return removeFromBack();
        } else {
            return removeAtIndex(index);
        }
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        T[] array = (T[]) new Object[size];
        CircularSinglyLinkedListNode current = head;
        for (int i = 0; i < size; i++) {
            array[i] = (T) (current.getData());
            current = current.getNext();
        }
        return array;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}