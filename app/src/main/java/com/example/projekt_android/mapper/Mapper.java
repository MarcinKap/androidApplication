package com.example.projekt_android.mapper;

public interface Mapper<F, T> {

    T mapToEntity(F from);
    F mapFromEntity(T to);
}