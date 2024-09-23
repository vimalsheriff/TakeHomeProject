package io.testapi.rmsmedia.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/", // Path to your feature files
                glue = { "io.testapi.rmsmedia.steps" }, // Package name of step definitions
                plugin = {
                                "pretty", // To print the steps in console
                                "html:target/html-report/cucumber-reports.html", // HTML report output
                                "json:target/cucumber.json", // JSON report output for further integrations
                                "junit:target/cucumber.xml" // JUnit XML report output
                }, monochrome = false // Ensures console output is readable
)
public class TestRunner {
}
