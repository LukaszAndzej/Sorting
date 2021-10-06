package com.company;

import java.util.Arrays;

/*
Radix sort it's really easy sorting. We need to just take sub-numbers from right side, and we sort numbers by sub-numbers,
but we go from right side to left side. e.g.:

                 first column  second column
                      ||          ||
                      \/          \/
         123        12|3|        4|9|0       |3|22       123
         231        23|1|        2|3|1       |1|23       231
         761        76|1| ==>    7|6|1 ==>   |2|31 ==>   322
         490        49|0|        3|2|2       |7|61       490
         322        32|2|        1|2|3       |4|90       761

We are sorting numbers in array, using just sub-numbers (numbers in column). Next we take next column, we sort numbers and so on.

I made a class DifferentLengthArray because Radix sort works just for constant length of numbers.
That's way I sort array using length numbers. e.g.:

        {234,12,9,0,12345}  ==>  {9,0,12,234,12345}

Every element in array need to be greater or equal than 0 (array[i] >= 0 for i >= 0)

* */

class MyArrays {
    protected static int[] countingNumbersArray;
    protected static int[] resultArray;
}

abstract class ArrayOperationForSorting extends MyArrays {

    abstract int getSubNumberOrLength(int number, int index);

    abstract void countNumbers(int[] array, int d);

    protected static void prefixSum(int[] array) {
        for (int k = 1; k < array.length; k++) {
            array[k] = array[k] + array[k - 1];
        }
    }

    protected void putEachElementsIntoCorrectPosition(int[] array, int d) {
        for (int i = array.length - 1; i >= 0; i--) {
            int subNumber = getSubNumberOrLength(array[i], d);
            resultArray[countingNumbersArray[subNumber] - 1] = array[i]; //must be array like 'don't touch'
            countingNumbersArray[subNumber]--;
        }
    }

}

abstract class ModifyCountingSort extends ArrayOperationForSorting {

    public void sortCounting(int[] array, int d) {

        int possibleDecimalNumeral = 10; // from 0 to 9
        countingNumbersArray = new int[possibleDecimalNumeral];

        countNumbers(array, d);
        prefixSum(countingNumbersArray);
        putEachElementsIntoCorrectPosition(array,d);
    }
}

public class Radix extends ModifyCountingSort {

    @Override
    int getSubNumberOrLength(int number, int index) {
        String stringNumber = String.valueOf(number);
        return Integer.parseInt(stringNumber.substring(index - 1, index));
    }

    @Override
    void countNumbers(int[] array, int d) {
        for (int i : array) {
            int j = getSubNumberOrLength(i,d);
            countingNumbersArray[j]++;
        }
    }

    public int[] sort(int[] array, int d) {

        resultArray = new int[array.length];

        for (int i = d; i > 0; i--) {
            sortCounting(array, i);
            array = Arrays.copyOf(resultArray, array.length);
        }

        return resultArray;
    }

}

class DifferentLengthArray extends ModifyCountingSort {

    protected static int[] arrayOfLength;

    @Override
    int getSubNumberOrLength(int number, int index) {
        return (getNumberLength(number)-1);
    }

    @Override
    void countNumbers(int[] array, int d) {
        for (int number: array) countingNumbersArray[getNumberLength(number) - 1]++;
        arrayOfLength = Arrays.copyOf(countingNumbersArray, countingNumbersArray.length);
        ArrayOperationForSorting.prefixSum(arrayOfLength);
    }

    private boolean theSameNumbers(int first, int second) { return (first == second);}

    private static int getStartIndex(int j) {
        if(j != 0) return arrayOfLength[j-1];
        else return 0;
    }

    private int getNumberLength(int value) {
        String number = String.valueOf(value);
        return number.length();
    }

    private void sortArrayByLength(int[] array) {
        resultArray = new int[array.length];
        countingNumbersArray = new int[10];
        sortCounting(array,0);
    }

    private void sortNumbersByLength(int[] tempArray, int[] array, int start) {
        Radix radix = new Radix();
        int[] arr = radix.sort(tempArray, getNumberLength(tempArray[0]));
        for (int i = start, k = 0; i < (arr.length + start); i++, k++) {
            array[i] = arr[k];
        }
    }

    public int[] sort(int[] array) {

        sortArrayByLength(array);
        int[] result = Arrays.copyOf(resultArray,resultArray.length);

        for (int i = 0; i < arrayOfLength.length; i++) {
            int start = getStartIndex(i), end = arrayOfLength[i];
            if(!theSameNumbers(start, end)) {
                int[] subArray = Arrays.copyOfRange(result,start,end);
                sortNumbersByLength(subArray,result,start);
            }
        }

        return result;
    }

}