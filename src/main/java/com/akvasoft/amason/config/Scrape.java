package com.akvasoft.amason.config;

import com.akvasoft.amason.common.Content;
import com.akvasoft.amason.common.ExcelReader;
import com.akvasoft.amason.common.Item;
import com.akvasoft.amason.repo.ContentRepo;
import com.akvasoft.amason.repo.ItemRepo;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.awt.*;
import java.util.ArrayList;
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

    public void initialise(List<Item> read) throws Exception {
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
        for (Item item : read) {
            boolean isExist = contentRepo.existsByProductTitleEquals(item.getName());
            System.err.println(isExist);
            if (isExist) {
                System.err.println("SKIPPING SCRAPED ITEM....");
                continue;
            } else {
                Links.add(item.getName());
            }
        }

        List<Content> data = scrape(Links);


    }

    private List<Content> scrape(List<String> links) throws AWTException, InterruptedException {
        List<Content> list = new ArrayList<>();
//        Robot robot = new Robot();
//        Actions actions = new Actions(driver);
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
        //moving to page directly end
        Content content = new Content();
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        boolean isscraped = false;
        int tried = 1;
        driver.get("https://viral-launch.com/sellers/launch-staging/pages/market-intelligence.html");
        // searching start
        while (!isscraped) {
            tried++;
            System.out.println("==================================" + tried);
            if (tried > 5) {
                return null;
            }
            try {
                Thread.sleep(2000);
                WebElement searchBox = driver.findElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div[2]/div/div/div/div[2]/form/div[1]/div/div/input");
                searchBox.clear();
                searchBox.sendKeys(link);
                WebElement searchButton = driver.findElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div[2]/div/div/div/div[2]/form/div[3]/div/button");
                searchButton.click();

                // searching end

                // collecting start
                Thread.sleep(60000);
                //market trends

                try {
                    WebElement marketTrends = driver.findElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div[2]/div/div/div[1]/div[2]/div[1]/div/div[1]/div/div[2]");
                    try {
                        marketTrends.click();
                    } catch (ElementClickInterceptedException r) {

                    }
                } catch (NoSuchElementException e) {
                    try {
                        WebElement alert = driver.findElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div[2]/div/div/div/div[6]/div/div[2]/button");
                        jse.executeScript("arguments[0].click();", alert);
                        content.setAvarage_price("");
                        content.setBest_selling_period("");
                        content.setIdea_source("");
                        content.setMonthly_revenue("");
                        content.setMonthly_sales("");
                        content.setPattern("");
                        content.setProductTitle(link);
                        content.setReview_increase("");
                        content.setSales_trend("");
                        content.setSell_well("");
                        content.setWarning("");
                        contentRepo.save(content);
                        return null;
                    } catch (Exception a) {
                        a.printStackTrace();
                    }
                }


                try {
                    String avaragePrice = driver.findElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div[2]/div/div/div[1]/div[2]/div[2]/div[2]/div[2]/div[1]/div[1]/p").getAttribute("innerText");
                    String sellingPeriod = driver.findElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div[2]/div/div/div[1]/div[2]/div[2]/div[2]/div[2]/div[1]/div[2]/p").getAttribute("innerText");
                    String reviewIncease = driver.findElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div[2]/div/div/div[1]/div[2]/div[2]/div[2]/div[2]/div[1]/div[3]/p").getAttribute("innerText");
                    String salesTrend = driver.findElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div[2]/div/div/div[1]/div[2]/div[2]/div[2]/div[2]/div[1]/div[4]/p").getAttribute("innerText");

                    String ideaScore = driver.findElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div[2]/div/div/div[1]/div[2]/div[2]/div[3]/div[2]/div[1]/div[1]/p").getAttribute("innerText");
                    String possibleMonthlySale = driver.findElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div[2]/div/div/div[1]/div[2]/div[2]/div[3]/div[2]/div[1]/div[2]/p").getAttribute("innerText");
                    String sellwell = driver.findElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div[2]/div/div/div[1]/div[2]/div[2]/div[3]/div[2]/div[1]/div[3]/p").getAttribute("innerText");
                    String pattern = driver.findElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div[2]/div/div/div[1]/div[2]/div[2]/div[3]/div[2]/div[1]/div[4]/p").getAttribute("innerText");
                    String warining = "There are no tips, warnings, or alerts for this market.";
                    try {
                        warining = driver.findElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div[2]/div/div/div[1]/div[2]/div[2]/div[3]/div[2]/div[2]/div/div[2]").getAttribute("innerText");
                    } catch (NoSuchElementException r) {
                        System.out.println("no warnings");
                        warining = "There are no tips, warnings, or alerts for this market.";
                    }
                    WebElement tbody = driver.findElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div[2]/div/div/div[1]/div[2]/div[2]/div[1]/div/div[1]/div[1]/div[2]/div[1]/div[2]/div[3]/table/tbody");
                    String revenue = tbody.findElements(By.xpath("./*")).get(0).findElements(By.xpath("./*")).get(7).getAttribute("innerText");

                    content.setAvarage_price(avaragePrice);
                    content.setBest_selling_period(sellingPeriod);
                    content.setIdea_source(ideaScore);
                    content.setMonthly_revenue(revenue);
                    content.setMonthly_sales(possibleMonthlySale);
                    content.setPattern(pattern);
                    content.setProductTitle(link);
                    content.setReview_increase(reviewIncease);
                    content.setSales_trend(salesTrend);
                    content.setSell_well(sellwell);
                    content.setWarning(warining.replace("\n", ", "));
                    contentRepo.save(content);
                    isscraped = true;
                } catch (NoSuchElementException c) {
                    try {
                        WebElement alert = driver.findElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div[2]/div/div/div[3]/div/div[3]/span/button[2]");
                        jse.executeScript("arguments[0].click();", alert);
                        c.printStackTrace();
                        System.err.println("error .............. can not locate element === " + c.getMessage());
                        continue;
                    } catch (Exception f) {
                        System.err.println("error .............. can not locate alert === " + c.getMessage());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                driver.get("https://viral-launch.com/sellers/launch-staging/pages/market-intelligence.html");
                System.err.println("retrying");
            }
            tried++;
        }
        return content;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        ExcelReader reader = new ExcelReader();
        List<Item> read = reader.read();
        this.initialise(read);
    }
}
