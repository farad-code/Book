package com.example.book.dto.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class BookResponse {

    private Long id;
    private String author;
    private String publishedDate;
    private String isbn;
    private String title;

}