package com.andrei.mobiletracker.user.facade.populator;

public interface Populator<S,T> {

    void populate(T target, S source);
}
