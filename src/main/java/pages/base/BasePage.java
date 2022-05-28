package pages.base;

import com.codeborne.selenide.*;
import data.EPages;

import data.EUsers;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.webinterface.LoginPage;
import ssh.DeviceConnect;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static common.Config.DEVICE_IP;

public class BasePage {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasePage.class);
    protected DeviceConnect deviceConnect = new DeviceConnect(DEVICE_IP, EUsers.system);

    /** Методы перехода на страницы */
    public void goDevice(){
        LoginPage loginPage = new LoginPage();
        open("http://" + DEVICE_IP);
        loginPage.waitLoadLoginPage();
        LOGGER.info("Open device login page - " + DEVICE_IP);
    }

    public void goDevicePageUrl(EPages pages) {
        open("http://" + DEVICE_IP + "/" + pages.getUrlPage());
        LOGGER.info("Open page - " + pages.getNamePage());
    }
    public void goToUrl(String url){
        open(url);
    }

    /** Метод очистки инпута от текста по умолчанию и ввода текста
     * @param element SelenideElement
     * @param value text
     */
    protected void clearAndType(SelenideElement element, String value) {
        element.shouldBe(Condition.visible);
        while (!element.getAttribute("value").equals("")) element.sendKeys(Keys.BACK_SPACE, Keys.DELETE);
        element.setValue(value);
    }

    protected void clearInput(SelenideElement element) {
        element.shouldBe(Condition.visible);
        while (!element.getAttribute("value").equals("")) element.sendKeys(Keys.BACK_SPACE, Keys.DELETE);
    }

    /** Метод который проверяет текст на всей странице
     * @param message текст который нужно проверить
     */
    public void checkMessageVisible(String message){
        $(byText(message)).shouldBe(Condition.visible);
    }

    public void checkMessageHidden(String message){
        $(byText(message)).shouldNotBe(Condition.visible);
    }

    /**
     * Методы включения/выключения чек-боксов
     * @param element чек-бокс
     * @param enabled true|false
     */
    public void OnOffCheckBox(boolean enabled, SelenideElement element) {
        String attribute = element.getAttribute("for");
        SelenideElement checked = $x("//input[@id='" + attribute + "']");
        if(enabled){
            if (null == checked.getAttribute("checked")){
                element.shouldBe(Condition.visible).click();
            }
        } else {
            if (!(null == checked.getAttribute("checked"))){
                element.shouldBe(Condition.visible).click();
            }
        }
    }

    /** Игнор confirm */
    public void mockConfirm(){
        executeJavaScript("window.confirm = function() {return true;};");
    }

    /** Плашки */
    public final SelenideElement alertForReboot = $(byText("Не забудьте перезагрузить систему, чтобы изменённые параметры вступили в силу"));
    public final SelenideElement alertInterception = $(byText("Внимание! Включен режим «Перехват»."));
    public final SelenideElement alertClearJournal = $(byText("Происходит очистка журнала. Пожалуйста, подождите"));
}
