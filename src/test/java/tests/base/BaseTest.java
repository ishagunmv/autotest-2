package tests.base;

import data.EUsers;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pages.base.BasePage;
import pages.webinterface.main_menu.LoginPage;
import pages.webinterface.config_fixing.ConfigFixingPage;
import pages.webinterface.main_menu.MainMenuPage;
import pages.webinterface.system_reboot.SystemRebootPage;
import ssh.DeviceConnect;


import java.io.File;
import java.time.LocalTime;
import java.util.Objects;

import static common.Config.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public abstract class BaseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);
    protected static DeviceConnect deviceConnect = new DeviceConnect(DEVICE_IP, EUsers.system);
    protected BasePage basePage = new BasePage();
    protected LoginPage loginPage = new LoginPage();
    protected MainMenuPage mainMenuPage = new MainMenuPage();
    protected ConfigFixingPage configFixingPage = new ConfigFixingPage();
    protected SystemRebootPage systemRebootPage = new SystemRebootPage();


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

    /** бекап базы */
    static {
        try {
            LOGGER.info("Start backup kris.sql3");
            deviceConnect.executeCommand("mkdir /tftpboot/boot/conf/backup");
            deviceConnect.executeCommand("cp /tftpboot/boot/conf/kris.sql3 /tftpboot/boot/conf/backup/kris.sql3");
            LOGGER.info("Backup kris.sql3 successfully");
        } catch (Exception e) {
            LOGGER.error("Fail to backupKrisBeforeTests() - {}" + e.getMessage());
        }
    }
}
