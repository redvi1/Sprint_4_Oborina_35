package tests;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobject.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class LogoYandexHeaderTest {

    private WebDriver driver;

    private final String browser;
    private final String startPage;

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    public LogoYandexHeaderTest(String browser, String startPage) {
        this.browser = browser;
        this.startPage = startPage;
    }

    @Parameterized.Parameters(name = "{0}, страница: {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"chrome", "main"},
                {"firefox", "main"},
                {"chrome", "orderStep1"},
                {"firefox", "orderStep1"},
                {"chrome", "orderStep2"},
                {"firefox", "orderStep2"},
                {"chrome", "status"},
                {"firefox", "status"},
        });
    }

    @Before
    public void setUp() {
        if ("chrome".equals(browser)) {
            driver = new ChromeDriver();
        } else if ("firefox".equals(browser)) {
            driver = new FirefoxDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        openStartPage(startPage);
    }

    private void openStartPage(String page) {
        driver.get(BASE_URL);

        MainPage mainPage = new MainPage(driver);

        if ("main".equals(page)) {
            return;
        }

        if ("orderStep1".equals(page)) {
            mainPage.clickTopOrderButton();
            return;
        }

        if ("orderStep2".equals(page)) {
            mainPage.clickTopOrderButton();
            OrderPage1 step1 = new OrderPage1(driver);
            step1.setFirstName("Петр");
            step1.setLastName("Петров");
            step1.setAddress("Площадь");
            step1.selectFirstMetro();
            step1.setPhone("89999999999");
            step1.clickNextButton();
            return;
        }

        if ("status".equals(page)) {
            mainPage.clickTopOrderButton();

            // шаг 1
            OrderPage1 step1 = new OrderPage1(driver);
            step1.setFirstName("Инна");
            step1.setLastName("Ивановна");
            step1.setAddress("Неплощадь");
            step1.selectFirstMetro();
            step1.setPhone("89876543221");
            step1.clickNextButton();

            // шаг 2
            OrderPage2 step2 = new OrderPage2(driver);
            step2.selectDate12();
            step2.selectTwoDaysRental();
            step2.selectBlackColor();
            step2.setComment("чёрный жемчуг");
            step2.clickOrder();

            // родтверждение
            new OrderPageConfirm(driver).clickYes();

            // страница «Заказ оформлен»
            OrderPageSuccess success = new OrderPageSuccess(driver);
            if (success.hasOrderNumber()) {
                success.clickStatusButton();
            }
            return;
        }
    }

    @Test
    public void yandexLogoOpensMainPageInNewTab() {
        HeaderBar header = new HeaderBar(driver);

        // запоминаем текущее окно
        String originalWindow = driver.getWindowHandle();
        Set<String> oldWindows = driver.getWindowHandles();

        // клик по логотипу Яндекса
        header.clickYandexLogo();

        // ждём появления новой вкладки
        new WebDriverWait(driver, 10).until(d -> d.getWindowHandles().size() > oldWindows.size());

        // переключаемся на новую вкладку
        Set<String> newWindows = driver.getWindowHandles();
        newWindows.removeAll(oldWindows);
        String newWindow = newWindows.iterator().next();
        driver.switchTo().window(newWindow);

        // ждём пока откроется хоть одна из трёх страниц
        new WebDriverWait(driver, 15).until(ExpectedConditions.or(
                ExpectedConditions.urlContains("ya.ru"),
                ExpectedConditions.urlContains("yandex.ru"),
                ExpectedConditions.urlContains("dzen.ru")
        ));

        String currentUrl = driver.getCurrentUrl().toLowerCase();
        System.out.println("Открылся URL: " + currentUrl);

        if (currentUrl.contains("ya.ru")) {
            System.out.println("Открылась главная Яндекс (ya.ru)");
        } else if (currentUrl.contains("yandex.ru")) {
            System.out.println("Открылась главная Яндекс (yandex.ru)");
        } else if (currentUrl.contains("dzen.ru")) {
            System.out.println("Открылась страница Dzen (перенаправление Яндекса)");
        } else {
            Assert.fail("Не открылся ни один из ожидаемых адресов: " + currentUrl);
        }

        // закрываем вкладку и возвращаемся
        driver.close();
        driver.switchTo().window(originalWindow);
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
