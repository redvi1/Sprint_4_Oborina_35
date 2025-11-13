package tests;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pageobject.MainPage;
import pageobject.OrderPage1;
import pageobject.OrderPage2;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class OrderFormErrorsTest {

    private WebDriver driver;
    private final String browser;

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    public OrderFormErrorsTest(String browser) {
        this.browser = browser;
    }

    @Parameterized.Parameters(name = "[Браузер: {0}]")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"chrome"},
                {"firefox"},
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
        driver.get(BASE_URL);
    }

    @After
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    // шаг 1
    @Test
    public void checkErrorsOnStep1() {
        MainPage main = new MainPage(driver);
        main.scrollToBottomOrderButton();
        main.clickBottomOrderButton();

        OrderPage1 step1 = new OrderPage1(driver);
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

        OrderPage1 step1 = new OrderPage1(driver);
        step1.setFirstName("Инна");
        step1.setLastName("Ивановна");
        step1.setAddress("Неплощадь");
        step1.selectFirstMetro();
        step1.setPhone("89876543221");
        step1.clickNextButton();

        // на шаге 2 ничего не заполняем
        OrderPage2 step2 = new OrderPage2(driver);
        step2.clickOrder();

        Assert.assertTrue(
                "Ошибка под комментарием не появилась",
                step2.isCommentErrorVisible()
        );
    }
}
