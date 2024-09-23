package io.testapi.rmsmedia.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.testapi.rmsmedia.filters.APILogFilter;
import io.testapi.rmsmedia.utils.ConfigReader;
import io.testapi.rmsmedia.utils.ReusableSpec;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;

public class MusicTracksSteps {

    private static String BASE_URI;
    private ValidatableResponse response;
    private Scenario scenario;
    private static String BASE_PATH;
    private static String MEDIA_ENDPOINT;
    private RequestSpecification requestSpec;
    private String actor;

    @BeforeAll
    public static void before_all() {

        // Load config.properties from src/test/resources folder
        BASE_URI = ConfigReader.getInstance().getPropertyValue("base_uri",
                "base_uri property is not set or blank in config.properties");

        BASE_PATH = ConfigReader.getInstance().getPropertyValue("base_path",
                "base_path property is not set or blank in config.properties");

        MEDIA_ENDPOINT = ConfigReader.getInstance().getPropertyValue("media_endpoint",
                "media_endpoint property is not set or blank in config.properties");

    }

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
        requestSpec = ReusableSpec.getRequestSpec().baseUri(BASE_URI);
    }

    @After
    public void after() {
        if (scenario != null) {
            scenario.log("Tested the scenario: " + scenario.getName() + ", it's status is: " + scenario.getStatus());
        }
    }

    @Given("{string} wants to browse music tracks")
    public void wants_to_browse_music_tracks(String actor) {

        this.actor = actor;
        scenario.log("Testing the scenario: " + scenario.getName());
        requestSpec.basePath(BASE_PATH);
    }

    @When("{string} requests music tracks")
    public void requests_music_tracks(String actor) {

        /*
         * Perform the request and log request/response details in the cucumber html
         * report
         */

        response = given().spec(requestSpec)
                .filter(new APILogFilter(scenario)) // to log request and response data in the html report
                .get(MEDIA_ENDPOINT).then();
    }

    @Then("he/she should get a list of music tracks")
    public void should_receive_the_list_of_music_tracks() {
        response.assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .spec(ReusableSpec.getResponseSpec())
                .body("data*.id", hasSize(greaterThan(10))); // verify that response has atleast 10 tracks
        scenario.log(actor + " successfully requested music tracks");
    }

    @And("the response should be received within {long} seconds")
    public void the_response_is_received_within_seconds(Long value) {
        response.assertThat().time(lessThan(value), TimeUnit.SECONDS);
        scenario.log(String.valueOf("Response time in milliseconds is: " + "\n" + response.extract().time()));
    }

    @Then("each track should have a unique ID")
    public void each_track_should_have_a_unique_id() {

        response.assertThat().body("data*.id", everyItem(not(emptyOrNullString())));
        scenario.log("Unique IDs are: " + "\n" + response.extract().path("data*.id").toString());
    }

    @Then("each track should have a primary title")
    public void each_track_should_have_a_primary_title() {

        response.assertThat().body("data*.title_list*.primary", everyItem(not(emptyOrNullString())));
        scenario.log("Primary titles are: " + "\n" + response.extract().path("data*.title_list*.primary").toString());
    }

    @Then("each track segment type should be {string}")
    public void each_track_segment_type_should_be(String segmentType) {

        response.assertThat().body("data*.segment_type",
                everyItem(equalTo(segmentType)));
        scenario.log("Segment Types are: " + "\n" + response.extract().path("data*.segment_type").toString());
    }

    @Then("only one track should have now playing as true")
    public void only_one_track_should_have_now_playing_as_true() {

        response.assertThat().body("data*.offset.findAll { it.now_playing == true }.size()", equalTo(1));
        scenario.log("Now playing track is: " + "\n"
                + response.extract().path("data*.offset.findAll { it.now_playing == true }").toString());

    }

    @Then("the response header date should be today")
    public void the_response_header_date_should_be_today() {

        // Validate response header 'Date' as per GMT time zone
        DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;
        response.assertThat().header("Date", equalTo(ZonedDateTime.now(ZoneId.of("GMT")).format(formatter)));
        scenario.log("Verified that actual response header Date is: " + response.extract().header("Date"));
    }

}
