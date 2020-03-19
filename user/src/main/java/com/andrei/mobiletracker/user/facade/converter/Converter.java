package com.andrei.mobiletracker.user.facade.converter;

import java.util.List;
import java.util.stream.Collectors;

public interface Converter<S,T> {

    T convert(S source);

    default List<T> convertAll(List<S> sourceList){

        return sourceList.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
