Feature: Bumble Registration

  @android
  Scenario: first
    Given I am at home screen of Bumble
    When I try to register by phone
    Then I should be able to enter phone number

  Scenario: second
    Given I am at home screen of Bumble
    When I try to register by phone number "0000"
    Then I should see phone number as "1234"

  Scenario: third
    Given I am at home screen of Bumble
    When I try to register by phone
    Then I should be able to enter phone number

  Scenario: fourth
    Given I am at home screen of Bumble
    When I try to register by phone number "0000"
    Then I should see phone number as "0000"