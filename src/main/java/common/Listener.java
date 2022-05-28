package common;

import com.codeborne.selenide.logevents.SelenideLogger;
import org.junit.jupiter.api.extension.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static common.CommonActions.*;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

public class Listener implements TestWatcher, BeforeAllCallback, BeforeEachCallback, AfterEachCallback, AfterAllCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        extensionContext.getRoot().getStore(GLOBAL).put(true, this);
        SelenideLogger.addListener("allure", new AllureSelenide());
        dropKrisSql();
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        goDeviceAllTests();
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        clearBrowserCookieAndStorage();
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) {
        restoreKrisAfterTests();
    }
}
