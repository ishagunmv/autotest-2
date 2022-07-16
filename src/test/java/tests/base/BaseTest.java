package tests.base;

import data.EPages;
import data.EUsers;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pages.base.BasePage;
import pages.base.FirmwarePage;
import pages.webinterface.main_menu.LoginPage;
import pages.webinterface.config_fixing.ConfigFixingPage;
import pages.webinterface.main_menu.MainMenuPage;
import pages.webinterface.system_firmware.SystemFirmwarePage;
import pages.webinterface.system_reboot.SystemRebootPage;
import ssh.DeviceConnect;


import java.io.File;
import java.io.IOException;
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
        if (allureResults.isDirectory()) {
            for (File item : Objects.requireNonNull(allureResults.listFiles()))
                item.delete();
        }

        if (CLEAR_REPORTS_DIR) {
            LOGGER.info("Start clear with download and screenshot dir");
            File allureScreenshots = new File("build/reports/tests");
            for (File item : Objects.requireNonNull(allureScreenshots.listFiles()))
                item.delete();
            try {
                FileUtils.deleteDirectory(new File("build/downloads"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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

    /** Прошивка прибора */
    static {
        BasePage basePage = new BasePage();
        LoginPage loginPage = new LoginPage();
        SystemFirmwarePage systemFirmwarePage = new SystemFirmwarePage();
        SystemRebootPage systemRebootPage = new SystemRebootPage();
        FirmwarePage firmwarePage = new FirmwarePage();

        if (FLASHING) {
            LOGGER.info("Start flashing event");
            basePage.goDevice();
            loginPage.loginUser(EUsers.system);
            basePage.goDevicePageUrl(EPages.system_reboot);
            systemRebootPage.insertSecurityAndReboot();
            basePage.waitRebootSystem(0);

            loginPage.loginUser(EUsers.system);
            basePage.goDevicePageUrl(EPages.system_firmware);
            String type = systemFirmwarePage.insertSecurityAndGetTypeFirmware();

            firmwarePage.goToFirmwarePage();
            firmwarePage.selectAndDownloadsFirmware(type, BRANCH);

            basePage.goDevicePageUrl(EPages.system_firmware);
            systemFirmwarePage.uploadFilesFirmwareAndFlash(type);
        }
    }
}
