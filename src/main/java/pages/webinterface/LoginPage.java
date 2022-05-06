package pages.webinterface;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.EUsers;

import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {

    /** Метод авторизации под юзером */
    public void login (EUsers users) {
        sendLogin(users.getLogin());
        sendPassword(users.getPassword());
        clickSubmit();
    }

    /** Метод ввода логина */
    public void sendLogin(String login) {
        inputLogin.shouldBe(Condition.visible).sendKeys(login);
    }

    /** Метод ввода пароля */
    public void sendPassword(String password) {
        inputPassword.shouldBe(Condition.visible).sendKeys(password);
    }

    /** Клик по кнопке "Вход" */
    public void clickSubmit() {
        submitButton.shouldBe(Condition.visible).click();
    }

    /** Метод проверяющий сообщение о неверном пользователе */
    public void errorAuthMessage() {
        authFailedMessage.shouldBe(Condition.visible);
    }

    /** xpath */
    private final SelenideElement inputLogin = $x("//input[@name='login']");
    private final SelenideElement inputPassword = $x("//input[@name='password']");
    private final SelenideElement submitButton = $x("//button[@type='submit']");
    private final SelenideElement authFailedMessage = $x("//div[@class='warning' and contains(text(), 'Неверные имя пользователя/пароль')]");
}
