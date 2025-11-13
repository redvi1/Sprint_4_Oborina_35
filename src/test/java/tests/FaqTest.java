package tests;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobject.MainPage;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class FaqTest {

    private WebDriver driver;

    private final String browser;      // хром или мозила
    private final int questionIndex;   // 1-8

    public FaqTest(String browser, int questionIndex) {
        this.browser = browser;
        this.questionIndex = questionIndex;
    }

    @Parameterized.Parameters(name = "{0}, вопрос {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"chrome", 1}, {"chrome", 2}, {"chrome", 3}, {"chrome", 4},
                {"chrome", 5}, {"chrome", 6}, {"chrome", 7}, {"chrome", 8},
                {"firefox", 1}, {"firefox", 2}, {"firefox", 3}, {"firefox", 4},
                {"firefox", 5}, {"firefox", 6}, {"firefox", 7}, {"firefox", 8},
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
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }


    @Test
    public void faqAnswerIsNotEmpty() {
        MainPage page = new MainPage(driver);

        // скроллим до FAQ через метод РО
        page.scrollToFaq();

        // простой маппинг через массивы
        By[] questions = new By[]{
                page.question1, page.question2, page.question3, page.question4,
                page.question5, page.question6, page.question7, page.question8
        };

        By[] answers = new By[]{
                page.answer1, page.answer2, page.answer3, page.answer4,
                page.answer5, page.answer6, page.answer7, page.answer8
        };

        int idx = questionIndex - 1; // из 1-8 в 0-7
        By question = questions[idx];
        By answer = answers[idx];

        // ждём ответа
        page.clickQuestion(question);

        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(answer));

        String text = page.getAnswerText(answer);

        Assert.assertFalse(
                "Пустой ответ для вопроса " + questionIndex + " в браузере " + browser,
                text.trim().isEmpty()
        );
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
