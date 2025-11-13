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

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class LogoScooterHeaderTest {

    private WebDriver driver;

    private final String browser;    // хром или мозила
    private final String startPage;  // разные страницы приложения

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    public LogoScooterHeaderTest(String browser, String startPage) {
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
        } else {
            throw new IllegalArgumentException("Неизвестный браузер: " + browser);
        }

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        openStartPage(startPage);
    }


    private void openStartPage(String page) {
        driver.get(BASE_URL);

        HeaderBar header = new HeaderBar(driver);
        MainPage mainPage = new MainPage(driver);

        if ("main".equals(page)) {
            return; // уже на главной
        }

        if ("orderStep1".equals(page)) {
            mainPage.clickTopOrderButton(); // на шаг 1
            return;
        }

        if ("orderStep2".equals(page)) {
            // на шаг 2
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
            // полный флоу
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

            // подтверждение
            new OrderPageConfirm(driver).clickYes();

            // окно «Заказ оформлен»
            OrderPageSuccess success = new OrderPageSuccess(driver);
            Assert.assertTrue("Не получили номер заказа", success.hasOrderNumber());
            success.clickStatusButton();

            // на странице статуса заказа
            return;
        }

        throw new IllegalArgumentException("Неизвестная стартовая страница: " + page);
    }

    @Test
    public void scooterLogoNavigatesToMainFromAnyPage() {
        HeaderBar header = new HeaderBar(driver);

        header.clickScooterLogo();

        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.urlToBe(BASE_URL));

        Assert.assertEquals("После клика по логотипу Самоката не открылась главная страница",
                BASE_URL,
                driver.getCurrentUrl());
    }

    @After
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
