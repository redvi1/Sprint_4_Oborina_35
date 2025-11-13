package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class OrderPage1 {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public OrderPage1(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    // Для кого самокат

    // Поле "Имя"
    private final By firstNameInput = By.xpath("//input[@placeholder='* Имя']");

    // Поле "Фамилия"
    private final By lastNameInput = By.xpath("//input[@placeholder='* Фамилия']");

    // Поле "Адрес: куда привезти заказ"
    private final By addressInput = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");

    // Поле "Станция метро"
    private final By metroInput = By.cssSelector("input[placeholder='* Станция метро']");

    // Первая кнопка в списке станций
    private final By firstMetroOption = By.xpath("(//button[contains(@class,'select-search__option')])[1]");

    // Вторая кнопка в списке станций
    private final By secondMetroOption = By.xpath("(//button[contains(@class,'select-search__option')])[2]");

    // Поле "Телефон"
    private final By phoneInput = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");

    // Кнопка "Далее"
    private final By nextButton = By.xpath("//button[text()='Далее']");

    // Методы

    public void setFirstName(String firstName) {
        driver.findElement(firstNameInput).sendKeys(firstName);
    }

    public void setLastName(String lastName) {
        driver.findElement(lastNameInput).sendKeys(lastName);
    }

    public void setAddress(String address) {
        driver.findElement(addressInput).sendKeys(address);
    }

    private void openMetroDropdown() {
        driver.findElement(metroInput).click();
    }

    // Выбрать первую станцию
    public void selectFirstMetro() {
        openMetroDropdown();
        wait.until(ExpectedConditions.elementToBeClickable(firstMetroOption)).click();
    }

    // Выбрать вторую станцию
    public void selectSecondMetro() {
        openMetroDropdown();
        wait.until(ExpectedConditions.elementToBeClickable(secondMetroOption)).click();
    }

    public void setPhone(String phone) {
        driver.findElement(phoneInput).sendKeys(phone);
    }

    public void clickNextButton() {
        driver.findElement(nextButton).click();
    }

    private boolean isErrorVisible(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    public boolean allErrorsVisible() {
        boolean nameError   = isErrorVisible(firstNameError);
        boolean lastError   = isErrorVisible(lastNameError);
        boolean addressErr  = isErrorVisible(addressError);
        boolean metroErr    = isErrorVisible(metroError);
        boolean phoneErr    = isErrorVisible(phoneError);
        return nameError && lastError && addressErr && metroErr && phoneErr;
    }

    // Ошибки
    private final By firstNameError = By.xpath("//input[@placeholder='* Имя']/following-sibling::div[contains(@class,'Input_Visible___syz6')]");
    private final By lastNameError  = By.xpath("//input[@placeholder='* Фамилия']/following-sibling::div[contains(@class,'Input_Visible___syz6')]");
    private final By addressError   = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']/following-sibling::div[contains(@class,'Input_Visible___syz6')]");
    private final By metroError     = By.xpath("//div[contains(@class,'Order_MetroError__1BtZb') and text()]");
    private final By phoneError     = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']/following-sibling::div[contains(@class,'Input_Visible___syz6')]");
}
