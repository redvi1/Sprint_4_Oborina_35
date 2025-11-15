package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import pageobject.*;

public class OrderFormErrorsTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    // шаг 1
    @Test
    public void checkErrorsOnStep1() {
        MainPage main = new MainPage(driver);
        main.scrollToBottomOrderButton();
        main.clickBottomOrderButton();

        OrderClientInfoPage step1 = new OrderClientInfoPage(driver);
        step1.clickNextButton();

        Assert.assertTrue(
                "Ошибки не появились у всех обязательных полей на шаге 1",
                step1.allErrorsVisible()
        );
    }

    // шаг 2
    @Test
    public void checkErrorsOnStep2() {
        MainPage main = new MainPage(driver);
        main.scrollToBottomOrderButton();
        main.clickBottomOrderButton();

        OrderClientInfoPage step1 = new OrderClientInfoPage(driver);
        step1.setFirstName("Инна");
        step1.setLastName("Ивановна");
        step1.setAddress("Неплощадь");
        step1.selectFirstMetro();
        step1.setPhone("89876543221");
        step1.clickNextButton();

        // на шаге 2 ничего не заполняем
        OrderRentDetailsPage step2 = new OrderRentDetailsPage(driver);
        step2.clickOrder();

        Assert.assertTrue(
                "Ошибка под комментарием не появилась",
                step2.isCommentErrorVisible()
        );
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
