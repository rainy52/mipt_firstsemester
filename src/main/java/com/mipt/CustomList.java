package com.mipt;

public interface CustomList<A> extends Iterable<A> {
    void add(A element);
    A get(Integer index);
    void remove(Integer index);
    int size();
    boolean isEmpty();
}
