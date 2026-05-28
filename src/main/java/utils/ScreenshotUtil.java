package utils;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class ScreenshotUtil {

    public static String captureScreenshot(
            WebDriver driver,
            String testName) {

        File src =
                ((TakesScreenshot) driver)
                        .getScreenshotAs(
                                OutputType.FILE
                        );

        String path =
                "reports/screenshots/"
                        + testName
                        + ".png";

        try {

            FileUtils.copyFile(
                    src,
                    new File(path)
            );

        } catch (IOException e) {

            e.printStackTrace();
        }

        return path;
    }
}
