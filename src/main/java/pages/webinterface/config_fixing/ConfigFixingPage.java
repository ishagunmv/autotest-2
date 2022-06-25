package pages.webinterface.config_fixing;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import pages.base.BasePage;

import static com.codeborne.selenide.Selenide.$x;
import static data.Constant.Data.SECURITY_CODE;


public class ConfigFixingPage extends BasePage {


    /** Режимы работы радара */
    public void selectRadarModeAny(){
        setModeRadar.shouldBe(Condition.visible).click();
        workingModeRadarAny.shouldBe(Condition.visible).click();
    }

    public void selectRadarModeOncoming(){
        setModeRadar.shouldBe(Condition.visible).click();
        workingMOdeRadarOncoming.shouldBe(Condition.visible).click();
    }

    public void selectRadarModeOutgoing(){
        setModeRadar.shouldBe(Condition.visible).click();
        workingMOdeRadarOutgoing.shouldBe(Condition.visible).click();
    }

    /** Методы выбора радио-баттонов "Цель без номера" */
    public void selectTargetNoNumberSkip(){
        if (alertInterception.is(Condition.visible)) {
            sendSecurityCodeInterception();
            disableInterception();
            targetNoNumberSkip.shouldBe(Condition.visible).click();
        } else {
            targetNoNumberSkip.shouldBe(Condition.visible).click();
        }
    }

    public void selectTargetNoNumberSkipOnlyOne(){
        if (alertInterception.is(Condition.visible)) {
            sendSecurityCodeInterception();
            disableInterception();
            targetNoNumberSkipOnlyOne.shouldBe(Condition.visible).click();
        } else {
            targetNoNumberSkipOnlyOne.shouldBe(Condition.visible).click();
        }
    }

    public void selectTargetNoNumberSend(){
        if (alertInterception.is(Condition.visible)) {
            sendSecurityCodeInterception();
            disableInterception();
            targetNoNumberSend.shouldBe(Condition.visible).click();
        } else {
            targetNoNumberSend.shouldBe(Condition.visible).click();
        }
    }

    /** Методы выбора радио-баттонов "Цель без скорости" */
    public void selectTargetNoSpeedSkip(){
        if (alertInterception.is(Condition.visible)) {
            sendSecurityCodeInterception();
            disableInterception();
            targetNoSpeedSkip.shouldBe(Condition.visible).click();
        } else {
            targetNoSpeedSkip.shouldBe(Condition.visible).click();
        }
    }

    public void selectTargetNoSpeedSend(){
        if (alertInterception.is(Condition.visible)) {
            sendSecurityCodeInterception();
            disableInterception();
            targetNoSpeedSend.shouldBe(Condition.visible).click();
        } else {
            targetNoSpeedSend.shouldBe(Condition.visible).click();
        }
    }

    /** Метод включения/выключения "Категория ТС" */
    public void onOffCategoryTs(boolean enabled){
        OnOffCheckBox(enabled, setCategoryTs);
    }

    /** Метод включения/выключения "Марки/модели" */
    public void onOffMarkModel(boolean enabled){
        OnOffCheckBox(enabled, setMarkModel);
    }

    /** Метод включения/выключения "на обзорной фотографии" */
    public void onOffViewPhoto(boolean enabled){
        OnOffCheckBox(enabled, setForViewPhoto);
    }

    /** Метод включения/выключения "на увеличенной фотографии" */
    public void onOffBigPhoto(boolean enabled){
        OnOffCheckBox(enabled, setForBigPhoto);
    }



    /** Методы выбора маркеров */
    public void selectNoViewMarker(){
        setMarker.shouldBe(Condition.visible).click();
        noViewMarker.shouldBe(Condition.visible).click();
    }
    public void selectCrossMarker(){
        setMarker.shouldBe(Condition.visible).click();
        crossMarker.shouldBe(Condition.visible).click();
    }
    public void selectFrameMarker(){
        setMarker.shouldBe(Condition.visible).click();
        frameMarker.shouldBe(Condition.visible).click();
    }

    /** Сохранение настроек фиксации */
    public void saveFixationSettings(){
        saveButtonFixationSettings.shouldBe(Condition.visible).click();
    }


    /** Методы ввода секретного кода */
    public void sendSecurityCodeInterception(){
        securityCodeInputInterception.shouldBe(Condition.visible).setValue(SECURITY_CODE);
        nextButtonInterception.shouldBe(Condition.visible).click();
    }

    public void sendSecurityCodeImageSize(){
        securityCodeInputImageSize.shouldBe(Condition.visible).setValue(SECURITY_CODE);
        nextButtonImageSize.shouldBe(Condition.visible).click();
    }

