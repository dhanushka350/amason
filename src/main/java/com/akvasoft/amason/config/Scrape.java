package com.akvasoft.amason.config;

import com.akvasoft.amason.common.Content;
import com.akvasoft.amason.common.DateTimeUtil;
import com.akvasoft.amason.common.Item;
import com.akvasoft.amason.repo.ContentRepo;
import com.akvasoft.amason.repo.ItemRepo;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Controller
public class Scrape implements InitializingBean {
    private static FirefoxDriver driver = null;
    private static String url[] = {"https://www.betaland.it/Sport/"};
    private static String codes[] = {"AMASON"};
    private static HashMap<String, String> handlers = new HashMap<>();
    @Autowired
    private ItemRepo itemRepo;
    @Autowired
    private ContentRepo contentRepo;

    public Scrape() throws AWTException {
    }

    public void initialise() throws Exception {
        System.setProperty("webdriver.gecko.driver", "/var/lib/tomcat8/geckodriver");
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(false);

        driver = new FirefoxDriver(options);

        for (int i = 0; i < url.length - 1; i++) {
            driver.executeScript("window.open()");
        }

        ArrayList<String> windowsHandles = new ArrayList<>(driver.getWindowHandles());

        for (int i = 0; i < url.length; i++) {
            handlers.put(codes[i], windowsHandles.get(i));
        }

        // get links from database
        List<String> Links = new ArrayList<>();
        for (Item item : itemRepo.findAll()) {
            Links.add(item.getName());
        }

        List<Content> data = scrape(Links);


    }

    private List<Content> scrape(List<String> links) throws AWTException, InterruptedException {
        List<Content> list = new ArrayList<>();
        Robot robot = new Robot();
        Actions actions = new Actions(driver);
        driver.get("https://viral-launch.com/sellers/signIn.html");

        // login start
        WebElement email = driver.findElementByXPath("//*[@id=\"signInEmail\"]");
        WebElement password = driver.findElementByXPath("//*[@id=\"signInPass\"]");
        WebElement loginButton = driver.findElementByXPath("//*[@id=\"signInButton\"]");

        email.sendKeys("jordan@thelastinterval.com");
        password.sendKeys("Millionaire$");
        loginButton.click();
        // login end

        Thread.sleep(5000); // wait until load the page

        //moving to page directly start
        robot.mouseMove(10, 300);
        robot.mouseMove(100, 300);
        driver.get("https://viral-launch.com/sellers/launch-staging/pages/market-intelligence.html");
        //moving to page directly end
        int Count = 1;
        for (String link : links) {
            if (Count == 1) {
                list.add(scrapeDetails(link, true));
            } else {
                list.add(scrapeDetails(link, false));
            }
            Count++;
            System.out.println("CURRENT LINK =" + link + " COUNT = " + Count);
        }


        return null;
    }

    private Content scrapeDetails(String link, boolean isFirstLink) throws InterruptedException {
        Content content = new Content();
        // searching start
        if (isFirstLink) {
            WebElement searchBox = driver.findElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div[2]/div/div/div/div[2]/form/div[1]/div/div/input");
            searchBox.sendKeys(link);
            WebElement searchButton = driver.findElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div[2]/div/div/div/div[2]/form/div[3]/div/button");
            searchButton.click();

        } else {
            WebElement searchBox2 = driver.findElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div[1]/div/div[1]/div/div/div/div[1]/input");
            searchBox2.sendKeys(link);
            WebElement searchButton2 = driver.findElementByXPath("//*[@id=\"header_search_button\"]");
            searchButton2.click();
        }
        // searching end

        // collecting start
        Thread.sleep(10000);
        //market trends
        WebElement marketTrends = driver.findElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div[2]/div/div/div[1]/div[2]/div[1]/div/div[1]/div/div[2]");
        marketTrends.click();

        String avaragePrice = driver.findElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div[2]/div/div/div[1]/div[2]/div[2]/div[2]/div[2]/div[1]/div[1]/p").getAttribute("innerText");
        String sellingPeriod = driver.findElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div[2]/div/div/div[1]/div[2]/div[2]/div[2]/div[2]/div[1]/div[2]/p").getAttribute("innerText");
        String reviewIncease = driver.findElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div[2]/div/div/div[1]/div[2]/div[2]/div[2]/div[2]/div[1]/div[3]/p").getAttribute("innerText");
        String salesTrend = driver.findElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div[2]/div/div/div[1]/div[2]/div[2]/div[2]/div[2]/div[1]/div[4]/p").getAttribute("innerText");

        String ideaScore = driver.findElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div[2]/div/div/div[1]/div[2]/div[2]/div[3]/div[2]/div[1]/div[1]/p").getAttribute("innerText");
        String possibleMonthlySale = driver.findElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div[2]/div/div/div[1]/div[2]/div[2]/div[3]/div[2]/div[1]/div[2]/p").getAttribute("innerText");
        String sellwell = driver.findElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div[2]/div/div/div[1]/div[2]/div[2]/div[3]/div[2]/div[1]/div[3]/p").getAttribute("innerText");
        String pattern = driver.findElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div[2]/div/div/div[1]/div[2]/div[2]/div[3]/div[2]/div[1]/div[4]/p").getAttribute("innerText");
        String warining = driver.findElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div[2]/div/div/div[1]/div[2]/div[2]/div[3]/div[2]/div[2]/div/div[2]").getAttribute("innerText");

        System.out.println(avaragePrice + " - " + sellingPeriod + " - " + reviewIncease + " - " + salesTrend);
        System.out.println(ideaScore + " - " + possibleMonthlySale + " - " + sellwell + " - " + pattern + " - " + warining);

        WebElement tbody = driver.findElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div[2]/div/div/div[1]/div[2]/div[2]/div[1]/div/div[1]/div[1]/div[2]/div[1]/div[2]/div[3]/table/tbody");
        String revenue = tbody.findElements(By.xpath("./*")).get(0).findElements(By.xpath("./*")).get(7).getAttribute("innerText");

        //        for (WebElement row : tbody.findElements(By.xpath("./*"))) {
//            String revenue = row.findElements(By.xpath("./*")).get(7).getAttribute("innerText");
//            System.out.println(revenue);
//        }

        content.setAvarage_price(avaragePrice);
        content.setBest_selling_period(sellingPeriod);
        content.setIdea_source(ideaScore);
        content.setMonthly_revenue(revenue);
        content.setMonthly_sales(possibleMonthlySale);
        content.setPattern(pattern);
        content.setProduct_title(link);
        content.setReview_increase(reviewIncease);
        content.setSales_trend(salesTrend);
        content.setSell_well(sellwell);
        content.setWarning(warining.replace("\n", ", "));
        contentRepo.save(content);
        return content;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        this.initialise();
    }
}
