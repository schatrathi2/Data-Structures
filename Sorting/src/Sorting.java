import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Sruthi Chatrathi
 * @version 1.0
 * @userid schatrathi6
 * @GTID 903558326
 *
 */

public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array or comparator is null.");
        }

        for (int i = 0; i < arr.length; i++) {
            T key = arr[i];
            int j = i - 1;
            while (j >= 0 && comparator.compare(arr[j], key) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array or comparator is null.");
        }

        boolean swapped = true;
        int start = 0;
        int end = arr.length;
        while (swapped) {
            swapped = false;
            for (int i = start; i < end - 1; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
            swapped = false;
            end--;
            for (int i = end; i > start; i--) {
                if (comparator.compare(arr[i], arr[i - 1]) < 0) {
                    T temp = arr[i - 1];
                    arr[i - 1] = arr[i];
                    arr[i] = temp;
                    swapped = true;
                }
            }
            start++;
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array or comparator is null.");
        }
        if (arr.length < 2) {
            return;
        }

        int mid = arr.length / 2;
        T[] l = (T[]) new Object[mid];
        T[] r = (T[]) new Object[arr.length - mid];
        for (int i = 0; i < mid; i++) {
            l[i] = arr[i];
        }
        for (int i = mid; i < arr.length; i++) {
            r[i - mid] = arr[i];
        }
        mergeSort(l, comparator);
        mergeSort(r, comparator);
        merge(arr, l, r, mid, arr.length - mid, comparator);
    }

    /**
     * Recursive method for merge sort.
     * @param <T> data type to sort
     * @param a original array
     * @param l left array
     * @param r right array
     * @param left left index
     * @param right right index
     * @param comparator the Comparator used to compare the data in arr
     */
    private static <T> void merge(T[] a, T[] l, T[] r, int left, int right, Comparator<T> comparator) {
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < left && j < right) {
            if (comparator.compare(l[i], r[j]) <= 0) {
                a[k++] = l[i++];
            } else {
                a[k++] = r[j++];
            }
        }
        while (i < left) {
            a[k++] = l[i++];
        }
        while (j < right) {
            a[k++] = r[j++];
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("The array is null.");
        }

        Queue<Integer>[] buckets = new Queue[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new LinkedList<Integer>();
        }
        boolean sorted = false;
        int exp = 1;
        while (!sorted) {
            sorted = true;
            for (int num : arr) {
                int bucket = ((num / exp) % 10) + 10;
                if (bucket > 10) {
                    sorted = false;
                }
                buckets[bucket].add(num);
            }
            exp *= 10;
            int index = 0;
            for (Queue<Integer> bucket : buckets) {
                while (!bucket.isEmpty()) {
                    arr[index++] = bucket.remove();
                }
            }
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("The array, comparator, or random object is null.");
        }

        qSort(arr, comparator, rand, 0, arr.length);
    }

    /**
     * Recursive method for quick sort.
     * @param <T> data type to sort
     * @param arr original array
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @param low low index
     * @param high high index
     */
    private static <T> void qSort(T[] arr, Comparator<T> comparator,
                                  Random rand, int low, int high) {
        if (low >= high) {
            return;
        }
        if (low < 0) {
            return;
        }
        if (high > arr.length - 1) {
            return;
        }

        int pivot = partition(arr, comparator, rand, low, high);
        qSort(arr, comparator, rand, low, pivot - 1);
        qSort(arr, comparator, rand, pivot + 1, high);
    }
    /**
     * To help with recursive method for quick sort.
     * @param <T> data type to sort
     * @param arr original array
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @param low low index
     * @param high high index
     * @return the low index
     */
    private static <T> int partition(T[] arr, Comparator<T> comparator,
                                 Random rand, int low, int high) {
        int random = rand.nextInt(high - low + 1) + low;
        int last = high;
        swap(arr, random, high);
        high--;
        while (low <= high) {
            if (comparator.compare(arr[low], arr[last]) < 0) {
                low++;
            } else {
                swap(arr, low, high);
                high--;
            }
        }
        swap(arr, low, last);
        return low;
    }
    /**
     * To swap two elements.
     * @param <T> data type to sort
     * @param arr original array
     * @param i index of first element
     * @param j index of second element
     */
    private static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}