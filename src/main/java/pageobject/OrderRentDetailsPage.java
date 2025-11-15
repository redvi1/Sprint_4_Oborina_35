package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderRentDetailsPage {

    private final WebDriver driver;

    public OrderRentDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    // Поле даты и календарь

    // Поле "Когда привезти самокат"
    private final By dateInput = By.xpath("//input[@placeholder='* Когда привезти самокат']");

    // День 11
    private final By date11 = By.cssSelector(".react-datepicker__day--011");

    // День 12
    private final By date12 = By.cssSelector(".react-datepicker__day--012");


    // Срок аренды

    // Список "Срок аренды"
    private final By rentalDropdown = By.cssSelector(".Dropdown-root .Dropdown-control");

    // Пункты списка
    private final By rentalOneDay   = By.xpath("//div[contains(@class,'Dropdown-option') and text()='сутки']");
    private final By rentalTwoDays  = By.xpath("//div[contains(@class,'Dropdown-option') and text()='двое суток']");

    // Цвет самоката

    // Чекбокс "чёрный жемчуг"
    private final By blackColor = By.id("black");

    // Чекбокс "серая безысходность"
    private final By greyColor = By.id("grey");


    // Комментарий

    // Поле "Комментарий для курьера"
    private final By commentInput = By.xpath("//input[@placeholder='Комментарий для курьера']");


    // Кнопки

    // Кнопка "Назад"
    private final By backButton = By.xpath("//div[contains(@class,'Order_Buttons__1xGrp')]//button[text()='Назад']");

    // Кнопка "Заказать"
    private final By orderButton = By.xpath("//div[contains(@class,'Order_Buttons__1xGrp')]//button[text()='Заказать']");

    // Ошибка
    private final By commentError = By.xpath("//div[contains(@class,'Input_ErrorMessage__3HvIb') and contains(@class,'Input_Visible___syz6') and text()]");

    // Методы

    // Выбрать дату 11 ноября
    public void selectDate11() {
        driver.findElement(dateInput).click();
        driver.findElement(date11).click();
    }

    // Выбрать дату 12 ноября
    public void selectDate12() {
        driver.findElement(dateInput).click();
        driver.findElement(date12).click();
    }

    // Открыть и выбрать срок аренды: 1 сутки
    public void selectOneDaysRental() {
        driver.findElement(rentalDropdown).click();
        driver.findElement(rentalOneDay).click();
    }

    // Открыть и выбрать срок аренды: 2 суток
    public void selectTwoDaysRental() {
        driver.findElement(rentalDropdown).click();
        driver.findElement(rentalTwoDays).click();
    }

    // Цвета
    public void selectBlackColor() {
        driver.findElement(blackColor).click();
    }

    public void selectGreyColor() {
        driver.findElement(greyColor).click();
    }

    // Коммент
    public void setComment(String comment) {
        driver.findElement(commentInput).sendKeys(comment);
    }

    // Кнопки
    public void clickBack() {
        driver.findElement(backButton).click();
    }

    public void clickOrder() {
        driver.findElement(orderButton).click();
    }

    public boolean isCommentErrorVisible() {
        return !driver.findElements(commentError).isEmpty();
    }
}
