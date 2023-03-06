package com.navigation.googletest.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = "classpath:features",
		glue= {"classpath:"},
		plugin = {"pretty:target/cucumber-reports/cucumber-pretty.txt",
		"html:target/cucumber-reports/raw-cucumber-html-report.html",
		"json:target/report.json"},
		monochrome = true,
		dryRun = true,
		tags = "google-nav"
		)

public class TestRunner extends AbstractTestNGCucumberTests {

}
