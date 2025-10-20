package com.mipt;

import java.util.Iterator;

public class CustomArrayList<A> implements CustomList<A> {
    private static final double COEFFICIENT = 1.5;
    private static final int DEFAULT_CAPACITY = 10;
    private int size;
    private int capacity;
    private Object[] array;

    public CustomArrayList() {
        array = new Object[DEFAULT_CAPACITY];
        size = 0;
        capacity = DEFAULT_CAPACITY;
    }

    @Override
    public void add(A element) {
        if (element == null) {
            throw new NullPointerException("Null element can't be added to CustomArrayList");
        }
        if (size >= capacity) {
            Object[] newArray = new Object[capacity *= COEFFICIENT];
            if (size >= 0) System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        }
        array[size] = element;
        ++size;
    }

    @Override
    public A get(Integer index) {
        if (index >= size || index < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return (A) array[index];
    }

    @Override
    public void remove(Integer index) {
        if (index >= size || index < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        Object[] newArray = new Object[capacity];
        System.arraycopy(array, 0, newArray, index, size);
        for (int i = index + 1; i < size; i++) {
            newArray[i - 1] = array[i];
        }
        --size;
        array = newArray;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<A> iterator() {
        return new CustomArrayListIterator();
    }

    private class CustomArrayListIterator implements Iterator<A> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public A next() {
            if (!hasNext()) {
                throw new ArrayIndexOutOfBoundsException();
            }
            A item = (A) array[index];
            ++index;
            return item;
        }
    }
}
