package pages.base;

import com.codeborne.selenide.*;
import data.EPages;

import data.EUsers;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ssh.DeviceConnect;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static common.Config.DEVICE_IP;

public class BasePage {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasePage.class);
    protected DeviceConnect deviceConnect = new DeviceConnect(DEVICE_IP, EUsers.system);

    /** Методы перехода на страницы */
    public void goDevice(){
        open("http://" + DEVICE_IP);
        waitLoginPage();
    }

    public void goDevicePageUrl(EPages pages) {
        open("http://" + DEVICE_IP + "/" + pages.getUrlPage());
        LOGGER.info("Open page - " + pages.getNamePage());
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

    /**
     * Методы ожидания для перезагрузки прибора.
     */
    public void waitRebootSystem(int waitReboot) {
        LOGGER.info("Wait for reboot");
        closeWebDriver();
        LOGGER.info("Wait server ping");
        waitHavePing(waitReboot);
        LOGGER.info("Waiting Initialization page");
        waitHaveInitializationPage();
        LOGGER.info("Waiting login page");
        waitLoginPage();
    }

    private void waitHavePing(int waitReboot){
        int WAIT_ALIVE = 300000;
        WAIT_ALIVE = waitReboot == 0 ? WAIT_ALIVE : waitReboot;
        long start = System.currentTimeMillis();
        while (pingHost(10000)) {
            if ((System.currentTimeMillis() - start) > WAIT_ALIVE) {
                throw new Error("Device doesn't reboot");
            }
        }
        LOGGER.info("Server is not alive");
        start = System.currentTimeMillis();
        while (!pingHost(10000)) {
            if ((System.currentTimeMillis() - start) > WAIT_ALIVE) {
                throw new Error("Device doesn't alive");
            }
        }
        LOGGER.info("Server is alive");
    }

    private void waitHaveInitializationPage() {
        for (int i = 0; i < 200; i++) {
            try {
                sleep(1000);
                open("http://" + DEVICE_IP);
                break;
            } catch (Exception e) {
                continue;
            }
        }
        LOGGER.info("Initialization page appear");
    }

    private void waitLoginPage() {
        for (int i = 0; i < 100; i++) {
            refresh();
            if($(By.name("login")).isDisplayed()){
                LOGGER.info("LoginPage " + DEVICE_IP + " - opened");
                break;
            } else {
                sleep(6000);
            }
        }
    }

    private boolean pingHost(int timeOut) {
        try (Socket socket = new Socket()){
            socket.connect(new InetSocketAddress(DEVICE_IP, 22), timeOut);
            return true;
        } catch (IOException err) {
            return false;
        }
    }

    /**
     * Игнор confirm
     */
    public void mockConfirm(){
        executeJavaScript("window.confirm = function() {return true;};");
    }

    /** Плашки для всех страниц */
    public final SelenideElement alertForReboot = $(byText("Не забудьте перезагрузить систему, чтобы изменённые параметры вступили в силу"));
    public final SelenideElement alertInterception = $(byText("Внимание! Включен режим «Перехват»."));
    public final SelenideElement alertClearJournal = $(byText("Происходит очистка журнала. Пожалуйста, подождите"));
}
