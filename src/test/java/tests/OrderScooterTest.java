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
import pageobject.OrderPageConfirm;
import pageobject.OrderPageSuccess;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class OrderScooterTest {

    private WebDriver driver;

    private final String browser; // хром и мозила

    public OrderScooterTest(String browser) {
        this.browser = browser;
    }

    @Parameterized.Parameters(name = "Браузер: {0}")
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

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    // набор 1: верхняя кнопка, 11 число, сутки, чёрный цвет
    @Test
    public void order_viaTopButton_set1() {
        // Точка входа
        MainPage main = new MainPage(driver);
        main.clickTopOrderButton();

        // шаг 1
        OrderPage1 step1 = new OrderPage1(driver);
        step1.setFirstName("Пётр");
        step1.setLastName("Петров");
        step1.setAddress("Площадь");
        step1.selectFirstMetro();
        step1.setPhone("89999999999");
        step1.clickNextButton();

        // шаг 2
        OrderPage2 step2 = new OrderPage2(driver);
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
        // точка входа
        MainPage main = new MainPage(driver);
        main.scrollToBottomOrderButton();
        main.clickBottomOrderButton();

        // шаг 1
        OrderPage1 step1 = new OrderPage1(driver);
        step1.setFirstName("Инна");
        step1.setLastName("Ивановна");
        step1.setAddress("Неплощадь");
        step1.selectSecondMetro();
        step1.setPhone("89876543221");
        step1.clickNextButton();

        // шаг 2
        OrderPage2 step2 = new OrderPage2(driver);
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
