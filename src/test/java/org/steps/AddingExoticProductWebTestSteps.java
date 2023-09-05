package org.steps;

import io.cucumber.java.ru.Допустим;
import io.cucumber.java.ru.И;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class AddingExoticProductWebTestSteps {
    protected static WebDriver driver;
    static ChromeOptions chromeOptions = new ChromeOptions();
    static WebDriverWait wait;

    @Допустим("Пользователь открыл браузер")
    public void init() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        chromeOptions.setBinary("C:\\Users\\Роман\\Downloads\\chrome-win64\\chrome.exe");
        driver = new ChromeDriver(chromeOptions);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10, 1000));

        driver.manage().window().maximize();
    }

    @И("Пользователь открыл страницу {string}")
    public void openPage(String pageUrl){
        driver.get(pageUrl);
    }

    @И("Проверил текст заголовка {string}")
    public void checkHeader(String header) {
        Assertions.assertEquals(header,
                wait.until(ExpectedConditions.visibilityOf(
                        driver.findElement(By.xpath("//h5[text()='Список товаров']")))).getText());
    }

    @И("Нажал кнопку 'Добавить'")
    public void clickOnAddButton() {
        driver.findElement(By.xpath("//button[text()='Добавить']")).click();
    }

    @И("Проверил заголовок окна добавление продукта {string}")
    public void checkAddProductHeader(String header) {
        Assertions.assertEquals(header,
                wait.until(ExpectedConditions.visibilityOf(
                        driver.findElement(By.xpath("//h5[text()='Добавление товара']")))).getText());
    }

    @И("Ввел название в поле 'Наименование'")
    public void sendKeysInField(Map<String, String> dataTable) {
        driver.findElement(By.id("name")).sendKeys(dataTable.get("Наименование"));
    }

    @И("Проверил, что название ввелось")
    public void checkValueInField(Map<String, String> dataTable) {
        Assertions.assertEquals(dataTable.get("Наименование"),
                driver.findElement(By.id("name")).getAttribute("value"));
    }

    @И("Выбрал тип")
    public void selectInDropDownElement(Map<String, String> dataTable) {
        new Select(driver.findElement(By.id("type"))).selectByValue(dataTable.get("Тип"));
    }

    @И("Проверил, что выбор применился")
    public void checkSelectInDropDownElement(Map<String, String> dataTable) {
        Assertions.assertEquals(dataTable.get("Тип"), driver.findElement(By.id("type")).getAttribute("value"),
                "Тип продукта выбран неверно");
    }

    @И("Нажал на чек-бок 'Экзотический'")
    public void clickOnExoticCheckBox() {
        driver.findElement(By.id("exotic")).click();
    }

    @И("Проверил, что чек-бокс 'Экзотический' выбран")
    public void checkSelectCheckBox() {
        Assertions.assertTrue(driver.findElement(By.id("exotic")).isSelected(), "Чек-бокс не выбран");
    }

    @И("Нажал кнопку 'Сохранить'")
    public void clickOnSaveButton() {
        driver.findElement(By.id("save")).click();
    }

    @И("Проверил, что в таблице появилась запись с новым продуктом")
    public void checkTableProductList(Map<String, String> dataTable) {
        Assertions.assertEquals("5", driver.findElement(By.xpath("//tr[5]/th")).getText());
        Assertions.assertEquals(dataTable.get("Наименование"), driver.findElement(By.xpath("//tr[5]/td[1]")).getText());
        Assertions.assertEquals(dataTable.get("ТипРу"), driver.findElement(By.xpath("//tr[5]/td[2]")).getText());
        Assertions.assertEquals("true", driver.findElement(By.xpath("//tr[5]/td[3]")).getText());
    }

    @И("Закрыл страницу")
    public static void close() {
        driver.quit();
    }
}