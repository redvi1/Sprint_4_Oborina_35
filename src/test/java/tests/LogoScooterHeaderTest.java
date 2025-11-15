package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobject.HeaderBar;
import pageobject.MainPage;
import pageobject.OrderClientInfoPage;
import pageobject.OrderPageConfirm;
import pageobject.OrderPageSuccess;
import pageobject.OrderRentDetailsPage;

import java.util.concurrent.TimeUnit;

public class LogoScooterHeaderTest {

    private WebDriver driver;

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    // Главная
    private void openMainPage() {
        driver.get(BASE_URL);
    }

    // Шаг 1 оформления заказа
    private void openOrderStep1() {
        openMainPage();
        MainPage mainPage = new MainPage(driver);
        mainPage.clickTopOrderButton();
    }

    // Шаг 2 оформления заказа
    private void openOrderStep2() {
        openMainPage();
        MainPage mainPage = new MainPage(driver);
        mainPage.clickTopOrderButton();

        OrderClientInfoPage step1 = new OrderClientInfoPage(driver);
        step1.setFirstName("Петр");
        step1.setLastName("Петров");
        step1.setAddress("Площадь");
        step1.selectFirstMetro();
        step1.setPhone("89999999999");
        step1.clickNextButton();
    }

    // Страница статуса заказа
    private void openStatusPage() {
        openMainPage();
        MainPage mainPage = new MainPage(driver);
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

        // подтверждение
        new OrderPageConfirm(driver).clickYes();

        // окно «Заказ оформлен» → перейти к статусу
        OrderPageSuccess success = new OrderPageSuccess(driver);
        Assert.assertTrue("Не получили номер заказа", success.hasOrderNumber());
        success.clickStatusButton(); // оказываемся на странице статуса
    }

    @Test
    public void scooterLogoFromMainOpensMain() {
        openMainPage();

        HeaderBar header = new HeaderBar(driver);
        header.clickScooterLogo();

        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.urlToBe(BASE_URL));

        Assert.assertEquals("После клика по логотипу Самоката не открылась главная страница",
                BASE_URL, driver.getCurrentUrl());
    }

    @Test
    public void scooterLogoFromOrderStep1OpensMain() {
        openOrderStep1();

        HeaderBar header = new HeaderBar(driver);
        header.clickScooterLogo();

        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.urlToBe(BASE_URL));

        Assert.assertEquals("После клика по логотипу Самоката не открылась главная страница",
                BASE_URL, driver.getCurrentUrl());
    }

    @Test
    public void scooterLogoFromOrderStep2OpensMain() {
        openOrderStep2();

        HeaderBar header = new HeaderBar(driver);
        header.clickScooterLogo();

        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.urlToBe(BASE_URL));

        Assert.assertEquals("После клика по логотипу Самоката не открылась главная страница",
                BASE_URL, driver.getCurrentUrl());
    }

    @Test
    public void scooterLogoFromStatusPageOpensMain() {
        openStatusPage();

        HeaderBar header = new HeaderBar(driver);
        header.clickScooterLogo();

        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.urlToBe(BASE_URL));

        Assert.assertEquals("После клика по логотипу Самоката не открылась главная страница",
                BASE_URL, driver.getCurrentUrl());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
