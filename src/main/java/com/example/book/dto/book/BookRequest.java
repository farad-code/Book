package com.example.book.dto.book;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookRequest {

    @NotBlank(message = "Book is required")
    private String author;
    @NotBlank(message = "Published Date is required")
    private String publishedDate;
    @NotBlank(message = "Isbn is required")
    private String isbn;
    @NotBlank(message = "Title is required")
    private String title;
}
