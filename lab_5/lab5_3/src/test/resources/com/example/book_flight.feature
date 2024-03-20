Feature: Booking a flight

    Scenario: Booking a flight from Boston to Buenos Aires
        When I navigate to "https://blazedemo.com/"
        And I select "Paris" as the departure city
        And I click Find Flights
        Then the page subsubheader should say "Flights from Boston to Buenos Aires:"
        And the page title should be "BlazeDemo - reserve"

        When I select the flight number 2
        Then the page subheader should say "Your flight from TLV to SFO has been reserved."
        And the page title should be "BlazeDemo Purchase"

        When I write my name as "Rafael"
        And I write my "address" as "Idk"
        And I write my "city" as "idc"
        And I write my "state" as "maybe"
        And I write my "zipCode" as "4700-121"
        And I write my "creditCardNumber" as "499319283"
        And I write my "nameOnCard" as "Rafael from Braga"
        And I click Purchase Flight
        Then the page header should say "Thank you for your purchase today!"