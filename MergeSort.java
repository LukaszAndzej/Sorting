package com.company;

import java.util.Arrays;

/*

Merge Sort it is really simple sorting because we need to cut arrays on a half, and we doing it until
we will get an arrays of length 1.

When all arrays are length 1, then we need to merge it, and when we doing it we insert elements from small arrays
into a large array, but we checked which element is smaller or bigger.

        The complexity of the overall algorithm is O(n log n)
* */

public class MergeSort {

    private static boolean thereIsARest(int index, int arrayLength) { return (index > arrayLength);}

    private static int[] newHalfArray(int[] array, int from, int to) {
        return Arrays.copyOfRange(array,from,to);
    }

    private static void merge(int[] firstArray, int[] secondArray, int[] array) {

        int indexFirst = 0;
        int indexSecond = 0;
        int index = 0;

        while (indexFirst < firstArray.length && indexSecond < secondArray.length) {
            if(firstArray[indexFirst] < secondArray[indexSecond]) array[index++] = firstArray[indexFirst++];
            else array[index++] = secondArray[indexSecond++];
        }

        if(thereIsARest(indexFirst, firstArray.length)) System.arraycopy(firstArray,indexFirst,array,index,array.length - index);

        if(thereIsARest(indexSecond, secondArray.length)) System.arraycopy(secondArray,indexSecond,array,index,array.length - index);

    }

    public static void sort(int[] array) {

        int arrayLength = array.length;
        if(arrayLength > 1) {
            int[] firstHalfOfArray = newHalfArray(array,0,(arrayLength/2));
            int[] secondHalfOfArray = newHalfArray(array,(arrayLength/2),arrayLength);

            sort(firstHalfOfArray);
            sort(secondHalfOfArray);

            merge(firstHalfOfArray,secondHalfOfArray,array);

        }
    }

}
