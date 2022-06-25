package pages.webinterface.main_menu;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import data.EPages;
import pages.base.BasePage;

import static com.codeborne.selenide.Selenide.$x;

public class MainMenuPage extends BasePage {

    /** Метод перехода на страницу через click по xpath  */
    public void goDevicePageXpath(EPages pages) {
        $x(pages.getXpathPage()).shouldBe(Condition.visible).click();
    }

    /** метод вызова xpath EPages */
    public void pagesXpath(EPages pages) {
       $x(pages.getXpathPage());
    }

    public void logoutUser(){
        exit.shouldBe(Condition.visible).click();
    }

    /** Остальные xpath */
    public final SelenideElement profile = $x("//span/a[contains(text(), 'Профиль')]");
    public final SelenideElement exit = $x("//span/a[contains(text(), 'Выход')]");
    public final SelenideElement aboutDevice = $x("//span/a[contains(text(), 'Об устройстве')]");
    public final SelenideElement helps = $x("//span/a[contains(text(), 'Справка')]");
    public final SelenideElement root = $x("//span/b[contains(text(), 'Root')]");
    public final SelenideElement support = $x("//span/b[contains(text(), 'Тех. поддержка')]");
    public final SelenideElement admin = $x("//span/b[contains(text(), 'Администратор')]");
    public final SelenideElement tadmin = $x("//span/b[contains(text(), 'Тех. Администратор')]");
    public final SelenideElement dBuser = $x("//span/b[contains(text(), 'Оператор баз розыска')]");
    public final SelenideElement user = $x("//span/b[contains(text(), 'Пользователь')]");
    public final SelenideElement tester = $x("//span/b[contains(text(), 'Гос. поверитель')]");
}
