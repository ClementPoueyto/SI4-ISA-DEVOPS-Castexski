Feature: register_buy_passage

  Background:
    Given a zone "whole_station" with skilift "marmottes" and a gate

  Scenario: A person registers, buys a pass and validates it
    When a customer registers with "toto@me.com" "Toto" "896983"
    And he adds "1" card
    Then he checkouts and get his card
    And he adds a pass for today to this card "1" "classic" "full_day" "1" "adult" "whole_station"
    Then he checkouts