package pages.webinterface;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import org.openqa.selenium.By;
import pages.base.BasePage;

import static com.codeborne.selenide.Selenide.*;
import static data.Constant.Data.SECURITY_CODE;

public class SystemRebootPage extends BasePage {

    public void insertSecurityAndReboot(){
        securityCodeInput.shouldBe(Condition.visible).setValue(SECURITY_CODE);
        rebootButton.shouldBe(Condition.visible).click();
        sleep(300000);
    }

    /** xpath */
    private final SelenideElement securityCodeInput = $(By.name("security_code"));
    private final SelenideElement rebootButton = $x("//input[@value='Перезагрузить сейчас']");
}
