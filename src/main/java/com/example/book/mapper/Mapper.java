package com.example.book.mapper;


import org.modelmapper.ModelMapper;

public class Mapper {

    private final ModelMapper modelMapper;
    public Mapper() {
        this.modelMapper = new ModelMapper();
    }

    public ModelMapper getMapper() {
        return modelMapper;
    }
}
