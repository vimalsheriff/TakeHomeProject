package io.testapi.rmsmedia.filters;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.cucumber.java.Scenario;

public class APILogFilter implements Filter {

    private final Scenario scenario;

    public APILogFilter(Scenario scenario) {
        this.scenario = scenario;
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
            FilterContext ctx) {
        // Log the request details
        String requestLog = "Request Method: " + requestSpec.getMethod() + "\n" +
                "Request URI: " + requestSpec.getURI() + "\n" +
                "Request Headers: " + requestSpec.getHeaders() + "\n" +
                "Request Body: " + (requestSpec.getBody() != null ? requestSpec.getBody().toString() : "No body")
                + "\n";
        scenario.log("Request:\n" + requestLog);

        // Execute the request and get the response
        Response response = ctx.next(requestSpec, responseSpec);

        // Log the response details
        String responseLog = "Response Status: " + response.getStatusCode() + "\n" +
                "Response Headers: " + response.getHeaders() + "\n" +
                "Response Body: " + response.getBody().asPrettyString();
        scenario.log("Response:\n" + responseLog);
        return response;
    }
}
