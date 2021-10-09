package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

class ArrayOperationForCountingSort {

    protected static int[] temporaryArray;
    protected static double[] resultArray;
    protected static LinkedList<Integer> listOfIndex;

    protected static int getMaxValue(double[] array) {
        int maxNumber = -1;
        for (double value: array) if(maxNumber < (int)value) maxNumber = (int)value;

        return maxNumber;
    }

    protected static void countNumbers(double[] array) {
        for (double value : array) {
            temporaryArray[(int)value]++;
        }
    }

    protected static void countElementsLessOrEqual() {
        listOfIndex = new LinkedList<>();
        listOfIndex.add(temporaryArray[0]);
        for (int k = 1; k < temporaryArray.length; k++) {
            temporaryArray[k] = temporaryArray[k] + temporaryArray[k - 1];
            if(temporaryArray[k] != temporaryArray[k-1]) listOfIndex.add(temporaryArray[k]);
        }

    }

    protected static void putEachElementsIntoCorrectPosition(double[] array) {
        for (int i = array.length - 1; i >= 0; i--) {
            resultArray[temporaryArray[(int) array[i]] - 1] = array[i];
            temporaryArray[(int) array[i]]--;
        }
    }

}

class Counting extends ArrayOperationForCountingSort {

    public static double[] sort(double[] array) {

        int maxValueInArray = getMaxValue(array);
        temporaryArray = new int[maxValueInArray + 1];
        resultArray = new double[array.length];

        countNumbers(array);

        countElementsLessOrEqual();

        putEachElementsIntoCorrectPosition(array);

        return resultArray;
    }

}

class Insertion {

    private static boolean conditionInSortedArray(ArrayList<Double> array, int earlierElement, double currentElement) {
        return (earlierElement >= 0.0 && array.get(earlierElement) > currentElement);
    }

    public static void sort(ArrayList<Double> array) {

        int size = array.size();
        for(int i = 1; i < size; i++) {
            double currentElement = array.get(i); //take an element and insert into sorted array
            int earlierElement; // sorted array is from index (i - 1) to 0

            for(earlierElement = i - 1; conditionInSortedArray(array,earlierElement,currentElement); earlierElement--) {
                array.set(earlierElement + 1,array.get(earlierElement));
            }

            array.set(earlierElement + 1,currentElement);
        }
    }
}

class Bucket {

    private static boolean emptyBucket(int size) { return (size == 0);}

    private static void putValueIntoTheBucket(ArrayList<Double>[] bucketArray, double[] array, int n) {

        for (double v : array) {
            double afterDot = v - (int)v;
            int index = (int)(n*afterDot);
            bucketArray[index].add(v);
        }
    }

    private static void sortEveryBucket(ArrayList<Double>[] bucketArray) {
        for (ArrayList<Double> bucketArrays : bucketArray) {
            Insertion.sort(bucketArrays);
        }
    }

    private static double[] concatenateListsOfBucket(ArrayList<Double>[] bucketArray, int size) {
        double[] result = new double[size];

        int index = 0;
        for (ArrayList<Double> bucketArrays : bucketArray) {
            if (!emptyBucket(bucketArrays.size())) {
                for (Double value : bucketArrays) result[index++] = value;
            }
        }

        return result;
    }

    public static double[] sort(double[] array) {
        int n = array.length;

        ArrayList<Double>[] bucketArray = new ArrayList[n];
        Arrays.setAll(bucketArray,element -> new ArrayList<Double>());

        putValueIntoTheBucket(bucketArray,array,n);
        sortEveryBucket(bucketArray);

        return concatenateListsOfBucket(bucketArray,n);

    }

}

public class RationNumbers {

    private static int getStartIndex(int j) {
        if(j != 0) return Counting.listOfIndex.get(j-1);
        else return 0;
    }

    public static void sort(double[] array) {
        double[] resultArray = Arrays.copyOf(Counting.sort(array),array.length);

        for (int i = 0; i < Counting.listOfIndex.size(); i++) {
            int startIndex = getStartIndex(i), endIndex = Counting.listOfIndex.get(i);

            double[] tempArray = Bucket.sort(Arrays.copyOfRange(resultArray,startIndex,endIndex));
            for (int j = startIndex, k = 0; (j < endIndex) && (k < tempArray.length); j++, k++) {
                resultArray[j] = tempArray[k];
            }
        }

        System.out.println(Arrays.toString(resultArray));
    }


    public static void main(String[] args) {
        double[] d = {0.78,0.17,0.39,0.26,0.72,0.94,0.21,0.12,0.23,0.68};
        double[] test = {1.78,1.17,1.39,1.26,1.72,1.94,1.21,1.12,1.23,1.68,
                        0.94,0.78,0.17,0.39,0.26,0.72,0.21,0.12,0.23,0.68,
                        12.78,12.17,12.39,12.26,12.72,12.94,12.21,12.12,12.23,12.68,
                        456.78,456.17,456.39,456.26,456.72,456.94,456.21,456.12,456.23,456.68,
                        78.78,78.17,78.39,78.26,78.72,78.94,78.21,78.12,78.23,78.68};

        RationNumbers.sort(test);

    }

}
