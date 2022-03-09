Feature: Second

  Scenario: Monday isn't Friday
    Given today is "Monday"
    When I ask whether it's Friday yet
    Then I should be told "Nope"

  Scenario: Tuesday isn't Friday
    Given today is "Tuesday"
    When I ask whether it's Friday yet
    Then I should be told "Nope"