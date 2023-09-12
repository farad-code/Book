package com.example.book.utils;

import com.example.book.mapper.Mapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.modelmapper.ModelMapper;

@ApplicationScoped
public class MapperUtil {

    public ModelMapper getMapper(){
        Mapper mapper = new Mapper();
        return mapper.getMapper();
    }


}
