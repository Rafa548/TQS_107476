@calc_sample
Feature: Basic Arithmetic

  Background: A Calculator
    Given a calculator I just turned on

  Scenario: Addition
    When I add 4 and 5
    Then the result is 9

  Scenario: Substraction
    When I substract 7 to 2 
    Then the result is 5

  Scenario Outline: Several additions
    When I add <a> and <b>
    Then the result is <c>

  Examples: Single digits
    | a | b | c  |
    | 1 | 2 | 3  |
    | 3 | 7 | 10 |

  Scenario Outline: Several substractions
    When I substract <c> to <b>
    Then the result is <a>

  Examples: Single digits
    | a | b | c  |
    | 1 | 2 | 3  |
    | 3 | 7 | 10 |

  Scenario: Multiplication
      When I multiply 4 and 5
      Then the result is 20

  Scenario: Division
      When I divide 10 by 2
      Then the result is 5

  Scenario: Division
      When I divide 0 by 2
      Then the result is 0

  Scenario: Division by zero
      When I divide 10 by 0
      Then the result is infinity

  Scenario: Power
      When I raise 2 to the power of 3
      Then the result is 8

  Scenario: Square root
      When I take the square root of 9
      Then the result is 3

  Scenario: Square root of negative number
      When I take the square root of -9
      Then the result is an invalid operation


  Scenario: Factorial
      When I calculate the factorial of 5
      Then the result is 120


  Scenario: Factorial of negative number
      When I calculate the factorial of -5
      Then the result is an invalid operation


  Scenario: Factorial of zero
      When I calculate the factorial of 0
      Then the result is 1
