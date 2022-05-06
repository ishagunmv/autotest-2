package common;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Config {

    /** Выбор IP прибора */
    public static final String DEVICE_IP = "";

    /** Старт на локальном компьютере или в доккере */
    public static final Boolean START_LOCAL = false;
    /** Выбор браузера для теста */
    public static final String BROWSER = "chrome"; //chrome firefox и т.д...
    /** Очистка куки и данных по завершению теста */
    public static final Boolean CLEAR_COOKIES = true;
    /** Держать ли браузер открытым по завершению теста */
    public static final Boolean HOLD_BROWSER_OPEN = false;
    /** Очистка директории скриншотов перед стартом теста */
    public static final Boolean CLEAR_REPORTS_DIR = false;
    /** Ожидание любого элемента по умолчавнию */
    public static final int TIMEOUT_ELEMENTS_MS = 60000;


    static {
        if (START_LOCAL) {
            Configuration.holdBrowserOpen = HOLD_BROWSER_OPEN;
            Configuration.reportsFolder = "build/reports/tests";
            Configuration.browser = BROWSER;
            Configuration.timeout = TIMEOUT_ELEMENTS_MS;
        } else {
            Configuration.remote = "http://192.168.2.92:4444/wd/hub";
            Configuration.browser = BROWSER;
            Configuration.timeout = TIMEOUT_ELEMENTS_MS;
            Configuration.holdBrowserOpen = HOLD_BROWSER_OPEN;
            Configuration.reportsFolder = "build/reports/tests";
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", true);
            Configuration.browserCapabilities = capabilities;
        }
    }
}
