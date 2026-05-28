package utils;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {

        if (extent == null) {

            ExtentSparkReporter sparkReporter =
                    new ExtentSparkReporter(
                            "test-output/extent-report.html"
                    );

            sparkReporter.config()
                    .setReportName(
                            "ParaBank Automation Report"
                    );

            sparkReporter.config()
                    .setDocumentTitle(
                            "Automation Test Results"
                    );

            extent = new ExtentReports();

            extent.attachReporter(sparkReporter);

            extent.setSystemInfo(
                    "Tester",
                    "Sathya"
            );

            extent.setSystemInfo(
                    "Environment",
                    "QA"
            );
        }

        return extent;
    }
}
