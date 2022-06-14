package tests.general;

import com.codeborne.selenide.Condition;
import common.Listener;
import data.EUsers;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tests.base.BaseTest;


@Feature("Tests to check authorization for login page")
@Story("positive and negative scenario")
//@Execution(ExecutionMode.CONCURRENT) // Запуск тестов в нескольких потоках /src/test/resources/junit-platform.properties
@ExtendWith(Listener.class)

public class LoginTest extends BaseTest {

    @BeforeEach
    void goDevice(){
        basePage.goDevice();
    }

    /** positive */
    @Test
    void rootLogin() {
        loginPage.loginUser(EUsers.system);
        mainMenuPage.root.shouldBe(Condition.visible);
        mainMenuPage.logoutUser();
    }

    @Test
    void adminLogin() {
        loginPage.loginUser(EUsers.admin);
        mainMenuPage.admin.shouldBe(Condition.visible);
        mainMenuPage.logoutUser();
    }

    @Test
    void userLogin() {
        loginPage.loginUser(EUsers.user);
        mainMenuPage.user.shouldBe(Condition.visible);
        mainMenuPage.logoutUser();
    }

    /** negative */
    @Test
    void trySystemLogin() {
        loginPage.sendLogin("system");
        loginPage.sendPassword("1234567890");
        loginPage.clickSubmit();
        loginPage.errorAuthMessage();
    }

    @Test
    void tryAdminLogin() {
        loginPage.sendLogin("admin");
        loginPage.sendPassword("1234567890");
        loginPage.clickSubmit();
        loginPage.errorAuthMessage();
    }

    @Test
    void tryUserLogin() {
        loginPage.sendLogin("user");
        loginPage.sendPassword("1234567890");
        loginPage.clickSubmit();
        loginPage.errorAuthMessage();
    }

    @Test
    void emptySystemPassword() {
        loginPage.sendLogin("system");
        loginPage.clickSubmit();
        loginPage.errorAuthMessage();
    }

    @Test
    void emptyAccount() {
        loginPage.clickSubmit();
        loginPage.errorAuthMessage();
    }
}
