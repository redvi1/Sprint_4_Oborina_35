package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;

public class MainPage {

    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // Шапка

    // Кнопка "Заказать" в шапке
    public By topOrderButton = By.xpath("//div[@class='Header_Nav__AGCXC']/button[text()='Заказать']");

    // Средний блок с заказом

    // Кнопка "Заказать" в нижней части страницы
    public By bottomOrderButton = By.xpath("//div[@class='Home_FinishButton__1_cWm']/button[text()='Заказать']");


    // Блок "Вопросы о важном"

    // Заголовок блока
    public By faqHeader = By.xpath("//div[text()='Вопросы о важном']");

    // Вопрос 1
    public By question1 = By.id("accordion__heading-0");
    // Ответ 1
    public By answer1 = By.id("accordion__panel-0");

    // Вопрос 2
    public By question2 = By.id("accordion__heading-1");
    // Ответ 2
    public By answer2 = By.id("accordion__panel-1");

    // Вопрос 3
    public By question3 = By.id("accordion__heading-2");
    // Ответ 3
    public By answer3 = By.id("accordion__panel-2");

    // Вопрос 4
    public By question4 = By.id("accordion__heading-3");
    // Ответ 4
    public By answer4 = By.id("accordion__panel-3");

    // Вопрос 5
    public By question5 = By.id("accordion__heading-4");
    // Ответ 5
    public By answer5 = By.id("accordion__panel-4");

    // Вопрос 6
    public By question6 = By.id("accordion__heading-5");
    // Ответ 6
    public By answer6 = By.id("accordion__panel-5");

    // Вопрос 7
    public By question7 = By.id("accordion__heading-6");
    // Ответ 7
    public By answer7 = By.id("accordion__panel-6");

    // Вопрос 8
    public By question8 = By.id("accordion__heading-7");
    // Ответ 8
    public By answer8 = By.id("accordion__panel-7");

    // Методы

    // Клик по верхней кнопке "Заказать"
    public void clickTopOrderButton() {
        driver.findElement(topOrderButton).click();
    }

    // Клик по нижней кнопке "Заказать"
    public void clickBottomOrderButton() {
        driver.findElement(bottomOrderButton).click();
    }

    // Прокрутка к блоку FAQ
    public WebElement getFaqHeader() {
        return driver.findElement(faqHeader);
    }

    // Клик по вопросу
    public void clickQuestion(By question) {
        driver.findElement(question).click();
    }

    // Получить текст ответа
    public String getAnswerText(By answer) {
        return driver.findElement(answer).getText();
    }

    // Прокрутить страницу к нижней кнопке "Заказать"
    public void scrollToBottomOrderButton() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
                driver.findElement(bottomOrderButton));
    }

    public void scrollToFaq() {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView();", getFaqHeader()
        );
    }
}