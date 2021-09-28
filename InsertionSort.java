package com.company;

/*

Insertion Sort is a sorting when we just take an element i from array, and we try to insert this element into the left
part of array.
Left part of array must be sorted that why we need to second loop for where we compare current element with elements
in the sorted array (left part of array).

            The complexity of the overall algorithm is O(n^2)

* */

public class InsertionSort {

    private static boolean conditionInSortedArray(int[] array, int earlierElement, int currentElement) {
        return (earlierElement >= 0 && array[earlierElement] > currentElement);
    }

    public static void sort(int[] array) {

        for(int i = 1; i < array.length; i++) {
            int currentElement = array[i]; //take an element and insert into sorted array

            int earlierElement; // sorted array is from index (i - 1) to 0
            for(earlierElement = i - 1; conditionInSortedArray(array,earlierElement,currentElement); earlierElement--) {
                array[earlierElement + 1] = array[earlierElement];
            }

            array[earlierElement + 1] = currentElement;
        }

    }

}
