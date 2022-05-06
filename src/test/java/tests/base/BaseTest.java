package tests.base;

import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pages.base.BasePage;
import pages.webinterface.LoginPage;
import pages.webinterface.MainMenuPage;


import java.io.File;
import java.time.LocalTime;
import java.util.Objects;

import static common.Config.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public abstract class BaseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);
    protected BasePage basePage = new BasePage();
    protected LoginPage loginPage = new LoginPage();
    protected MainMenuPage mainMenuPage = new MainMenuPage();


    /** Очистка скриншотов и отчетов */
    static {
        LOGGER.info("Start time: " + LocalTime.now());
        LOGGER.info("Start clear with report dir: allure-results");
        File allureResults = new File("allure-results");
        if(allureResults.isDirectory()) {
            for (File item : Objects.requireNonNull(allureResults.listFiles()))
                item.delete();
        }

        if (CLEAR_REPORTS_DIR) {
            LOGGER.info("Start clear with screenshot dir: build/reports/tests");
            File allureScreenshots = new File("build/reports/tests");
            for (File item : Objects.requireNonNull(allureScreenshots.listFiles()))
                item.delete();
        }
    }
}
