package com.example.book.service.implementation;

import com.example.book.constant.Constant;
import com.example.book.dto.book.BookRequest;
import com.example.book.dto.book.BookResponse;
import com.example.book.exception.NotFoundException;
import com.example.book.mapper.BookMapper;
import com.example.book.model.Book;
import com.example.book.repository.Repository;
import com.example.book.service.BookService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BookServiceImpl implements BookService {

    Repository<Book, Long> bookRepository = new Repository<>(Book.class);


    @Inject
    private BookMapper bookMapper;


    @Override
    public BookResponse addBook(BookRequest request) {
        Book book = bookMapper.bookRequestToBookMapper(request);
        Book savedBook = bookRepository.save(book);
        return BookResponse.builder()
                .id(savedBook.getId())
                .author(savedBook.getAuthor())
                .isbn(savedBook.getIsbn())
                .title(savedBook.getTitle())
                .publishedDate(savedBook.getPublishedDate())
                .build();
    }

    @Override
    public BookResponse updateBook(BookRequest request, Long id) throws NotFoundException {
        Optional<Book> foundBook = bookRepository.findById(id);
        if(foundBook.isEmpty()) throw new NotFoundException(Constant.BOOK_NOT_FOUND);
        foundBook.get().setAuthor(request.getAuthor());
        foundBook.get().setIsbn(request.getIsbn());
        foundBook.get().setTitle(request.getTitle());
        foundBook.get().setPublishedDate(request.getPublishedDate());
        Book editedBook = bookRepository.save(foundBook.get());
        return   BookResponse.builder()
                .id(editedBook.getId())
                .author(editedBook.getAuthor())
                .isbn(editedBook.getIsbn())
                .title(editedBook.getTitle())
                .publishedDate(editedBook.getPublishedDate())
                .build();
    }

    @Override
    public boolean deleteBook(Long id) throws NotFoundException {
        Optional<Book> foundBook = bookRepository.findById(id);
        if(foundBook.isEmpty()) throw new NotFoundException(Constant.BOOK_NOT_FOUND);
        bookRepository.delete(foundBook.get());
        return true;
    }

    @Override
    public List<BookResponse> fetchAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(book -> BookResponse.builder()
                .id(book.getId())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .publishedDate(book.getPublishedDate())
                .build()).toList();
    }

    @Override
    public BookResponse getBook(Long id) throws NotFoundException {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isEmpty()) throw new NotFoundException(Constant.BOOK_NOT_FOUND);
        return BookResponse.builder()
                .id(book.get().getId())
                .author(book.get().getAuthor())
                .isbn(book.get().getIsbn())
                .publishedDate(book.get().getPublishedDate())
                .build();
    }
}

