package com.andrei.mobiletracker.beans.populator;

public interface Populator<S, T> {

    void populate(T target, S source);
}

