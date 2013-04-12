package org.vaadin.addons.javaee.selenium;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TakeScreenshot extends TestWatcher {

    private static Logger log = LoggerFactory.getLogger(TakeScreenshot.class);

    private SeleniumDriverRule seleniumDriverRule;

    public TakeScreenshot() {
    }

    public TakeScreenshot(SeleniumDriverRule seleniumDriverRule) {
        this.seleniumDriverRule = seleniumDriverRule;
    }

    @Override
    public void starting(Description desc) {
    }

    @Override
    public void finished(Description desc) {
        log.info("Quitting driver...");
        seleniumDriverRule.getDriver().quit();
    }

    @Override
    public void failed(Throwable e, Description d) {
        log.info("Creating screenshot...");
        WebDriver driver = seleniumDriverRule.getDriver();
        if (!(driver instanceof TakesScreenshot)) {
            driver = new Augmenter().augment(seleniumDriverRule.getDriver());
        }
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String scrFilename = d.getTestClass().getSimpleName() + "#" + d.getMethodName() + "-failed.png";
        File directory = new File("target/surefire-reports");
        directory.mkdirs();
        File outputFile = new File(directory, scrFilename);
        log.info(scrFilename + " screenshot created.");
        try {
            FileUtils.copyFile(scrFile, outputFile);
        } catch (IOException ioe) {
            log.error("Error copying screenshot after exception.", ioe);
        }
    }
}