    /** Включение или выключение перехвата */
    public void enableInterception() {
        if (alertInterception.is(Condition.hidden)) {
            buttonOnInterception.shouldBe(Condition.visible).click();
        } else {
            buttonOffInterception.shouldBe(Condition.visible).click();
            buttonOnInterception.shouldBe(Condition.visible).click();
        }
    }

    public void disableInterception(){
        if (alertInterception.is(Condition.visible)) {
            buttonOffInterception.shouldBe(Condition.visible).click();
        } else {
            buttonOnInterception.shouldBe(Condition.visible).click();
            buttonOffInterception.shouldBe(Condition.visible).click();
        }
    }

    public void checkDisableInterception(){
        buttonOffInterception.shouldNotBe(Condition.visible);
        buttonOnInterception.shouldBe(Condition.visible);
        alertInterception.shouldNotBe(Condition.visible);
    }

    public void checkEnableInterception(){
        buttonOnInterception.shouldNotBe(Condition.visible);
        buttonOffInterception.shouldBe(Condition.visible);
        alertInterception.shouldBe(Condition.visible);
    }

    /** Методы ввода размера для изображения */
    public void sendValueImageSize0(String size){
        clearAndType(inputImageSize0, size);
    }

    public void sendValueImageSize1(String value){
        clearAndType(inputImageSize1, value);
    }

    public String getValueImageSize0(){
        return inputImageSize0.shouldBe(Condition.visible).getValue();
    }

    public String getValueImageSize1(){
        return inputImageSize1.shouldBe(Condition.visible).getValue();
    }

    /** Сохранение размера изображения */
    public void saveImageSize(){
        mockConfirm();
        saveButtonImageSize.shouldBe(Condition.visible).click();
    }

    /** Проверка наличия сообщения о перезагрузке */
    public void viewAlertForReboot(){
        alertForReboot.shouldBe(Condition.visible);
    }

    public void viewAlertForNegativeImageSize(String value){
        checkMessageVisible("Размер изображения не входит в допустимый диапазон: 0, 4096 - 8388608 (" + value +")");
    }

    /** xpath */
    private final SelenideElement setModeRadar = $x("//select[@name='radar_direction']");
    private final SelenideElement workingModeRadarAny = $x("//select[@name='radar_direction']/option[@value='ANY']");
    private final SelenideElement workingMOdeRadarOncoming = $x("//select[@name='radar_direction']/option[@value='ONCOMING']");
    private final SelenideElement workingMOdeRadarOutgoing = $x("//select[@name='radar_direction']/option[@value='OUTGOING']");
    private final SelenideElement targetNoNumberSkip = $x("//label[@for='tarskip']");
    private final SelenideElement targetNoNumberSkipOnlyOne = $x("//label[@for='taronlyone']");
    private final SelenideElement targetNoNumberSend = $x("//label[@for='tarsend']");
    private final SelenideElement targetNoSpeedSkip = $x("//label[@for='tarwithsp']");
    private final SelenideElement targetNoSpeedSend = $x("//label[@for='tarwithoutsp']");

    private final SelenideElement setCategoryTs = $x("//label[@for='use_tspeed2']");
    private final SelenideElement setMarkModel = $x("//label[@for='detect_vehicle_mark']");
    private final SelenideElement setForViewPhoto = $x("//label[@for='marker_road']");
    private final SelenideElement setForBigPhoto = $x("//label[@for='marker_target']");

    private final SelenideElement setMarker = $x("//select[@name='marker_type']");
    private final SelenideElement noViewMarker = $x("//select[@name='marker_type']/option[1]");
    private final SelenideElement crossMarker = $x("//select[@name='marker_type']/option[2]");
    private final SelenideElement frameMarker = $x("//select[@name='marker_type']/option[3]");

    private final SelenideElement saveButtonFixationSettings = $x("//span/button[@type='submit']");

    private final SelenideElement securityCodeInputInterception = $x("//form[@name='frmSecret1']//input[@name='security_code']");
    private final SelenideElement nextButtonInterception = $x("//input[@form='frmSecret1'] ");
    private final SelenideElement buttonOnInterception = $x("//button[@name='interception' and text()='Включить режим «Перехват»']");
    private final SelenideElement buttonOffInterception = $x("//button[@name='interception' and text()='Выключить режим «Перехват»']");

    private final SelenideElement securityCodeInputImageSize = $x("//form[@name='formImageSize']//input[@name='security_code']");
    private final SelenideElement inputImageSize0 = $x("//input[@name='image_size[0]']");
    private final SelenideElement inputImageSize1 = $x("//input[@name='image_size[1]']");
    private final SelenideElement nextButtonImageSize = $x("//td[@colspan='2']//input[@type='submit']");
    private final SelenideElement saveButtonImageSize = $x("(//input[@type='submit' and @class='fbutton'])[last()]");
}
