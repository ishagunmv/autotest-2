package pages.base;

import com.codeborne.selenide.*;
import data.EPages;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static common.Config.DEVICE_IP;

public class BasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasePage.class);

    /** Методы перехода на страницы */
    public void goToDevice() {
        open("http://" + DEVICE_IP);
        LOGGER.info("Open device login page - " + DEVICE_IP);
    }

    public void goToDevicePage(EPages pages) {
        open("http://" + DEVICE_IP + "/" + pages.getUrlPage());
        LOGGER.info("Open page" + pages.getNamePage());
    }
    public void goToUrl(String url){
        open(url);
    }


    /** Метод очистки инпута от текста по умолчанию и ввода текста
     * @param element SelenideElement
     * @param value text
     * */
    protected void clearAndType(SelenideElement element, String value) {
        while (!element.getAttribute("value").equals("")) element.sendKeys(Keys.BACK_SPACE);
        element.setValue(value);
    }

    protected void clearInput(SelenideElement element) {
        while (!element.getAttribute("value").equals("")) element.sendKeys(Keys.BACK_SPACE);
    }


    /** Метод который проверяет текст на всей странице
     * @param message текст который нужно проверить
     * */
    public void checkMessage(String message){
        $(byText(message)).shouldBe(Condition.visible);
    }
}
