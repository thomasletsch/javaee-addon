package org.vaadin.addons.javaee.selenium;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TakeScreenshot extends TestWatcher {

    private static Logger log = LoggerFactory.getLogger(TakeScreenshot.class);

    private WebDriver driver;

    public TakeScreenshot() {
    }

    public TakeScreenshot(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void starting(Description desc) {
        log.info("Launching browser...");
    }

    @Override
    public void finished(Description desc) {
        log.info("Quitting driver...");
        if (driver != null) {
            driver.quit();
        }
    }

    @Override
    public void failed(Throwable e, Description d) {
        log.debug("Creating screenshot...");
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String scrFilename = d.getTestClass().getSimpleName() + "#" + d.getMethodName() + "-failed.png";
        File outputFile = new File("target/surefire", scrFilename);
        log.info(scrFilename + " screenshot created.");
        try {
            FileUtils.copyFile(scrFile, outputFile);
        } catch (IOException ioe) {
            log.error("Error copying screenshot after exception.", ioe);
        }
        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }
}
