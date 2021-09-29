package com.company;

/*
Quick Sort is a kind of sorting where we choose an index and using this index we divide the array.
To divide an array I use a partition method. This method take the last element from array (it depends on the range)
and put every smallest elements into the left side of array.
Of course, I'm using a recursion then if in first quickSort last variable will be smaller than 0, then recursion is over
in second quickSort recursion will be finish when first variable will be higher than large element.

The worst-case behavior for quicksort occurs when the partitioning method produces one subproblem
with n - 1 elements and one with 0 elements.
                            The complexity in this case will be O(n^2).

In the most even possible split, PARTITION method produces two subproblems, each of
size no more than n/2, since one is of size [n/2] and one of size [n/2] - 1. In this
case, quicksort runs much faster.
                            The complexity in this case will be O(n log n).
* */

public class QuickSort {

    private static boolean elementIsSmallerThenPivot(int element, int pivot) { return (element <= pivot);}

    private static void exchange(int[] array, final int i, final int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static int partition(int[] array, final int first, final int last) {

        int pivot = array[last];
        int replaceIndex = first - 1;

        for (int j = first; j < last; j++) {
            if (elementIsSmallerThenPivot(array[j], pivot)) {
                replaceIndex++;
                exchange(array,replaceIndex,j);
            }
        }

        // I chose the last element, that's why I need to put him behind last replaceIndex
        exchange(array,replaceIndex + 1, last);

        return (replaceIndex + 1);
    }

    private static void quickSort(int[] array, final int first, final int last) {
        if(first < last) {
            int index = partition(array, first, last);
            quickSort(array,first,index - 1);
            quickSort(array,index + 1, last);
        }
    }

    public static void sort(int[] array) {
        quickSort(array,0,array.length - 1);
    }

}