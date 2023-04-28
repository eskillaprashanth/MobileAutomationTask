package com.souq.testcases;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import static org.example.DesiredCapabilitiesConstants.*;
import static org.example.Constants.PASSWORD;
import static org.example.Constants.USER_NAME;
public class DeliveryAddressAndCardDetailsErrorTests {
    private static Logger logger= LogManager.getLogger(DeliveryAddressAndCardDetailsErrorTests.class);
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
                baseURI=properties.getProperty("base_url");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration.properties not found at " +"C:\\Users\\QQA0334\\IdeaProjects\\SouqMobileAppTesting\\TestData\\config.properties");
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
    @Test(priority=1)
    public void whileAddingAddressUserNameNotEditableTest() throws InterruptedException {
        driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc=\"Product Image\"])[1]")).click();
        driver.findElement(By.id("com.marwaeltayeb.souq:id/buy")).click();
        driver.findElement(By.id("com.marwaeltayeb.souq:id/txtName")).click();
        logger.info("After clicking on user name text box it is not able to editable");
    }
    @Test(priority=2)
    public void acceptingInvalidCountryNameTest() throws InterruptedException {
        driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc=\"Product Image\"])[1]")).click();
        driver.findElement(By.id("com.marwaeltayeb.souq:id/buy")).click();
        driver.findElement(By.id("com.marwaeltayeb.souq:id/country")).sendKeys("Ind22");
        WebElement response = driver.findElement(By.id("com.marwaeltayeb.souq:id/country"));
        Assert.assertEquals("Ind22", response.getText());
        logger.info("After entering the invalid country name it is accepting");

    }
    @Test(priority=3)
    public void noLimitForPhoneNumbersTest() throws InterruptedException {
        driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc=\"Product Image\"])[1]")).click();
        driver.findElement(By.id("com.marwaeltayeb.souq:id/buy")).click();
        driver.findElement(By.id("com.marwaeltayeb.souq:id/phone")).sendKeys("99999888887777766666");
        WebElement response = driver.findElement(By.id("com.marwaeltayeb.souq:id/phone"));
        Assert.assertEquals("99999888887777766666", response.getText());
        logger.info("While adding the delivery address it is taking an invalid phone number and more than 15 number");
    }
    @Test(priority=4)
    public void zipCodeAcceptingAsStringTest() throws InterruptedException {
        Thread.sleep(5000);
        driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc=\"Product Image\"])[1]")).click();
        driver.findElement(By.id("com.marwaeltayeb.souq:id/buy")).click();
        driver.findElement(By.id("com.marwaeltayeb.souq:id/zip")).sendKeys("ttthhhaaa");
        WebElement response = driver.findElement(By.id("com.marwaeltayeb.souq:id/zip"));
        Assert.assertEquals("ttthhhaaa", response.getText());
        logger.info("If we enter the ZIP code as string it is accepting");
    }
    @Test(priority=5)
    public void acceptingExpiryDateTest() throws InterruptedException {
        Thread.sleep(5000);
        driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc=\"Product Image\"])[1]")).click();
        driver.findElement(By.id("com.marwaeltayeb.souq:id/buy")).click();
        driver.findElement(By.id("com.marwaeltayeb.souq:id/address")).sendKeys("hyderabad,koti");
        driver.findElement(By.id("com.marwaeltayeb.souq:id/city")).sendKeys("hyderabad");
        Thread.sleep(5000);
        driver.findElement(By.id("com.marwaeltayeb.souq:id/country")).sendKeys("India");
        driver.findElement(By.id("com.marwaeltayeb.souq:id/zip")).sendKeys("55555");
        driver.findElement(By.id("com.marwaeltayeb.souq:id/phone")).sendKeys("36757242653");
        driver.findElement(By.id("com.marwaeltayeb.souq:id/proceed")).click();
        driver.findElement(By.id("com.marwaeltayeb.souq:id/nameOnCard")).sendKeys("prashanth");
        driver.findElement(By.id("com.marwaeltayeb.souq:id/cardNumber")).sendKeys("99785764653");
        driver.findElement(By.id("com.marwaeltayeb.souq:id/spinnerYearMenu")).click();
        WebElement response = driver.findElement(By.id("com.marwaeltayeb.souq:id/spinnerYearMenu"));
        Assert.assertEquals("2020", response.getText());
        driver.findElement(By.id("com.marwaeltayeb.souq:id/addCard")).click();
        logger.info("If we entered card expiry dropdown, it is taking already expiry date");
    }


}

