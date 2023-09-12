package com.example.book.controller;

import com.example.book.dto.book.BookRequest;
import com.example.book.dto.book.BookResponse;
import com.example.book.exception.NotFoundException;
import com.example.book.model.Book;
import com.example.book.service.BookService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Path("/book")
@CrossOrigin("*")
public class BookController {

    @Inject
    private BookService bookService;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchAllBooks(){
        List<BookResponse> books = bookService.fetchAllBooks();
       return Response.ok(books).header("Access-Control-Allow-Origin", "*")
               .build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public BookResponse addBook(BookRequest request){
        return bookService.addBook(request);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public BookResponse updateBook(BookRequest request, @PathParam("id") Long id) throws NotFoundException {
        return bookService.updateBook(request,id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public BookResponse viewBook(@PathParam("id") Long id) throws NotFoundException {
        return bookService.getBook(id);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public boolean removeBook(@PathParam("id") Long id) throws NotFoundException {
        return bookService.deleteBook(id);
    }



}
