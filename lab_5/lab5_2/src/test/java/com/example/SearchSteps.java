package com.example;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;

import java.util.List;


public class SearchSteps {
    static final Logger log = getLogger(lookup().lookupClass());
    private Library library;

    private List<Book> books;

    @Given("The library contains the following books")
    public void the_library_has_the_following_books(DataTable dataTable) {
        library = new Library();
        dataTable.asMaps().forEach(row -> {
            Book book = new Book(row.get("Title"), row.get("Author"), row.get("Date"), row.get("Category"));
            library.addBook(book);
        });
        //System.out.println(library.getBooks());
        books = null;
    }

    @When("The customer searches for books published between {int} and {int}")
    public void search_books_by_publication_year(int yearStart, int yearEnd) {
        log.info("Searching books published between {} and {}", yearStart, yearEnd);
        books = library.getBooksInYearInterval(yearStart, yearEnd);
        log.info("Books found: {}", books);
    }

    @Then("{int} book should have been found")
    public void found_n_books_from_query(int num_books) {
        assertEquals(books.size(), num_books);
    }

    @And("The Book {int} should have the title {string}")
    public void book_number_n_has_title(int n, String title) {
        assertEquals(books.get(n - 1).getTitle(), title);
    }


    @When("The customer searches for books with category {string}")
    public void search_books_by_category(String category) {
        log.info("Searching books in the category {}", category);
        books = library.getBooksByCategory(category);
        log.info("Books found: {}", books);
    }

    @Then("The {int} book should have the author {string}")
    public void book_number_n_has_author(int n, String author) {
        assertEquals(books.get(n - 1).getAuthor(), author);
    }

    @When("The customer searches for books written by {string}")
    public void search_books_by_author(String author) {
        log.info("Searching books by author {}", author);
        books = library.getBooksByAuthor(author);
        log.info("Books found: {}", books);
    }

    @When("The customer searches for books with title {}")
    public void search_books_by_title(String title) {
        log.info("Searching books by title {}", title);
        books = library.getBooksByTitle(title);
        log.info("Books found: {}", books);
    }

    @Then("All books should have {} as author")
    public void all_books_have_author(String author) {
        books.forEach(book -> assertEquals(book.getAuthor(), author));
    }

}
