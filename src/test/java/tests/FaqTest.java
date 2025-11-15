package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobject.MainPage;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class FaqTest {

    private WebDriver driver;

    @Parameterized.Parameter
    public int questionIndex; // 1..8

    @Parameterized.Parameters(name = "Вопрос {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {1}, {2}, {3}, {4},
                {5}, {6}, {7}, {8}
        });
    }

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @Test
    public void faqAnswerHasCorrectText() {
        MainPage page = new MainPage(driver);

        page.scrollToFaq();
        page.openFaqQuestion(questionIndex);

        String actualText = page.getFaqAnswerText(questionIndex).trim();
        String expectedText = page.getExpectedFaqAnswer(questionIndex).trim();
        String questionText = page.getFaqQuestionText(questionIndex);

        Assert.assertEquals(
                "Неверный ответ для вопроса: " + questionText,
                expectedText,
                actualText
        );
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
