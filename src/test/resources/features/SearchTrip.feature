Feature: Bring Global Assessment

  Scenario Outline: Search for a trip
    Given user visits the Ryanair website
    When user search for a trip from "Portugal" to "France" using the following criteria
      | <origin>      |
      | <destination> |
      | <depart>      |
      | <return>      |
      | <adults>      |
      | <childs>      |
    And user change the departure date to "Dec 12" and return date to "Dec 6"
    And user selects value fare
    And user adds the following passengers names
      | Ms/Sónia Pereira     |
      | Mr/Diogo Bettencourt |
      | Ms/Inês Marçal       |
    Then seat selection view should be displayed
    Examples:
      | origin | destination    | depart | return | adults | childs |
      | Lisbon | Paris Beauvais | Dec 6  | Jan 2  | 2      | 1      |


    # And select the small package option
    # And select the seats
    # And click the continue to payment option
    # Then the test completes

