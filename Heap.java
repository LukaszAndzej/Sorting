package com.company;

import java.util.Arrays;

class Child {

    public static int left(int i) {
        return (2*i + 1);
    }

    public static int right(int i) {
        return (2*i + 2);
    }
}

public class Heap {

    private static int heapSize = Integer.MAX_VALUE;

    private static boolean childIsBigger(int[] array, int child, int largest) {

        //it's necessary for sorting
        int size = Math.min(array.length, heapSize);

        return (child < size && array[child] > array[largest]);
    }

    private static void exchange(int[] array, final int i, final int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static int indexOfTheLargestChild(int[] array, int subRoot) {
        int left = Child.left(subRoot);
        int right = Child.right(subRoot);

        int largestChild;

        if (childIsBigger(array,left,subRoot)) largestChild = left;
        else largestChild = subRoot;

        if (childIsBigger(array,right,largestChild)) largestChild = right;

        return largestChild;
    }

    public static void maxHeapify(int[] array, int subRoot) {

        int largest = indexOfTheLargestChild(array,subRoot);

        if (largest != subRoot) {
            exchange(array,subRoot,largest);
            maxHeapify(array,largest);
        }

    }

    public static void buildHeap(int[] array) {

        for (int i = (array.length/2) - 1; i >= 0; i--) {
            Heap.maxHeapify(array,i);
        }

    }

    public static void sort(int[] array) {

        buildHeap(array);

        for (int i = array.length - 1; i > 0; i--) {
            exchange(array,0,i);
            heapSize = i;
            maxHeapify(array,0);
        }

    }

    public static void main(String[] args) {
        int[] a = {16,4,10,14,7,9,3,2,8,1};
        Heap.sort(a);
        System.out.println(Arrays.toString(a));

    }

}
