package com.company;

import java.util.*;

/*
Bucket sort divides the interval [0,1) into n equal-sized subintervals, or buckets,
and then distributes the n input numbers into the buckets.
Since the inputs are uniformly and independently distributed over [0,1),
we do not expect many numbers to fall into each bucket. To produce the output,
we simply sort the numbers in each bucket and then go through the buckets in order, listing the elements in each.

             Bucket sort has an average-case running time of O(n).


I modify this program (class RationNumbers) to sort not just numbers from interval [0,1) but every number greater than 0.
For e.g.:
            {1.78, 1.17, 7.77, 0.94, 0.78, 12.78, 12.17, 456.78, 456.17, 78.78, 78.17}

First of all, I use Counting sort to sort numbers but sort by using this part before dot (Integer -> [12].78 )
That's why the result will be like:
            {0.94, 0.78, 1.78, 1.17, 7.77, 12.78, 12.17, 78.78, 78.17, 456.78, 456.17}

Now, I can use Bucket sort to sort every sub-array (f.e.g: 0.94, 0.78 or 12.78, 12.17) but I will be sort just
numbers by using part after dot. For e.g.:
                    12.78, 12.17 take just second part -> 0.78, 0.17
The results will be:
             { 0.78, 0.94, 1.17, 1.78, 7.77, 12.17, 12.78, 78.17, 78.78, 456.17, 456.78}

* */

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

    private static boolean condition(int index, int end) { return (index < end);}

    private static int getStartIndex(int j) {
        if(j != 0) return Counting.listOfIndex.get(j-1);
        else return 0;
    }

    private static void sortAndUpdateAnArray(double[] resultArray, int start, int end) {
        double[] tempArray = Bucket.sort(Arrays.copyOfRange(resultArray,start,end));

        for (int j = start, k = 0; condition(j,end) && condition(k, tempArray.length); j++, k++) {
            resultArray[j] = tempArray[k];
        }
    }

    public static double[] sort(double[] array) {
        double[] resultArray = Arrays.copyOf(Counting.sort(array),array.length);

        for (int i = 0; i < Counting.listOfIndex.size(); i++) {
            int startIndex = getStartIndex(i);
            int endIndex = Counting.listOfIndex.get(i);

            sortAndUpdateAnArray(resultArray,startIndex,endIndex);
        }

        return resultArray;
    }
    
}
