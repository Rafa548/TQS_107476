package com.example;

import java.util.ArrayList;
import java.util.List;

/*
 * Library holds a list of books
 */

public class Library {
    private List<Book> books;

    public Library() {
        books = new ArrayList<>();
    }

    public List<Book> getBooks() {
        return books;
    }

    public boolean addBook(Book b) {
        return books.add(b);
    }

    public List<Book> getBooksByAuthor(String author) {
        return books.stream().filter((b) -> b.getAuthor().equals(author)).toList();
    }

    public List<Book> getBooksByCategory(String category) {
        return books.stream().filter((b) -> b.getCategory().equals(category)).toList();
    }

    public List<Book> getBooksInYearInterval(int yearStart, int yearEnd) {
        return books.stream().filter((b) -> Integer.parseInt(b.getPublicationDate().split("-")[2]) >= yearStart && Integer.parseInt(b.getPublicationDate().split("-")[2]) <= yearEnd).toList();
    }

    public List<Book> getBooksByTitle(String title) {
        return books.stream().filter((b) -> b.getTitle().equals(title)).toList();
    }
}
