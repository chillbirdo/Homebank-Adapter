package com.gf.doughflow.downloader;

import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

public class SeleniumDownloader {

    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new FirefoxDriver(getFirefoxProfile2());
        driver.get("https://ebanking.easybank.at/InternetBanking/InternetBanking?d=login&svc=EASYBANK&ui=html&lang=de");

        WebElement username = driver.findElement(By.id("lof5"));
        WebElement pass = driver.findElement(By.id("lof9"));

        username.sendKeys("xxxxx");
        pass.sendKeys("xxxxx");
        pass.submit();

        WebElement giro = driver.findElement(By.id("PART_GIRO_EUR"));
        WebElement giroAccNr = giro.findElement(By.xpath(".//a"));

        giroAccNr.click();

        WebElement downloadCsv = driver.findElement(By.id("lof1")).findElement(By.id("print")).findElements(By.tagName("a")).get(1);

        downloadCsv.click();

        System.out.println("downloadCsv Name: " + downloadCsv.getTagName());
        System.out.println("downloadCsv Text: " + downloadCsv.getText());

//        System.out.println("Page title is: " + driver.getTitle());
//        System.out.println("Page source: " + driver.getPageSource());
        Thread.sleep(20000);

        driver.quit();
    }

    private static FirefoxProfile getFireFoxProfile() {
        FirefoxProfile fxProfile = new FirefoxProfile();

        fxProfile.setPreference("browser.download.folderList", 2);
        fxProfile.setPreference("browser.download.manager.showWhenStarting", false);
        fxProfile.setPreference("browser.download.dir", "/home/gilbert/");
        fxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
        fxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/octet-stream");
        return fxProfile;
    }

    private static DesiredCapabilities getFirefoxProfile2() {
//        File downloadDir = new File("home/gilbert");
        FirefoxProfile fProfile = new FirefoxProfile();
        fProfile.setAcceptUntrustedCertificates(true);
//        fProfile.setPreference("browser.download.dir", downloadDir.getAbsolutePath());
        fProfile.setPreference("browser.download.folderList", 2);
        fProfile.setPreference("browser.download.manager.showWhenStarting", false);
        fProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
        fProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "*");
        DesiredCapabilities dc = DesiredCapabilities.firefox();
        dc.setJavascriptEnabled(true);
        dc.setCapability(FirefoxDriver.PROFILE, fProfile);
        return dc;
    }
}
