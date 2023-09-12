package com.example.book.service;



import com.example.book.dto.book.BookRequest;
import com.example.book.dto.book.BookResponse;
import com.example.book.exception.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface BookService {

    BookResponse addBook(BookRequest request);
    BookResponse updateBook(BookRequest request, Long id) throws NotFoundException;
    boolean deleteBook(Long id) throws NotFoundException;
    List<BookResponse> fetchAllBooks();
    BookResponse getBook(Long id) throws NotFoundException;

}
