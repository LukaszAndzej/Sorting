package com.company;

/*
The Counting sort works like, first I need to find the highest number in array, and I have to create
new temporary array where I count my every element from array for e.g. if in array will be 4, than in
temporaryArray[4] I need to do increment this number (temporaryArray[4] = temporaryArray[4] + 1), that's way
I will know how many exactly the same elements is in array.

Next step, I need to add every element step by step ( i + (i-1)) for e.g.:
        temporary array -> [2,0,2,3,0,1]
using countElementsLessOrEqual method I will be known, how many numbers will be before my actual number:
        i + (i-1) -> temporaryArray[1] + temporaryArray[0], next temporaryArray[2] + temporaryArray[1] and so on...
        that's why temporary array will be looks like:
        temporary array -> [2,2,4,7,7,8]

At The End We need to just put every element from array to result array using temporary array like:
        resultArray[temporaryArray[array[i] - 1]]
and after, we need to decrement temporaryArray[array[i] - 1] because we can fill array just to the left.

Elements in array should be greater than or equal to 0 (array[i] >= 0 for i >= 0)

                    The complexity of the overall algorithm is O(n + k)

* */

class ArrayOperation {

    protected static int[] temporaryArray;
    protected static int[] resultArray;

    protected static int getMaxValue(int[] array) {
        int maxNumber = Integer.MIN_VALUE;
        for (int i: array) if(maxNumber < i) maxNumber = i;

        return maxNumber;
    }

    protected static void countNumbers(int[] array) {
        for (int i : array) temporaryArray[i] = temporaryArray[i] + 1;
    }

    protected static void countElementsLessOrEqual() {
        for (int k = 1; k < temporaryArray.length; k++) {
            temporaryArray[k] = temporaryArray[k] + temporaryArray[k - 1];
        }
    }

    protected static void putEachElementsIntoCorrectPosition(int[] array) {
        for (int i = array.length - 1; i >= 0; i--) {
            resultArray[temporaryArray[array[i]] - 1] = array[i];
            temporaryArray[array[i]] = temporaryArray[array[i]] - 1;
        }
    }

}

public class CountingSort extends ArrayOperation {

    public static int[] sort(int[] array) {

        int maxValueInArray = getMaxValue(array);
        temporaryArray = new int[maxValueInArray + 1];
        resultArray = new int[array.length];

        countNumbers(array);

        countElementsLessOrEqual();

        putEachElementsIntoCorrectPosition(array);

        return resultArray;
    }

}
