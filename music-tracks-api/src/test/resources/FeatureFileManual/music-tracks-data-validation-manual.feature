Feature: Music tracks API data validation Manual

   Scenario: Scenario-1: Each track has type as music_track
        Given 'Jo' requests music tracks
        When she should get a list of music tracks
        Then each track type should be "music_track"

    Scenario: Scenario-2: Each track shows synopses as null
        Given 'Jo' requests music tracks
        When she should get a list of music tracks
        Then each track synopses should be null

    Scenario: Scenario-3: Each track shows imageUrl as null
        Given 'Jo' requests music tracks
        When she should get a list of music tracks
        Then imageUrl for each track should be null