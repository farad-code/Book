package com.example.book.mapper;

import com.example.book.dto.book.BookRequest;
import com.example.book.dto.book.BookResponse;
import com.example.book.model.Book;
import com.example.book.utils.MapperUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
public class BookMapper {

    @Inject
    private MapperUtil mapperUtil;
    public BookRequest bookToBookRequestMapper(Book book){
        return mapperUtil.getMapper().map(book,BookRequest.class);
    }

    public Book bookRequestToBookMapper(BookRequest bookRequest){
        return mapperUtil.getMapper().map(bookRequest,Book.class);
    }

    public BookResponse bookToBookResponseMapper(Book book){
        return mapperUtil.getMapper().map(book, BookResponse.class);
    }
}
