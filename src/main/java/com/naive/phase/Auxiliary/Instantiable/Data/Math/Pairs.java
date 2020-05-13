package com.naive.phase.Auxiliary.Instantiable.Data.Math;

public class Pairs<E, T> {
    private final E e;
    private final T t;

    public Pairs(E e, T t) {
        this.e = e;
        this.t = t;
    }

    public E getFirst() {
        return e;
    }

    public T getSecond() {
        return t;
    }
}