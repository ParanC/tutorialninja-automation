package org.example.utilities;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class Listeners implements ITestListener {
    ExtentReports extent;
    ExtentTest test;

    @Override
    public void onStart(ITestContext context) {
        ExtentSparkReporter reporter = new ExtentSparkReporter("reports/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
    }

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.fail(result.getThrowable());
        try {
            TakesScreenshot ts = (TakesScreenshot) result.getTestClass().getRealClass()
                    .getDeclaredField("driver").get(result.getInstance());
            File src = ts.getScreenshotAs(OutputType.FILE);
            File dest = new File("reports/screenshots/" + result.getName() + ".png");
            Files.createDirectories(dest.getParentFile().toPath());
            Files.copy(src.toPath(), dest.toPath());
        } catch (IOException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
