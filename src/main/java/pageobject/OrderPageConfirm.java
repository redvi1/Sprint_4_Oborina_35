package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderPageConfirm {

    private final WebDriver driver;

    public OrderPageConfirm(WebDriver driver) {
        this.driver = driver;
    }

    // Кнопка "Да"
    private final By yesButton = By.xpath("//button[text()='Да']");

    // Нажать "Да"
    public void clickYes() {
        driver.findElement(yesButton).click();
    }

}
