package com.company;

import java.util.ArrayList;
import java.util.Arrays;

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

public class Bucket {

    public static double[] sort(double[] array) {
        int n = array.length;
        double[] result = new double[n];

        ArrayList<Double>[] bucketArray = new ArrayList[n];
        Arrays.setAll(bucketArray,element -> new ArrayList<Double>());

        for (double v : array) {
            int index = (int) (n * v);
            bucketArray[index].add(v);
        }

        for (ArrayList<Double> bucketArrays : bucketArray) {
            Insertion.sort(bucketArrays);
        }

        int index = 0;
        for (ArrayList<Double> bucketArrays : bucketArray) {
            if (bucketArrays.size() != 0) {
                for (Double value : bucketArrays) result[index++] = value;
            }
        }

        return result;

    }

    public static void main(String[] args) {
        double[] d = {0.78,0.17,0.39,0.26,0.72,0.94,0.21,0.12,0.23,0.68};

        System.out.println(Arrays.toString(Bucket.sort(d)));


    }

}
