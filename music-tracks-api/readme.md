# Music Tracks API - RestAssured - Cucumber Testing Framework
- This project is a Cucumber-based API testing framework using RestAssured to automate the verification of Music Tracks API responses. 
- The test suite includes API tests for verifying music tracks, with checks for unique IDs, segment types, titles, "now  playing" status, and response headers.
- The feature files are written in plain English, ensuring clarity and conciseness.
- The request and response details will be automatically embedded in the Cucumber HTML report for each scenario.


## Assumptions:
* The API under test has a base URI of https://testapi.io (this is set in the config.properties file).
* The config.properties file has 'base_path' set to 'api/rmstest'
* The config.properties file has 'media_endpoint' set to 'media'
* Assuming API Get request response time is 2 seconds as API sometimes fails to respond within 1 second.

## Instructions to Run the Tests
1. First, copy the 'music-tracks-api' project zip file  to your local machine and extract to say C:/tests folder on Windows.
2. Open the project in an IDE, say Eclipse
3. Run the feature file in different ways as shown below
	(a) Open TestRunner.java file in Eclipse. Right click then select 'Run As -> JUnit Test'
	(b) Open the command prompt, get into the directory where pom.xml exists. Then run below Maven command 
		mvn clean test

This command will:

* Run all feature files located in src/test/resources/features.
* Execute the corresponding step definitions located in src/test/java/steps.
* Generate logs and embed request and response details in the Cucumber HTML report.
  
4. ### Generate Cucumber Reports
After running the tests, the following reports will be automatically generated:

HTML Report: target/html-report/cucumber-reports.html
JSON Report: target/cucumber.json
JUnit Report: target/cucumber.xml
The HTML report will contain a detailed log of each request and response embedded in the test scenarios.

5. ### Open the Cucumber HTML Report
- After the tests are completed, you can open the Cucumber HTML report in a browser to see detailed logs of requests and responses for each scenario.

- To open the HTML report, navigate to target/html-report/cucumber-reports.html and open it in any browser.
- Expand the feature file to see the test results for each scenario

6. ### Part 2 - Functional Manual Testing
-The feature file is located under src/test/resources/FeatureFileManual