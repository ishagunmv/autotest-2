package pages.webinterface;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import data.EUsers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.base.BasePage;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class LoginPage extends BasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginPage.class);

    /** Метод авторизации под пользователями */
    public void loginUser (EUsers users) {
        sendLogin(users.getLogin());
        sendPassword(users.getPassword());
        clickSubmit();
        LOGGER.info("Login user - " + users.getName());
    }



    /** Метод ввода логина */
    public void sendLogin(String login) {
        inputLogin.shouldBe(Condition.visible).setValue(login);
    }

    /** Метод ввода пароля */
    public void sendPassword(String password) {
        inputPassword.shouldBe(Condition.visible).setValue(password);
    }

    /** Клик по кнопке "Вход" */
    public void clickSubmit() {
        submitButton.shouldBe(Condition.visible).click();
    }

    /** Метод проверяющий сообщение о неверном пользователе */
    public void errorAuthMessage() {
        authFailedMessage.shouldBe(Condition.visible);
    }

    /** Ожидание загрузки страницы */
    public void waitLoadLoginPage() {
        submitButton.shouldBe(Condition.visible, Duration.ofMinutes(10));
    }

    /** xpath */
    private final SelenideElement inputLogin = $x("//input[@name='login']");
    private final SelenideElement inputPassword = $x("//input[@name='password']");
    private final SelenideElement submitButton = $x("//button[@type='submit']");
    private final SelenideElement authFailedMessage = $x("//div[@class='warning' and contains(text(), 'Неверные имя пользователя/пароль')]");
}
