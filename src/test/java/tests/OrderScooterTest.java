package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import pageobject.MainPage;
import pageobject.OrderClientInfoPage;
import pageobject.OrderRentDetailsPage;
import pageobject.OrderPageConfirm;
import pageobject.OrderPageSuccess;

public class OrderScooterTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    // набор 1: верхняя кнопка, 11 число, сутки, чёрный цвет
    @Test
    public void order_viaTopButton_set1() {
        MainPage main = new MainPage(driver);
        main.clickTopOrderButton();

        // шаг 1
        OrderClientInfoPage step1 = new OrderClientInfoPage(driver);
        step1.setFirstName("Пётр");
        step1.setLastName("Петров");
        step1.setAddress("Площадь");
        step1.selectFirstMetro();
        step1.setPhone("89999999999");
        step1.clickNextButton();

        // шаг 2
        OrderRentDetailsPage step2 = new OrderRentDetailsPage(driver);
        step2.selectDate11();
        step2.selectOneDaysRental();
        step2.selectBlackColor();
        step2.setComment("ага");
        step2.clickOrder();

        // подтверждение
        new OrderPageConfirm(driver).clickYes();

        // окно с номером
        OrderPageSuccess success = new OrderPageSuccess(driver);
        Assert.assertTrue("Нет текста с номером заказа", success.hasOrderNumber());
    }

    // набор 2: нижняя кнопка, 12 число, двое суток, серый цвет
    @Test
    public void order_viaBottomButton_set2() {
        MainPage main = new MainPage(driver);
        main.scrollToBottomOrderButton();
        main.clickBottomOrderButton();


        // шаг 1
        OrderClientInfoPage step1 = new OrderClientInfoPage(driver);
        step1.setFirstName("Инна");
        step1.setLastName("Ивановна");
        step1.setAddress("Неплощадь");
        step1.selectSecondMetro();
        step1.setPhone("89876543221");
        step1.clickNextButton();

        // шаг 2
        OrderRentDetailsPage step2 = new OrderRentDetailsPage(driver);
        step2.selectDate12();
        step2.selectTwoDaysRental();
        step2.selectGreyColor();
        step2.setComment("неа");
        step2.clickOrder();

        // подтверждение
        new OrderPageConfirm(driver).clickYes();

        // окно с номером
        OrderPageSuccess success = new OrderPageSuccess(driver);
        Assert.assertTrue("Нет текста с номером заказа", success.hasOrderNumber());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
