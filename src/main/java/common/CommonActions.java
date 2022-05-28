package common;

import com.codeborne.selenide.Selenide;
import data.EPages;
import data.EUsers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.base.BasePage;
import pages.webinterface.LoginPage;
import pages.webinterface.SystemRebootPage;
import ssh.DeviceConnect;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static common.Config.*;


public class CommonActions {

    private static final DeviceConnect deviceConnect = new DeviceConnect(DEVICE_IP, EUsers.system);
    private static final BasePage basePage = new BasePage();
    private static final LoginPage loginPage = new LoginPage();
    private static final SystemRebootPage systemRebootPage = new SystemRebootPage();
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonActions.class);


    /** Метод удаления данных с браузера */
    public static void clearBrowserCookieAndStorage(){
        if (CLEAR_COOKIES) {
            try {
                LOGGER.info("Start clear cookie and storage browser");
                Selenide.clearBrowserCookies();
                Selenide.clearBrowserLocalStorage();
                Selenide.executeJavaScript("window.sessionStorage.clear()");
                sleep(1000);
            } catch (Exception e) {
                LOGGER.error("Fail to clearBrowserCookieAndStorage() - {}" + e.getMessage());
            }
        }
    }

    /** Метод перехода на прибор */
    public static void goDeviceAllTests() {
        open("http://" + DEVICE_IP);
        loginPage.waitLoadLoginPage();
        LOGGER.info("Open device login page - " + DEVICE_IP);
    }

    /** Методы работы с базой */
    public static void dropKrisSql() {
        if (DROP_KRIS_SQL){
            try {
                LOGGER.info("Start drop kris.sql3");
                deviceConnect.executeCommand("sqlite3 /tftpboot/boot/conf/kris.sql3 'delete from tblSettings'");
                goDeviceAllTests();
                loginPage.loginUser(EUsers.system);
                basePage.goDevicePageUrl(EPages.system_reboot);
                LOGGER.info("Reboot device...");
                systemRebootPage.insertSecurityAndReboot();
                LOGGER.info("Drop kris.sql3 successfully");
            } catch (Exception e) {
                LOGGER.error("Fail to dropKrisSql() - {}" + e.getMessage());
            }
        }
    }

    public static void restoreKrisAfterTests() {
        if(RESTORE_KRIS_SQL){
            try {
                LOGGER.info("Start restore kris.sql3");
                deviceConnect.executeCommand("cp /tftpboot/boot/conf/backup/kris.sql3 /tftpboot/boot/conf/kris.sql3");

                goDeviceAllTests();
                loginPage.loginUser(EUsers.system);
                basePage.goDevicePageUrl(EPages.system_reboot);
                LOGGER.info("Reboot device...");
                systemRebootPage.insertSecurityAndReboot();
                LOGGER.info("Restore kris.sql3 successfully");
            } catch (Exception e) {
                LOGGER.error("Fail to restoreKrisAfterTests() - {}" + e.getMessage());
            }
        }
    }
}
