Feature: register_buy_analytics

  Background:
    Given a zone "South-Station" with skilift "pioupiou", a gate with id "51" and a customer registered with "pedroo@me.com" "Joee" "896983"


  Scenario: A customer buys, and an employee checks analytics
    When he adds "1" card
    And he checkouts
    And he adds a pass for today to this card "3" "classic" "full_day" "1" "adult" "South-Station"
    And he checkouts
    Then an employee fetches pass analytics of the day and get "3" more

  Scenario: an employee checks analytics and customer checkouts
    When he adds "1" card
    And he checkouts
    And he adds a pass for today to this card "5" "classic" "full_day" "1" "adult" "South-Station"
    Then an employee fetches pass analytics of the day and get "0" more
    And he checkouts

  Scenario: A customer A customer buys, uses a ski lift, and an employee checks analytics
    When he adds "1" card
    And he checkouts
    And he adds a pass for today to this card "1" "classic" "full_day" "1" "adult" "South-Station"
    And he checkouts
    And he uses ski lift at gate "51" with his card
    Then an employee fetches pass analytics of the day and get "1" more
    And an employee fetches visit analytics of the day and get "1" more