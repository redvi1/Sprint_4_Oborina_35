package tests;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.concurrent.TimeUnit;

import pageobject.*;

import io.github.bonigarcia.wdm.WebDriverManager;

@RunWith(Parameterized.class)
public class LogoYandexHeaderTest {

    private WebDriver driver;

    private final String startPage;

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    public LogoYandexHeaderTest(String startPage) {
        this.startPage = startPage;
    }

    @Parameterized.Parameters(name = "Страница: {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"main"},
                {"orderStep1"},
                {"orderStep2"},
                {"status"},
        });
    }

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

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
            OrderClientInfoPage step1 = new OrderClientInfoPage(driver);
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
            OrderClientInfoPage step1 = new OrderClientInfoPage(driver);
            step1.setFirstName("Инна");
            step1.setLastName("Ивановна");
            step1.setAddress("Неплощадь");
            step1.selectFirstMetro();
            step1.setPhone("89876543221");
            step1.clickNextButton();

            // шаг 2
            OrderRentDetailsPage step2 = new OrderRentDetailsPage(driver);
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


        // закрываем вкладку и возвращаемся
        driver.close();
        driver.switchTo().window(originalWindow);
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
