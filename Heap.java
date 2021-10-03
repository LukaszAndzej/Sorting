package com.company;

import java.util.Arrays;

class Child {

    protected static int parent(int i) { return (i/2);}

    protected static int left(int i) { return (2*i + 1);}

    protected static int right(int i) {
        return (2*i + 2);
    }
}

class HeapArray {

    protected int capacity = 10;
    protected int size = 0;

    protected int[] heapArray = new int[capacity];
}

abstract class ArrayOperations extends HeapArray {

    protected abstract boolean compareNodes(int idFirstNode, int idSecondNode);

    protected void checkTheCapacity() {
        if(size == capacity) {
            capacity *= 2;
            heapArray = Arrays.copyOf(heapArray,capacity);
        }
    }

    protected void exchange(final int i, final int j) {
        int temp = heapArray[i];
        heapArray[i] = heapArray[j];
        heapArray[j] = temp;
    }

    protected int checkTheKids(int subRoot) {
        int left = Child.left(subRoot);
        int right = Child.right(subRoot);

        int largestChild = subRoot;

        if ( (left < size) && compareNodes(heapArray[left],heapArray[subRoot])) largestChild = left;
        if ( (right < size) && compareNodes(heapArray[right],heapArray[largestChild])) largestChild = right;

        return largestChild;
    }

    public void printHeap() {
        System.out.println();
        for (int i = 0; i < size; i++) {
            if(i == 0) System.out.print("[");

            System.out.print(heapArray[i]);

            if((i+1) != size) System.out.print(", ");
            else System.out.println("]");
        }
    }

}

abstract class HeapProcedures extends ArrayOperations {

    private void heapify(int subRoot) {

        int index = checkTheKids(subRoot);

        if (index != subRoot) {
            exchange(subRoot,index);
            heapify(index);
        }
    }

    public void insert(int key) {

        checkTheCapacity();
        heapArray[size] = key;
        int indexForNewElement = size;

        while ( (indexForNewElement > 0) && !compareNodes(heapArray[Child.parent(indexForNewElement)], key) ) {
            heapArray[indexForNewElement] = heapArray[Child.parent(indexForNewElement)];
            indexForNewElement = Child.parent(indexForNewElement);
        }

        heapArray[indexForNewElement] = key;
        size++;

    }


    public void build() {
        if(size == 0) throw new IllegalStateException("You didn't build a Heap or it's empty!");

        for (int i = (size/2) - 1; i >= 0; i--) heapify(i);
    }

    //could be different results for build by insert and heapify
    public void buildByInsert(int[] array) { for (int j : array) insert(j);}

    public void build(int[] array) {
        capacity = array.length;
        size = array.length;

        heapArray = Arrays.copyOf(array,capacity);
        build();
    }

    public void sort() {

        if(size == 0) throw new IllegalStateException("You didn't build a Heap or it's empty!");

        int rememberSize = size;

        for (int i = size - 1; i > 0; i--) {
            exchange(0,i);
            size--;
            heapify(0);
        }

        size = rememberSize;
    }

}


public class Heap {

    static class Min extends HeapProcedures {

        @Override
        protected boolean compareNodes(int idFirstNode, int idSecondNode) {
            return (idFirstNode < idSecondNode);
        }
    }

    static class Max extends HeapProcedures {

        @Override
        protected boolean compareNodes(int idFirstNode, int idSecondNode) {
            return (idFirstNode > idSecondNode);
        }
    }

    public static void main(String[] args) {
        Heap.Max heap = new Max();

        int[] a = {4,1,3,2,16,9,10,14,8,7};
        heap.build(a);
        heap.printHeap();
//
//        heap.insert(55);
//        heap.printHeap();
//
//        heap.sort();
//        heap.printHeap();
//
//        heap.insert(67);
//        heap.build();
//        heap.sort();
//        heap.printHeap();

    }

}
