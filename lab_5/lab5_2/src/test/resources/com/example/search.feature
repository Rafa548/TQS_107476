@search_books
Feature: Book search
  To allow a customer to find his favourite books quickly, the library must offer multiple ways to search for a book.

  Background: List of books
    Given The library contains the following books
      | Title                  | Author          | Date       | Category |
      | idc                    | Anonymous       | 19-07-2012 | sci-fi   |
      | idk                    | Tom Sayer       | 31-08-2017 | advice   |
      | How to fly like a chicken     | Jack Yellowstone | 30-03-2012 | sci-fi  |
      | Science is cool | Tom Sayer      | 02-08-2022 | science  |

  Scenario: Search books by publication year
    When The customer searches for books published between 2013 and 2017
    Then 1 book should have been found
    And The Book 1 should have the title 'idk'

  Scenario: Search books by title
    When The customer searches for books with title 'idc'
    Then 1 book should have been found
    And The Book 1 should have the title 'idc'

  Scenario: Search book by author
    When The customer searches for books written by 'Tom Sayer'
    Then 2 book should have been found
    And The Book 1 should have the title 'idk'
    And The Book 2 should have the title 'Science is cool'
    And All books should have 'Tom Sayer' as author

  Scenario: Search books by category
    When The customer searches for books with category 'sci-fi'
    Then 2 book should have been found
    And The Book 1 should have the title 'idc'
    And The Book 2 should have the title 'How to fly like a chicken'