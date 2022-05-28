package tests.arch.x86_64.webinterface;

import common.Listener;
import data.EPages;
import data.EUsers;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tests.base.BaseTest;

@Feature("Tests to check fotofixation settings page")
@Story("positive and negative scenario")
@ExtendWith(Listener.class)

public class ConfigFixingTest extends BaseTest{


    @BeforeEach
    void beforeEach(){
        loginPage.loginUser(EUsers.system);
        basePage.goDevicePageUrl(EPages.config_fixing);
    }


    /**
     * positive
     */
    @Test
    void saveTargetNoNumberSkip(){
        configFixingPage.selectTargetNoNumberSkip();
        configFixingPage.saveFixationSettings();
        configFixingPage.viewAlertForReboot();
        Assertions.assertEquals("SKIP", deviceConnect.getValueFromSql("INDUSTRIAL_TRAGETSWITHOUTLICNUMMODE"));
    }

    @Test
    void saveTargetNoNumberSkipOnlyOne(){
        configFixingPage.selectTargetNoNumberSkipOnlyOne();
        configFixingPage.saveFixationSettings();
        configFixingPage.viewAlertForReboot();
        Assertions.assertEquals("ONLYONE", deviceConnect.getValueFromSql("INDUSTRIAL_TRAGETSWITHOUTLICNUMMODE"));
    }

    @Test
    void saveTargetNoNumberSend(){
        configFixingPage.selectTargetNoNumberSend();
        configFixingPage.saveFixationSettings();
        configFixingPage.viewAlertForReboot();
        Assertions.assertEquals("SEND", deviceConnect.getValueFromSql("INDUSTRIAL_TRAGETSWITHOUTLICNUMMODE"));
    }

    @Test
    void saveTargetNoSpeedSkip(){
        configFixingPage.selectTargetNoSpeedSkip();
        configFixingPage.saveFixationSettings();
        configFixingPage.viewAlertForReboot();
        Assertions.assertEquals("false", deviceConnect.getValueFromSql("INDUSTRIAL_SENDTRAGETSWITHOUTSPEED"));
    }

    @Test
    void saveTargetNoSpeedSend(){
        configFixingPage.selectTargetNoSpeedSend();
        configFixingPage.saveFixationSettings();
        configFixingPage.viewAlertForReboot();
        Assertions.assertEquals("true", deviceConnect.getValueFromSql("INDUSTRIAL_SENDTRAGETSWITHOUTSPEED"));
    }

    @Test
    void saveEnableCategoryTs(){
        configFixingPage.onOffCategoryTs(true);
        configFixingPage.saveFixationSettings();
        configFixingPage.viewAlertForReboot();
        Assertions.assertEquals("NEURAL", deviceConnect.getValueFromSql("INDUSTRIAL_CLASSIFICATOR_TYPE"));
    }

    @Test
    void saveDisableCategoryTs(){
        configFixingPage.onOffCategoryTs(false);
        configFixingPage.saveFixationSettings();
        configFixingPage.viewAlertForReboot();
        Assertions.assertEquals("", deviceConnect.getValueFromSql("INDUSTRIAL_CLASSIFICATOR_TYPE"));
    }

    @Test
    void saveEnableMarkModel(){
        configFixingPage.onOffMarkModel(true);
        configFixingPage.saveFixationSettings();
        configFixingPage.viewAlertForReboot();
        Assertions.assertEquals("true", deviceConnect.getValueFromSql("INDUSTRIAL_ENABLE_CAR_MARK_MODEL"));
    }

    @Test
    void saveDisableMarkModel(){
        configFixingPage.onOffMarkModel(false);
        configFixingPage.saveFixationSettings();
        configFixingPage.viewAlertForReboot();
        Assertions.assertEquals("false", deviceConnect.getValueFromSql("INDUSTRIAL_ENABLE_CAR_MARK_MODEL"));
    }

    @Test
    void saveEnableOnViewPhoto(){
        configFixingPage.onOffViewPhoto(true);
        configFixingPage.saveFixationSettings();
        configFixingPage.viewAlertForReboot();
        Assertions.assertEquals("true", deviceConnect.getValueFromSql("INDUSTRIAL_PRINT_ROAD_MARKER"));
    }

    @Test
    void saveDisableOnViewPhoto(){
        configFixingPage.onOffViewPhoto(false);
        configFixingPage.saveFixationSettings();
        configFixingPage.viewAlertForReboot();
        Assertions.assertEquals("false", deviceConnect.getValueFromSql("INDUSTRIAL_PRINT_ROAD_MARKER"));
    }

    @Test
    void saveEnableBigPhoto(){
        configFixingPage.onOffBigPhoto(true);
        configFixingPage.saveFixationSettings();
        configFixingPage.viewAlertForReboot();
        Assertions.assertEquals("true", deviceConnect.getValueFromSql("INDUSTRIAL_PRINT_TARGET_MARKER"));
    }

    @Test
    void saveDisableBigPhoto(){
        configFixingPage.onOffBigPhoto(false);
        configFixingPage.saveFixationSettings();
        configFixingPage.viewAlertForReboot();
        Assertions.assertEquals("false", deviceConnect.getValueFromSql("INDUSTRIAL_PRINT_TARGET_MARKER"));
    }

