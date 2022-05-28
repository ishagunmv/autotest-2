## Описание проекта <a name="header"></a>

* [Структура проекта](#structure)
    * [Используемый стек](#structure_tech)
    * [Дерево страниц](#structure_page)
    * [Дерево тестов](#structure_test)
    * [Описание директорий проекта](#structure_package)
    * [Удаленный сервер и контейнеризация](#structure_jenkins)
* [Запуск тестов](#settings)
* [Создание тестов и страниц](#create_test_page)
    * [Класс описывающий страницу](#create_page)
    * [Класс описывающий тесты](#create_test)
* [Добавление тестовых пакетов](#create_test_suite)

### Структура проекта <a name="structure"></a>
Проект спроетирован по паттерну [PageObject](https://kreisfahrer.gitbooks.io/selenium-webdriver/content/page_object_pattern_arhitektura_testovogo_proekta/ispolzovanie_patterna_page_object.html). Данный шаблон проектирования помогает работать с отдельными элементами страниц, что позволяет уменьшить количество кода и его поддержку.  
`Веб-страница` ---> `класс описывающий страницу` ---> `тесты для этой страницы`.  
Имя класса описывающую страницу и имя тестового класса для данной страницы должны быть идентичными, с одной лишь разницой - для страниц имя заканчивается на `Page`, для тестов на `Test`. Если на странице присутствует `pop-up`, то для него создается отдельный описывающий класс, название которого заканчивается на `Popup`.

##### Используемый стек <a name="structure_tech"></a>
* версия языка `java-1.8`
* сборщик `maven`
* тесты `junit5`
* управление веб-драйвером `selenide`
* ssh-менеджер `jsch`
* отчеты `allure`

##### Дерево страниц <a name="structure_page"></a>
* `src/main/pages`
    * `/webinterface/{страница}` - пример [LoginPage.java](src/main/java/pages/webinterface/LoginPage.java)
    * `/webapps/{страница}`
        * `/popup/{поп-ап}`

##### Дерево тестов <a name="structure_test"></a>
* `src/test/java/tests`
    * `/general/{тесты}` - пример [LoginTest.java](src/test/java/tests/general/LoginTest.java)
    * `/arch/`
        * `/webintarface/{тесты}`
        * `/webapps/{тесты}`

##### Описание директорий проекта <a name="structure_package"></a>
| Директория | Полный путь                                                | Описание                                                                                                                                                |
|------------|------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------|
| `common`   | [src/main/java/common](src/main/java/common)               | Основные настройки проекта. Жизненный цикл тестов.                                                                                                      |
| `data`     | [src/main/java/data](src/main/java/data)                   | Тестовые данные. Классы перечислений Enums.                                                                                                             |
| `pages`    | [src/main/java/pages](src/main/java/pages)                 | Содержит классы описания страниц. Вложенные директории: `base`- содержит базовый `Page` класс. `webinterface`, `webapps` - содержат все классы страниц. |
| `ssh`      | [src/main/java/ssh](src/main/java/ssh)                     | Директория с работой ssh-менеджера.                                                                                                                     |
| `arch`     | [src/test/java/tests/arch](src/test/java/tests/arch)       | Основная директория тестов для каждой архитектуры. Внутри `package` для каждой архитектуры с разделением на `webinterface` и `webapps`.                 |
| `base`     | [src/test/java/tests/base](src/test/java/tests/base)       | Содержит базовый класс `BaseTest`.                                                                                                                      |
| `general`  | [src/test/java/tests/general](src/test/java/tests/general) | Директория для общих тестовых классов, которые можно гонять на всех архитектурах.                                                                       |
| `suite`    | [src/test/java/tests/suite](src/test/java/tests/suite)     | Содержит классы тестовых пакетов для архитектур. Именно тестовые пакеты запукаются при прогоне какой-либо архитектуры.                                  |

##### Удаленный сервер и контейнеризация <a name="structure_jenkins"></a>
Для автоматизации прогонов архитектур используется [jenkins](http://192.168.2.99:8080/), а для запуска в контейнерах - [selenoid](http://192.168.2.92:8080/#/). Для параметров запуска заведен отдельный статический класс [JenkinsParameters](src/main/java/data/Constant.java). Каждая архитектура имеет свои параметры запуска, которые можно поменять в один момент на `jenkins`.
Сервер и контейнеры постоянно обновляются и имеют последние стабильные версии браузеров и пакетов.

[В начало](#header)

### Запуск тестов <a name="settings"></a>
Конфиг для запуска тестов: [Config.java](src/main/java/common/Config.java). Все значения в конфиге установлены для прогонов через `jenkins`.
Перед тем как запускать тесты на локальном компьютере, необходимо установить нужные значения, в зависимости от ваших требований.

| Параметр               | Тип        | Описание                                                                                                                                                                                                                    |
|------------------------|------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `DEVICE_IP`            | `String`   | Выбор ip прибора для тестов.                                                                                                                                                                                                |
| `START_SELENOID`       | `Boolean`  | Запуск тестов в доккер контейнерах на удаленном сервере `selenoid` или запуск на локальной машине                                                                                                                           |
| `BROWSER`              | `String`   | Доступные варианты: `chrome`, `firefox`, `opera`, `edge` и т.д. На локальной машине можно выбрать любой, в зависимости от того, что у вас установлено. Для удаленного запуска доступны только `chrome`, `firefox`, `opera`. |
| `DROP_KRIS_SQL`        | `Boolean`  | Сбрасывает базу. Бекап базы делается всегда перед каждым запусков тестов, независимо от выбора.                                                                                                                             |
| `RESTORE_KRIS_SQL`     | `Boolean`  | Восстанавливает базу по окончанию тестов (Дает возможность возвращать приборы в состояние "До тестов").                                                                                                                     |
| `CLEAR_COOKIES`        | `Boolean`  | Очистка куки и данных после каждого теста.                                                                                                                                                                                  |
| `HOLD_BROWSER_OPEN`    | `Boolean`  | Держать ли браузер открытым после завершения теста. Полезная функция, когда производится дебагинг теста.                                                                                                                    |
| `CLEAR_REPORTS_DIR`    | `Boolean`  | Очистка директории `build/reports/tests` в которой хранятся allure-отчеты и скриншоты заваленных тестов. Полезно когда накопилось много мусора.                                                                             |
| `TIMEOUT_ELEMENTS_MS`  | `int`      | Таймаут ожидания элемента, если не может выполниться условие.                                                                                                                                                               |
| `TIMEOUT_PAGE_LOAD_MS` | `int`      | Таймаут ожидания загрузки страницы, если не может выполниться условие.                                                                                                                                                      |

[В начало](#header)

### Создание тестов и страниц <a name="create_test_page"></a>
На проекте пишутся интеграционные тесты.  
При создании тестов или страниц нужно учитывать, что все классы описывающие страницы и popup наследуются от [BasePage](src/main/java/pages/base/BasePage.java), а тестовые классы наследуются от [BaseTest](src/test/java/tests/base/BaseTest.java). Все методы необходимо писать в классе для описания страниц, а в тестовом классе только их вызывать. 

##### Класс описывающий страницу <a name="create_page"></a>
Имена для классов страниц используются аналогичные названию ссылки. Например: Страница `"Фотофиксация"` ---> `config_fixing.php` ---> `configFixingPage.java`.  
Перед тем как создать новый класс страницы, необходимо убедиться, что данный класс отсутствует в `pages`. Затем необходимо ее добавить в перечисляемый класс `EPages` в директории `data`. Дать имя, добавить прямую ссылку, добавить локатор `xpath`.
Теперь необходимо создать `java` класс для этой страницы в определенной директории `pages`. Дать имя `EPages()` + `Page.java` и наследуемся от класса [BasePage](src/main/java/pages/base/BasePage.java).  
Готово, теперь можно заполнять данную страницу локаторами и методами.

###### Пример
```java
package pages.webinterface;
import pages.base.BasePage;

public class SystemRebootPage extends BasePage {
}
```

##### Класс описывающий тесты <a name="create_test"></a>
Имена для тестовых классов используются аналогичными pages, но заканчивают свое название на `Test`. Необходимо определиться с архитектурой для которой пишутся тесты, создать `java` класс и наследоваться от [BaseTest](src/test/java/tests/base/BaseTest.java).
Нужно будет добавить аннотацию `junit`, которой наследуется жизненный цикл теста `@ExtendWith(Listener.class)`. Дополнительно нужны будут аннотации, которые будут содержать краткое описание класса, для отчетов.

###### Пример
```java
package tests.general;

import common.Listener;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.extension.ExtendWith;
import tests.base.BaseTest;

@Feature("Tests to check authorization for login page")
@Story("positive and negative scenario")
@ExtendWith(Listener.class)

public class LoginTest extends BaseTest {
}
```
[В начало](#header)

### Добавление тестовых пакетов <a name="create_test_suite"></a>
Все пакеты тестов лежат в директории [suite](src/test/java/tests/suite).
Для того, чтобы добавить новый пакет тестов, необходимо добавить класс, с соответствующим названием. В данный момент пакеты у нас разбиты по архитектурам приборов.
В классе необходимо указать директории или классы которые будет включать в себя пакет.

###### Пример
```java
package tests.suite;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@SelectPackages({
        "tests.general",
        "tests.arch.x86_64"
})
@Suite

public class X86_64Suite {
}
```
Затем в файле [pom.xml](pom.xml) необходимо его добавить в плагин `maven-surefire` в тег `include`.
```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-surefire-plugin</artifactId>
  <version>3.0.0-M6</version>
  <configuration>
    <includes>
      <include>X86_64Suite.java</include>
      <include>AriaSuite.java</include>
      <include>ArteriaSuite.java</include>
    </includes>
  </configuration>
</plugin>
```
Запуск пакета осуществляется передачей параметра в maven `-Dtest={имя пакета}`. Например команда для старта пакета может выглядеть так: `mvn clean test -Dtest=X86_64Suite`.

[В начало](#header)