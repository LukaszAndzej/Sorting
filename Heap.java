package com.company;

import java.util.Arrays;

/*
The binary Heap (Min and Max) data structure is an array object that we can view as a nearly complete binary tree.
The important think is relation between children and parent. This relation should be (Parent >= Child).
That's mean that first element in array will be the highest/smallest (Heap Max/ Heap Min) number in Heap.
We can use this information to sort all array but first, we need to build a heap.

To build a heap we can do this in two ways, by heapify or build by insert.
Heapify works like, go to the last parent (size/2 - 1) and check relation between parent and children.
if the parent is bigger/smaller (Min or Max) than the child, we need to exchange this two nodes.
If it's ok than we can continue procedure by go to node (size/2 - 2) because every node from (size/2 - 1) to 0 is parent.

Build by insert works like, I take a value, and I put this value at the end of heap. From the end I check every parent
by climbing all the way to the root. On this way I check where I could put my value.
I stop this procedure when (Parent > value / Parent < value).

That's why it could be different results using this two methods.

Sorting looks like, I take a first element from array, and I exchange them with the last one. I need to reduce array size by 1,
and build a heap but for size = size - 1 and so on until size will be 1.

I use static classes Min and MAx to clearly show, which heap I want to build.
* */

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

    private boolean arrayIsFull() {return (size == capacity);}

    protected void checkTheCapacity() {
        if(arrayIsFull()) {
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

    private boolean findIndex(int indexForNewElement, int key) {
        return ((indexForNewElement > 0) && !compareNodes(heapArray[Child.parent(indexForNewElement)], key));
    }

    private boolean heapIsEmpty() { return (size == 0);}

    private void restoreArraySize(int rememberSize) { size = rememberSize; }

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

        while ( findIndex(indexForNewElement,key) ) {
            heapArray[indexForNewElement] = heapArray[Child.parent(indexForNewElement)];
            indexForNewElement = Child.parent(indexForNewElement);
        }

        heapArray[indexForNewElement] = key;
        size++;

    }

    public void build() {
        if(heapIsEmpty()) throw new IllegalStateException("You didn't build a Heap or it's empty!");

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

        restoreArraySize(rememberSize);
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

}