package com.ssttech.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources",
        glue = "com/ssttech/stepDefinitions",
        //dryRun = true,
        plugin = {
                "pretty",
                "html:target/cucumber-html-report",
                "json:target/cucumber.json",
                "junit:target/cucumber-junit-report.xml",
                "net.masterthought.cucumber.ReportGenerator:target/cucumber-html-report",
                "rerun:target/rerun.txt"
        },

        monochrome = true,


        tags = "@regression"           // This must be assigned with Test Execution ticket number from JIRA in order to run all scenarios
        // and get their "cucumber.json" report to export to the JIRA at the end.

)

public class CukesRunner {
}
