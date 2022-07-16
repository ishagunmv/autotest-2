package common;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.remote.DesiredCapabilities;

import static data.Constant.JenkinsParameters.*;

public class Config {

    /** Выбор IP прибора */
    public static final String DEVICE_IP = JENKINS_DEVICE_IP; // string ip "192.168.10.15"

    /** Старт на локальном компьютере или в доккере */
    public static final Boolean START_SELENOID = JENKINS_START_SELENOID; // true-false

    /** Выбор браузера для теста */
    public static final String BROWSER = JENKINS_BROWSER; //chrome firefox и т.д...

    /** Прошивка прибора */
    public static final Boolean FLASHING = true; //перепрошивка прибора
    public static final String BRANCH = "5.22"; //версия прошивки

    /** Работа с базой */
    public static final Boolean DROP_KRIS_SQL = JENKINS_DROP_KRIS_SQL; //очистка базы перед началом тестов
    public static final Boolean RESTORE_KRIS_SQL = JENKINS_RESTORE_KRIS_SQL; //восстановление базы по окончанию тестов

    /** Очистка куки и данных по завершению теста */
    public static final Boolean CLEAR_COOKIES = true;

    /** Держать ли браузер открытым по завершению теста */
    public static final Boolean HOLD_BROWSER_OPEN = false;

    /** Очистка директории скриншотов перед стартом теста */
    public static final Boolean CLEAR_REPORTS_DIR = true;

    /** Ожидание любого элемента по умолчанию */
    public static final int TIMEOUT_ELEMENTS_MS = 60000;
    public static final int TIMEOUT_PAGE_LOAD_MS = 120000;


    static {
        Configuration.browser = BROWSER;
        Configuration.timeout = TIMEOUT_ELEMENTS_MS;
        Configuration.pageLoadTimeout = TIMEOUT_PAGE_LOAD_MS;
        Configuration.holdBrowserOpen = HOLD_BROWSER_OPEN;
        Configuration.reportsFolder = "build/reports/tests";

        if (START_SELENOID) {
            Configuration.remote = "http://192.168.2.92:4444/wd/hub";
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", true);
            Configuration.browserCapabilities = capabilities;
        }
    }
}
