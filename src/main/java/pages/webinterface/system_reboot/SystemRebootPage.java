package pages.webinterface.system_reboot;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.base.BasePage;

import static com.codeborne.selenide.Selenide.*;
import static data.Constant.Data.SECURITY_CODE;

public class SystemRebootPage extends BasePage {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemRebootPage.class);

    public void insertSecurityAndReboot() {
        securityCodeInput.shouldBe(Condition.visible).setValue(SECURITY_CODE);
        rebootButton.shouldBe(Condition.visible).click();
        LOGGER.info("Start reboot");
    }

    /**
     * xpath
     */
    private final SelenideElement securityCodeInput = $(By.name("security_code"));
    private final SelenideElement rebootButton = $x("//input[@value='Перезагрузить сейчас']");
}
