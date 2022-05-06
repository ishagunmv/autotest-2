package common;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static common.CommonActions.clearBrowserCookieAndStorage;
import static pages.base.BasePage.goToDevice;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

public class Listener implements TestWatcher, BeforeAllCallback, BeforeEachCallback, AfterEachCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        extensionContext.getRoot().getStore(GLOBAL).put(true, this);
        }

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        goToDevice();
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        clearBrowserCookieAndStorage();
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        LOGGER.info("Test " + context.getTestMethod().get().getName() + " - FAILED!");
        String screenshotName = context.getTestMethod().get().getName() +
                String.valueOf(System.currentTimeMillis()).substring(9, 13);
        LOGGER.info("Trying to trace screenshot...");
        Selenide.screenshot(screenshotName);
        attachScreenshotToAllure();
    }

    @Attachment(value = "Attachment Screenshot", type = "image/png")
    public byte[] attachScreenshotToAllure() {
        if (WebDriverRunner.hasWebDriverStarted())
            return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
        else return null;
    }
}
