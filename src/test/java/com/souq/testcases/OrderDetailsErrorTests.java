package com.souq.testcases;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import static org.example.Constants.PASSWORD;
import static org.example.Constants.USER_NAME;
import static org.example.DesiredCapabilitiesConstants.*;

public class OrderDetailsErrorTests {
    private static Logger logger = LogManager.getLogger(OrderDetailsErrorTests.class);
    AndroidDriver driver;
    public String baseURI;

    @BeforeMethod
    public void setup() throws MalformedURLException, InterruptedException {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("C:\\Users\\QQA0334\\IdeaProjects\\SouqMobileAppTesting\\TestData\\config.properties"));
            Properties properties = new Properties();
            try {
                properties.load(reader);
                baseURI = properties.getProperty("base_url");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration.properties not found at " + "C:\\Users\\QQA0334\\IdeaProjects\\SouqMobileAppTesting\\TestData\\config.properties");
        }
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE);
        capabilities.setCapability(MobileCapabilityType.APP, APK_PATH);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AUTO_NAME);
        capabilities.setCapability("appPackage", APP_PACKAGE);
        capabilities.setCapability("appActivity", APP_ACTIVITY);
        try {
            driver = new AndroidDriver(new URL(baseURI), capabilities);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(9));
        driver.findElement(By.id("com.marwaeltayeb.souq:id/inputEmail")).sendKeys(USER_NAME);
        driver.findElement(By.id("com.marwaeltayeb.souq:id/inputPassword")).sendKeys(PASSWORD);
        driver.findElement(By.id("com.marwaeltayeb.souq:id/buttonLogin")).click();

    }

    @Test
    public void orderHistoryShowingOneOrderMultipleTimesTest() {
        driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"Open navigation drawer\"]")).click();
        driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc=\"Product Image\"])[1]")).click();
        logger.info("Here In my order history we can see one item showing multiple times");

    }

    @Test
    public void searchBoxNotRespondingTest() throws InterruptedException {
        Thread.sleep(5000);
        driver.findElement(By.id("com.marwaeltayeb.souq:id/txtSearch")).click();
        Thread.sleep(5000);
        driver.findElement(By.id("com.marwaeltayeb.souq:id/editQuery")).sendKeys("tuys");
        driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout")).click();
        logger.info("After searching for any product in search bar it is not fetching the products");
    }
}
