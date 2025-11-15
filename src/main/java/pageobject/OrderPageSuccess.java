package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderPageSuccess {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public OrderPageSuccess(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    // Элементы окна

    // Текст с номером заказа
    private final By orderText = By.className("Order_Text__2broi");

    // Кнопка "Посмотреть статус"
    private final By statusButton = By.xpath("//button[text()='Посмотреть статус']");


    // Методы

    // Получить текст с номером заказа
    public String getOrderText() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderText));
        return driver.findElement(orderText).getText();
    }

    public boolean hasOrderNumber() {
        String text = getOrderText();
        return text.contains("Номер заказа");
    }

    // Нажать на кнопку "Посмотреть статус"
    public void clickStatusButton() {
        wait.until(ExpectedConditions.elementToBeClickable(statusButton)).click();
    }
}
