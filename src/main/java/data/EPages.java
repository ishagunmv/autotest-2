package data;

public enum EPages {

    /** Программы */
    fixation                ("Фотофиксация ТС","app_common.php#/device-page/fixation", "//dd/a[contains(text(), 'Фотофиксация ТС')]"),
    app_cctv                ("Видеонаблюдение","app_cctv.php", "//dd/a[contains(text(), 'Видеонаблюдение')]"),
    poisk                   ("Поиск", "app_common.php#/device-page/poisk", "//dd/a[contains(text(), 'Поиск')]"),

    /** Журналы и базы */
    journalA                ("Зафиксированные ТС", "journalA.php", "//dd/a[contains(text(), 'Зафиксированные ТС')]"),
    journalV                ("Нарушения ПДД", "journalV.php", "//dd/a[contains(text(), 'Нарушения ПДД')]"),
    journalVideo2           ("Видеоархив", "journalVideo2.php", "//dd/a[contains(text(), 'Видеоархив')]"),
    app_licnumdatabase      ("Базы розыска", "app_licnumdatabase.php", "//dd/a[contains(text(), 'Базы розыска')]"),

    /** Настройки */
    app_sfp                 ("Оборудование", "app_sfp.php", ""),
    config_lens             ("Камера", "config_lens.php", "//dd/a[contains(text(), 'Камера')]"),
    app_motorization        ("Моторизация", "app_motorization.php", ""),
    config_fixing           ("Фотофиксация", "config_fixing.php" ,"//dd/a[@href='config_fixing.php' and contains(text(), 'Фотофиксация')]"),
    app_mounting            ("Монтаж устройства", "app_mounting.php", "//dd/a[contains(text(), 'Монтаж устройства')]"),
    app_traffic_lanes       ("Полосы движения", "app_traffic_lanes.php", "//dd/a[contains(text(), 'Полосы движения')]"),
    system_firmware         ("Прошивка устройства", "system_firmware.php", "//dd/a[contains(text(), 'Прошивка устройства')]"),
    system_reboot           ("Перезагрузка системы", "system_reboot.php", "//dd/a[contains(text(), 'Перезагрузка системы')]");

    public String getUrlPage() {
        return url;
    }

    public String getNamePage() {
        return name;
    }

    public String getXpathPage() {
        return xpath;
    }

    private String name;
    private String url;
    private String xpath;

    EPages(String name, String url, String xpath) {
        this.name = name;
        this.url = url;
        this.xpath = xpath;
    }
}