    @Test
    void saveMarkerNoView(){
        configFixingPage.selectNoViewMarker();
        configFixingPage.saveFixationSettings();
        configFixingPage.viewAlertForReboot();
        Assertions.assertEquals("0", deviceConnect.getValueFromSql("INDUSTRIAL_MARKER_TYPE"));
    }

    @Test
    void saveMarkerCross(){
        configFixingPage.selectCrossMarker();
        configFixingPage.saveFixationSettings();
        configFixingPage.viewAlertForReboot();
        Assertions.assertEquals("ROUND", deviceConnect.getValueFromSql("INDUSTRIAL_MARKER_TYPE"));
    }

    @Test
    void saveMarkerFrame(){
        configFixingPage.selectFrameMarker();
        configFixingPage.saveFixationSettings();
        configFixingPage.viewAlertForReboot();
        Assertions.assertEquals("RECTANGLE", deviceConnect.getValueFromSql("INDUSTRIAL_MARKER_TYPE"));
    }

    @Test
    void enableInterception(){
        configFixingPage.sendSecurityCodeInterception();
        configFixingPage.enableInterception();
        configFixingPage.checkEnableInterception();
        Assertions.assertEquals("true", deviceConnect.getValueFromSql("INDUSTRIAL_ALL_TARGETS_MODE"));
    }

    @Test
    void disableInterception(){
        configFixingPage.sendSecurityCodeInterception();
        configFixingPage.disableInterception();
        configFixingPage.checkDisableInterception();
        Assertions.assertEquals("false", deviceConnect.getValueFromSql("INDUSTRIAL_ALL_TARGETS_MODE"));
    }


    @Test
    void saveImageSize0Avg(){
        configFixingPage.sendSecurityCodeImageSize();
        configFixingPage.sendValueImageSize0("666666");
        configFixingPage.saveImageSize();
        Assertions.assertEquals("666666",
                deviceConnect.getValueFromSql("I0__STORAGEMANAGER_MAXIMAGESIZEINBYTES"));
    }

    @Test
    void saveImageSize1Avg(){
        configFixingPage.sendSecurityCodeImageSize();
        configFixingPage.sendValueImageSize1("1777777");
        configFixingPage.saveImageSize();
        Assertions.assertEquals("1777777", deviceConnect.getValueFromSql("I1__STORAGEMANAGER_MAXIMAGESIZEINBYTES"));
    }

    @Test
    void saveImageSize0Min(){
        configFixingPage.sendSecurityCodeImageSize();
        configFixingPage.sendValueImageSize0("4096");
        configFixingPage.saveImageSize();
        Assertions.assertEquals("4096", deviceConnect.getValueFromSql("I0__STORAGEMANAGER_MAXIMAGESIZEINBYTES"));
    }

    @Test
    void saveImageSize1Max(){
        configFixingPage.sendSecurityCodeImageSize();
        configFixingPage.sendValueImageSize1("8388608");
        configFixingPage.saveImageSize();
        Assertions.assertEquals("8388608", deviceConnect.getValueFromSql("I1__STORAGEMANAGER_MAXIMAGESIZEINBYTES"));
    }

    @Test
    void saveImageSize0Null(){
        configFixingPage.sendSecurityCodeImageSize();
        configFixingPage.sendValueImageSize0("0");
        configFixingPage.saveImageSize();
        Assertions.assertEquals("0", deviceConnect.getValueFromSql("I0__STORAGEMANAGER_MAXIMAGESIZEINBYTES"));
    }

    @Test
    void saveImageSizeAll(){
        configFixingPage.sendSecurityCodeImageSize();
        configFixingPage.sendValueImageSize0("111111");
        configFixingPage.sendValueImageSize1("1111111");
        configFixingPage.saveImageSize();
        Assertions.assertEquals("111111", deviceConnect.getValueFromSql("I0__STORAGEMANAGER_MAXIMAGESIZEINBYTES"));
        Assertions.assertEquals("1111111", deviceConnect.getValueFromSql("I1__STORAGEMANAGER_MAXIMAGESIZEINBYTES"));
    }


    /**
     * negative
     */
    @Test
    void sendLettersImageSize0(){
        configFixingPage.sendSecurityCodeImageSize();
        configFixingPage.sendValueImageSize0("jhjhasd");
        Assertions.assertEquals("", configFixingPage.getValueImageSize0());
    }

    @Test
    void sendLettersImageSize1(){
        configFixingPage.sendSecurityCodeImageSize();
        configFixingPage.sendValueImageSize1("jhjhasd");
        Assertions.assertEquals("", configFixingPage.getValueImageSize1());
    }

    @Test
    void saveNegativeImageSize0(){
        configFixingPage.sendSecurityCodeImageSize();
        configFixingPage.sendValueImageSize0("4095");
        configFixingPage.saveImageSize();
        configFixingPage.viewAlertForNegativeImageSize("4095");
    }

    @Test
    void saveNegativeImageSize1(){
        configFixingPage.sendSecurityCodeImageSize();
        configFixingPage.sendValueImageSize1("8388609");
        configFixingPage.saveImageSize();
        configFixingPage.viewAlertForNegativeImageSize("8388609");
    }


    @AfterEach
    void logoutUser(){
        mainMenuPage.logoutUser();
    }
}
