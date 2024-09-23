Feature: Music tracks API data validation

    Background:
        Given 'Jo' wants to browse music tracks

    Scenario: Scenario-1: Music tracks API availablity and performance
        When 'Jo' requests music tracks
        Then she should get a list of music tracks
        And the response should be received within 2 seconds

    Scenario: Scenario-2: Each track has a unique ID and segment type music
        When 'Jo' requests music tracks
        Then each track should have a unique ID
        And each track segment type should be "music"

    Scenario: Scenario-3: Each track has a primary title
        When 'Jo' requests music tracks
        Then each track should have a primary title

    Scenario: Scenario-4: Only one track is currently playing
        When 'Jo' requests music tracks
        Then only one track should have now playing as true

    Scenario: Scenario-5: Response header date is today
        When 'Jo' requests music tracks
        Then the response header date should be today
