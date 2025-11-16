package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HeaderBar {

    private final WebDriver driver;

    public HeaderBar(WebDriver driver) {
        this.driver = driver;
    }

    // Лого Самоката
    private final By scooterLogo = By.className("Header_LogoScooter__3lsAR");

    // Лого Яндекса
    private final By yandexLogo = By.className("Header_LogoYandex__3TSOI");

    // Кнопка Статус заказа
    private final By statusButton = By.xpath("//button[contains(text(),'Статус заказа')]");

    public void clickScooterLogo() {
        driver.findElement(scooterLogo).click();
    }

    public void clickYandexLogo() {
        driver.findElement(yandexLogo).click();
    }

}
