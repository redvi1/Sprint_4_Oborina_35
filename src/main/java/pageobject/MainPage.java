package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage {

    private final WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // кнопки заказа

    private final By topOrderButton =
            By.xpath("//div[@class='Header_Nav__AGCXC']/button[text()='Заказать']");

    private final By bottomOrderButton =
            By.xpath("//div[@class='Home_FinishButton__1_cWm']/button[text()='Заказать']");

    // блок "Вопросы о важном"

    private final By faqHeader = By.xpath("//div[text()='Вопросы о важном']");

    // вопросы
    private final By questionCost = By.id("accordion__heading-0");
    private final By questionSeveralScooters = By.id("accordion__heading-1");
    private final By questionRentTime = By.id("accordion__heading-2");
    private final By questionToday = By.id("accordion__heading-3");
    private final By questionExtendOrReturn = By.id("accordion__heading-4");
    private final By questionCharger = By.id("accordion__heading-5");
    private final By questionCancel = By.id("accordion__heading-6");
    private final By questionOutsideMKAD = By.id("accordion__heading-7");

    // ответы
    private final By answerCost = By.id("accordion__panel-0");
    private final By answerSeveralScooters = By.id("accordion__panel-1");
    private final By answerRentTime = By.id("accordion__panel-2");
    private final By answerToday = By.id("accordion__panel-3");
    private final By answerExtendOrReturn = By.id("accordion__panel-4");
    private final By answerCharger  = By.id("accordion__panel-5");
    private final By answerCancel = By.id("accordion__panel-6");
    private final By answerOutsideMKAD = By.id("accordion__panel-7");

    // массивы для удобного доступа по номеру 1–8
    private final By[] questionLocators = new By[]{
            questionCost,
            questionSeveralScooters,
            questionRentTime,
            questionToday,
            questionExtendOrReturn,
            questionCharger,
            questionCancel,
            questionOutsideMKAD
    };

    private final By[] answerLocators = new By[]{
            answerCost,
            answerSeveralScooters,
            answerRentTime,
            answerToday,
            answerExtendOrReturn,
            answerCharger,
            answerCancel,
            answerOutsideMKAD
    };

    // ожид тексты ответов
    private static final String[] EXPECTED_ANSWERS = {
            "Сутки — 400 рублей. Оплата курьеру — наличными или картой.",
            "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.",
            "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.",
            "Только начиная с завтрашнего дня. Но скоро станем расторопнее.",
            "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.",
            "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.",
            "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.",
            "Да, обязательно. Всем самокатов! И Москве, и Московской области."
    };

    private static final String[] QUESTION_TEXTS = {
            "Сколько это стоит? И как оплатить?",
            "Хочу сразу несколько самокатов! Так можно?",
            "Как рассчитывается время аренды?",
            "Можно ли заказать самокат прямо на сегодня?",
            "Можно ли продлить заказ или вернуть самокат раньше?",
            "Вы привозите зарядку вместе с самокатом?",
            "Можно ли отменить заказ?",
            "Я живу за МКАДом, привезёте?"
    };

    private int toIndex(int questionNumber) {
        return questionNumber - 1;
    }

    // методы для тестов

    public void clickTopOrderButton() {
        driver.findElement(topOrderButton).click();
    }

    public void clickBottomOrderButton() {
        driver.findElement(bottomOrderButton).click();
    }

    public void scrollToBottomOrderButton() {
        WebElement element = driver.findElement(bottomOrderButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    public void scrollToFaq() {
        WebElement header = driver.findElement(faqHeader);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", header);
    }

    // открыть нужный вопрос
    public void openFaqQuestion(int questionNumber) {
        int idx = toIndex(questionNumber);
        driver.findElement(questionLocators[idx]).click();
    }

    // фактический текст ответа
    public String getFaqAnswerText(int questionNumber) {
        int idx = toIndex(questionNumber);
        return driver.findElement(answerLocators[idx]).getText();
    }

    // ожидаемый текст ответа
    public String getExpectedFaqAnswer(int questionNumber) {
        return EXPECTED_ANSWERS[toIndex(questionNumber)];
    }

    // текст вопроса
    public String getFaqQuestionText(int questionNumber) {
        return QUESTION_TEXTS[toIndex(questionNumber)];
    }
}
