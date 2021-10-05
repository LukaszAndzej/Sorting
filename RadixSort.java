package com.company;

import java.util.Arrays;

class MyArrays {
    protected static int[] temporaryArray;
    protected static int[] resultArray;
}

class MyArrayOperationForSorting extends MyArrays {

    protected static int getSubNumber(int number, int index) {
        String stringNumber = String.valueOf(number);
        return Integer.parseInt(stringNumber.substring(index - 1, index));
    }

    protected static void countNumbers(int[] array, int d) {
        for (int i : array) {
            int j = getSubNumber(i,d);
            temporaryArray[j] = temporaryArray[j] + 1;
        }
    }

    protected static void countElementsLessOrEqual() {
        for (int k = 1; k < temporaryArray.length; k++) {
            temporaryArray[k] = temporaryArray[k] + temporaryArray[k - 1];
        }
    }

    protected static void putEachElementsIntoCorrectPosition(int[] array, int d) {
        for (int i = array.length - 1; i >= 0; i--) {
            int subNumber = getSubNumber(array[i], d);
            resultArray[temporaryArray[subNumber] - 1] = array[i]; //must be array like 'don't touch'
            temporaryArray[subNumber] = temporaryArray[subNumber] - 1;
        }
    }

}

class ModifyCountingSort extends MyArrayOperationForSorting {

    public static void sort(int[] array, int d) {

        int maxForDecimal = 10; // from 0 to 9
        temporaryArray = new int[maxForDecimal];

        countNumbers(array, d);
        countElementsLessOrEqual();
        putEachElementsIntoCorrectPosition(array,d);
    }

}


public class RadixSort extends MyArrays {

    public static int[] sort(int[] array, int d) {

        resultArray = new int[array.length];

        for (int i = d; i > 0; i--) {
            ModifyCountingSort.sort(array, i);
            array = Arrays.copyOf(resultArray, array.length);
        }

        return resultArray;
    }

//    public static void main(String[] args) {
//        int[] a = {141,126,261,259};
//        int[] b = {329,457,657,839,436,720,355};
//
//        System.out.println(Arrays.toString(sort(b, 3)));
//    }
}



// for fun, different size on number

class MyArray {
    protected static int capacity = 1;

    protected static int[] arrayOfCountNumbers = new int[capacity];

    private static boolean arrayIsFull(int newCapacity) {return (newCapacity >= capacity);}


    protected static void checkTheCapacity(int newCapacity) {
        if(arrayIsFull(newCapacity)) {
            capacity = newCapacity;
            arrayOfCountNumbers = Arrays.copyOf(arrayOfCountNumbers,capacity);
        }
    }
}

class ArraySortByLengthNumber extends MyArray {

    private static void getAllLengths(int[] array) {

        for (int value: array) {
            String number = String.valueOf(value);
            checkTheCapacity(number.length());
            arrayOfCountNumbers[number.length() - 1] = arrayOfCountNumbers[number.length() - 1] + 1;
        }

        countElementsLessOrEqual();

    }

    private static void countElementsLessOrEqual() {
        for (int k = 1; k < arrayOfCountNumbers.length; k++) {
            arrayOfCountNumbers[k] = arrayOfCountNumbers[k] + arrayOfCountNumbers[k - 1];
        }
    }

    protected static int[] sortByLength(int[] array) {
        int[] resultArray = new int[array.length];
        int[] rememberArray = Arrays.copyOf(arrayOfCountNumbers,arrayOfCountNumbers.length);

        for (int i = array.length - 1; i >= 0; i--) {
            String s = String.valueOf(array[i]);
            resultArray[arrayOfCountNumbers[s.length() - 1] - 1] = array[i];
            arrayOfCountNumbers[s.length() - 1] = arrayOfCountNumbers[s.length() - 1] - 1;
        }

        arrayOfCountNumbers = rememberArray;

        return resultArray;
    }

    public static int[] sort(int[] array) {

        getAllLengths(array);

        array = Arrays.copyOf(sortByLength(array),array.length);


        int start = 0;
        int end;

        for (int j = 0; j < arrayOfCountNumbers.length; j++) {
            if(j != 0) start = arrayOfCountNumbers[j-1];

            end = arrayOfCountNumbers[j];

            int[] tempArray = Arrays.copyOfRange(array,start,end);
            String lengthNumber = String.valueOf(tempArray[0]);

            int[] arr = RadixSort.sort(tempArray, lengthNumber.length());
            for (int i = start, k = 0; i < (arr.length + start); i++, k++) {
                array[i] = arr[k];
            }

        }

        return array;
    }

    public static void main(String[] args) {
        int[] a = {1,12,123,1234,12345,0,34};
        int[] b = {1,0,123,234,235,22,11,9,1234,329,457,657,839,436,720,355,6,5,4,3,2,23,45,87};

        System.out.println(Arrays.toString(sort(b)));


    }
}