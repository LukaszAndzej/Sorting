package com.company;

/*

Bubble Sort is sorting where we push the bigger number at the end of array, that's why in second loop
we have condition (array.length - k) because from (array.length - k) to the end of array we have sorting array.

Interesting thing in this code is boolean statement needSortingAgain. It says us that we don't need to sort array again because
this second loop says us that we didn't swap anything that's wy we don't need any sorting again.

            The complexity of the overall algorithm is O(n^2)
* */

public class BubbleSort {

    private static void swapCells(int[] array, int index) {
        int temp = array[index];
        array[index] = array[index + 1];
        array[index + 1] = temp;
    }

    public static void sort(int[] array) {

        boolean needSortingAgain = true;

        for(int k = 1; k < array.length && needSortingAgain; k++) {

            needSortingAgain = false;
            for (int i = 0; i < array.length - k; i++) {
                if(array[i] > array[i+1]) {
                    swapCells(array,i);
                    needSortingAgain = true;
                }
            }
        }
    }

}
